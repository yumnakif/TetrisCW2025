package com.comp2042;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.BrickOperations;
import com.comp2042.logic.bricks.RandomBrickGenerator;
import javafx.geometry.Point2D;
import java.awt.*;

/**
 * Implementation of the Game board
 * Manages the game grid, current falling brick, scoring and game mechanics
 * The board maintains a matrix showing the game state where the placed bricks are stored
 * Coordinates the movement or rotation of current falling brick.
 *
 * @see Board
 * @see Brick
 */
public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    Brick currentBrick;
    private int[][] currentGameMatrix;
    Point2D currentOffset;
    private final Score score;


    /**
     * Constructs a new game board with the specified dimensions
     * Initializes game matrix, brick generator and score tracker
     * @param width the width of the game board (the number of columns)
     * @param height the height of the game board (the number of rows)
     */
    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        score = new Score();
    }


    /**
     * Attempt to move the brick down one cell
     * @return {@code true} if the brick moved down successfully
     *         {@code false} if movement would cause a collision
     */
    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point2D p = new Point2D(currentOffset.getX(),currentOffset.getY()+1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, currentBrick.getCurrentMatrix(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }


    /**
     Attempt to move the brick left one cell
     * @return {@code true} if the brick moved left successfully
     *         {@code false} if movement would cause a collision
     */
    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point2D p = new Point2D(currentOffset.getX()-1, currentOffset.getY());
        boolean conflict = MatrixOperations.intersect(currentMatrix, currentBrick.getCurrentMatrix(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     Attempt to move the brick right one cell
     * @return {@code true} if the brick moved right successfully
     *         {@code false} if movement would cause a collision
     */
    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point2D p = new Point2D(currentOffset.getX() +1,currentOffset.getY());
        boolean conflict = MatrixOperations.intersect(currentMatrix, currentBrick.getCurrentMatrix(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    /**
     * Attempt to rotate the brick 90 degrees clockwise
     * if it causes a collision, the rotation is reversed
     * @return {@code true} if the brick rotated successfully
     *         {@code false} if rotation would cause a collision
     */
    @Override
    public boolean rotateBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        currentBrick.rotate();
        boolean conflict = MatrixOperations.intersect(currentMatrix, currentBrick.getCurrentMatrix(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            currentBrick.reverseRotate();
            return false;
        } else {
            return true;
        }
    }

    /**
     Creates a new brick at the top center of the board.
     *
     * @return {@code true} if the new brick immediately collides (game over),
     *         {@code false} if the brick can be placed
     */
    @Override
    public boolean createNewBrick() {
        currentBrick = brickGenerator.getBrick();
        currentOffset = new Point2D(4, 1);
        return MatrixOperations.intersect(currentGameMatrix, currentBrick.getCurrentMatrix(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Return current board matrix
     * @return @2D array representing game board state
     */
    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    /**
     * Gets view data required for the current game state.
     * Includes the current brick, its position, and the brick object
     * @return {@link ViewData} containing rendering information
     */
    @Override
    public ViewData getViewData() {
        return new ViewData(currentBrick.getCurrentMatrix(), (int) currentOffset.getX(), (int) currentOffset.getY(),currentBrick);
    }

    /**
     *Merges the current brick into background game matrix
     * Called when a brick can no longer move down
     */
    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, currentBrick.getCurrentMatrix(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    /**
     * Check for and remove anu completed rows from the board
     * Update the game matrix and returns information about cleared row
     * @return {@link ClearRow} object containing the updated matrix and clear row count
     */
    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    /**
     * Returns the score tracker for the game.
     * @return {@link Score} object that is tracking game points
     */
    @Override
    public Score getScore() {
        return score;
    }

    /**
     * Gets information about the next brick that will appear.
     * Used for displaying next brick preview
     * @return {@link NextShapeInfo} containing the next brick's matrix and object
     */
    public NextShapeInfo getNextShape() {
        Brick nextBrick = brickGenerator.getNextBrick();
        if (nextBrick == null) {
            return new NextShapeInfo(new int[][]{{0}},null);
        }
        return new NextShapeInfo(nextBrick.getCurrentMatrix(), nextBrick);
    }

    /**
     * Reset the game to initial state
     * Clears the board, resets the score, creates a new starting brick
     */
    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewBrick();
    }
}
