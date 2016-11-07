package cs414.a5.groupA.monopoly.server;

abstract class Space {

    // Not 0 based, Go is 1
    protected int position;
    protected Board board;
    protected String name;
    protected Token owner;

    Space(Board board, int position) {
        this.position = position;
        this.board = board;
        owner = null;
    }

    abstract void action(Token target);

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
}