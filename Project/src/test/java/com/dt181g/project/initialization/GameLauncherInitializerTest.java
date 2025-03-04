package com.dt181g.project.initialization;

import static org.junit.jupiter.api.Assertions.assertSame;

import org.junit.jupiter.api.Test;

import com.dt181g.project.controller.LauncherController;

public class GameLauncherInitializerTest {
    @Test
    void testGameLauncherInitializerOnlyProducesOneController() {
        // Act
        LauncherController controller1 = GameLauncherInitializer.INSTANCE.createAndReturnLauncherController();
        LauncherController controller2 = GameLauncherInitializer.INSTANCE.createAndReturnLauncherController();

        // Assert
        assertSame(controller1, controller2);
    }
}
