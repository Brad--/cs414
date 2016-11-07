package cs414.a5.groupA.monopoly.tests;

import org.junit.Test;

import cs414.a5.groupA.monopoly.shared.Board;
import cs414.a5.groupA.monopoly.shared.Deed;
import cs414.a5.groupA.monopoly.shared.Token;

import java.util.ArrayList;

import static org.junit.Assert.*;

/**
 * Created by Garrett on 10/26/2016.
 */
public class BoardTest {

    public Board createBoard(){
        return new Board();
    }

    @Test
    public void init() throws Exception {
        Board b = createBoard();
        assertNotNull(b);
    }

    @Test
    public void handleRoll() throws Exception {
        Board b = createBoard();
        Token player = new Token("gabe", "image");
        int current = player.getCurrentPosition();
        player = b.handleRoll(player);
        assertNotEquals(player.getCurrentPosition(), current);
    }

    @Test
    public void addUser() throws Exception {
        Board b = createBoard();
        Token player = new Token("gabe", "image");
        b.addUser(player);
        assertTrue(b.containsUser(player.getName()));
    }

    @Test
    public void addUserByRoll() throws Exception {
        Board b = createBoard();
        Token player = new Token("gabe", "image");
        b.handleRoll(player);
        assertTrue(b.containsUser(player.getName()));
    }

    @Test
    public void payRent() throws Exception {
        Board b = createBoard();
        Token player = new Token("gabe", "image");
        int credits = player.getCashMoney();
        player = b.handleRoll(player);
        b.payRent(player);
        assertNotEquals(credits, player.getCashMoney());
    }

    @Test
    public void delegateDeed() throws Exception {
        Board b = createBoard();
        Token player = new Token("gabe", "image");
        player = b.handleRoll(player);
        b.delegateDeed(player);
        ArrayList<Deed> deeds = b.getOwnedDeeds(player);
        assertNotNull(deeds);
    }

    @Test
    public void trade() throws Exception {
        Board b = createBoard();
        Token player = new Token("gabe", "image");
        b.trade(player, 4);
        ArrayList<Deed> deeds = b.getOwnedDeeds(player);
        assertNotNull(deeds);

    }

    @Test
    public void getOwnedDeeds() throws Exception {
        Board b = createBoard();
        Token player = new Token("gabe", "image");
        player = b.handleRoll(player);
        b.delegateDeed(player);
        ArrayList<Deed> deeds = b.getOwnedDeeds(player);
        assertNotNull(deeds);
    }

}