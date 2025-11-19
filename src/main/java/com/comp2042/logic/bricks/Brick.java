package com.comp2042.logic.bricks;

import javafx.scene.paint.Color;

import java.util.List;

public interface Brick {


    int[][] getCurrentMatrix();
    Color getColor();
    void rotate();
    void reverseRotate();

}
