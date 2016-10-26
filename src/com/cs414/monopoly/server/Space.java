package com.cs414.monopoly.server;

import com.cs414.monopoly.shared.Token;

abstract class Space {

    // Not 0 based, Go is 1
    protected int position;
    protected Board board;

    Space(Board board, int position) {
        this.position = position;
        this.board = board;
    }

    abstract void action(Token target);
}