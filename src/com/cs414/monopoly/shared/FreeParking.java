package com.cs414.monopoly.shared;

import com.cs414.monopoly.shared.Board;
import com.cs414.monopoly.shared.Space;
import com.cs414.monopoly.shared.Token;

public class FreeParking extends Space {

    public FreeParking(Board board, int position) {
        super(board, position);
    }

    @Override
    public void action(Token target) {
        // Nothing happens, but that is intentional
    }
}
