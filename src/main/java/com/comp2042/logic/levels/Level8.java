package com.comp2042.logic.levels;

import com.comp2042.Board;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.util.Duration;

public class Level8 extends SpeedLevel{
    private Timeline obstacleTimeline;
    private boolean obstacleVisible=false;
    private int[][] currentObstacles;

    public Level8(int levelNumber, double increaseRate){
        super(levelNumber,increaseRate);
    }

    @Override
    public boolean hasDynamicObstacles(){
        return true;
    }

    @Override
    public void startDynamicObstacles(Board board){
        stopObstacles();
        obstacleTimeline=new Timeline(new KeyFrame(Duration.seconds(10),e-> toggleObstacle(board)));

        obstacleTimeline.setCycleCount(Timeline.INDEFINITE);
        obstacleTimeline.play();
        placeStaticObject(board);
    }

    @Override
    public void stopObstacles(){
        if(obstacleTimeline!=null){
            obstacleTimeline.stop();
            obstacleTimeline=null;
        }
        obstacleVisible=false;
        currentObstacles=null;
    }

    private void toggleObstacle(Board board){
        int[][] matrix=board.getBoardMatrix();
        if(obstacleVisible){
            removeObstacles(matrix,currentObstacles);
            currentObstacles=null;
        }
        else{
            placeDynamicObstacle(board);
        }
        obstacleVisible= !obstacleVisible;
    }

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
    @Override
    public boolean hasStaticObstacles(){
        return false;
    }
    @Override
    public boolean hasFog(){
        return false;
    }

}
