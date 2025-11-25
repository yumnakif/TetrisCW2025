package com.comp2042;

public class GameController implements InputEventListener {

    private Board board = new SimpleBoard(33, 20);
    private final GuiController viewGuiController;

    public GameController(GuiController c) {
        viewGuiController = c;
        board.createNewBrick();
        viewGuiController.setEventListener(this);
        viewGuiController.initInputHandler();
        viewGuiController.getRenderer().initGameView(board.getBoardMatrix());
        viewGuiController.getRenderer().initBrick(board.getViewData());
        viewGuiController.bindScore(board.getScore().scoreProperty());
        viewGuiController.getRenderer().updateNextShape(board.getNextShape());
        viewGuiController.newGame(null);
    }

    @Override
    public DownData onDownEvent(MoveEvent event) {
        boolean canMove = board.moveBrickDown();
        ClearRow clearRow ;
        if (!canMove) {
            board.mergeBrickToBackground();
            clearRow=board.clearRows();
            if (clearRow.getLinesRemoved() > 0) {
                board.getScore().add(clearRow.getScoreBonus());
            }
            if (board.createNewBrick()) {
                viewGuiController.gameOver();
            }
            viewGuiController.getRenderer().updateNextShape(board.getNextShape());
            viewGuiController.getRenderer().refreshBackground(board.getBoardMatrix());

        } else {
            clearRow=new ClearRow(0,board.getBoardMatrix(),0);
            if (event.getEventSource() == EventSource.USER) {
                board.getScore().add(1);
            }
        }
        return new DownData(clearRow, board.getViewData());
    }

    @Override
    public ViewData onLeftEvent(MoveEvent event) {
        board.moveBrickLeft();
        ViewData viewData=board.getViewData();
        viewGuiController.brickRender(viewData);
        return viewData;
    }

    @Override
    public ViewData onRightEvent(MoveEvent event) {
        board.moveBrickRight();
        ViewData viewData=board.getViewData();
        viewGuiController.brickRender(viewData);
        return viewData;
    }

    @Override
    public ViewData onRotateEvent(MoveEvent event) {
        board.rotateBrick();
        ViewData viewData=board.getViewData();
        viewGuiController.brickRender(viewData);
        return viewData;
    }

    @Override
    public ViewData onHardDropEvent(MoveEvent event){
        boolean canMove=true;
        while(canMove){
            canMove=board.moveBrickDown();
            if(canMove && event.getEventSource()==EventSource.USER){
                board.getScore().add(1);
            }
        }
        board.mergeBrickToBackground();
        ClearRow clearRow=board.clearRows();
        if (clearRow.getLinesRemoved()>0){
            board.getScore().add(clearRow.getScoreBonus());
        }
        viewGuiController.getRenderer().refreshBackground(board.getBoardMatrix());
        if (board.createNewBrick()) {
            viewGuiController.gameOver();
        }
        viewGuiController.getRenderer().updateNextShape(board.getNextShape());
        ViewData viewData=board.getViewData();
        viewGuiController.brickRender(viewData);
        return viewData;
    }

    @Override
    public NextShapeInfo getNextShape(){
        return board.getNextShape();
}

    @Override
    public int[][] getBoardMatrix(){
        return board.getBoardMatrix();
    }

    @Override
    public void createNewGame() {
        board.newGame();
        viewGuiController.getRenderer().clearBoard();
        viewGuiController.getRenderer().initGameView(board.getBoardMatrix());
        viewGuiController.getRenderer().initBrick(board.getViewData());
        viewGuiController.getRenderer().updateNextShape(board.getNextShape());

    }
}
