package model;

import argorithm.MinMaxAlgorithm;

public class AIPlayer implements IPlayer{

	private Stone stone;
	private BoardHelper boardHelper;
	private MinMaxAlgorithm algorithm;
	public AIPlayer(Stone stone, BoardHelper boardHelper) {
		this.stone = stone;
		this.boardHelper = boardHelper;
		this.algorithm = new MinMaxAlgorithm(stone, boardHelper);
	}

	@Override
	public Cell getMove() {
		int size = boardHelper.getProgress().size();
		if (size == 0) {
			return boardHelper.getBoard().get(Board.COLUMN / 2, Board.COLUMN / 2);
		}

		return algorithm.getBestMove(4);
	}
}
