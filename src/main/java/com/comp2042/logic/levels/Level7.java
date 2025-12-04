package com.comp2042.logic.levels;

import com.comp2042.Board;

/**
 * Level 7 implementation with a "wind" effect that pushes the brick left or right randomly
 * Extends SpeedLevel with periodic wind effect affecting brick movement.
 */
public class Level7 extends SpeedLevel{
    private long lastWind=0;
    private static final long WIND_INTERVAL=2000;
    private int direction=0;

    /**
     * Create Level 7 with specified level number and speed increase rate.
     * @param levelNumber  level number (7)
     * @param increaseRate Rate at which game speed increases
     */
    public Level7(int levelNumber,double increaseRate){
        super(levelNumber,increaseRate);
    }

    /**
     * Indicate that this level does not have static obstacles
     * @return false
     */
    @Override
    public boolean hasStaticObstacles(){
        return false;
    }

    /**
     * Indicates that this level does not contain fog effect
     * @return false
     */
    @Override
    public boolean hasFog(){
        return false;
    }

    /**
     * Indicates the level has a wind effect
     * @return true
     */
    @Override
    public boolean hasWind(){
        return true;
    }

    /**
     * Applies wind effect to the current brick periodically
     * wind changes directions every 2 seconds and has a 20% chance to move the brick
     *
     * @param board the game board containing the current brick
     * @return true if wind successfully moved the brick, otherwise return false
     */
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

    /**
     * Indicates the level has no dynamic obstacles
     * @return false
     */
    @Override
    public boolean hasDynamicObstacles(){
        return false;
    }
}