package com.comp2042.logic.bricks;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.ThreadLocalRandom;

public class RandomBrickGenerator implements BrickGenerator {

    private final Deque<Brick> nextBricks = new ArrayDeque<>();

    public RandomBrickGenerator() {
        nextBricks.add(randomBrick());
        nextBricks.add(randomBrick());
    }

    private Brick randomBrick() {
        BrickShape shape = BrickShape.values()[ThreadLocalRandom.current().nextInt(0, BrickShape.values().length)];
        return new BrickOperations(shape);
    }

    @Override
    public Brick getBrick() {
        if (nextBricks.size() <= 1) {
            nextBricks.add(randomBrick());
        }
        return nextBricks.poll();
    }

    @Override
    public Brick getNextBrick() {
        return nextBricks.peek();
    }
}
