package argorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.BoardHelper;
import model.Cell;
import model.Line;
import model.Stone;
import utils.Utils;

public class BoardEvaluator {

	private BoardHelper boardHelper;
	private Stone stone;
	private Set<Line> visitedLines = new HashSet<Line>();
	public static int count = 0;

	private Map<Stone, List<Type>> stoneMap = new HashMap<Stone, List<Type>>();

	public BoardEvaluator(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		stoneMap.put(stone, new ArrayList<Type>());
		stoneMap.put(stone.getOpposite(), new ArrayList<Type>());
	}

	public int evaluate(int boardId) {
		
		List<Type> lineTypeList = stoneMap.get(stone);
		List<Type> lineTypeList2 = stoneMap.get(stone.getOpposite());
		visitedLines.clear();
		lineTypeList.clear();
		lineTypeList2.clear();

		for (Cell cell : boardHelper.getProgress().getCells()) {
			List<Line> lines = Utils.getReferenceLines2(cell);
			for (Line line : lines) {
				if (visitedLines.contains(line)) {
					continue;
				}

				visitorLine(line, stone, lineTypeList);
				visitorLine(line, stone.getOpposite(), lineTypeList2);

				visitedLines.add(line);
			}
		}
		int totalValue = 0;

		return calcValue(totalValue);
	}

	private int calcValue(int totalValue) {
		for(Entry<Stone, List<Type>> e : stoneMap.entrySet()) {
			Stone targetStone = e.getKey();
			List<Type> map = e.getValue();
			int posNeg = (targetStone == stone) ? 1 : -1;
			for(Type ee : map) {
				int value = posNeg * ee.score;
				totalValue += value;
			}
		}
		return totalValue;
	}
	
	

	private void visitorLine(Line line, Stone targetStone, List<Type> lineTypeList) {
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
			lineTypeList.add(types.get(i));
		}
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
			List<Line> lines = Utils.getReferenceLines2(cell);
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
}
