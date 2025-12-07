package com.comp2042.logic.board;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Utility class for matrix operations in the game
 * Handles collision detection, matrix merging, row clearing and matrix copying
 * All methods are static, so this class cannot be instantiated
 */
public class MatrixOperations {


    /**
     * Private constructor to prevent instantiation of utility class
     */
    private MatrixOperations(){

    }

    /**
     * Checks if a brick collides with the game board at specified position
     * @param matrix the game board matrix
     * @param brick brick shape matrix
     * @param x x coordinate for brick placement
     * @param y y coordinate for brick placement
     * @return true if collision detected, false if the placement is valid
     */
    public static boolean intersect(final int[][] matrix, final int[][] brick, int x, int y) {
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + j;
                int targetY = y + i;
                if (brick[i][j] != 0) {
                    boolean outOfBound=checkOutOfBound(matrix,targetX,targetY);
                    if(outOfBound|| (!outOfBound && matrix[targetY][targetX]!=0)){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * Check if coordinates are outside the matrix boundaries
     * @param matrix game board matrix
     * @param targetX x coordinate to check
     * @param targetY y coordinate to check
     * @return tru if coordinates are out of bound, false otherwise
     */
    private static boolean checkOutOfBound(int[][] matrix, int targetX, int targetY) {

        if(targetY<0 || targetY>= matrix.length){
            return true;
        }
        if(targetX<0||targetX>=matrix[targetY].length){
            return true;
        }
        return false;
        }

    /**
     * Create a deep copy of 2D integer array
     * @param original matrix to copy
     * @return new independent copy of the matrix
     */
    public static int[][] copy(int[][] original) {
        int[][] myInt = new int[original.length][];
        for (int i = 0; i < original.length; i++) {
            int[] aMatrix = original[i];
            int aLength = aMatrix.length;
            myInt[i] = new int[aLength];
            System.arraycopy(aMatrix, 0, myInt[i], 0, aLength);
        }
        return myInt;
    }

    /**
     * Merge brick into the game board matrix at specified position
     * @param filledFields the current board matrix
     * @param brick brick shape matrix
     * @param x x coordinate for brick placement
     * @param y y coordinate for brick placement
     * @return new matrix with brick merger into it
     */
    public static int[][] merge(int[][] filledFields, int[][] brick, int x, int y) {
        int[][] copy = copy(filledFields);
        for (int i = 0; i < brick.length; i++) {
            for (int j = 0; j < brick[i].length; j++) {
                int targetX = x + j;
                int targetY = y + i;
                if (brick[i][j] != 0) {
                    copy[targetY][targetX] = brick[i][j];
                }
            }
        }
        return copy;
    }

    /**
     * Check and remove completed rows from the game board
     * @param matrix current board matrix
     * @return ClearRow object containing lines removed, new matrix and score bonus
     */
    public static ClearRow checkRemoving(final int[][] matrix) {
        int[][] tmp = new int[matrix.length][matrix[0].length];
        Deque<int[]> newRows = new ArrayDeque<>();
        List<Integer> clearedRows = new ArrayList<>();

        for (int i = 0; i < matrix.length; i++) {
            int[] tmpRow = new int[matrix[i].length];
            boolean rowToClear = true;
            for (int j = 0; j < matrix[0].length; j++) {
                if (matrix[i][j] == 0) {
                    rowToClear = false;
                }
                tmpRow[j] = matrix[i][j];
            }
            if (rowToClear) {
                clearedRows.add(i);
            } else {
                newRows.add(tmpRow);
            }
        }
        for (int i = matrix.length - 1; i >= 0; i--) {
            if (!newRows.isEmpty()){
                tmp[i]= newRows.pollLast();
            }else{
                tmp[i]=new int[matrix[0].length];
                Arrays.fill(tmp[i],0);
            }
        }
        int scoreBonus = 50 * clearedRows.size() * clearedRows.size();
        return new ClearRow(clearedRows.size(), tmp, scoreBonus);
    }
}
