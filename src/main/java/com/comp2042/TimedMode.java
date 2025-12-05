package com.comp2042;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

public class TimedMode {
    private final IntegerProperty timeRemaining = new SimpleIntegerProperty(90);
    private Timeline countdownTimer;
    private Runnable onGameEnd;
    private boolean gameRunning = false;

    public void start() {
        if (gameRunning) {
            return;
        }
        gameRunning = true;
        timeRemaining.set(60);
        countdownTimer = new Timeline(
                new KeyFrame(Duration.seconds(1), e -> {
                    int current = timeRemaining.get();
                    if (current > 0) {
                        timeRemaining.set(current - 1);
                    } else {
                        stop();
                        if (onGameEnd != null) {
                            onGameEnd.run();
                        }
                    }
                })
        );
        countdownTimer.setCycleCount(Timeline.INDEFINITE);
        countdownTimer.play();
    }

    public void stop() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        gameRunning = false;

    }

    public void pause() {
        if (countdownTimer != null && gameRunning) {
            countdownTimer.pause();
        }
    }

    public void resume() {
        if(countdownTimer != null && gameRunning) {
            countdownTimer.play();
        }
    }

    public void reset(){
        stop();
        timeRemaining.set(90);
    }

    public void bindTimer(Label label){
        label.textProperty().bind(timeRemaining.map(time->{
            int mins=(int)time/60;
            int seconds=(int)time%60;
            return String.format("%02d:%02d",mins,seconds);
        }));
    }

    public void setOnGameEnd(Runnable onGameEnd) {
        this.onGameEnd = onGameEnd;
    }

    public boolean isGameRunning() {
        return gameRunning;
    }

    public int getTimeRemaining() {
        return timeRemaining.get();
    }

    public IntegerProperty timeRemainingProperty() {
        return timeRemaining;
    }
}
