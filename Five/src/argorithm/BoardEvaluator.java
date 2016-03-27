package argorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BoardHelper;
import model.Cell;
import model.Line;
import model.Stone;
import controller.Utils;

public class BoardEvaluator {
	
	private BoardHelper boardHelper;
	private Stone stone;
	private static Map<String, Integer> valueMap = new HashMap<String, Integer>();
	
	public BoardEvaluator(BoardHelper boardHelper, Stone stone) {
		this.boardHelper = boardHelper;
		this.stone = stone;
	}
	
	public int evaluate() {
		int maxValue = 0;
		for (Cell cell : boardHelper.getNearAvailable()) {
			int value = getValueOfCell(cell);
			if (value > maxValue) {
				maxValue = value;
			}
		}
		
		return maxValue;
	}
	
	private int getValueOfCell(Cell cell) {
		List<Line> lines = Utils.getReferenceLines(cell);
		for (Line line : lines) {
		}
	}
}
