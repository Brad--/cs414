package com.cs414.monopoly.tests;

import com.cs414.monopoly.shared.Die;
import com.cs414.monopoly.shared.Token;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by bradley on 10/26/16.
 */
public class DieTest {

    public Die die; //die
    public Token token;

    public void init() {
        die = new Die();
        token = new Token("p1", "image.png");
    }

    @Test
    public void testRoll() {
        init();
        int initialPosition = token.getCurrentPosition();
        die.roll(token);
        //Assert that they moved
        assertTrue(initialPosition < token.getCurrentPosition());
    }

    @Test
    public void speedingNoRoll() {
        init();
        assertFalse(die.speeding(token));
    }

    @Test
    public void caughtSpeeding() {
        init();
        for(int i = 0; i < 3; i++)
            die.speeding(token);
        // 11 is jail
        assertEquals(11, token.getCurrentPosition());
    }

    @Test
    public void getOutOfJailWithDoubles() {
        init();
        for(int i = 0; i < 3; i++)
            die.speeding(token);
        for(int i = 0; i < 3; i++)
            die.roll(token);
        assertNotEquals(11, token.getCurrentPosition());
    }
}
