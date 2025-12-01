package com.comp2042.logic.levels;

import com.comp2042.Board;
import com.comp2042.MoveEvent;

public class SpeedLevel implements Level{
    private final int levelNumber;
    private final double increaseRate;

    public SpeedLevel(int levelNumber, double increaseRate){
        this.levelNumber=levelNumber;
        this.increaseRate=increaseRate;
    }
    @Override
    public double getSpeedMultiplier(){
        return Math.pow(increaseRate,levelNumber-1);
    }

    @Override
    public int getLevelNumber() {
        return levelNumber;
    }

    @Override
    public boolean hasStaticObstacles() {
        return false;
    }

    @Override
    public void placeStaticObject(Board board) {
    }

    @Override
    public boolean hasFog() {
        return false;
    }

    @Override
    public boolean hasWind() {
        return false;
    }

    @Override
    public boolean applyWind(Board board) {
        return false;
    }

    @Override
    public boolean hasDynamicObstacles() {
        return false;
    }

    @Override
    public void startDynamicObstacles(Board board) {

    }

    @Override
    public void stopObstacles() {

    }

    @Override
    public boolean hasReverseControl() {
        return false;
    }

    @Override
    public boolean isSwapped() {
        return false;
    }

}
