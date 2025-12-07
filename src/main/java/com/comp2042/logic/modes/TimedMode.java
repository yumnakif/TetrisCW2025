package com.comp2042.logic.modes;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.util.Duration;

/**
 * Sets up the functions required in a new time mode game
 */
public class TimedMode {
    private final IntegerProperty timeRemaining = new SimpleIntegerProperty(90);
    private Timeline countdownTimer;
    private Runnable onGameEnd;
    private boolean gameRunning = false;


    /**
     * Starts the countdown timer. The timer begins at 60 seconds and decreases each second
     * When timer reaches zero the game ends {@code onGameEnd} is called
     */
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

    /**
     * Stops the countdown timer and resets the gameRunning flag
     */
    public void stop() {
        if (countdownTimer != null) {
            countdownTimer.stop();
        }
        gameRunning = false;
    }

    /**
     * Pauses the countdown timer when the user pauses the game while keeping the current time remaining
     * Only effects if the timer is running
     */
    public void pause() {
        if (countdownTimer != null && gameRunning) {
            countdownTimer.pause();
        }
    }

    /**
     * Resumes the countdown timer if it is paused, continuing from the current time remaining
     * Only effects if the timer is not running
     */
    public void resume() {
        if(countdownTimer != null && gameRunning) {
            countdownTimer.play();
        }
    }

    /**
     * Resets the timer back to 60 seconds
     */
    public void reset(){
        stop();
        timeRemaining.set(60);
    }

    /**
     * Binds the time remaining to a label to update the UI automatically
     * Formats the time into MM:SS format
     * @param label the UI label to display the time
     */
    public void bindTimer(Label label){
        label.textProperty().bind(timeRemaining.map(time->{
            int mins=(int)time/60;
            int seconds=(int)time%60;
            return String.format("%02d:%02d",mins,seconds);
        }));
    }

    /**
     * Set the display for when the game is ended
     * @param onGameEnd Runnable
     */
    public void setOnGameEnd(Runnable onGameEnd) {
        this.onGameEnd = onGameEnd;
    }

}
