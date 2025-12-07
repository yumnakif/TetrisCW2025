package com.comp2042.logic.bricks;

import javafx.scene.paint.Color;

import java.util.List;

/**
 * Represent a Tetromino or brick with rotation capabilities
 * Defines the shape and behaviour of the piece
 */
public interface Brick {

    /**
     * Gets the current brick matrix in its current rotation state
     * @return copy of the current rotated brick matrix
     */
    int[][] getCurrentMatrix();

    /**
     * Gets the bricks display color
     * @return Color assigned to the brick type
     */
    Color getColor();

    /**
     * Rotates the brick 90 degrees clockwise
     */
    void rotate();

    /**
     * Rotates the brick 90 degrees counter clockwise to the previous rotation state
     */
    void reverseRotate();

}
