package com.cs414.monopoly.server;

abstract class Space {

    // Not 0 based, Go is 1
    protected int position;
    protected Board board;

    Space(Board board, int position) {
        this.position = position;
        this.board = board;
    }

    abstract void action(String target);
}