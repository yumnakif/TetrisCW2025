package com.comp2042.logic.bricks;

import com.comp2042.MatrixOperations;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class BrickOperations implements Brick{

    private final BrickShape brick;
    private final List<int[][]> rotations;
    private int rotationIndex=0;

    public BrickOperations(BrickShape brick) {
        this.brick = brick;
        this.rotations = brickRotations(brick.getShapeMatrix());
    }

    public Color getColor() {
        return brick.getColor();
    }

    @Override
    public int[][] getCurrentMatrix(){
        return deepCopy(rotations.get(rotationIndex));
    }

    @Override
    public void rotate() {
        rotationIndex=(rotationIndex +1 )% rotations.size();
    }
    public void reverseRotate(){
        rotationIndex = (rotationIndex - 1 + rotations.size()) % rotations.size();
    }

    private List<int[][]> brickRotations(int[][] base) {
        List<int[][]> list = new ArrayList<>();
        int[][] current = deepCopy(base);

        for(int i = 0; i < 4; i++) {
            list.add(current);
            current=rotateBrick(current);
        }
        return list;
    }

    private int[][] rotateBrick(int[][] base) {
        int[][] rotated = new int[base.length][base[0].length];
        for(int i = 0; i < base.length; i++) {
            for(int j = 0; j < base[0].length; j++) {
                rotated[i][j] = base[base.length-1-j][i];
            }
        }
        return rotated;
    }

    private int[][] deepCopy(int[][] base) {
        int[][] copy = new int[base.length][base[0].length];
        for(int i = 0; i < base.length; i++) {
            System.arraycopy(base[i], 0, copy[i], 0, base[i].length);
        }
        return copy;
    }
}
