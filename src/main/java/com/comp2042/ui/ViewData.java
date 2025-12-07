package com.comp2042.ui;

import com.comp2042.logic.board.MatrixOperations;
import com.comp2042.logic.bricks.Brick;

/**
 * Returns the brick data and its coordinates
 */
public final class ViewData {

    private final int[][] brickData;
    private final int xPosition;
    private final int yPosition;
    private final Brick brick;

    public ViewData(int[][] brickData, int xPosition, int yPosition,Brick brick) {
        this.brickData = brickData;
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        this.brick = brick;
    }

    public int[][] getBrickData() {
        return MatrixOperations.copy(brickData);
    }

    public int getxPosition() {
        return xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public Brick getBrick(){
        return brick;
    }


}
