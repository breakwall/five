package argorithm;

import java.util.List;

import logger.GameLogger;
import model.BoardHelper;
import model.Cell;
import model.Stone;

public class MinMaxAlgorithm {

	private final BoardEvaluator evaluator;
	private final BoardHelper boardHelper;
	private final Stone stone;
	private final GameLogger logger = GameLogger.getInstance();

	public MinMaxAlgorithm(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		this.evaluator = new BoardEvaluator(stone, boardHelper);
	}

	public Cell getBestMove(int depth) {
		MoveAndValue moveAndValue= doGetMaxMove(null, depth);
		return moveAndValue.cell;
	}

	private MoveAndValue doGetMaxMove(Cell lastMove, int depth) {
		if (lastMove != null && boardHelper.checkWin(lastMove)) {
			int value = evaluator.evaluate();
			logger.logDebug("return because check "+ lastMove.getStone() +" is win.");
			return new MoveAndValue(lastMove, value);
		}

		if (depth == 0) {
			int value = evaluator.evaluate();
			logger.logDebug("return because depth is 0.");
			return new MoveAndValue(lastMove, value);
		}

		List<Cell> cells = boardHelper.getNearAvailable();
		if (cells.isEmpty()) {
			int value = evaluator.evaluate();
			logger.logDebug("return because there is no available cell to move.");
			return new MoveAndValue(lastMove, value);
		}

		boolean isMaxNode = isMaxNode(lastMove);
		int minMaxValue = isMaxNode ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		Stone targetStone = isMaxNode ? stone : stone.getOpposite();

		Cell minMaxCell = null;
		for(Cell cell : cells) {
			boardHelper.tryMove(targetStone, cell);
			logger.logDebug("depth: " + depth + ", try Move " + targetStone + ", " + cell);
			logger.logDebug(boardHelper.getBoardStr());
			int value = doGetMaxMove(cell, depth - 1).value;
			logger.logDebug("depth: " + depth + ", value is " + value);
			if (isMaxNode) {
				// max
	 			if (value > minMaxValue) {
					minMaxValue = value;
					minMaxCell = cell;
					logger.logDebug("depth: " + depth + ", max value: " + minMaxValue);
				}
			} else {
				// min
	 			if (value < minMaxValue) {
					minMaxValue = value;
					minMaxCell = cell;
					logger.logDebug("depth: " + depth + ", min value: " + minMaxValue);
				}
			}
			boardHelper.rollbackTry();
		}

		return new MoveAndValue(minMaxCell, minMaxValue);
	}

	private boolean isMaxNode(Cell lastMove) {
		return lastMove == null || lastMove.getStone() == stone.getOpposite();
	}

	private class MoveAndValue {
		Cell cell;
		int value;

		public MoveAndValue(Cell cell, int value) {
			this.cell = cell;
			this.value = value;
		}
	}
}
