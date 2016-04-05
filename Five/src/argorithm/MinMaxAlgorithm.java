package argorithm;

import java.util.Set;

import logger.GameLogger;
import model.BoardHelper;
import model.Cell;
import model.Stone;

public class MinMaxAlgorithm {

	private final BoardEvaluator evaluator;
	private final BoardHelper boardHelper;
	private final Stone stone;
	private final GameLogger logger = GameLogger.getInstance();
	public static int evaluationCount = 0;
	private MoveAndValue moveAndValue = new MoveAndValue();

	public MinMaxAlgorithm(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		this.evaluator = new BoardEvaluator(stone, boardHelper);
	}

	public Cell getBestMove(int depth) {
		int thisCount = evaluationCount;
		MoveAndValue moveAndValue= doGetMinMaxMove(null, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		thisCount = evaluationCount - thisCount;
		logger.logInfo("evaluation count: " + thisCount + "/" + evaluationCount);
		return moveAndValue.cell;
	}

	private MoveAndValue doGetMinMaxMove(Cell lastTryMove, int depth, int alpha, int beta, boolean isMax) {
		if (lastTryMove != null && boardHelper.checkWin(lastTryMove)) {
			evaluationCount++;
			int value = evaluator.evaluate(evaluationCount);
			return moveAndValue.get(lastTryMove, value, evaluationCount);
		}

		if (depth == 0) {
			evaluationCount++;
			int value = evaluator.evaluate(evaluationCount);
			return moveAndValue.get(lastTryMove, value, evaluationCount);
		}

		Set<Cell> cells = getCandidateCells();
		if (cells.isEmpty()) {
			evaluationCount++;
			int value = evaluator.evaluate(evaluationCount);
			return moveAndValue.get(lastTryMove, value, evaluationCount);
		}

		Stone targetStone = isMax ? stone : stone.getOpposite();

		Cell minMaxCell = null;
		for (Cell cell : cells) {
			boardHelper.tryMove(targetStone, cell, stone);
			MoveAndValue mav = doGetMinMaxMove(cell, depth - 1, alpha, beta,
					!isMax);
			boardHelper.rollbackTry(stone);
			int value = mav.value;
			if (isMax) {
				// max
				if (value > alpha) {
					alpha = value;
					minMaxCell = cell;
					if (alpha >= beta) {
						break;
					}
				}
			} else {
				// min
				if (value < beta) {
					beta = value;
					minMaxCell = cell;
					if (alpha >= beta) {
						break;
					}
				}
			}
		}

		if (isMax) {
			return createMAV(minMaxCell, alpha);
		} else {
			return createMAV(minMaxCell, beta);
		}
	}

	private MoveAndValue createMAV(Cell minMaxCell, int minMaxValue) {
		MoveAndValue mav = moveAndValue.get(minMaxCell, minMaxValue, 0);
		return mav;
	}

	private Set<Cell> getCandidateCells() {
		Set<Cell> cells = evaluator.getThreateningCells();
		if (cells.isEmpty()) {
			return boardHelper.getNearAvailable();
		}
		return cells;
	}

	private class MoveAndValue {
		Cell cell;
		int value;
		int boardId;

		MoveAndValue get(Cell cell, int value, int boardId) {
			this.cell = cell;
			this.value = value;
			this.boardId = boardId;
			return this;
		}

		@Override
		public String toString() {
			return boardId + " " + cell.toString() + "(" + value + ")";
		}
	}
}
