package com.comp2042;

import javafx.beans.property.BooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.function.Consumer;

/**
 * Handles keyboard input for the Tetris game controls
 * Maps key presses to game actions and forwards them to event listener
 */
public class InputHandler {
    private final InputEventListener eventListener;
    private final Runnable onHardDrop;
    private final Runnable newGame;

    /**
     * Creates an input handler with specified callbacks
     * @param eventListener listener  for game movement events
     * @param onHardDrop runnable for hard drop action
     * @param newGame runnable for starting a new game
     */
    public InputHandler(InputEventListener eventListener, Runnable onHardDrop, Runnable newGame) {
        this.eventListener = eventListener;
        this.onHardDrop = onHardDrop;
        this.newGame = newGame;
    }

    /**
     * Processes keyboard events and triggers corresponding game actions
     * @param keyEvent the KeyEvent to process
     * @param isPause  boolean to check whether game is currently paused
     * @param isGameOver  boolean to check whether game is in game over state
     */
    public void handle(KeyEvent keyEvent, boolean isPause, boolean isGameOver) {
        if (!isPause && !isGameOver) {
            if (keyEvent.getCode() == KeyCode.LEFT || keyEvent.getCode() == KeyCode.A) {
                eventListener.onLeftEvent(new MoveEvent(EventType.LEFT, EventSource.USER));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.RIGHT || keyEvent.getCode() == KeyCode.D) {
                eventListener.onRightEvent(new MoveEvent(EventType.RIGHT, EventSource.USER));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.W) {
                eventListener.onRotateEvent(new MoveEvent(EventType.ROTATE, EventSource.USER));
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.ENTER) {
                onHardDrop.run();
                keyEvent.consume();
            }
            if (keyEvent.getCode() == KeyCode.N) {
                newGame.run();
            }

        }
    }
}
