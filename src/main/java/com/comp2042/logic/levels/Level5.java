package com.comp2042.logic.levels;

import com.comp2042.Board;

/**
 * Level 5 implementation with for overlay effect
 * Extend SpeedLevel with a translucent gray overlay
 */
public class Level5 extends SpeedLevel{

    /**
     * Create level 5 with specified level number and speed increase rate
     * @param levelNumber level 5
     * @param increaseRate speed increase rate
     */
    public Level5(int levelNumber, double increaseRate) {
        super(levelNumber, increaseRate);
    }

    /**
     * Indicates the level has no static obstacles
     * @return false
     */
    @Override
    public boolean hasStaticObstacles(){
        return false;
    }

    /**
     * Indicates the level has a fog effect
     * @return true
     */
    @Override
    public boolean hasFog(){
        return true;
    }

    /**
     * Indicates the level has no dynamic obstacles
     * @return false
     */
    @Override
    public boolean hasDynamicObstacles(){
        return false;
    }
}
