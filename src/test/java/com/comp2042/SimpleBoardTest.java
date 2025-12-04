package com.comp2042;

import javafx.geometry.Point2D;
import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardTest {

    @Test
    void moveBrickDownTest() {
        SimpleBoard board = new SimpleBoard(10, 20);
        board.createNewBrick();
        Point2D originalPosition = new Point2D(board.currentOffset.getX(), board.currentOffset.getY());
        boolean result = board.moveBrickDown();

        assertTrue(result);
        assertEquals(originalPosition.getX(), board.currentOffset.getX());
        assertEquals(originalPosition.getY() + 1, board.currentOffset.getY());
    }

    @Test
    void moveBrickLeftTest() {
        SimpleBoard board = new SimpleBoard(10, 20);
        board.createNewBrick();
        Point2D originalPosition = new Point2D(board.currentOffset.getX(), board.currentOffset.getY());
        boolean result = board.moveBrickLeft();

        assertTrue(result);
        assertEquals(originalPosition.getX() - 1, board.currentOffset.getX());
        assertEquals(originalPosition.getY(), board.currentOffset.getY());
    }

    @Test
    void moveBrickRightTest() {
        SimpleBoard board = new SimpleBoard(10, 20);
        board.createNewBrick();
        Point2D originalPosition = new Point2D(board.currentOffset.getX(), board.currentOffset.getY());
        boolean result = board.moveBrickRight();

        assertTrue(result);
        assertEquals(originalPosition.getX() + 1, board.currentOffset.getX());
        assertEquals(originalPosition.getY(), board.currentOffset.getY());
    }

    @Test
    void rotateBrickTest() {
        SimpleBoard board= new SimpleBoard(10,20);
        board.createNewBrick();
        int[][] matrix=board.currentBrick.getCurrentMatrix();
        boolean result= board.rotateBrick();

        assertTrue(result);
        assertNotEquals(matrix,board.currentBrick.getCurrentMatrix());
    }

    @Test
    void createNewBrickTest() {
        SimpleBoard board= new SimpleBoard(10,20);
        boolean result= board.createNewBrick();
        assertFalse(result);
        assertNotNull(board.currentBrick);

        assertEquals(4,board.currentOffset.getX());
        assertEquals(1,board.currentOffset.getY());

    }

    @Test
    void clearRowsTest() {
        SimpleBoard board= new SimpleBoard(10,20);
        boolean result= board.createNewBrick();
        for(int i=0;i<10;i++) {
            board.getBoardMatrix();
        }
    }

    @Test
    void newGameTest() {
        SimpleBoard board = new SimpleBoard(10, 20);
        board.getScore().add(100);
        board.newGame();
        assertNotNull(board.getScore());
        assertNotNull(board.currentBrick);
    }
}