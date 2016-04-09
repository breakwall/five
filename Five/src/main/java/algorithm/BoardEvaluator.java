package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.BoardHelper;
import model.Cell;
import model.ICellListener;
import model.Line;
import model.Stone;
import utils.Utils;

public class BoardEvaluator implements ICellListener {

	private BoardHelper boardHelper;
	private Stone stone;
	private Set<Line> visitedLines = new HashSet<Line>();
	private Map<Line, Integer> lineValueMap = new HashMap<Line, Integer>();
	private List<Cell> dirtyCells = new ArrayList<Cell>();

	public static int count = 0;

	public BoardEvaluator(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		boardHelper.getBoard().addListener(this);
	}

	public int evaluate() {
		EvaluationCount.generate();
		visitedLines.clear();
		for (Cell cell : dirtyCells) {
			List<Line> lines = boardHelper.getBoard().getReferenceLines(cell);
			for (Line line : lines) {
				if (visitedLines.contains(line)) {
					continue;
				}


				int lineValue = visitorLine(line);
				lineValueMap.put(line, lineValue);
				visitedLines.add(line);
			}
		}
		int totalValue = 0;
		for (int i : lineValueMap.values()) {
			totalValue += i;
		}
		dirtyCells.clear();
		return totalValue;
	}

	private int visitorLine(Line line) {
		Map<Type,Pattern[]> patternMap = PatternMap.getPatterns();
		int lineValue = 0;
		String lineStr = line.getStr();
		for(Stone targetStone : Utils.sides) {
			int posNeg = (stone == targetStone) ? 1 : -1;
			for (Entry<Type, Pattern[]> e : patternMap.entrySet()) {
				Pattern[] patterns = e.getValue();
				for (int i = 0; i < patterns.length; i++) {
					if (!patterns[i].isMatch(targetStone, lineStr)) {
						continue;
					}

					lineValue = lineValue + posNeg * e.getKey().score;
				}
			}
		}

		return lineValue;
	}

	/**
	 * @param focusStone the next move stone
	 * @return all candidate cells
	 */
	public Set<Cell> getThreateningCells(Stone focusStone) {
		visitedLines.clear();
		Set<Line> lines = getLines();

		for(Type type : PatternMap.getKeytypes()) {
			for (Stone s : new Stone[] { focusStone,
					focusStone.getOpposite() }) {
				Set<Cell> candidateCells = getAvailableCells(lines, s, type);
				if (!candidateCells.isEmpty()) {
					return candidateCells;
				}
			}
		}

		return null;
	}

	private Set<Line> getLines() {
		for (Cell cell : boardHelper.getProgress().getCells()) {
			List<Line> lines = boardHelper.getBoard().getReferenceLines(cell);
			for (Line line : lines) {
				visitedLines.add(line);
			}
		}
		return visitedLines;
	}

	private Set<Cell> getAvailableCells(Set<Line> lines, Stone targetStone, Type type) {
		Pattern[] patterns = PatternMap.getPatterns().get(type);
		Set<Cell> cells = new HashSet<>();
		for (Line line : lines) {
			for (Pattern pattern : patterns) {
				List<Cell> list = pattern.getEmptyCells(targetStone, line);
				if (list != null) {
					cells.addAll(list);
				}
			}
		}
		return cells;
	}

	@Override
	public void cellChanged(Cell cell, Stone side) {
		if (side == Stone.NONE || side == stone) {
			dirtyCells.add(cell);
		}
	}
}
