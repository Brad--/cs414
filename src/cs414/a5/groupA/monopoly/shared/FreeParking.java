package cs414.a5.groupA.monopoly.shared;

import cs414.a5.groupA.monopoly.shared.Board;
import cs414.a5.groupA.monopoly.shared.Space;
import cs414.a5.groupA.monopoly.shared.Token;

public class FreeParking extends Space {

    public FreeParking(Board board, int position) {
        super(board, position);
    }

    @Override
    public void action(Token target) {
        // Nothing happens, but that is intentional
    }
}
