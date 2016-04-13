package algorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
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
		int lineValue = 0;
		for (Stone targetStone : Utils.sides) {
			int posNeg = (stone == targetStone) ? 1 : -1;
			List<Type> types = PatternMap.getLineScore(targetStone, line, Utils.ALL_TYPES);
			for(int i = 0; i < types.size(); i++) {
				lineValue += posNeg * types.get(i).score;
			}
		}

		return lineValue;
	}

	/**
	 * @param focusStone
	 *            the next move stone
	 * @return all candidate cells
	 */
	public Set<Cell> getEmptyCellsByTypes(Stone focusStone) {
		Set<Line> lines = getLines();

		// get empty cells by key type lines
		Stone[] sides = new Stone[] { focusStone, focusStone.getOpposite() };
		for (Type type : Utils.KEY_TYPES) {
			for (Stone s : sides) {
				Set<Cell> candidateCells = getCellsByType(lines, s, type);
				if (!candidateCells.isEmpty()) {
					return candidateCells;
				}
			}
		}

		// get empty cells by two lines which intersect
		for (Stone s : sides) {
			Set<Cell> candidateCells = getCellsBy2Type(lines, s);
			if (!candidateCells.isEmpty()) {
				return candidateCells;
			}
		}


		// get empty cells by non key type lines
//		Set<Cell> cells = new LinkedHashSet<>();
//		for (Type type : Utils.NON_KEY_TYPES) {
//			for (Stone s: sides) {
//				Set<Cell> candidateCells = getCellsByType(lines, s, type);
//				if (!candidateCells.isEmpty()) {
//					cells.addAll(candidateCells);
//				}
//			}
//		}
//
//		if (!cells.isEmpty()) {
//			return cells;
//		}

		return null;
	}

	private Set<Cell> getCellsBy2Type(Set<Line> lines, Stone s) {
		Set<Cell> set = new LinkedHashSet<>();
		Map<Line, Set<Cell>> lineCellMap = new LinkedHashMap<>();
		for (Line line : lines) {
			Set<Cell> lineEmptyCells = PatternMap.getEmptyCells(s, line,
					Utils.INTERSECT_TO_KEY_TYPES);
			if (lineEmptyCells.isEmpty()) {
				continue;
			}

			findTwiceEmptyCells(lineCellMap, line, lineEmptyCells, set);
			lineCellMap.put(line, lineEmptyCells);
		}

		return set;
	}

	private void findTwiceEmptyCells(Map<Line, Set<Cell>> lineCellMap,
			Line line, Set<Cell> lineEmptyCells, Set<Cell> twiceEmptyCells) {

		for (Entry<Line, Set<Cell>> e : lineCellMap.entrySet()) {
			if (e.getKey().getDirection() == line.getDirection()) {
				continue;
			}

			for (Cell cell : e.getValue()) {
				if (!twiceEmptyCells.contains(cell)
						&& lineEmptyCells.contains(cell)) {
					twiceEmptyCells.add(cell);
				}
			}
		}
	}

	private Set<Line> getLines() {
		visitedLines.clear();
		for (Cell cell : boardHelper.getProgress().getCells()) {
			List<Line> lines = boardHelper.getBoard().getReferenceLines(cell);
			for (Line line : lines) {
				visitedLines.add(line);
			}
		}
		return visitedLines;
	}

	private Set<Cell> getCellsByType(Set<Line> lines, Stone targetStone,
			Type type) {
		Pattern[] patterns = PatternMap.getPatterns().get(type);
		Set<Cell> cells = new LinkedHashSet<>();
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
