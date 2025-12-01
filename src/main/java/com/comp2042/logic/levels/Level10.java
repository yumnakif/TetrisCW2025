package com.comp2042.logic.levels;

import com.comp2042.Board;
import com.comp2042.EventSource;
import com.comp2042.EventType;
import com.comp2042.MoveEvent;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.Currency;

public class Level10 extends SpeedLevel {
    private Timeline swapTimeline;
    private boolean swapped=false;
    public Level10(int levelNumber, double increaseRate) {
        super(levelNumber, increaseRate);
    }

    @Override
    public boolean hasReverseControl(){
        return true;
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
        return false;
    }
    @Override
    public  boolean hasDynamicObstacles(){
        return false;
    }
    @Override
    public boolean isSwapped(){
        return swapped;
    }

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

    @Override
    public void stopObstacles(){
        if(swapTimeline!=null){
            swapTimeline.stop();
            swapTimeline=null;
        }
        swapped=false;
    }
}
