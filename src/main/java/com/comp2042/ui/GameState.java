package com.comp2042.ui;

import com.comp2042.logic.board.Score;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;

/**
 * Manages the game states including pause and game over states and score tracking
 * Controls visibility of pause menu and game over panel
 */
public class GameState {
    private final PausePanel pausePanel;
    private final GameOverPanel gameoverPanel;
    private boolean isPause=false;
    private Score score;

    /**
     * Create a GameSate manager with overlay panels
     * @param rootStackPane the root container for overlay panels
     */
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

    /**
     * Sets the restart action for pause panel
     * @param run Runnable to execute when restart button is clicked from pause panel
     */
    public void pauseRestart(Runnable run){
        pausePanel.setOnRestart(run);
    }

    /**
     *Sets the main menu action for pause panel
     * @param run Runnable to execute when the main menu button is clicked
     */
    public void pauseMainMenu(Runnable run){
        pausePanel.setOnMainMenu(run);
    }

    /**
     * Toggle pause state and updates button text when it is pressed
     * @param button the button to update text on
     */
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

    /**
     * Checks if game is currently paused.
     * @return true if game is paused, false if not
     */
    public boolean isPaused(){
        return isPause;
    }

    /**
     * Hide pause menu
     */
    public void hidePause(){
        pausePanel.hide();
    }

    /**
     * Sets the restart action for the game over panel
     * @param run Runnable to execute when new game button is clicked from game over
     */
    public void gameOverRestart(Runnable run){
        gameoverPanel.setOnNewGame(run);
    }

    /**
     * Sets the main menu action for the game over panel
     * @param run Runnable to execute when main  menu is clicked from game over
     */
    public void gameOverMainMenu(Runnable run){
        gameoverPanel.setOnMainMenu(run);
    }

    /**
     * Display game over panel
     */
    public void showGameOver(){
        gameoverPanel.show();
    }

    /**
     * Hide game over panel
     */
    public void hideGameOver(){
        gameoverPanel.hide();
    }

    /**
     * set the elapsed time for game over panel
     * @param elapsedTime
     */
    public void setElapsedTime(String elapsedTime){
        gameoverPanel.setElapsedTime(elapsedTime);
    }


    /**
     * Gets the current game score
     * @return the score object tracking game points
     */
    public Score getScore(){
        return score;
    }

    /**
     * reset game score to zero
     */
    public void resetScore(){
        score.reset();
    }

}
