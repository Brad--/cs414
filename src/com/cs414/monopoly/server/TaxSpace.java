package com.cs414.monopoly.server;

import com.cs414.monopoly.shared.Board;
import com.cs414.monopoly.shared.Token;

public class TaxSpace extends Space {

    public TaxSpace(Board board, int position) {
        super(board, position);
    }

    @Override
    public void action(Token target) {
        target.payRent(100);
    }
}
