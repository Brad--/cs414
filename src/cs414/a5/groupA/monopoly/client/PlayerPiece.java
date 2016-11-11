package cs414.a5.groupA.monopoly.client;

public class PlayerPiece {

	private String name;
	private String gamePiece;
	private int money;
	
	public PlayerPiece(String name, String piece, int money) {
		this.setName(name);
		this.setGamePiece(piece);
		this.setMoney(money);
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGamePiece() {
		return gamePiece;
	}

	public void setGamePiece(String gamePiece) {
		this.gamePiece = gamePiece;
	}

	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}
}
