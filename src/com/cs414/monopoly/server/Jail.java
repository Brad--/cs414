package com.cs414.monopoly.server;

public class Jail extends Space {

    public Jail(Board board, int position) {
        super(board, position);
    }

    @Override
    public void action(String target) {
        // Do nothing
    }
}
