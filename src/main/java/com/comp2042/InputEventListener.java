package com.comp2042;


public interface InputEventListener {

    DownData onDownEvent(MoveEvent event);

    ViewData onLeftEvent(MoveEvent event);

    ViewData onRightEvent(MoveEvent event);

    ViewData onRotateEvent(MoveEvent event);

    NextShapeInfo getNextShape();

    int[][] getBoardMatrix();

    void createNewGame();

    DownData onHardDropEvent(MoveEvent moveEvent);
}
