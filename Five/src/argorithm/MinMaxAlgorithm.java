package argorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import logger.GameLogger;
import model.BoardHelper;
import model.Cell;
import model.Stone;

public class MinMaxAlgorithm {

	private final BoardEvaluator evaluator;
	private final BoardHelper boardHelper;
	private final Stone stone;
	private final GameLogger logger = GameLogger.getInstance();
	private final Map<MoveAndValue, List<MoveAndValue>> map = new HashMap<MoveAndValue, List<MoveAndValue>>();
	private static int evaluationCount = 0;

	public MinMaxAlgorithm(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		this.evaluator = new BoardEvaluator(stone, boardHelper);
	}

	public Cell getBestMove(int depth) {
		map.clear();
		int thisCount = evaluationCount;
		MoveAndValue moveAndValue= doGetMinMaxMove(null, depth, Integer.MIN_VALUE, Integer.MAX_VALUE);
		thisCount = evaluationCount - thisCount;
		logger.logInfo("evaluation count: " + thisCount);
		printTree(moveAndValue, "(" + moveAndValue.value + ")");
		return moveAndValue.cell;
	}

	private void printTree(MoveAndValue parent, String s) {
		List<MoveAndValue> list = map.get(parent);

		if (list == null || list.size() == 0) {
			logger.logDebug(s);
			return;
		}

		for(MoveAndValue mav : list) {
			if (mav.value == parent.value) {
				printTree(mav, s + "->" + mav.toString());
			}
		}
	}

	private MoveAndValue doGetMinMaxMove(Cell lastTryMove, int depth, int alpha, int beta) {
		if (lastTryMove != null && boardHelper.checkWin(lastTryMove)) {
			evaluationCount++;
			int value = evaluator.evaluate(evaluationCount);
			return new MoveAndValue(lastTryMove, value, evaluationCount);
		}

		if (depth == 0) {
			evaluationCount++;
			int value = evaluator.evaluate(evaluationCount);
			return new MoveAndValue(lastTryMove, value, evaluationCount);
		}

		List<Cell> cells = boardHelper.getNearAvailable();
		if (cells.isEmpty()) {
			evaluationCount++;
			int value = evaluator.evaluate(evaluationCount);
			return new MoveAndValue(lastTryMove, value, evaluationCount);
		}

		boolean isMaxNode = isMaxNode(lastTryMove);
		Stone targetStone = isMaxNode ? stone : stone.getOpposite();

		List<MoveAndValue> children = new ArrayList<MoveAndValue>();
		Cell minMaxCell = null;
		for(int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);
			boardHelper.tryMove(targetStone, cell);
			MoveAndValue mav = doGetMinMaxMove(cell, depth - 1, alpha, beta);
			children.add(mav);
			boardHelper.rollbackTry();
			int value = mav.value;
			if (isMaxNode) {
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

		if (isMaxNode) {
			return createMAV(lastTryMove, minMaxCell, alpha, children);
		} else {
			return createMAV(lastTryMove, minMaxCell, beta, children);
		}
	}

	private MoveAndValue createMAV(Cell lastTryMove, Cell minMaxCell, int minMaxValue, List<MoveAndValue> children) {
		Cell cell = lastTryMove;
		if (lastTryMove == null) {
			cell = minMaxCell;
		}
		MoveAndValue mav = new MoveAndValue(cell, minMaxValue, 0);
		map.put(mav, children);
		return mav;
	}

	private boolean isMaxNode(Cell lastMove) {
		return lastMove == null || lastMove.getStone() == stone.getOpposite();
	}

	private class MoveAndValue {
		Cell cell;
		int value;
		int boardId;

		public MoveAndValue(Cell cell, int value, int boardId) {
			this.cell = cell;
			this.value = value;
			this.boardId = boardId;
		}

		@Override
		public String toString() {
			return boardId + " " + cell.getPoint().toString() + "(" + value + ")";
		}
	}
}
