package algorithm;

public enum Type {
	LIAN5(5000, 5),
	HUO4(2000, 4),
	CHONG4(1000, 4),
	HUO3(600, 3),
	MIAN3(80, 3),
	HUO2(40, 2),
	MIAN2(9, 2);

	int score;
	int count;

	private Type(int score, int count) {
		this.score = score;
		this.count = count;
	}
};