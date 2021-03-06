package cs414.a5.groupA.monopoly.server;

import cs414.a5.groupA.monopoly.shared.Token;

abstract class Space {

    protected int position;
    protected String name;
    protected Token owner;
    protected boolean isMortgaged;

    Space(int position) {
        this.position = position;
        this.isMortgaged = false;
        owner = null;
    }

//    abstract void action(Token target);

    public int getPosition() {
        return position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Token getOwner() {
        return owner;
    }

	public boolean isMortgaged() {
		return isMortgaged;
	}

	public void setMortgaged(boolean isMortgaged) {
		this.isMortgaged = isMortgaged;
	}
}