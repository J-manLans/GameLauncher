package com.dt181g.project.support;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

/**
 * Singleton class for managing audio playback within the application.
 * This class handles loading, playing, and managing sound effects by implementing
 * the Producer/Consumer pattern, offloading the actual preloading of sounds to a
 * background thread so the clients can return to doing their thing, ensuring that
 * audio resources are ready for playback once needed.
 *
 * <p>The audio manager uses a singleton pattern to provide a single instance
 * for sound management throughout the application lifecycle. It supports pre
 * loading of sound effects and keeps audio active to prevent playback issues.</p>
 *
 * @author Joel Lansgren
 */
public enum AudioManager {
    INSTANCE;
    private final Map<String, Clip> soundEffects = new HashMap<>();
    private final BlockingQueue<String> soundEffectQueue = new LinkedBlockingQueue<>();
    private final Thread soundConsumerThread = new SoundConsumer("SoundConsumer");
    private final ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor(r -> {
        return new Thread(r, "AudioManager-Scheduler");
    });
    private final Clip silence = loadSound(AppConfig.SOUND_EFFECT_SILENCE);
    private final AtomicBoolean isSoundLoaded = new AtomicBoolean();
    private CountDownLatch latch = new CountDownLatch(1);

    /** Constructs the AudioManager and start up the consumer thread that is in charge of preloading sounds. */
    private AudioManager() {
        soundConsumerThread.start();
    }

    /**
     * This is the Consumer in the Consumer/Producer pattern.
     *
     * <p>It simply removes a sound from the queue if one is
     * available and adds it to the map for later retrieval
     * and playback by threads, when a end string is recognized it means
     * all sound files have been loaded and the consumer sets the isSoundLoaded
     * boolean to true to signal game threads they can play sounds and countdown
     * the latch to release the EDT so it can continue and display the game, otherwise
     * it patiently waits until the application is shutdown.</p>
     *
     * @author Joel Lansgren
     */
    private class SoundConsumer extends Thread {

        private SoundConsumer(String name) { super(name); }

        @Override
        public void run() {
            try {
                while (true) {
                    final String soundEffect = soundEffectQueue.take(); // Blocking call: waits if queue is empty.

                    if (soundEffect.equals(AppConfig.SOUND_EFFECTS_LOADED)) {
                        isSoundLoaded.set(true); // A signal to game threads they can playback sound effects
                        latch.countDown();  // Count down the latch and releases the EDT if it's waiting.
                    } else {
                        soundEffects.put(soundEffect, loadSound(soundEffect));
                    }
                }
            } catch (final InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    /**
     * This method allows clients (producers) to add a sound to the queue to be consumed by the soundConsumerThread.
     * The thread then preloads the sound so its ready for playback when needed.
     *
     * <p> When all sounds have been loaded from the client's side, an end string will be provided.
     * this is used to coordinate complete loading before letting the client move on and
     * display the game.</p>
     * @param soundEffect The last part of the path to the sound-file.
     */
    public void queueSoundEffect(final String soundEffect) {
        if (isSoundLoaded.get()) { return; } // Early return if sound is loaded.

        try {
            soundEffectQueue.put(soundEffect); // Blocking call: waits if another thread is accessing the queue.
        } catch (final InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (soundEffect.equals(AppConfig.SOUND_EFFECTS_LOADED)) {
            while (!isSoundLoaded.get()) {
                try {
                    latch.await();
                } catch (final InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
            }
        }
    }

    /**
     * Loads a sound file and returns a Clip object representing the audio.
     * @param soundFile the last part of the path to the sound file to be loaded
     * @return a Clip object containing the loaded sound, or null if an error occurs
     */
    private Clip loadSound(final String soundFile) {
        // The getClass().getResourceAsStream() is essential to ensure that sound files can be
        // properly accessed and loaded from within a JAR file, the BufferedInputStream provides
        // smoother playback for reacuring sounds.
        try (InputStream is = new BufferedInputStream(
            getClass().getResourceAsStream(AppConfig.PATH_TO_SOUNDS + soundFile)
        )) {
            final AudioInputStream audioStream = AudioSystem.getAudioInputStream(is);
            final Clip clip = AudioSystem.getClip();
            clip.open(audioStream); // Make the clip available for playback.
            return clip;
        } catch (final Exception e) {
            DebugLogger.INSTANCE.logException(e);
        }
        return null; // Just return null if operation fails.
    }

    /**
     * Plays a specified pre loaded sound effect.
     * @param soundEffect the key identifying the sound effect to be played
     */
    public void playSoundEffect(final String soundEffect) {
        playSoundEffect(soundEffects.get(soundEffect));
    }

    /**
     * Keeps the audio system alive by periodically playing a silent sound.
     * This is useful for preventing issues with sound playback in some environments.
     * This scheduler is alive the entirety of the launchers lifecycle while clips
     * concerning games are shutdown by the launcher as the game is shutdown.
     */
    public void keepAudioAlive() {
        scheduler.scheduleAtFixedRate(() -> {
            playSoundEffect(silence);
        }, 0, 5, TimeUnit.SECONDS);
    }

    /**
     * Plays a given Clip object from the beginning.
     * @param clip the Clip object representing the sound to be played
     */
    private void playSoundEffect(final Clip clip) {
        clip.setFramePosition(0); // Reset the clip to the start.
        clip.start(); // Plays the clip.
    }

    /*==============================
    * Shutdown
    ==============================*/

    /**
     * Cleans up audio resources.
     *
     * <p>This is done by closing all loaded sound clips, clearing the sound effects map, resetting the
     * IsSoundLoaded boolean and latch.</p>
     *
     * <p> This method should be invoked by the {@link com.dt181g.project.controller.LauncherController}
     * every time a game is shut down to ensure that only relevant audio resources are stored in memory.
     * This helps prevent memory leaks and ensures that audio clips are not lingering in the system
     * after they are no longer needed.</p>
     */
    public void shutdownAudio() {
        soundEffects.values().forEach(Clip::close);
        soundEffects.clear();
        isSoundLoaded.set(false);
        latch =  new CountDownLatch(1);
    }
}

