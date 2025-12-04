package com.comp2042.logic.bricks;

import com.comp2042.MatrixOperations;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;
/**
 * Handles brick rotation operations and matrix transformations
 * Implements Brick interface to provide rotated brick matrices
 */
public class BrickOperations implements Brick{

    private final BrickShape brick;
    private final List<int[][]> rotations;
    private int rotationIndex=0;

    /**
     * Creates a brick operation handler for specified brick shape
     * Pre calculates all 4 possible rotations of the brick
     * @param brick the BrickShape to operate on
     */
    public BrickOperations(BrickShape brick) {
        this.brick = brick;
        this.rotations = brickRotations(brick.getShapeMatrix());
    }

    /**
     * Gets the bricks display color
     * @return Color assigned to the brick type
     */
    public Color getColor() {
        return brick.getColor();
    }

    /**
     * Gets the current brick matrix in its current rotation state
     * @return copy of the current rotated brick matrix
     */
    @Override
    public int[][] getCurrentMatrix(){
        return deepCopy(rotations.get(rotationIndex));
    }

    /**
     * Rotates the brick 90 degrees clockwise
     */
    @Override
    public void rotate() {
        rotationIndex=(rotationIndex +1 )% rotations.size();
    }

    /**
     * Rotates the brick 90 degrees counter clockwise to the previous rotation state
     */
    public void reverseRotate(){
        rotationIndex = (rotationIndex - 1 + rotations.size()) % rotations.size();
    }

    /**
     * Generates all 4 rotation states of a brick matrix
     * @param base the original brick matrix
     * @return List containing all 4 rotation matrices
     */
    private List<int[][]> brickRotations(int[][] base) {
        List<int[][]> list = new ArrayList<>();
        int[][] current = deepCopy(base);

        for(int i = 0; i < 4; i++) {
            list.add(current);
            current=rotateBrick(current);
        }
        return list;
    }

    /**
     * Rotates the 2D matrix clockwise 90 degrees
     * @param base matrix to rotate
     * @return new rotated matrix
     */
    private int[][] rotateBrick(int[][] base) {
        int[][] rotated = new int[base.length][base[0].length];
        for(int i = 0; i < base.length; i++) {
            for(int j = 0; j < base[0].length; j++) {
                rotated[i][j] = base[base.length-1-j][i];
            }
        }
        return rotated;
    }

    /**
     * Creates a deep copy of a 2D matrix
     * @param base Matrix to copy
     * @return independent copy of the matrix
     */
    private int[][] deepCopy(int[][] base) {
        int[][] copy = new int[base.length][base[0].length];
        for(int i = 0; i < base.length; i++) {
            System.arraycopy(base[i], 0, copy[i], 0, base[i].length);
        }
        return copy;
    }
}
