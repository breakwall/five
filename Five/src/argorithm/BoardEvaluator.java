package argorithm;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.BoardHelper;
import model.Cell;
import model.Direction;
import model.Line;
import model.Stone;
import controller.Utils;

public class BoardEvaluator {
	
	private BoardHelper boardHelper;
	private Stone stone;
	private Map<String, Integer> valueMap = new HashMap<String, Integer>();

	private enum Type {
		LIAN5, HUO4, CHONG4, HUO3, MIAN3, HUO2, MIAN2
	};
	
	public BoardEvaluator(BoardHelper boardHelper, Stone stone) {
		this.boardHelper = boardHelper;
		this.stone = stone;
		initValueMap();
	}
	
	private void initValueMap(int stoneNumber, int length) {
		if(stoneNumber == 1) {

		}
	}
	
	private String getString(int index, int length) {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < length; i++) {
			if (index == i) {
				sb.append(stone.str);
			} else {
				sb.append(".");
			}
			
		}
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
			getLineValue();
		}
	}
	
	private int getLineValue(Line line) {
		
		if (isLian5(lineStr)) {
			type = Type.LIAN5;
		} else if (isHuo4(line, lineStr)) {
			
		} else {
			
		}
	}
	
	private Type getType(Line line) {
		String lineStr = line.getStr();
		Type type = null;
		
		String lian5 = Utils.getStrPattern("11111", stone);
		if (lineStr.contains(lian5)) {
			return Type.LIAN5;
		}
		
		String huo4 = Utils.getStrPattern("1111", stone);
		int index = lineStr.indexOf(huo4);
		if(index != -1) {
			Cell cell = line.getCell(index);
			Direction direction = line.getDirection();
			Cell forward = cell.getNearbyCell(direction.getOpposite());
			Cell backward = cell.getNearbyCell(direction);
			if (forward.getStone() == Stone.NONE
					&& backward.getStone() == Stone.NONE) {
				return Type.HUO4; 
			}
		}
		
		return type;
	}
	
	private boolean isLian5(String lineStr) {
		return lineStr.contains(lian5);
	}
	
	private boolean isHuo4(Line line, String lineStr) {
	}
}
