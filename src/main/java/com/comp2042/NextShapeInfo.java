package com.comp2042;

import com.comp2042.logic.bricks.Brick;

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
