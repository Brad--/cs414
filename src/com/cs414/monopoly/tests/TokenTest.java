package com.cs414.monopoly.tests;

import com.cs414.monopoly.shared.Token;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by bradley on 10/27/16.
 */
public class TokenTest {
    public Token token;

    public void init() {
        token = new Token("Owen", "Filler because images don't matter in this unit test");
    }

    @Test
    public void createToken() {
        init();
        assertNotNull(token);
        assertEquals(1500, token.getCashMoney());
        assertFalse(token.inJail());
        assertEquals(1, token.getCashMoney());
        assertEquals(0, token.getSpeeding());
    }

    @Test
    public void payRent() {
        init();
        token.payRent(100);
        assertEquals(1400, token.getCashMoney());
    }

    @Test
    public void earnRent() {
        init();
        token.earnRent(100);
        assertEquals(1600, token.getCashMoney());
    }

    @Test
    public void passGo() {
        init();
        token.passGo();
        assertEquals(1700, token.getCashMoney());
    }

    @Test
    public void updatePosition() {
        init();
        token.updatePosition(1);
        assertEquals(2, token.getCurrentPosition());
    }

    @Test
    public void updatePositionPastGo() {
        init();
        token.updatePosition(40);
        assertEquals(1, token.getCurrentPosition());
    }

    @Test
    public void jailed() {
        init();
        token.goToJail();
        assertTrue(token.inJail());
    }

    @Test
    public void unjailed() {
        init();
        token.goToJail();
        token.getOutofJail();
        assertFalse(token.inJail());
    }

}
