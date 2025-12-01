package com.comp2042.logic.bricks;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RandomBrickGeneratorTest {

    @Test
    void getBrickRefillTest() {
        RandomBrickGenerator generator = new RandomBrickGenerator();

        generator.getBrick();
        Brick b = generator.getBrick();

        assertNotNull(b);
        assertNotNull(generator.getNextBrick()); // queue repopulated
    }

    @Test
    void getNextBrickTest() {
        RandomBrickGenerator generator = new RandomBrickGenerator();
        Brick next1 = generator.getNextBrick();
        Brick next2 = generator.getNextBrick();

        assertSame(next1, next2);
    }

}
