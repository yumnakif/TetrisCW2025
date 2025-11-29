package com.comp2042;

import com.comp2042.logic.bricks.BrickShape;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

    public class GameRender {
        private static final int BRICK_SIZE = 20;
        @FXML
        private GridPane gamePanel;
        @FXML
        private GridPane brickPanel;

        @FXML
        private GridPane ghostPanel;

        @FXML
        private GridPane nextBlockPreview;

        private Rectangle[][] ghostMatrix;
        private Rectangle[][] displayMatrix;
        private Rectangle[][] rectangles;

        public GameRender(GridPane gamePanel, GridPane brickPanel, GridPane ghostPanel){
            this.gamePanel=gamePanel;
            this.brickPanel=brickPanel;
            this.ghostPanel=ghostPanel;
        }

        public void setNextBlockPreview(GridPane nextBlockPreview){
            this.nextBlockPreview=nextBlockPreview;
        }

        public void initGameView(int[][] boardMatrix) {

            int rows=boardMatrix.length;
            int cols=boardMatrix[0].length;

            displayMatrix = new Rectangle[rows][cols];
            ghostMatrix = new Rectangle[rows][cols];

            gamePanel.getChildren().clear();
            ghostPanel.getChildren().clear();

            for (int i = 2; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                    rectangle.setFill(Color.BLACK);
                    displayMatrix[i][j] = rectangle;
                    gamePanel.add(rectangle, j, i);
                }
            }


            for (int i = 2; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                    rectangle.setFill(Color.TRANSPARENT);
                    ghostMatrix[i][j] = rectangle;
                    gamePanel.add(rectangle, j, i);
                }
            }
        }

        public void initBrick(ViewData brick){
            rectangles = new Rectangle[brick.getBrickData().length][brick.getBrickData()[0].length];
            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                    rectangle.setVisible(false);
                    rectangles[i][j] = rectangle;
                    brickPanel.add(rectangle, j, i);
                }
            }
        }

        public void refreshBrick(ViewData brick){
            int[][] shape=brick.getBrickData();
            Color brickColor = brick.getBrick().getColor();

            brickPanel.setLayoutX(gamePanel.getLayoutX() + brick.getxPosition() * brickPanel.getVgap() + brick.getxPosition() * BRICK_SIZE);
            brickPanel.setLayoutY(gamePanel.getLayoutY() + (brick.getyPosition()-2) * brickPanel.getHgap() + (brick.getyPosition()-2) * BRICK_SIZE);

            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    if (shape[i][j] != 0) {
                        setRectangleData(brickColor, rectangles[i][j]);
                        rectangles[i][j].setVisible(true);
                    } else {
                        rectangles[i][j].setVisible(false);
                    }
                }
            }
        }
        public void updateNextShape(NextShapeInfo nextShapeInfo) {
            if (nextBlockPreview == null) {
                return;
            }
            nextBlockPreview.getChildren().clear();

            int[][] nextShape = nextShapeInfo.getShape();
            Color brickColor = nextShapeInfo.getBrick().getColor();

            int offsetX = Math.max(0, (BRICK_SIZE - nextShape[0].length) / 2);
            int offsetY = Math.max(0, (BRICK_SIZE - nextShape.length) / 2);

            for (int i = 0; i < nextShape.length; i++) {
                for (int j = 0; j < nextShape[i].length; j++) {
                    int cell = nextShape[i][j];
                    if (cell != 0) {
                        Rectangle rectangle = new Rectangle(BRICK_SIZE, BRICK_SIZE);
                        setRectangleData(brickColor, rectangle);
                        nextBlockPreview.add(rectangle, j + offsetX, i + offsetY);
                    }
                }
            }
        }

        public void refreshGhost(ViewData brick, int[][] ghostBoard) {
            if(brick==null || ghostMatrix==null){
                return;
            }
            Color brickColor = brick.getBrick().getColor();
            for (int i = 0; i < ghostBoard.length; i++) {
                for (int j = 0; j < ghostBoard[i].length; j++) {
                    if (ghostMatrix[i][j] != null) {
                        ghostMatrix[i][j].setVisible(false);
                    }
                }
            }
            int ghostY = brick.getyPosition();
            while (ghostY + 1 < ghostBoard.length && !collides(ghostBoard, brick.getBrickData(), brick.getxPosition(), ghostY + 1)) {
                ghostY++;
            }

            for (int i = 0; i < brick.getBrickData().length; i++) {
                for (int j = 0; j < brick.getBrickData()[i].length; j++) {
                    int cell = brick.getBrickData()[i][j];
                    int boardY = ghostY + i;
                    int boardX = brick.getxPosition() + j;

                    if (boardY >= 0 && boardY < ghostMatrix.length && boardX >= 0 && boardX < ghostMatrix[0].length) {
                        if(ghostMatrix[boardY][boardX]!=null) {
                            Rectangle rectangle = ghostMatrix[boardY][boardX];
                            if (cell != 0 && ghostBoard[boardY][boardX] == 0) {
                                setRectangleData(brickColor, rectangle);
                                rectangle.setOpacity(0.3);
                                rectangle.setVisible(true);
                            }
                        }
                    }
                }
            }
        }

        private static boolean collides(int[][] board, int[][] brick, int x, int y) {
            for (int i = 0; i < brick.length; i++) {
                for (int j = 0; j < brick[i].length; j++) {
                    if (brick[i][j] != 0) {
                        int boardY = y + i;
                        int boardX = x + j;
                        if (boardY >= board.length || boardX < 0 || boardX >= board[0].length) {
                            return true;
                        }
                        if (boardY < 0) {
                            continue;
                        }
                        if (board[boardY][boardX] != 0) {
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        public void refreshBackground(int[][] boardMatrix){
            for (int i = 2; i < boardMatrix.length; i++) {
                for (int j = 0; j < boardMatrix[i].length; j++) {
                    int colorint= boardMatrix[i][j];
                    if(colorint==8){
                        setRectangleData(Color.GRAY,displayMatrix[i][j]);
                    }
                    else if(colorint>=0){
                        Color brickColor = BrickShape.getColorInt(colorint);
                        setRectangleData(brickColor,displayMatrix[i][j]);
                    }

                }
            }
        }

        private void setRectangleData(Color color, Rectangle rectangle) {
            rectangle.setFill(color);
            rectangle.setArcHeight(5);
            rectangle.setArcWidth(5);
        }
        public void clearBoard(){
            if(displayMatrix!=null){
                for(Rectangle[] row: displayMatrix){
                    for (Rectangle rectangle:row){
                        if(rectangle!=null){
                            rectangle.setFill(Color.BLACK);
                        }
                    }
                }
            }
            if(brickPanel!=null){
                brickPanel.getChildren().clear();
            }
        }
    }