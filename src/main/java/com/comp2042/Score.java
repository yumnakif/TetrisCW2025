package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.prefs.Preferences;

/**
 * Manages game scoring, high score persistence and game over messages
 * Uses Javafx properties for observable values and Preferences for high score storage
 */
public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty highScore=new SimpleIntegerProperty(0);
    private final StringProperty gameOverMessage= new SimpleStringProperty("");
    private final Preferences pref;

    /**
     * Creates a score tracker with high score loaded from persistent storage
     */
    public Score(){
        pref=Preferences.userNodeForPackage(Score.class);
        loadHighScore();
    }

    /**
     * Gets the observable high score property
     * @return IntegerProperty which can be bound to a UI element
     */
    public IntegerProperty highScoreProperty() {
        return highScore;
    }

    /**
     * Gets the observable game over message property
     * @return StringProperty containing formatted game over text
     */
    public StringProperty gameOverTextProperty() {
        return gameOverMessage;
    }

    /**
     * Gets the observable current score property
     * @return IntegerProperty that can be bound to UI element
     */
    public IntegerProperty scoreProperty() {
        return score;
    }

    /**
     * Adds points to the current score
     * @param i number of points to add
     */
    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    /**
     * reset the score to 0
     */
    public void reset() {
        score.setValue(0);
    }

    /**
     * Sets the game over message to display the current score and high score
     * Compares current score and high score to display New high score message if the current score exceeds high score
     */
    public void setGameOverMessage(){
        if(score.get()>highScore.get()){
            highScore.set(score.get());
            saveHighScore();
            gameOverMessage.set("NEW HIGH SCORE!\nHigh Score: " + highScore.get()+ "\nYour Score: "+score.get());
        }else{
            gameOverMessage.set("Your Score:" +score.get()+"\nHigh Score:" + highScore.get());
        }
    }

    /**
     * Loads high score from persistent storage (Preference)
     */
    private void loadHighScore() {
        highScore.set(pref.getInt("highScore",0));
    }

    /**
     * saves high score to persistent storage
     */
    private void saveHighScore(){
        pref.putInt("highScore",highScore.get());
    }
}
