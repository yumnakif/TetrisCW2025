package com.comp2042;

import javafx.beans.property.BooleanProperty;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.function.Consumer;

public class InputHandler {
    private final InputEventListener eventListener;
    private final Runnable onHardDrop;
    private final Runnable newGame;

    public InputHandler(InputEventListener eventListener, Runnable onHardDrop, Runnable newGame) {
        this.eventListener = eventListener;
        this.onHardDrop = onHardDrop;
        this.newGame = newGame;
    }


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
            if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.S) {
                eventListener.onDownEvent(new MoveEvent(EventType.DOWN, EventSource.USER));
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
