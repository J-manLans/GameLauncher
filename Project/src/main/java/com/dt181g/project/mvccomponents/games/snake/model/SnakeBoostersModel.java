package com.dt181g.project.mvccomponents.games.snake.model;

import com.dt181g.project.mvccomponents.BaseModel;

public interface SnakeBoostersModel extends BaseModel {

    void eatBooster(SnakeModel snakeModel);
    int[][] getBooster();
    int getBoosterColor();
    boolean isActive();
}
