package algorithm;

import java.util.Set;

import logger.GameLogger;
import logger.Statistics;
import model.BoardHelper;
import model.Cell;
import model.Stone;
import utils.GameConstants;

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

		EvaluationCount.beginEvalute();
		Statistics.addRootNode();
		MoveAndValue moveAndValue= doGetMinMaxMove(null, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true, false);
		Statistics.rootNodeOver(moveAndValue.cell, moveAndValue.value);
		EvaluationCount.endEvaluate();

		logger.logInfo("evaluation count: " + EvaluationCount.getCount() + "/" + EvaluationCount.getTotalCount() + ", pruningCount: " + pruningCount + ", value: " + moveAndValue.value);
		return moveAndValue.cell;
	}

	private MoveAndValue doGetMinMaxMove(Cell lastTryMove, int depth, int alpha, int beta, boolean isMax, boolean keyTypeEmptyCell) {
		if (lastTryMove != null && boardHelper.checkWin(lastTryMove)) {
			int value = evaluator.evaluate();
			return moveAndValue.get(lastTryMove, value, EvaluationCount.getCount());
		}

		if (!isSearchDeeper(keyTypeEmptyCell, depth)) {
			int value = evaluator.evaluate();
			return moveAndValue.get(lastTryMove, value, EvaluationCount.getCount());
		}

		Stone targetStone = isMax ? stone : stone.getOpposite();

		Set<Cell> cells = evaluator.getEmptyCellsByTypes(targetStone);
		if (cells == null) {
			cells =  boardHelper.getNearAvailable();
			keyTypeEmptyCell = false;
		} else {
			keyTypeEmptyCell = true;
		}

		if (cells.isEmpty()) {
			// no available cells to move
			int value = evaluator.evaluate();
			return moveAndValue.get(lastTryMove, value, EvaluationCount.getCount());
		}

		Cell minMaxCell = null;
		for (Cell cell : cells) {
			// try move
			boardHelper.tryMove(targetStone, cell, stone);
			Statistics.tryMove(cell);
			// do min/max evaluate
			MoveAndValue mav = doGetMinMaxMove(cell, depth + 1, alpha, beta, !isMax, keyTypeEmptyCell);
//			if (depth == GameConstants.DEPTH) {
//				logger.logInfo(mav.value + "->" + cell.toString() + "->"
//						+ mav.cell + ";");
//			}
			// roll back move
			boardHelper.rollbackTry(stone);
			Statistics.rollbackMove(mav.value);
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

		Statistics.setSelectedChild(minMaxCell);

		if (isMax) {
			return createMAV(minMaxCell, alpha);
		} else {
			return createMAV(minMaxCell, beta);
		}
	}

	private boolean isSearchDeeper(boolean keyTypeEmptyCell, int depth) {
//		return depth != GameConstants.DEPTH;
		if (depth < GameConstants.DEPTH) {
			return true;
		}

		boolean b = (depth % 2 == 0);
		if (!b) {
			return true;
		}

		return keyTypeEmptyCell
				&& depth < GameConstants.MAX_DEPTH
				&& EvaluationCount.getCount() < GameConstants.MAX_EVALUATION_COUNT;
	}

	private MoveAndValue createMAV(Cell minMaxCell, int minMaxValue) {
		MoveAndValue mav = moveAndValue.get(minMaxCell, minMaxValue, 0);
		return mav;
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
