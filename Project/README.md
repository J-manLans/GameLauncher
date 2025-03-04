# Final Project

## Environment & Tools
### Hardware
- Processor: AMD Ryzen 7 5800H - 3.20 GHz
- Installed RAM: 16,0 GB
- System type: 64-bit operating system, x64-based processor - Windows 11
- NVIDIA GeForce RTX 3060 Laptop GPU
- NVMe SAMSUNG MZVLQ512 - 475GB
### Software
- **Vs Code** version 1.97.2
- **Git** version 2.45.1.windows.1
- **Git Bash**
- **Bitbucket**
- **Apache Maven** version 3.9.6
- **Windows**
    - Edition Windows 11 Home
    - Version 23H2
    - OS build 22631.4169
    - Experience Windows Feature Experience Pack 1000.22700.1034.0
- **Java**:
    - openjdk version "21.0.2" 2024-01-16 LTS
    - OpenJDK Runtime Environment Temurin-21.0.2+13 (build 21.0.2+13-LTS)
    - OpenJDK 64-Bit Server VM Temurin-21.0.2+13 (build 21.0.2+13-LTS, mixed mode, sharing)

## Purpose
The overarching aim of this project is to build upon and implement the snake game aspect of the game launcher that was created in lab3, while still adhere to the MVC principles about packaging and communication between entities in the pattern. The snake game is the same old concept most are familiar with - you control a snake and eat pellets to grow and increase your speed, the target is to grow as long as possible. This version aims to, on top of that, implement a booster mechanic that shall be easy to extend with different kind of boosters. For this project though, only a cherry and speed booster will be available, where the former is the original pellet that increase both length and speed and the latter boosts the speed for a shorter duration of time. Other than this the concrete goals below also needs to be met.

### Concrete goals
- All graphical setup and updates shall take place on the Event Dispatch Thread (EDT)
- Utilize at least 5 unique Swing components
- Utilize at least 3 unique Swing layouts
- All resources must be included in the project's resource bundle, and running `mvn clean verify` should generate a functioning JAR with these resources properly included
- Have at least three concurrent processes
- Ensure at least two instances of synchronization mechanisms to coordinate between concurrent processes.
- At least one use case of Streams API
- Implement at least 4 of the design patterns we have touched upon in the course
- The MVC pattern shall be strictly followed. This entails:
    - All entities containing this design should be packaged accordingly
    - No direct communication between views and models, instead actions needs to be routed through a controller, and all communication back to the controller should be performed via listeners or callbacks
    - Views and Models should have no notion about each others or the controllers existence

## Procedures
### Controlling the snake
The first step in this lab was to continue developing the snake model to accept key-presses for control of directions At the time, the snake was only moving right in an infinite loop. For this intent, a `CardinalDirectionListener` listener was created as a separate class in the controller since it handles communication between the view - who takes key-inputs, and the model, who sets the direction based on that. It was implemented with reusability in mind so other games with similar movement mechanics can utilize it. It takes a reference to a method in the controller which delegates to the snake how to set its direction. When a key is pressed, the `KeyCode` is retrieved from the event and the method reference is used as a callback utilizing the `Consumer` functional interface from Java's `java.util.function` package, which accepts one argument in its `accept` method. This listener is registered on the `gameGrid` of the `SnakeSinglePlayerView`, allowing it to be notified whenever keys are pressed, following the typical **Observer Pattern** approach.

The argument passed is a `Direction` enum, which resides in the model package to keep input handling separate from game logic. This design simplifies future changes. For instance, if the controller method were to change from a keyboard to another input device, `Direction.UP` would remain consistent, unlike the direct use of `KeyEvent.VK_UP`. In addition to that the `ordinal` method of the enum that return the position of the constant as an int can be used to detect opposite directions, which comes very handy in the snake game.

The `setDirection` method in the controller who is the end receiver just set the recorded direction as pending. This direction will then be checked in the `updateSnakeData` method that runs each game-tick to prevent illegal moves with the `ordinal` method mentioned above, if the move is allowed, `currentDirection` is updated with the pending one and the direction is checked in a switch expression to create the new head as a `Point` that takes a `x` and `y` coordinate based on the old head. This new head is passed into the `checkCollision` helper method to detect if the snake collides with itself or consumes a booster. Afterwards, the head is added to the end of the deque, and the tail is removed unless a booster was consumed. After this a callback is used to update the view with the new snake.

### Detecting Collision
Next, collision detection needs to be implemented. To check if the snake collides with itself, the new head is compared with the existing elements in the deque. If the new head is already in the deque, the game ends. For boosters, the position of the booster `Point` is checked against the new head, and if a match is found, the booster is consumed, and its effect is applied to the snake. The mechanics for handling boosters involve additional complexity, which requires further explanation.

### Booster Mechanic
![Booster Mechanic](../_RepoResources/img/BoosterSetup.jpg)

#### Boosters
This mechanic will involves a few components as shown by the simplified uml above. The first is the booster models, which are concrete implementations of an abstract base class that extends `Thread`. This design allows for easy sub-classing in different games with booster mechanics. Additionally, the boosters implement the **Strategy Pattern** to dynamically switch behavior at runtime. They also follow the **Template Pattern** with the `run` method which have been made final, where the general flow of the booster's lifecycle (activation, consumption, effect application, sound, and return to the pool) is defined. Concrete subclasses override the `applyEffect` and `playSoundEffect` methods to implement unique functionality for each booster type at the appropriate points in the `run` method.

The concrete boosters communicate with the `SnakeModel` through its interface, `SnakeBoosterTarget`, which defines a contract with methods that allow boosters to interact with the model without exposing its internal state. By not passing this interface to the abstract class, we ensure that the `SnakeModel` instance remains protected from the `BoosterPool` (which couples to the abstract class as the image above shows) and is only accessible by the boosters that directly communicate with it.

The **cherry booster** sets a `grow` boolean to true, which prevents the tail from being removed in the `update` method, causing the snake to grow. This boolean is then reset within the `if` block of the same method. Additionally, the snake's speed is modified by adding a speed multiplier, which is adjusted if a speed booster is active. The active speed booster reduces the speed by 0.1, accounting for its slower effect. The updated speed is then communicated to the controller via an observer-like callback, which adjusts the game loop delay accordingly.

The **speed booster** applies a multiplier that doubles the snake's speed for  7 seconds using a scheduler and a resettable `FutureTask`, which manages the asynchronous task. Using the `cancel` method of the `Future`, the task is aborted if the game ends or the user the back button during play, the boost is reset by restoring the original speed recorded at the start of the effect, factoring in any speed increase from the cherry booster.

These methods are implemented by the snake itself, as it is responsible for how it interacts with the boosters. This approach also helps avoid exposing internal state, eliminating the need for getters and setters.

#### Pool
The `BoosterPool` class, implemented as an enum singleton, is designed to be reused across multiple games with similar mechanics. It provides a `shutdown` method for cleaning up resources and an `initialize` method that takes a list of boosters and adds them to the `boosterPool`. Boosters are dynamically managed, with a separate list of active boosters returned as a `Map` by utilizing `Streams API` to protect internal data.

Once initialized, the pool starts a scheduler to activate boosters at random intervals. A random booster is removed from the pool, assigned a new `Point(x, y)`, and the `Point` is added to a list of occupied coordinates to prevent overlap, this list is used to check if it contains the newly produced `Point` at each cycle, if so the current cycle is aborted. Synchronization is handled using a `boosterLock`, ensuring that only one thread modifies the pool at a time. If the pool is empty, the thread waits until a booster returns, after which it is notified on the `boosterLock` and continues. Other than that a random delay is introduced if the thread has been waiting, to prevent adding a new booster immediately after the previous one is eaten. Checks are also implemented to ensure boosters are not activated while the game is in pause mode before any boosters is activated. This is done before and after the random sleep interval after the wait/sync block and consist of a boolean thats being checked. This boolean is altered by the controller, either at the start of a game, in the case of game over or if the user clicks the `Back` button during gameplay.

### Sound Manager
#### Producer/Consumer
As mentioned earlier, the concrete boosters must implement the abstract `playSoundEffect` method to define their unique functionality within the template method. This is connected to the `SoundManager` singleton, which is responsible for loading and preparing sound effects for playback during gameplay. The process utilizes a `LinkedBlockingQueue` of strings, a `soundConsumerThread`, and a `CountDownLatch` set to 1, all working together to implement a **Producer/Consumer** pattern. In this setup, booster threads add the file path suffix for their sound effects to the `LinkedBlockingQueue`. The `soundConsumerThread` then removes these entries, loads the corresponding sounds prefixing them with the path to the sound folder, prepares them for playback, and stores them in a `Map` where the string is the key, and the loaded `Clip` is the value.

To ensure the game doesn't start before all sound effects are fully loaded, a guarding mechanism is in place. The controller offers a string to signal that all effects have been loaded before the crucial part of the game begins. Once the signal is given, the controller thread is paused by the `CountDownLatch`. When the `soundConsumerThread` retrieves the signal, it sets the `AtomicBoolean` `isSoundLoaded` to true and counts down on the latch. This sequence of actions releases the controller thread, allowing the game to continue starting.

The `soundConsumerThread` is implemented as a private nested class that extends `Thread` and overrides the `run` method. Its implementation consists of a simple while loop, ensuring the thread remains alive. After all sounds are loaded, the thread waits until another game is started or the application is shut down. When the game is exited, all sound effects are closed, the `Map` is cleared, and both the `AtomicBoolean` and the countdown latch are reset, ready for the next session.

#### Keeping alive
In certain environments, it seems like the `javax.sound.sampled` API can experience a slight delay in sound playback if no audio is actively being played, potentially due to the audio system entering an idle state. To address this, the `AudioManager` periodically plays a silent sound using a `ScheduledExecutorService` at a fixed interval of 5 seconds. This approach ensures the audio system remains active, minimizing any delays when actual sound effects are played during gameplay.

#### generate a functioning JAR with sounds properly included for playback
To ensure that sound files are correctly included in the **JAR** when running `mvn clean verify`, the sound files are loaded using `getClass().getResourceAsStream()`. This method retrieves the resources from the JAR file, and the stream is wrapped in a `BufferedInputStream` to optimize reading performance and improve playback efficiency. Additionally, an `AudioInputStream` is created from the resource stream, allowing the `Clip` objects to load and open the audio, making it ready for playback within the application. Once playback begins, the clip's frame position is reset to 0 to ensure it starts from the beginning, and then the clip is played.

### Summary
In summary, the procedure outlines the development of the snake game mechanics, including controlling the snake's movement, detecting collisions, handling boosters, and managing sound effects. The implementation follows design patterns like **Observer**, **Strategy**, **Object Pool**, **Template** and **Producer/Consumer** and ensures a smooth interaction between the game components. The approach is structured to be easily extendable for future enhancements and to provide a clear and functional gameplay experience.

## Discussion
### Purpose Fulfillment
The purpose of this project was to build upon and implement the snake game aspects of the game launcher while adhering to the MVC principles. The aim was to implement base mechanics of the game as well as a booster system ready for extension including two boosters to start with, while taking heed of the intricacies of dealing with multiple threads.

All classes were carefully considered before implementation, with a focus on extensibility and their place within the MVC pattern. The communication between different packages was also a priority. By using callbacks for communication to the controller and leveraging Java’s `java.util.function` package, the `CardinalDirectionListener` could be securely set on the game grid in the game view. Another callback was passed as an observer from the controller to the snake model, allowing for notifications of changes in speed. These callbacks was also used streamlining communication between the `BoosterPool` and `BoosterModel`, passing the `returnBooster` method to the model, so it could be kept private and not risk exposure. Additionally, by coupling the boosters to the `SnakeModel` through an interface, rather than directly to the concrete class, the internal state of the model is protected. This prevents boosters from needing access to the full implementation of the model, maintaining a clean separation of concerns. The decision to implement both the `BoosterPool` and `AudioManager` as singletons within the support package also makes sense. While the `BoosterPool` interacts with model classes, its primary role isn't to manage the model's data state or act as part of the controller. Furthermore, given the global nature of boosters in the game, it is more efficient to have a single instance of these entities, ensuring consistency and streamlined access. This meets the requirements of adhering to the MVC principles outlined in the purpose section.

The patterns employed and described above, **Observer**, **Strategy**, **Object Pool**, **Template** and **Producer/Consumer** also meets the concrete goal of implementing at least 4 of the patterns that has been thought throughout this course. Additionally, a functional factory for producing `IGameController` instances, responsible for spawning the entire MVC hierarchy for games, was implemented using `Supplier<T>`. This allows for dynamic instantiation of different game controllers at runtime. While not one of the traditional factory patterns covered in the course, it still provides a clean and flexible approach to controller instantiation within the MVC structure.

Furthermore, The Streams API has been utilized in both `BoosterPool` and `LauncherModel` for tasks such as creating maps from objects and sorting lists. While these implementations may not be the most advanced, they effectively fulfill the concrete goal of incorporating streams into the project.

When it comes to meet the goal of having at least three concurrent processes and two instances of synchronization, multiple threads are used throughout the system. For example, the two boosters and the `BoosterActivator-Scheduler` run concurrently. Synchronized events occur in several places: in the `BoosterModel`, where threads wait to be notified if they have become active or consumed; in the `BoosterPool`, where the scheduler waits to be notified when a booster is added to the list; and in both `AudioManager` and `GameLauncherInitializer`, where a `CountDownLatch` is used for synchronization. And the process described above about how to produce a working **JAR** with the sound files properly included signifies that that goal have been met as well.

More over, 5 unique swing components and 3 layouts needs to be utilized. And if the `LauncherSideView` is examined one can se that the component goal is met since it houses a ``JButton``, ``JLabel``, ``JPanel``, ``JScrollBar`` and a ``JScrollPane``. It also uses a `Boxlayout`. Then a `CardLayout` is used in the launcher to display and switch between all its views and the same goes for the snake game. Finally `GridbagLayout` have been used in multiple views, and that concludes the goal of utilizing 3 layouts.

To ensure that all graphical setups and updates occur on the EDT, the constructors of all view classes in the launcher are wrapped within the `invokeLater` method, which queues the procedure on the EDT. The `initialize` method in the controller that follows the initial initiation of views and model, responsible for additional UI setup - including setting up listeners - is also wrapped in `invokeLater`. Afterward, all events triggered by listeners, except those originating from the created threads, are executed on the EDT. By keeping UI-related events separated from other tasks, this approach ensures that all graphical updates and interactions are processed on the EDT, maintaining thread safety and proper synchronization within the user interface.

Overall, the project successfully met the goals outlined in the purpose section. While some implementations may have been adapted to fit the framework and might not represent the most optimal approach for this specific project, they still fulfill the required objectives effectively.

### Alternative Approaches
#### BoosterPool
The BoosterPool as it is at the moment is quite tied to the specific booster mechanics of the snake game, primarily focusing on boosters that are activated at random intervals. This makes the pool rigid and unsuitable for other game types that do not rely on the same booster mechanic. To mitigate this the manager could be made more general and specific booster controllers could be employed who dictates the usage of the pool. That way, game-specific logic is decoupled from the object pooling system, allowing the pool to be reused across different game types without needing to be rewritten. In fact, the pool could be remade to just hold objects like enemies, bullets, Tetris blocks, what have you. In such a case it would also make sense to lose the singleton pattern and make it instantiatable, allowing for greater reuse, as the same pool design can be employed for multiple types of objects, each with different needs.

#### AudioManager
The current `AudioManager` design is relatively bare-bones, focusing mainly on implementing the **Producer/Consumer** pattern for the purpose of preloading sounds. While this approach ensures that sound assets are loaded in the background without blocking the game execution, it introduces some limitations. For example, it halts the game controller until the sounds are fully loaded, which somewhat defeats the purpose of background loading. The **Producer/Consumer** pattern is functional for the basic needs of the application but may struggle to scale if the game expands and requires managing a larger number of sounds.

 Despite these weaknesses, the design works well for the basic needs of loading and managing simple sound effects. It provides an efficient way to preload sound resources without blocking the main game flow, and the sound playback functionality is straightforward and sufficient for the current scope.

## Personal Reflections
The process of landing on a design took some time and many iterations, but I think I learned something about building for future expansion at least. And thread management is always good to practice. Think I need some more of it.