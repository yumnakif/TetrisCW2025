package com.comp2042.logic.levels;

import com.comp2042.Board;
import com.comp2042.EventSource;
import com.comp2042.EventType;
import com.comp2042.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Currency;


/**
 * Level 10 implementation with left and right keys switching
 * Left and right keys switch periodically every 3 to 8 seconds
 * */
public class Level10 extends SpeedLevel {
    private Timeline swapTimeline;
    private boolean swapped=false;

    /**
     * Creates Level 10 with specified level number and speed increase rate.
     * @param levelNumber the level number (should be 10)
     * @param increaseRate rate at which game speed increases
     */
    public Level10(int levelNumber, double increaseRate) {
        super(levelNumber, increaseRate);
    }

    /**
     * Indicates that this level reverses key controls periodically
     * @return true
     */
    @Override
    public boolean hasReverseControl(){
        return true;
    }

    /**
     * Indicates that this level does not contain static obstacles
     * @return false
     */
    @Override
    public boolean hasStaticObstacles(){
        return false;
    }
    /**
     * Indicates the level has no fog effect
     * @return false
     */
    @Override
    public boolean hasFog(){
        return false;
    }
    /**
     * Indicates the level has no wind
     * @return false
     */
    @Override
    public boolean hasWind(){
        return false;
    }
    /**
     * Indicates the level has no dynamic obstacles
     * @return false
     */
    @Override
    public  boolean hasDynamicObstacles(){
        return false;
    }

    /**
     * return the value of swapped: true or false
     * @return
     */
    @Override
    public boolean isSwapped(){
        return swapped;
    }
    /**
     * Starts the control swapping timeline
     * Swaps controls every 3 to 8 seconds randomly
     * @param board The game board
     */
    @Override
    public void startDynamicObstacles(Board board){
        stopObstacles();
        swapTimeline=new Timeline(new KeyFrame(Duration.seconds(3+ Math.random()*5),e->swapped=!swapped));

        swapTimeline.setCycleCount(Timeline.INDEFINITE);

        swapTimeline.play();

        if(Math.random()>0.5){
            swapped=!swapped;
        }
    }

    /**
     * Stop the control swapping timeline and reset to normal controls
     */
    @Override
    public void stopObstacles(){
        if(swapTimeline!=null){
            swapTimeline.stop();
            swapTimeline=null;
        }
        swapped=false;
    }
}
