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
		MoveAndValue moveAndValue= doGetMinMaxMove(null, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		Statistics.rootNodeOver(moveAndValue.cell, moveAndValue.value);
		EvaluationCount.endEvaluate();

		logger.logInfo("evaluation count: " + EvaluationCount.getCount() + "/" + EvaluationCount.getTotalCount() + ", pruningCount: " + pruningCount + ", value: " + moveAndValue.value);
		return moveAndValue.cell;
	}

	private MoveAndValue doGetMinMaxMove(Cell lastTryMove, int depth, int alpha, int beta, boolean isMax) {
		if (lastTryMove != null && boardHelper.checkWin(lastTryMove)) {
			int value = evaluator.evaluate();
			return moveAndValue.get(lastTryMove, value);
		}

		Stone targetStone = isMax ? stone : stone.getOpposite();
		Set<Cell> cells = evaluator.getEmptyCellsByTypes(targetStone);

		boolean isSearchDeeper = depth < GameConstants.DEPTH
				|| (depth < GameConstants.MAX_DEPTH && cells != null);

		if (!isSearchDeeper) {
			int value = evaluator.evaluate();
			return moveAndValue.get(lastTryMove, value);
		}

		if (cells == null) {
			cells =  boardHelper.getNearAvailable();
		}

		if (cells.isEmpty()) {
			// no available cells to move
			int value = evaluator.evaluate();
			return moveAndValue.get(lastTryMove, value);
		}
		
		if (cells.size() == 1 && depth == 0) {
			int value = evaluator.evaluate();
			return moveAndValue.get(cells.iterator().next(), value);
		}

		Cell minMaxCell = null;
		for (Cell cell : cells) {
			// try move
			boardHelper.tryMove(targetStone, cell, stone);
			Statistics.tryMove(cell);
			// do min/max evaluate
			MoveAndValue mav = doGetMinMaxMove(cell, depth + 1, alpha, beta, !isMax);
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
			return getMAV(minMaxCell, alpha);
		} else {
			return getMAV(minMaxCell, beta);
		}
	}

	private MoveAndValue getMAV(Cell minMaxCell, int minMaxValue) {
		return moveAndValue.get(minMaxCell, minMaxValue);
	}

	private class MoveAndValue {
		Cell cell;
		int value;

		MoveAndValue get(Cell cell, int value) {
			this.cell = cell;
			this.value = value;
			return this;
		}
	}
}
