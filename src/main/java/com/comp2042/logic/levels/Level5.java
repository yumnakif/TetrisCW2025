package com.comp2042.logic.levels;

import com.comp2042.Board;

public class Level5 extends SpeedLevel{

    public Level5(int levelNumber, double increaseRate) {
        super(levelNumber, increaseRate);
    }

    @Override
    public boolean hasStaticObstacles(){
        return false;
    }

    @Override
    public boolean hasFog(){
        return true;
    }
    @Override
    public boolean hasDynamicObstacles(){
        return false;
    }
}
