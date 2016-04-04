package argorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
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

	public int evaluate(int boardId) {
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
		for(Stone targetStone : Utils.sides) {
			int posNeg = (stone == targetStone) ? 1 : -1;
			String lineStr = line.getStr(targetStone);
			List<String> patterns = PatternMap.getPatterns(targetStone);
			List<Type> types = PatternMap.getTypes();
			boolean isMatchHuo3 = false;
			for (int i = 0; i < patterns.size(); i++) {
				if (lineStr.indexOf(patterns.get(i)) == -1) {
					continue;
				}

				if (types.get(i) == Type.HUO3) {
					if (isMatchHuo3) {
						continue;
					} else {
						isMatchHuo3 = true;
					}
				}
				lineValue = lineValue + posNeg * types.get(i).score;
			}
		}
		
		return lineValue;
	}
	
	public Set<Cell> getThreateningCells() {
		visitedLines.clear();
		Set<Cell> threateningCells = new LinkedHashSet<Cell>();
		Set<Line> lines = getLines();
		for(Stone s : Utils.sides){
			List<String> patterns = PatternMap.getSPatterns(s);
			for (int i = 0; i < patterns.size(); i++) {
				String pattern = patterns.get(i);
				for (Line line : lines) {
					List<Cell> list = getAvailableCells(line, s,
							pattern);
					if (list != null) {
						threateningCells.addAll(list);
					}
				}
			}
		}
		
		return threateningCells;
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
	
	private List<Cell> getAvailableCells(Line line, Stone targetStone, String pattern) {
		String lineStr = line.getStr(targetStone);
		int index = lineStr.indexOf(pattern);
		if (index == -1) {
			return null;
		}
		
		List<Cell> cells = new ArrayList<Cell>();
		char blankChar = Stone.NONE.chr;
		for (int i = 0; i < pattern.length(); i++) {
			if (pattern.charAt(i) == blankChar) {
				cells.add(line.getCell(index - 1 + i));
			}
		}
		return cells;
	}

	@Override
	public void cellChanged(Cell cell, Stone oldVal, Stone newVal) {
		dirtyCells.add(cell);
	}
}
