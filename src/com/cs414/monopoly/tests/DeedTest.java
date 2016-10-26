package com.cs414.monopoly.tests;

import com.cs414.monopoly.Exceptions.HouseException;
import com.cs414.monopoly.server.Board;
import com.cs414.monopoly.server.Deed;
import static org.junit.Assert.*;

import org.junit.Test;

/**
 * Created by bradley on 10/25/16.
 */
public class DeedTest {
    public Deed deed;
    public Board board;

    public void init(int position) {
        Board b = new Board();
        deed = new Deed(b, position);
    }

    @Test
    public void deedCreate() {
        init(2);
        assertNotNull(deed);
        assertEquals(2, deed.getRent());
        assertNull(deed.getOwner());
    }

    @Test
    public void mortgagePropertyNoOwner() {
        init(2);
        assertEquals(0, deed.mortgageProperty());
    }

    @Test
    public void mortgageProperty() {
        init(2);
        assertEquals(1, deed.mortgageProperty());
        assertNull(deed.getOwner());
    }

    @Test
    public void addHouse() throws HouseException{
        init(2);
        deed.addHouse();
        assertEquals(10, deed.getRent());
    }

    @Test(expected=HouseException.class)
    public void addTooManyHouses() throws HouseException {
        init(2);
        for(int i = 0; i < 10; i++) {
            deed.addHouse();
        }
    }

}
