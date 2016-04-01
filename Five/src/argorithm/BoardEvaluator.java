package argorithm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import model.BoardHelper;
import model.Cell;
import model.Line;
import model.Progress;
import model.Stone;
import controller.Utils;

public class BoardEvaluator {

	private BoardHelper boardHelper;
	private Stone stone;
//	private GameLogger gameLogger = GameLogger.getInstance();
	public Map<Cell, Line> cellLineMap = new HashMap<Cell, Line>();
	public Progress progressSnap = null;

	public enum Type {
		LIAN5(5000), HUO4(2000), CHONG4(1000), HUO3(600), MIAN3(80), HUO2(40), MIAN2(9);
		int score;
		private Type(int score) {
			this.score = score;
		}
	};

	private Map<Stone, List<Type>> stoneMap = new HashMap<Stone, List<Type>>();

	public BoardEvaluator(Stone stone, BoardHelper boardHelper) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		stoneMap.put(stone, new ArrayList<Type>());
		stoneMap.put(stone.getOpposite(), new ArrayList<Type>());
	}

	public int evaluate(int boardId) {
		stoneMap.get(stone).clear();
		stoneMap.get(stone.getOpposite()).clear();
		Set<Line> visitedLines = new HashSet<Line>();

		List<Type> lineTypeList = stoneMap.get(stone);
		List<Type> lineTypeList2 = stoneMap.get(stone.getOpposite());

		for (Cell cell : boardHelper.getNearAvailable()) {
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
}
