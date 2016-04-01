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
//	private final Map<MoveAndValue, List<MoveAndValue>> map = new HashMap<MoveAndValue, List<MoveAndValue>>();
	private static int evaluationCount = 0;
	private MoveAndValue moveAndValue = new MoveAndValue();

	public MinMaxAlgorithm(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		this.evaluator = new BoardEvaluator(stone, boardHelper);
	}

	public Cell getBestMove(int depth) {
//		map.clear();
		int thisCount = evaluationCount;
		MoveAndValue moveAndValue= doGetMinMaxMove(null, depth, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		thisCount = evaluationCount - thisCount;
		logger.logInfo("evaluation count: " + thisCount + "/" + evaluationCount);
//		printTree(moveAndValue, "(" + moveAndValue.value + ")");
		return moveAndValue.cell;
	}

//	private void printTree(MoveAndValue parent, String s) {
//		List<MoveAndValue> list = map.get(parent);
//
//		if (list == null || list.size() == 0) {
//			logger.logDebug(s);
//			return;
//		}
//
//		for(MoveAndValue mav : list) {
//			if (mav.value == parent.value) {
//				printTree(mav, s + "->" + mav.toString());
//			}
//		}
//	}

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

		List<Cell> cells = boardHelper.getNearAvailable();
		if (cells.isEmpty()) {
			evaluationCount++;
			int value = evaluator.evaluate(evaluationCount);
			return moveAndValue.get(lastTryMove, value, evaluationCount);
		}

		Stone targetStone = isMax ? stone : stone.getOpposite();

		Cell minMaxCell = null;
		if (isMax) {
			for(int i = 0; i < cells.size(); i++) {
				Cell cell = cells.get(i);
				boardHelper.tryMove(targetStone, cell);
				MoveAndValue mav = doGetMinMaxMove(cell, depth - 1, alpha, beta, !isMax);
				boardHelper.rollbackTry();
				int value = mav.value;
				// max
	 			if (value > alpha) {
	 				alpha = value;
					minMaxCell = cell;
					if (alpha >= beta) {
						break;
					}
				}
			}

			return createMAV(lastTryMove, minMaxCell, alpha);
		} else {
			for(int i = 0; i < cells.size(); i++) {
				Cell cell = cells.get(i);
				boardHelper.tryMove(targetStone, cell);
				MoveAndValue mav = doGetMinMaxMove(cell, depth - 1, alpha, beta, !isMax);
				boardHelper.rollbackTry();
				int value = mav.value;
				// min
	 			if (value < beta) {
	 				beta = value;
					minMaxCell = cell;
					if (alpha >= beta) {
						break;
					}
				}
			}

			return createMAV(lastTryMove, minMaxCell, beta);
		}
	}

	private MoveAndValue createMAV(Cell lastTryMove, Cell minMaxCell, int minMaxValue) {
		Cell cell = lastTryMove;
		if (lastTryMove == null) {
			cell = minMaxCell;
		}
		MoveAndValue mav = moveAndValue.get(cell, minMaxValue, 0);
//		map.put(mav, children);
		return mav;
	}

	private boolean isMaxNode(Cell lastMove) {
		return lastMove == null || lastMove.getStone() == stone.getOpposite();
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
