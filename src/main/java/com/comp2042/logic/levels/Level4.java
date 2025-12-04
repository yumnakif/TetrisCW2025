package com.comp2042.logic.levels;

import com.comp2042.Board;

/**
 * Level 4 implementation of static obstacles
 * Extends SpeedLevel and places random static obstacles on the board */
public class Level4 extends SpeedLevel{

    /**
     * Creates level 4 with specified level number and speed increase rate
     * @param levelNumber the level number (4)
     * @param increaseRate rate at which game speed increases
     */
    public Level4(int levelNumber, double increaseRate) {
        super(levelNumber, increaseRate);
    }

    /**
     * Indicate that static obstacles are implemented in this level
     * @return true
     */
   @Override
    public boolean hasStaticObstacles(){
        return true;
    }

    /**
     * @return false to show this level does not contain dynamic obstacles
     */
    @Override
    public boolean hasDynamicObstacles(){
        return false;
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

}
