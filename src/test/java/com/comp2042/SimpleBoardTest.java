package com.comp2042;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

class SimpleBoardTest {

    @Test
    void moveBrickDownTest() {
        SimpleBoard board= new SimpleBoard(10,20);
        board.createNewBrick();
        Point originalPosition=new Point(board.currentOffset);
        boolean result= board.moveBrickDown();

        assertTrue(result);
        assertEquals(originalPosition.x,board.currentOffset.x);
        assertEquals(originalPosition.y+1,board.currentOffset.y);
    }

    @Test
    void moveBrickLeftTest() {
        SimpleBoard board= new SimpleBoard(10,20);
        board.createNewBrick();
        Point originalPosition=new Point(board.currentOffset);
        boolean result= board.moveBrickLeft();

        assertTrue(result);
        assertEquals(originalPosition.x-1,board.currentOffset.x);
        assertEquals(originalPosition.y,board.currentOffset.y);
    }

    @Test
    void moveBrickRightTest() {
        SimpleBoard board= new SimpleBoard(10,20);
        board.createNewBrick();
        Point originalPosition=new Point(board.currentOffset);
        boolean result= board.moveBrickRight();

        assertTrue(result);
        assertEquals(originalPosition.x+1,board.currentOffset.x);
        assertEquals(originalPosition.y,board.currentOffset.y);
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
        assertEquals(new Point(4,1),board.currentOffset);
    }

    @Test
    void mergeBrickToBackgroundTest() {
        SimpleBoard board= new SimpleBoard(10,20);
        int[][] matrix=MatrixOperations.copy(board.getBoardMatrix());
        board.mergeBrickToBackground();

        assertNotEquals(matrix, board.getBoardMatrix());
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