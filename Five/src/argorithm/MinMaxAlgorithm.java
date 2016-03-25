package argorithm;

import java.util.List;

import model.BoardHelper;
import model.Cell;
import model.Stone;

public class MinMaxAlgorithm {
	
	private BoardEvaluator evaluator;
	private BoardHelper boardHelper;
	public MinMaxAlgorithm(BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.evaluator = new BoardEvaluator(boardHelper);
	}
	
	public Cell getBestMove(Stone stone, int depth) {
		List<Cell> cells = boardHelper.getNearAvailable();
		if (cells.isEmpty()) {
			return null;
		}
		
		int maxValue = 0;
		Cell bestCell = null;
		for (Cell nearCell : cells) {
			boardHelper.tryMove(stone, nearCell);
			int value = doGetMinMove(stone, depth - 1);
			if (value > maxValue) {
				maxValue = value;
				bestCell = nearCell;
			}
			boardHelper.rollbackTry();
		}
		
		return bestCell;
	}
	
	private int doGetMaxMove(Stone stone, int depth) {
		List<Cell> cells = boardHelper.getNearAvailable();
		if (cells.isEmpty() || depth == 0) {
			return evaluator.evaluate(stone);
		}
		
		int maxValue = 0;
		for(Cell cell : cells) {
			boardHelper.tryMove(stone, cell);
			int value = doGetMinMove(stone, depth - 1);
			if (value > maxValue) {
				maxValue = value;
			}
			boardHelper.rollbackTry();
		}
		
		return maxValue;
	}
	
	private int doGetMinMove(Stone stone, int depth) {
		List<Cell> cells = boardHelper.getNearAvailable();
		if (cells.isEmpty() || depth == 0) {
			return evaluator.evaluate(stone);
		}
		
		int minValue = 0;
		for(Cell cell : cells) {
			boardHelper.tryMove(stone.getOpposite(), cell);
			int value = doGetMaxMove(stone, depth - 1);
			if (value < minValue) {
				minValue = value;
			}
			boardHelper.rollbackTry();
		}
		
		return minValue;
	}
}
