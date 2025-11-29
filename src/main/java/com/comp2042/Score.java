package com.comp2042;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

import java.util.prefs.Preferences;

public final class Score {

    private final IntegerProperty score = new SimpleIntegerProperty(0);
    private final IntegerProperty highScore=new SimpleIntegerProperty(0);
    private final StringProperty gameOverMessage= new SimpleStringProperty("");
    private final Preferences pref;

    public Score(){
        pref=Preferences.userNodeForPackage(Score.class);
        loadHighScore();
    }
    public IntegerProperty highScoreProperty() {
        return highScore;
    }

    public StringProperty gameOverTextProperty() {
        return gameOverMessage;
    }

    public IntegerProperty scoreProperty() {
        return score;
    }

    public void add(int i){
        score.setValue(score.getValue() + i);
    }

    public void reset() {
        score.setValue(0);
    }

    public void setGameOverMessage(){
        if(score.get()>highScore.get()){
            highScore.set(score.get());
            saveHighScore();
            gameOverMessage.set("NEW HIGH SCORE!\nHigh Score: " + highScore.get()+ "\nYour Score: "+score.get());
        }else{
            gameOverMessage.set("Your Score:" +score.get()+"\nHigh Score:" + highScore.get());
        }
    }
    private void loadHighScore() {
        highScore.set(pref.getInt("highScore",0));
    }
    private void saveHighScore(){
        pref.putInt("highScore",highScore.get());
    }
}
