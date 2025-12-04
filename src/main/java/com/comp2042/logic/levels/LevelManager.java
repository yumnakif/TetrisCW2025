package com.comp2042.logic.levels;

import com.comp2042.Board;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Manages game level progression and transitions between different level types
 * Handles switching levels and cleaning previous level effects
 */
public class LevelManager {
    private int currentLevel=1;
    private final double increaseRate =1.15;
    private Level level;
    private Level previousLevel;
    private IntegerProperty levelProperty=new SimpleIntegerProperty(1);

    /**
     * Creates a LevelManager starting at level 1 with standard speed increase
     */
    public LevelManager(){
        level = new SpeedLevel(currentLevel, increaseRate);
        previousLevel=level;
    }

    /**
     * Gets the observable property for the current level
     * @return IntegerProperty that can be bound to UI elements
     */
    public IntegerProperty getLevelProperty(){
        return levelProperty;
    }

    /**
     * Advances to the next level and creates appropriate level instance
     * Calls the corresponding class according to the level
     * Level 4,5,7,8,10 have special effects, the rest will apply standard speed levels
     */
    public void nextLevel(){
        previousLevel=level;
        currentLevel++;
        Label levelLabel=new Label("Next Level: "+currentLevel);
        levelLabel.setStyle("-fx-font-size:20; -fx-text-fill: gold;");

        switch (currentLevel){
            case 4:
                level=new Level4(currentLevel,increaseRate);
                break;
            case 5:
                level=new Level5(currentLevel,increaseRate);
                break;
            case 7:
                level=new Level7(currentLevel,increaseRate);
                break;
            case 8:
                level=new Level8(currentLevel,increaseRate);
                break;
            case 10:
                level=new Level10(currentLevel,increaseRate);
                break;
            default:
                level=new SpeedLevel(currentLevel,increaseRate);
        }
        cleanupLevels(previousLevel,level);
        levelProperty.set(currentLevel);
    }

    /**
     * Gets the current active level
     * @return current level object
     */
    public Level getCurrentLevel(){
        return level;
    }

    /**
     * Gets the previously active level
     * @return previous level object
     */
    public Level getPreviousLevel(){return previousLevel;}

    /**
     * Resets to level 1 and stop any active level effects
     */
    public void reset(){
        if (level.hasDynamicObstacles() || level.hasReverseControl()) {
            level.stopObstacles();
        }
        previousLevel=level;
        currentLevel=1;
        level=new SpeedLevel(currentLevel,increaseRate);
        levelProperty.set(currentLevel);
    }

    /**
     * Cleans up effects from previous level when transitioning to new level
     * Stop dynamic obstacles or reverse control if new level does not have any
     * @param lastLevel the level being exited
     * @param newLevel the new level being entered
     */
    private void cleanupLevels(Level lastLevel, Level newLevel){
            if(lastLevel.hasDynamicObstacles()&& !newLevel.hasDynamicObstacles()){
                lastLevel.stopObstacles();
            }
            if(lastLevel.hasReverseControl()&& !newLevel.hasReverseControl()){
                lastLevel.stopObstacles();
            }
    }
}
