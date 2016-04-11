package algorithm;

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
	private int pruningCount = 0;
	private MoveAndValue moveAndValue = new MoveAndValue();

	public MinMaxAlgorithm(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		this.evaluator = new BoardEvaluator(stone, boardHelper);
	}

	public Cell getBestMove(int depth) {
		pruningCount = 0;
		int thisCount = EvaluationCount.getCount();
		MoveAndValue moveAndValue= doGetMinMaxMove(null, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		thisCount = EvaluationCount.getCount() - thisCount;
		logger.logInfo("evaluation count: " + thisCount + "/" + EvaluationCount.getCount() + ", pruningCount: " + pruningCount + ", value: " + moveAndValue.value);
		return moveAndValue.cell;
	}

	private MoveAndValue doGetMinMaxMove(Cell lastTryMove, int depth, int alpha, int beta, boolean isMax) {
		if (lastTryMove != null && boardHelper.checkWin(lastTryMove)) {
			int value = evaluator.evaluate();
			return moveAndValue.get(lastTryMove, value, EvaluationCount.getCount());
		}

		if (depth == 0) {
			int value = evaluator.evaluate();
			return moveAndValue.get(lastTryMove, value, EvaluationCount.getCount());
		}

		Stone targetStone = isMax ? stone : stone.getOpposite();
		Set<Cell> cells = getCandidateCells(targetStone);
		if (cells.isEmpty()) {
			int value = evaluator.evaluate();
			return moveAndValue.get(lastTryMove, value, EvaluationCount.getCount());
		}

		Cell minMaxCell = null;
		for (Cell cell : cells) {
			boardHelper.tryMove(targetStone, cell, stone);
			MoveAndValue mav = doGetMinMaxMove(cell, depth - 1, alpha, beta,
					!isMax);

//			if (lastTryMove == null) {
//				logger.logInfo(mav.value + "->" + cell.toString() + "->"
//						+ mav.cell + ";");
//			}

			boardHelper.rollbackTry(stone);
			int value = mav.value;
			if (isMax) {
				// max
				if (value > alpha) {
					alpha = value;
					minMaxCell = cell;
					if (alpha >= beta) {
						pruningCount ++;
						break;
					}
				}
			} else {
				// min
				if (value < beta) {
					beta = value;
					minMaxCell = cell;
					if (alpha >= beta) {
						pruningCount ++;
						break;
					}
				}
			}
		}


//		if (lastTryMove == null) {
//			System.out.println();
//		}

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

	private Set<Cell> getCandidateCells(Stone focusStone) {
		Set<Cell> cells = evaluator.getEmptyCellsByTypes(focusStone);
		if (cells == null) {
			return boardHelper.getNearAvailable();
//		} else {
//			logger.logInfo("" + cells);
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
