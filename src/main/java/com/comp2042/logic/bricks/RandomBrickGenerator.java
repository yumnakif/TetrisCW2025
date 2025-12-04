package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Generates random tetris bricks using a dequeue for preview
 * MAintains a queue of upcoming bricks for next piece display
 */
public class RandomBrickGenerator implements BrickGenerator {

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    /**
     * Creates generator with two random bricks pre-generated
     */
    public RandomBrickGenerator() {
        nextBricks.add(randomBrick());
        nextBricks.add(randomBrick());
    }

    /**
     * Creates a random brick from available BrickShape values
     * @return Randomly selected Brick
     */
    private Brick randomBrick() {
        BrickShape shape = BrickShape.values()[ThreadLocalRandom.current().nextInt(0, BrickShape.values().length)];
        return new BrickOperations(shape);
    }

    /**
     * get current brick and generates a new one for queue
     * @return current brick to drop
     */
    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(randomBrick());
        }
        return nextBricks.poll();
    }

    /**
     * Peeks at the next brick without removing it from queue
     * @return next brick to drop for the preview
     */
    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}
