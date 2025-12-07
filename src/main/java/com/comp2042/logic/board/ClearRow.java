package com.comp2042.logic.board;


/**
 * Show the result of clearing completed rows from the game board
 * Contains information about number of lines removed, updated board state and the score bonus earned
 */
public final class ClearRow {

    private final int linesRemoved;
    private final int[][] newMatrix;
    private final int scoreBonus;


    /**
     * Constructs a ClearRow object with the specified fields
     * @param linesRemoved the number of rows cleared
     * @param newMatrix the updated matrix after clearing the rows
     * @param scoreBonus the score bonus earned from clearing the rows
     */
    public ClearRow(int linesRemoved, int[][] newMatrix, int scoreBonus) {
        this.linesRemoved = linesRemoved;
        this.newMatrix = newMatrix;
        this.scoreBonus = scoreBonus;
    }

    /**
     * Gets the number of rows that were cleared
     * @return the count of cleared rows
     */
    public int getLinesRemoved() {
        return linesRemoved;
    }

    /**
     * Gets the new board matrix after clearing the complete rows
     * @return a copy of the 2D array representing the board after row clearance
     */
    public int[][] getNewMatrix() {
        return MatrixOperations.copy(newMatrix);
    }

    /**
     * Gets the score bonus earned from clearing rows
     * @return the bonus points
     */
    public int getScoreBonus() {
        return scoreBonus;
    }
}
