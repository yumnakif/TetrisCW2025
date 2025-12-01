package com.comp2042.logic.bricks;

import javafx.scene.paint.Color;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrickOperationsTest {

    @Test
    void getColor() {
        BrickOperations brick =new BrickOperations(BrickShape.I);
        Color color=brick.getColor();
        assertNotNull(color);
    }

    @Test
    void getCurrentMatrix() {
    }

    @Test
    void rotate() {
        BrickOperations brick =new BrickOperations(BrickShape.I);
        int[][] original= brick.getCurrentMatrix();
        brick.rotate();
        int[][] rotated=brick.getCurrentMatrix();
        assertNotEquals(original,rotated);
    }

    @Test
    void reverseRotate() {
        BrickOperations brick =new BrickOperations(BrickShape.I);
        int[][] original= brick.getCurrentMatrix();
        brick.rotate();
        brick.reverseRotate();
        int[][] result=brick.getCurrentMatrix();
        assertArrayEquals(original,result);
    }
}