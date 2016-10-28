package com.cs414.monopoly.tests;


import static org.junit.Assert.*;

import com.cs414.monopoly.shared.Token;
import org.junit.Test;

import com.cs414.monopoly.shared.Deed;
import com.cs414.monopoly.shared.exception.HotelException;
import com.cs414.monopoly.shared.exception.HouseException;
import com.cs414.monopoly.shared.Board;

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
    public void mortgageProperty() {
        init(2);
        deed.changeOwnership(new Token("name", "image"));
        deed.mortgageProperty();
        assertNull(deed.getOwner());
    }

    @Test
    public void addHouse() throws HouseException{
        init(2);
        deed.addHouse();
        assertEquals(10, deed.getRent());
    }

    @Test(expected = HouseException.class)
    public void addTooManyHouses() throws HouseException {
        init(2);
        for(int i = 0; i < 10; i++) {
            deed.addHouse();
        }
    }

    @Test(expected = HotelException.class)
    public void invalidHotelNotEnoughHouses() throws HotelException {
        init(2);
        deed.addHotel();
    }

    @Test(expected = HotelException.class)
    public void invalidHotelAlreadyExists() throws HotelException, HouseException {
        init(2);
        for(int i = 0; i < 4; i++) {
            deed.addHouse();
        }
        deed.addHotel();
        deed.addHotel();
    }

    @Test
    public void addHotel() throws HotelException, HouseException {
        init(2);
        for(int i = 0; i < 4; i++) {
            deed.addHouse();
        }
        deed.addHotel();
        assertEquals(250, deed.getRent());
    }

}
