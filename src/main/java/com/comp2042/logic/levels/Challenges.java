package com.comp2042.logic.levels;

import com.comp2042.logic.board.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

import java.util.HashSet;
import java.util.Set;

public class Challenges extends SpeedLevel{
    private Timeline obstacleTimeline;
    private boolean obstacleVisible=false;
    private int[][] currentObstacles;
    private Timeline swapTimeline;
    private boolean swapped=false;

    private final Set<LevelType> levelChallenges;
    /**
     * Constructs SpeedLevel with specified level number and speed increase rate
     *
     * @param levelNumber  the current level number
     * @param increaseRate the multiplier by which speed increases per level
     */
    public Challenges(int levelNumber, double increaseRate, Set<LevelType> levels) {
        super(levelNumber, increaseRate);
        this.levelChallenges = new HashSet<>(levels);
    }

    @Override
    public boolean hasStaticObstacles(){
        return levelChallenges.contains(LevelType.STATIC_OBSTACLES);
    }

    @Override
    public boolean hasWind(){
        return levelChallenges.contains(LevelType.WIND);
    }

    @Override
    public boolean hasDynamicObstacles(){
        return levelChallenges.contains(LevelType.DYNAMIC_OBSTACLES);
    }
    @Override
    public boolean hasReverseControl(){
        return levelChallenges.contains(LevelType.REVERSE_CONTROLS);
    }
    @Override
    public boolean hasFog(){
        return levelChallenges.contains(LevelType.FOG);
    }

    /**
     * Places static gray obstacles randomly on the board
     * Creates 2 blocks in random position. Set value to 8 to set the color
     * @param board  the game board to place obstacles on
     */
    @Override
    public void placeStaticObject(Board board){
        int[][] matrix=board.getBoardMatrix();
        for(int i=0; i<2; i++){
            int row= matrix.length/2 +(int)(Math.random()*4) -2;
            int col=(int)(Math.random()* matrix[0].length);

            if(row>=0 && row< matrix.length && col>=0 &&col<matrix[0].length){
                matrix[row][col]=8;
            }
        }
    }

    /**
     * Applies wind effect to the current brick periodically
     * wind changes directions every 2 seconds and has a 20% chance to move the brick
     *
     * @param board the game board containing the current brick
     * @return true if wind successfully moved the brick, otherwise return false
     */
    @Override
    public boolean applyWind(Board board){
        int direction=0;
        long lastWind=0;
        long currentTime=System.currentTimeMillis();
        if(currentTime-lastWind>2000){
            direction=(int) (Math.random()*3)-1;
            lastWind=currentTime;
        }

        if(Math.random()<0.2&& direction!=0) {
            boolean moved = false;
            if (direction > 0) {
                moved = board.moveBrickRight();
            } else if (direction < 0) {
                moved = board.moveBrickLeft();
            }
            return moved;
        }
        return false;
    }
    /**
     * Start the dynamic obstacle animation cycle
     * create a timeline that toggles obstacles to change positions every 10 seconds
     * @param board the game board to place obstacles on
     */
    @Override
    public void startDynamicObstacles(Board board){
        stopObstacles();
        obstacleTimeline=new Timeline(new KeyFrame(Duration.seconds(10), e->{
            int[][] matrix=board.getBoardMatrix();
            if(obstacleVisible){
                removeObstacles(matrix,currentObstacles);
                currentObstacles=null;
            }
            else{
                placeDynamicObstacle(board);
            }
            obstacleVisible= !obstacleVisible;
    }));

        obstacleTimeline.setCycleCount(Timeline.INDEFINITE);
        obstacleTimeline.play();
    }

    /**
     * Stops the dynamic obstacle animation and removes all obstacles.
     */
    @Override
    public void stopObstacles(){
        if(obstacleTimeline!=null){
            obstacleTimeline.stop();
            obstacleTimeline=null;
        }
        obstacleVisible=false;
        currentObstacles=null;
    }
    /**
     * Removes all gray obstacles of value 8 from the board.
     * @param matrix the game board matrix
     * @param currentObstacles array that tracks obstacle positions
     */
    private void removeObstacles(int[][] matrix, int[][] currentObstacles) {
        if(currentObstacles!=null){
            for(int i=0;i<currentObstacles.length; i++){
                int row=currentObstacles[i][0];
                int col=currentObstacles[i][1];

                if(row>=0&& row<matrix.length &&col>=0 && col<matrix[0].length){
                    if(matrix[row][col]==8){
                        matrix[row][col]=0;
                    }
                }
            }
            for(int i=0; i<matrix.length;i++){
                for(int j=0; j<matrix[0].length;j++){
                    if(matrix[i][j]==8){
                        matrix[i][j]=0;
                    }
                }
            }
        }
    }

    /**
     * Places 3 new dynamic obstacles in random positions in the upper half of the board
     * @param board the game board to place obstacles on
     */
    private void placeDynamicObstacle(Board board) {
        int[][] matrix= board.getBoardMatrix();
        if(currentObstacles!=null){
            removeObstacles(matrix, currentObstacles);
        }
        currentObstacles=new int[3][2];

        for(int i=0; i<3;i++){
            int max=matrix.length/2;
            int row=4 + (int) (Math.random() * (max-4));
            int col=(int) (Math.random() * (matrix[0].length));

            if(matrix[row][col]==0){
                matrix[row][col]=8;
                currentObstacles[i][0]=row;
                currentObstacles[i][1]=col;
            }
            obstacleVisible=true;
        }
    }

    /**
     * Starts the control swapping timeline
     * Swaps controls every 3 to 8 seconds randomly
     */
    @Override
    public void swapKeys(){

        if(swapTimeline != null){
            swapTimeline.stop();
        }
        swapTimeline=new Timeline(new KeyFrame(Duration.seconds(3+ Math.random()*5), e->{
            swapped=!swapped;
        }));
        swapTimeline.setCycleCount(Timeline.INDEFINITE);
        swapTimeline.play();
        swapped = Math.random() > 0.5;
    }
    /**
     * Indicates whether controls are swapped in this level.
     * @return false
     */
    @Override
    public boolean isSwapped() {
        return swapped;
    }

    /**
     * Stop the control swapping timeline and reset to normal controls
     */
    @Override
    public void stopSwapKeys(){
        if(swapTimeline!=null){
            swapTimeline.stop();
            swapTimeline=null;
        }
        swapped=false;
    }

}
