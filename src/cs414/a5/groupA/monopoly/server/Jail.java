package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.shared.Token;

public class Jail extends Space {

    public Jail(Board board, int position) {
        super(board, position);
    }

    @Override
    public void action(Token target) {
        // Do nothing
    }
}
