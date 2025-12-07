package com.comp2042.logic.levels;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import java.util.*;

/**
 * Manages game level progression and transitions between different level types
 * Handles switching levels and cleaning previous level effects
 */
public class LevelController {
    private int currentLevel=1;
    private final double increaseRate =1.05;
    private Level level;
    private Level previousLevel;
    private IntegerProperty levelProperty=new SimpleIntegerProperty(1);
    private Random random=new Random();

    /**
     * Creates a LevelManager starting at level 1 with standard speed increase
     */
    public LevelController(){
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
     * Higher levels include more challenges incrementally, from the challenge pool
     */
    public void nextLevel(){
        previousLevel=level;
        currentLevel++;
        int challengeCount= challengeCount(currentLevel);

        Set<LevelType> levels= randomChallenge(challengeCount);

        level=new Challenges(currentLevel,increaseRate,levels);

        cleanupLevels(previousLevel,level);
        levelProperty.set(currentLevel);
    }

    /**
     * Calculates the number of challenges based on the current level
     * the number of challenges possible increase by one every few levels, with maximum number of challenges being 4 after level 12
     * @param currentLevel the level to calculate challenges for
     * @return the number of challenges for the level
     */
    private int challengeCount(int currentLevel) {
        if(currentLevel<=2){
            return 0;
        }
        if(currentLevel<=5){
            return 1;
        }
        if(currentLevel<=8){
            return 2;
        }
        if(currentLevel<=12){
            return 3;
        }
        return 4;
    }


    /**
     * Selects random challenges for the available pool of challenge types without duplicates
     * @param challengeCount number of challenges to select
     * @return set of randomly selected LevelType challenges
     */
    private Set<LevelType> randomChallenge(int challengeCount) {
        Set<LevelType> challenges=new HashSet<>();
        List<LevelType> levels=getChallenges(currentLevel);
        challengeCount=Math.min(challengeCount,levels.size());

        while(challenges.size()<challengeCount && !levels.isEmpty()){
            int index=random.nextInt(levels.size());
            LevelType levelType=levels.get(index);
            challenges.add(levelType);
            levels.remove(index);
        }
        return challenges;
    }
    /**
     * Get the challenge type available at the specified level
     * Different challenge types are unlocked at different level thresholds
     * @param currentLevel the current level to get the available challenges for
     * @return list of LevelType available for the current level
     */
    private List<LevelType> getChallenges(int currentLevel) {
        List<LevelType> challenges=new ArrayList<>();

        if(currentLevel>=2){
            challenges.add(LevelType.FOG);
        }
        if(currentLevel>=3){
            challenges.add(LevelType.STATIC_OBSTACLES);
        }
        if(currentLevel>=5){
            challenges.add(LevelType.DYNAMIC_OBSTACLES);
        }
        if(currentLevel>=7){
            challenges.add(LevelType.WIND);
        }
        if(currentLevel>=9){
            challenges.add(LevelType.REVERSE_CONTROLS);
        }
        return challenges;
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
    public Level getPreviousLevel(){
        return previousLevel;
    }

    /**
     * Resets to level 1 and stop any active level effects
     */
    public void reset(){
        if (level.hasDynamicObstacles() || level.hasReverseControl()) {
            level.stopObstacles();
            level.stopSwapKeys();
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
                lastLevel.stopSwapKeys();
            }
    }
}
