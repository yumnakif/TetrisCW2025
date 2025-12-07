package com.comp2042.logic.bricks;

/**
 * Generates a random Tetromino with preview
 * Maintains a queue for the next piece display
 */
public interface BrickGenerator {

    /**
     * get current brick and generates a new one for queue
     * @return current brick to drop
     */
    Brick getBrick();

    /**
     * Peeks at the next brick without removing it from queue
     * @return next brick to drop for the preview
     */
    Brick getNextBrick();
}
