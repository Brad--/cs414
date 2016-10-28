package com.cs414.monopoly.shared;

public class TaxSpace extends Space {

    public TaxSpace(Board board, int position) {
        super(board, position);
    }

    @Override
    public void action(Token target) {
        target.payRent(100);
    }
}
