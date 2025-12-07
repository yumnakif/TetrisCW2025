package com.comp2042.input;


import com.comp2042.logic.board.DownData;
import com.comp2042.logic.board.MoveEvent;
import com.comp2042.logic.board.NextShapeInfo;
import com.comp2042.ui.ViewData;

/**
 * Interface for processing game input events
 * Translates user actions into game state changes
 */
public interface InputEventListener {


    /**
     * Processes downward brick movement
     * @param event MoveEvent containing the event type and source
     * @return DownData with clearance results and updated position
     */
    DownData onDownEvent(MoveEvent event);

    /**
     * Processes left movement of the brick
     * @param event MoveEvent containing the event type and source
     * @return ViewData with clearance results and updated position
     */
    ViewData onLeftEvent(MoveEvent event);

    /**
     * Processes right movement of the brick
     * @param event MoveEvent containing the event type and source
     * @return ViewData with clearance results and updated position
     */
    ViewData onRightEvent(MoveEvent event);

    /**
     * Processes the brick rotation
     * @param event MoveEvent containing the event type and source
     * @return ViewData with clearance results and updated view
     */
    ViewData onRotateEvent(MoveEvent event);

    /**
     * Gets the preview of the next brick to display
     * @return NextShapeInfo containing the next brick's matrix
     */
    NextShapeInfo getNextShape();

    /**
     * Returns current game board state as matrix
     * @return 2D array representing the game board
     */
    int[][] getBoardMatrix();

    /**
     * Resets the game to initial states
     */
    void createNewGame();

    /**
     * Instantly drop the brick to the bottom
     * @param moveEvent MoveEvent containing the event type and source
     * @return DownData with results and updated position
     */
    DownData onHardDropEvent(MoveEvent moveEvent);
}
