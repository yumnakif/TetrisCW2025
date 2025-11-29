package com.comp2042.logic.levels;

import com.comp2042.Board;

public class Level4 extends SpeedLevel{

    public Level4(int levelNumber, double increaseRate) {
        super(levelNumber, increaseRate);
    }
   @Override
    public boolean hasStaticObstacles(){
        return true;
    }

    @Override
    public boolean hasDynamicObstacles(){
        return false;
    }
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

}
