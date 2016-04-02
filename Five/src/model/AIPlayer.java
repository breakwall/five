package model;

import utils.GameConstants;
import argorithm.MinMaxAlgorithm;

public class AIPlayer implements IPlayer{

	private BoardHelper boardHelper;
	private MinMaxAlgorithm algorithm;
	public static long totalTime = 0;
	public static long onceTime = 0;
	public AIPlayer(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.algorithm = new MinMaxAlgorithm(stone, boardHelper);
	}

	@Override
	public Cell getMove() {
		long start = System.currentTimeMillis();
		int size = boardHelper.getProgress().size();
		if (size == 0) {
			return boardHelper.getBoard().get(Board.COLUMN / 2, Board.COLUMN / 2);
		}

		Cell cell = algorithm.getBestMove(GameConstants.depth);
		onceTime = System.currentTimeMillis() - start;
		totalTime += onceTime;
		return cell;
	}
}
