package com.comp2042.logic.levels;

import com.comp2042.Board;

public class Level7 extends SpeedLevel{
    private long lastWind=0;
    private static final long WIND_INTERVAL=2000;
    private int direction=0;

    public Level7(int levelNumber,double increaseRate){
        super(levelNumber,increaseRate);
    }

    @Override
    public boolean hasStaticObstacles(){
        return false;
    }
    @Override
    public boolean hasFog(){
        return false;
    }

    @Override
    public boolean hasWind(){
        return true;
    }

    @Override
    public boolean applyWind(Board board){
        long currentTime=System.currentTimeMillis();
        if(currentTime-lastWind>WIND_INTERVAL){
            direction=(int) (Math.random()*3)-1;
            lastWind=currentTime;
        }

        if(Math.random()<0.2&& direction!=0) {
            boolean moved = false;
            if (direction > 0) {
                moved = board.moveBrickRight();
            } else if (direction < 0) {
                moved = board.moveBrickLeft();
            }
            return moved;
        }
        return false;
    }

    @Override
    public boolean hasDynamicObstacles(){
        return false;
    }
}