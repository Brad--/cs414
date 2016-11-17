package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.shared.Token;

public class TaxSpace extends Space {

    public TaxSpace(Board board, int position) {
        super(board, position);
    }

    @Override
    public void action(Token target) {
    	// GD 11.15.16 Needs redone after token refactor
//        target.payRent(100);
    }
}
