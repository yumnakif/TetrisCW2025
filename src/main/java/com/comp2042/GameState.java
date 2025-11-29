package com.comp2042;

import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

public class GameState {
    private final PausePanel pausePanel;
    private final GameOverPanel gameoverPanel;
    private boolean isPause=false;
    private Score score;

    public GameState(StackPane rootStackPane){
        pausePanel=new PausePanel();
        gameoverPanel=new GameOverPanel();
        score=new Score();

        rootStackPane.getChildren().addAll(pausePanel.getOverlay(),gameoverPanel.getOverlay());
        pausePanel.bindSizeTo(rootStackPane);
        gameoverPanel.bindSizeTo(rootStackPane);
        gameoverPanel.setScore(score);
        pausePanel.hide();
        gameoverPanel.hide();
    }

    public Score getScore(){
        return score;
    }
    public void pauseRestart(Runnable run){
        pausePanel.setOnRestart(run);
    }
    public void pauseMainMenu(Runnable run){
        pausePanel.setOnMainMenu(run);
    }

    public void pauseToggle(Button button){
        if(isPause){
            pausePanel.hide();
            if (button != null) button.setText("Pause");
            isPause = false;
        } else {
            pausePanel.show();
            if (button != null) button.setText("Resume");
            isPause= true;
        }
    }

    public boolean isPaused(){
        return isPause;
    }

    public void gameOverRestart(Runnable run){
        gameoverPanel.setOnNewGame(run);
    }

    public void gameOverMainMenu(Runnable run){
        gameoverPanel.setOnMainMenu(run);
    }

    public void showGameOver(){
        gameoverPanel.show();
    }
    public void hideGameOver(){
        gameoverPanel.hide();
    }
    public void showPause(){
        pausePanel.show();
    }
    public void hidePause(){
        pausePanel.hide();
    }
    public void resetScore(){
        score.reset();
    }

}
