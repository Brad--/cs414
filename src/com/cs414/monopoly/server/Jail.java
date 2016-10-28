package com.cs414.monopoly.server;

import com.cs414.monopoly.shared.Board;
import com.cs414.monopoly.shared.Token;

public class Jail extends Space {

    public Jail(Board board, int position) {
        super(board, position);
    }

    @Override
    public void action(Token target) {
        // Do nothing
    }
}
