package com.comp2042.logic.levels;

import com.comp2042.logic.board.Board;

/**
 * Defines the functions for game level implementations in the challenge system
 */
public interface Level {

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
    void swapKeys();
    void stopSwapKeys();


}
