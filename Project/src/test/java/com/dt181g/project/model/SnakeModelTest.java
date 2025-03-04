package com.dt181g.project.model;


import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

public class SnakeModelTest {
    private final SnakeModel snakeModel = new SnakeModel(this::nullMethod);
    private final Runnable noTask = () -> {};

    void nullMethod(int nada) { }

    @Test
    void testInitializeSnake() {
        snakeModel.initializeSnakeData(noTask);
        assertFalse(snakeModel.getSnake().isEmpty());
    }
}
