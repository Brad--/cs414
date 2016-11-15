package cs414.a5.groupA.monopoly.tests;

import org.junit.Test;

import cs414.a5.groupA.monopoly.server.Token;

import static org.junit.Assert.*;

/**
 * Created by bradley on 10/27/16.
 */
public class TokenTest {
    public Token token;

    public void init() {
        token = new Token("Owen");
    }

    @Test
    public void createToken() {
        init();
        assertNotNull(token);
        assertEquals(1500, token.getCashMoney());
        assertFalse(token.getJailStatus());
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
        token.setGoToJail();
        assertTrue(token.getJailStatus());
    }

    @Test
    public void unjailed() {
        init();
        token.setGoToJail();
        token.setOutofJail();
        assertFalse(token.getJailStatus());
    }

}
