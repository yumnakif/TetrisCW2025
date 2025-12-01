package com.comp2042.logic.levels;

import com.comp2042.Board;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

public class LevelManager {
    private int currentLevel=1;
    private final double increaseRate =1.15;
    private Level level;
    private Level previousLevel;
    private IntegerProperty levelProperty=new SimpleIntegerProperty(1);

    public LevelManager(){
        level = new SpeedLevel(currentLevel, increaseRate);
        previousLevel=level;
    }
    public IntegerProperty getLevelProperty(){
        return levelProperty;
    }
    public void nextLevel(){
        previousLevel=level;
        currentLevel++;
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

    public Level getCurrentLevel(){
        return level;
    }
    public Level getPreviousLevel(){return previousLevel;}
    public void reset(){
        if (level.hasDynamicObstacles() || level.hasReverseControl()) {
            level.stopObstacles();
        }
        previousLevel=level;
        currentLevel=1;
        level=new SpeedLevel(currentLevel,increaseRate);
        levelProperty.set(currentLevel);
    }
    private void cleanupLevels(Level lastLevel, Level newLevel){
            if(lastLevel.hasDynamicObstacles()&& !newLevel.hasDynamicObstacles()){
                lastLevel.stopObstacles();
            }
            if(lastLevel.hasReverseControl()&& !newLevel.hasReverseControl()){
                lastLevel.stopObstacles();
            }
    }
}
