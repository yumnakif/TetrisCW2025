package com.comp2042;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MatrixOperationsTest {

    @Test
    void intersect() {
        int[][] board={{0,0,0},{0,0,0},{0,0,0}};
        int[][] brick={{1}};

        boolean result=MatrixOperations.intersect(board,brick,1,1);
        assertFalse(result);
    }
    @Test
    void intersectCollison(){
        int[][] board={{0,0,0},{0,1,0},{0,0,0}};
        int[][] brick={{1}};

        boolean result=MatrixOperations.intersect(board,brick,1,1);
        assertTrue(result);
    }

    @Test
    void intersectOutOfBounds(){
        int[][] board={{0,0,0},{0,0,0},{0,0,0}};
        int[][] brick={{1}};

        boolean result=MatrixOperations.intersect(board,brick,5,5);
        assertTrue(result);
    }

    @Test
    void copy() {
        int[][] brick={{1,0,0},{0,1,0},{0,0,1}};
        int[][] copy= MatrixOperations.copy(brick);
        for(int i=0;i<brick.length;i++){
            assertArrayEquals(brick[i],copy[i]);
        }
        brick[0][0]=9;
        assertEquals(1,copy[0][0]);
    }

    @Test
    void merge() {
        int[][] board=new int[4][4];
        int[][] brick={{1}};

        int[][] result=MatrixOperations.merge(board,brick,1,1);
        assertEquals(1,result[1][1]);
        assertEquals(0,result[0][0]);
    }

    @Test
    void checkRemoving() {
        int[][] board={{0,0,0},{1,1,1},{0,0,0}};
        ClearRow result =MatrixOperations.checkRemoving(board);

        assertEquals(1,result.getLinesRemoved());
        assertArrayEquals(new int[]{0,0,0},result.getNewMatrix()[1]);
    }
    @Test
    void checkRemovingNoLine(){
        int[][] board={{0,0,0},{1,0,1},{0,0,0}};
        ClearRow result=MatrixOperations.checkRemoving(board);
        assertEquals(0,result.getLinesRemoved());
    }

}