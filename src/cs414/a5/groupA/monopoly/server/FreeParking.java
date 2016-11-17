package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.server.Space;
import cs414.a5.groupA.monopoly.shared.Token;

public class FreeParking extends Space {

    public FreeParking(int position) {
        super(position);
    }

    @Override
    public void action(Token target) {
        // Nothing happens, but that is intentional
    }
}
