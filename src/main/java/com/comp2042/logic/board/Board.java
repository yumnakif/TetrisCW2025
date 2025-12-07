package com.comp2042.logic.board;


import com.comp2042.ui.ViewData;

/**
 * Represents the game board interface for the tetris game
 * Defines the main operations for manipulating the bricks, managing game state and retrieving board information
 *
 * @see SimpleBoard
 */
public interface Board {

    /**
     * Attempt to move the brick down one cell
     * @return {@code true} if the brick moved down successfully
     *         {@code false} if movement would cause a collision
     */
    boolean moveBrickDown();

    /**
     Attempt to move the brick left one cell
     * @return {@code true} if the brick moved left successfully
     *         {@code false} if movement would cause a collision
     */
    boolean moveBrickLeft();

    /**
     Attempt to move the brick right one cell
     * @return {@code true} if the brick moved right successfully
     *         {@code false} if movement would cause a collision
     */
    boolean moveBrickRight();

    /**
     * Attempt to rotate the brick 90 degrees clockwise
     * if it causes a collision, the rotation is reversed
     * @return {@code true} if the brick rotated successfully
     *         {@code false} if rotation would cause a collision
     */
    boolean rotateBrick();

    /**
     Creates a new brick at the top center of the board.
     *
     * @return {@code true} if the new brick immediately collides (game over),
     *         {@code false} if the brick can be placed
     */
    boolean createNewBrick();

    /**
     * Return current board matrix
     * @return @2D array representing game board state
     */
    int[][] getBoardMatrix();

    /**
     * Gets view data required for the current game state.
     * Includes the current brick, its position, and the brick object
     * @return {@link ViewData} containing rendering information
     */
    ViewData getViewData();

    /**
     *Merges the current brick into background game matrix
     * Called when a brick can no longer move down
     */
    void mergeBrickToBackground();

    /**
     * Gets information about the next brick that will appear.
     * Used for displaying next brick preview
     * @return {@link NextShapeInfo} containing the next brick's matrix and object
     */
    NextShapeInfo getNextShape();

    /**
     * Check for and remove anu completed rows from the board
     * Update the game matrix and returns information about cleared row
     * @return {@link ClearRow} object containing the updated matrix and clear row count
     */
    ClearRow clearRows();

    /**
     * Returns the score tracker for the game.
     * @return {@link Score} object that is tracking game points
     */
    Score getScore();

    /**
     * Reset the game to initial state
     * Clears the board, resets the score, creates a new starting brick
     */
    void newGame();
}
