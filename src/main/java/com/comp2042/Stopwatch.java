package com.comp2042;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class Stopwatch {
    private final IntegerProperty seconds= new SimpleIntegerProperty(0);
    private Timeline timeline;

    public Stopwatch(){
        timeline=new Timeline();
        timeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1), e-> {
                seconds.set(seconds.get() + 1);
            }));
        timeline.setCycleCount(Timeline.INDEFINITE);
    }

    public void bindtoLabel(Label label){
        label.textProperty().bind(seconds.map(s->{
            int sec=s.intValue();
            int min=sec/60;
            int seconds= sec%60;
            return String.format("%02d:%02d",min,seconds);
        }));
    }


    public void start() {
        timeline.play();
    }

    public void stop() {
        timeline.stop();
    }

    public void reset() {
        seconds.set(0);
    }

    public void restart(){
        stop();
        reset();
        start();
    }
}

