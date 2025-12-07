package com.comp2042.logic.bricks;

import javafx.scene.paint.Color;
/**
 * Enum representing Tetris brick shapes with their matrices, colors, and numeric codes
 * Contains all 7 standard tetromino shapes (I, J, L, O, S, T, Z)
 */

public enum BrickShape {
    I(Color.AQUA, 1,new int[][]{
            {0, 0, 0, 0},
            {1, 1, 1, 1},
            {0, 0, 0, 0},
            {0, 0, 0, 0}
    }),
    J(Color.FUCHSIA,2,new int[][]{
            {0, 0, 0, 0},
            {2, 2, 2, 0},
            {0, 0, 2, 0},
            {0, 0, 0, 0}
    }),
    L( Color.CHARTREUSE,3,new int[][]{
            {0, 0, 0, 0},
            {0, 3, 3, 3},
            {0, 3, 0, 0},
            {0, 0, 0, 0}
    }),
    O(Color.DEEPPINK,4,new int[][]{
            {0, 0, 0, 0},
            {0, 4, 4, 0},
            {0, 4, 4, 0},
            {0, 0, 0, 0}
    }),
    S(Color.RED,5,new int[][]{
            {0, 0, 0, 0},
            {0, 5, 5, 0},
            {5, 5, 0, 0},
            {0, 0, 0, 0}
    }),
    T(Color.YELLOW,6, new int[][]{
            {0, 0, 0, 0},
            {6, 6, 6, 0},
            {0, 6, 0, 0},
            {0, 0, 0, 0}
    }),
    Z(Color.ROYALBLUE,7,new int[][]{
            {0, 0, 0, 0},
            {7, 7, 0, 0},
            {0, 7, 7, 0},
            {0, 0, 0, 0}
    });

    public final int[][] shapeMatrix;
    private final Color color;
    private int colorInt;

    /**
     * Creates a brick shape with color, numeric code, and shape matrix
     * @param color display color for this shape
     * @param colorInt numeric code used in matrix representation
     * @param shapeMatrix a 4x4 matrix defining the brick shape
     */

    BrickShape(Color color,int colorInt, int[][] shapeMatrix) {
        this.color = color;
        this.shapeMatrix = shapeMatrix;
        this.colorInt=colorInt;
    }

    /**
     * Gets the shape matrix
     * @return a 4x4 int array representing the brick shape
     */
    public int[][] getShapeMatrix() {
        return shapeMatrix;
    }

    /**
     * Get the display color of the brick shape
     * @return color assigned to the brick shape
     */
    public Color getColor() {
        return color;
    }

    /**
     * Converts numeric color code to corresponding Color
     * @param colorcode numeric code 1-7 representing brick type
     * @return color associated with the code, or BLACK if none found
     */
    public  static Color getColorInt(int colorcode) {
        for (BrickShape shape : values()) {
            if (shape.colorInt == colorcode) {
                return shape.color;
            }
        }
        return Color.BLACK;
    }

}
