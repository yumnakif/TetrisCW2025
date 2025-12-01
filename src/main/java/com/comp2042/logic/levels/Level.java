package com.comp2042.logic.levels;

import com.comp2042.Board;
import com.comp2042.GuiController;
import com.comp2042.MoveEvent;

public interface Level {
    /**
     * Returns the falling speed multiplier and level number.
     * normal speed is 1.0
     * The speed increases according to the level
     */
    double getSpeedMultiplier();
    int getLevelNumber();
    boolean hasStaticObstacles();
    void placeStaticObject(Board board);
    boolean hasFog();

    boolean hasWind();
    boolean applyWind(Board board);

    boolean hasDynamicObstacles();
    void startDynamicObstacles(Board board);
    void stopObstacles();
    boolean hasReverseControl();
    boolean isSwapped();
}
