package com.comp2042.logic.levels;

import com.comp2042.logic.board.Board;


/**
 * Level implementation that increases game speed based on the current level
 *
 */
public class SpeedLevel implements Level{
    private final int levelNumber;
    private final double increaseRate;


    /**
     * Constructs SpeedLevel with specified level number and speed increase rate
     * @param levelNumber the current level number
     * @param increaseRate the multiplier by which speed increases per level
     */
    public SpeedLevel(int levelNumber, double increaseRate){
        this.levelNumber=levelNumber;
        this.increaseRate=increaseRate;
    }


    /**
     * Calculates the speed multiplier for the level
     * The speed increases exponentially based on the level number
     * @return the speed multiplier for the current level
     */
    @Override
    public double getSpeedMultiplier(){
        return Math.pow(increaseRate,levelNumber-1);
    }


    /**
     * Get the current level number
     * @return current level
     */
    @Override
    public int getLevelNumber() {
        return levelNumber;
    }

    /**
     * Indicates whether the level contain static obstacles
     * @return false
     */
    @Override
    public boolean hasStaticObstacles() {
        return false;
    }

    /**Placeholder method for placing static objects
     * @param board the game board
     */
    @Override
    public void placeStaticObject(Board board) {
    }

    /**
     * Indicates whether the level contains fog effect
     * @return false
     */
    @Override
    public boolean hasFog() {
        return false;
    }

    /**
     * Indicates whether the level contain wind effect
     * @return false
     */
    @Override
    public boolean hasWind() {
        return false;
    }

    /**
     * Placeholder method for applying wind effect
     * @param board game board
     * @return false
     */
    @Override
    public boolean applyWind(Board board) {
        return false;
    }

    /**
     * Indicates whether the level contain dynamic obstacles
     * @return false
     */
    @Override
    public boolean hasDynamicObstacles() {
        return false;
    }

    /**
     * Placeholder method for placing dynamic obstacles
     * @param board game board
     */
    @Override
    public void startDynamicObstacles(Board board) {
    }

    /**
     * Placeholder method for stopping obstacles
     */
    @Override
    public void stopObstacles() {
    }

    /**
     * Placeholder method to indicate whether this level includes reversed controls
     * @return false
     */
    @Override
    public boolean hasReverseControl() {
        return false;
    }

    /**
     * Indicates whether controls are swapped in this level.
     * @return false
     */
    @Override
    public boolean isSwapped() {
        return false;
    }

    /**
     * Swaps key functions for reverse control levels
     */
    @Override
    public void swapKeys(){
    }

    /**
     * Stop the control swapping timeline and reset to normal controls
     */
    @Override
    public void stopSwapKeys(){
}

}
