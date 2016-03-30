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
	private static int count = 0;

	public MinMaxAlgorithm(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		this.evaluator = new BoardEvaluator(stone, boardHelper);
	}

	public Cell getBestMove(int depth) {
		map.clear();
		MoveAndValue moveAndValue= doGetMinMaxMove(null, depth);
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
			printTree(mav, s + "->" + mav.toString());
		}
	}

	private MoveAndValue doGetMinMaxMove(Cell lastTryMove, int depth) {
		if (lastTryMove != null && boardHelper.checkWin(lastTryMove)) {
			String boardId = "" + count;
			int value = evaluator.evaluate(boardId);
			count++;
			return new MoveAndValue(lastTryMove, value, boardId);
		}

		if (depth == 0) {
			String boardId = "" + count;
			int value = evaluator.evaluate(boardId);
			count++;
			return new MoveAndValue(lastTryMove, value, boardId);
		}

		List<Cell> cells = boardHelper.getNearAvailable();
		if (cells.isEmpty()) {
			String boardId = "" + count;
			int value = evaluator.evaluate(boardId);
			count++;
			return new MoveAndValue(lastTryMove, value, boardId);
		}

		boolean isMaxNode = isMaxNode(lastTryMove);
		int minMaxValue = isMaxNode ? Integer.MIN_VALUE : Integer.MAX_VALUE;
		Stone targetStone = isMaxNode ? stone : stone.getOpposite();

		List<MoveAndValue> children = new ArrayList<MoveAndValue>();
		Cell minMaxCell = null;
		for(int i = 0; i < cells.size(); i++) {
			Cell cell = cells.get(i);
			boardHelper.tryMove(targetStone, cell);
			MoveAndValue mav = doGetMinMaxMove(cell, depth - 1);
			children.add(mav);
			int value = mav.value;
			if (isMaxNode) {
				// max
	 			if (value > minMaxValue) {
					minMaxValue = value;
					minMaxCell = cell;
				}
			} else {
				// min
	 			if (value < minMaxValue) {
					minMaxValue = value;
					minMaxCell = cell;
				}
			}
			boardHelper.rollbackTry();
		}


		Cell cell = lastTryMove;
		if (lastTryMove == null) {
			cell = minMaxCell;
		}
		MoveAndValue mav = new MoveAndValue(cell, minMaxValue, "");
		map.put(mav, children);
		return mav;
	}

	private boolean isMaxNode(Cell lastMove) {
		return lastMove == null || lastMove.getStone() == stone.getOpposite();
	}

	private class MoveAndValue {
		Cell cell;
		int value;
		String boardId;

		public MoveAndValue(Cell cell, int value, String boardId) {
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
