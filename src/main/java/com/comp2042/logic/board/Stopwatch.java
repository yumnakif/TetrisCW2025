package com.comp2042.logic.board;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Game stopwatch tracking elapsed play time
 * Uses Javafx timeline to count seconds and format as MM:SS
 */
public class Stopwatch {
    private final IntegerProperty seconds= new SimpleIntegerProperty(0);
    private Timeline timeline;

    /**
     * Creates a stopwatch with 1 second interval
     */
    public Stopwatch(){
        timeline=new Timeline();
        timeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1), e-> {
                seconds.set(seconds.get() + 1);
            }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    /**
     * Bind stopwatch time to a label for display
     * Format seconds as MM:SS
     * @param label the label to display formatted time
     */
    public void bindtoLabel(Label label){
        label.textProperty().bind(seconds.map(s->{
            int sec=s.intValue();
            int min=sec/60;
            int seconds= sec%60;
            return String.format("%02d:%02d",min,seconds);
        }));
    }

    /**
     * start the stopwatch
     */
    public void start() {
        timeline.play();
    }

    /**
     * stop the stopwatch
     */
    public void stop() {
        timeline.stop();
    }

    /**
     * Reset the stopwatch
     */
    public void reset() {
        seconds.set(0);
    }
    /**
     * Restart the stopwatch
     */
    public void restart(){
        stop();
        reset();
        start();
    }
}

