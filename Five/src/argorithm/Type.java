package argorithm;

public enum Type {
	LIAN5(5000), HUO4(2000), CHONG4(1000), HUO3(600), MIAN3(80), HUO2(40), MIAN2(
			9);
	int score;

	private Type(int score) {
		this.score = score;
	}
};