package com.comp2042.logic.board;

import com.comp2042.logic.bricks.Brick;

/**
 * Container class for information about the next brick shape
 * Holds the brick matrix and brick object to display the preview
 */
public final class NextShapeInfo {

    private final int[][] shape;
    private final Brick brick;

    public NextShapeInfo(final int[][] shape, Brick brick) {
        this.shape = shape;
        this.brick=brick;
    }

    public int[][] getShape() {
        return MatrixOperations.copy(shape);
    }

     public Brick getBrick(){
        return brick;
    }

}
