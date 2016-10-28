package com.cs414.monopoly.server;

import com.cs414.monopoly.shared.Board;
import com.cs414.monopoly.shared.Token;

public class FreeParking extends Space {

    public FreeParking(Board board, int position) {
        super(board, position);
    }

    @Override
    public void action(Token target) {

    }
}
