package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.server.Space;

public class FreeParking extends Space {

    public FreeParking(Board board, int position) {
        super(board, position);
    }

    @Override
    public void action(Token target) {
        // Nothing happens, but that is intentional
    }
}
