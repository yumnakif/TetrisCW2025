package com.comp2042;

import com.comp2042.logic.bricks.Brick;
import com.comp2042.logic.bricks.BrickGenerator;
import com.comp2042.logic.bricks.BrickOperations;
import com.comp2042.logic.bricks.RandomBrickGenerator;

import java.awt.*;

public class SimpleBoard implements Board {

    private final int width;
    private final int height;
    private final BrickGenerator brickGenerator;
    private Brick currentBrick;
    private int[][] currentGameMatrix;
    private Point currentOffset;
    private final Score score;

    public SimpleBoard(int width, int height) {
        this.width = width;
        this.height = height;
        currentGameMatrix = new int[width][height];
        brickGenerator = new RandomBrickGenerator();
        score = new Score();
    }

    @Override
    public boolean moveBrickDown() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(0, 1);
        boolean conflict = MatrixOperations.intersect(currentMatrix, currentBrick.getCurrentMatrix(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }


    @Override
    public boolean moveBrickLeft() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(-1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, currentBrick.getCurrentMatrix(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }

    @Override
    public boolean moveBrickRight() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        Point p = new Point(currentOffset);
        p.translate(1, 0);
        boolean conflict = MatrixOperations.intersect(currentMatrix, currentBrick.getCurrentMatrix(), (int) p.getX(), (int) p.getY());
        if (conflict) {
            return false;
        } else {
            currentOffset = p;
            return true;
        }
    }
    private boolean moveBrick(int x, int y){
        Point p=new Point(currentOffset);
        p.translate(x,y);
        boolean intersect=MatrixOperations.intersect(currentGameMatrix,currentBrick.getCurrentMatrix(),(int) p.getX(),(int)p.getY());
        if(intersect){
            return false;
        }
        currentOffset=p;
        return true;
    }
    @Override
    public boolean rotateBrick() {
        int[][] currentMatrix = MatrixOperations.copy(currentGameMatrix);
        currentBrick.rotate();
        boolean conflict = MatrixOperations.intersect(currentMatrix, currentBrick.getCurrentMatrix(), (int) currentOffset.getX(), (int) currentOffset.getY());
        if (conflict) {
            currentBrick.reverseRotate();
            return false;
        } else {
            return true;
        }
    }

    @Override
    public boolean createNewBrick() {
        currentBrick = brickGenerator.getBrick();
        currentOffset = new Point(4, 1);
        return MatrixOperations.intersect(currentGameMatrix, currentBrick.getCurrentMatrix(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public int[][] getBoardMatrix() {
        return currentGameMatrix;
    }

    @Override
    public ViewData getViewData() {
        return new ViewData(currentBrick.getCurrentMatrix(), (int) currentOffset.getX(), (int) currentOffset.getY(),currentBrick);
    }

    @Override
    public void mergeBrickToBackground() {
        currentGameMatrix = MatrixOperations.merge(currentGameMatrix, currentBrick.getCurrentMatrix(), (int) currentOffset.getX(), (int) currentOffset.getY());
    }

    @Override
    public ClearRow clearRows() {
        ClearRow clearRow = MatrixOperations.checkRemoving(currentGameMatrix);
        currentGameMatrix = clearRow.getNewMatrix();
        return clearRow;

    }

    @Override
    public Score getScore() {
        return score;
    }

    public NextShapeInfo getNextShape() {
        Brick nextBrick = brickGenerator.getNextBrick();
        if (nextBrick == null) {
            return new NextShapeInfo(new int[][]{{0}},null);
        }
        return new NextShapeInfo(nextBrick.getCurrentMatrix(), nextBrick);
    }

    @Override
    public void newGame() {
        currentGameMatrix = new int[width][height];
        score.reset();
        createNewBrick();
    }
}
