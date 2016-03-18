package model;

import java.util.ArrayList;
import java.util.List;

import controller.Utils;

public class Computer {
	
	private Stone stone;
	private TableHelper tableHelper;
	public Computer(Stone stone, TableHelper tableHelper) {
		this.stone = stone;
		this.tableHelper = tableHelper;
	}
	
	public Point getStepPoint() {
		Step lastStep = tableHelper.getLastStep();
		if (lastStep == null) {
			return Utils.random(Point.get(Table.COLUMN / 2, Table.COLUMN / 2), 2);
		}
		
		List<Cell> cells = tableHelper.getProgress().getCells(stone);
		if (cells.size() < 1) {
			Cell lastCell = lastStep.getCell();
			List<Cell> available = tableHelper.getNearAvailable(lastCell.getPoint());
			if (!available.isEmpty()) {
				return available.get(Utils.random(available.size())).getPoint();
			}
		}
		
		List<Cell> nearAvailable = tableHelper.getNearAvailable();
		if (nearAvailable.isEmpty()) {
			return null;
		}
		
		Point defencePoint = getDefencePoint();
		if (defencePoint != null) {
			System.out.println("defence");
			return defencePoint;
		}
		System.out.println("attack");
		Point candidatePoint = null;
		int candidateLinePriority = 0;
		
		for (Cell ne : nearAvailable) {
			List<Line> lines = Utils.getReferenceLines(ne.getPoint());
			for (Line line : lines) {
				Line tmpLine = getCandidateLine(stone, ne, line);
//				System.out.println("candidate line " + tmpLine);
				if (tmpLine != null) {
					int linePriority = getLinePriority(line);
//					System.out.println("priority: " + linePriority) ;
					if (linePriority > candidateLinePriority) {
						candidatePoint = ne.getPoint();
						candidateLinePriority = linePriority;
					}
				}
			}
		}
		
		return candidatePoint;
	}
	
	private Point getDefencePoint() {
		Table table = tableHelper.getTable();
		Step lastStep = tableHelper.getLastStep();
		Cell lastCell = lastStep.getCell();
		List<Line> lines = Utils.getReferenceLines(lastCell.getPoint());
		for (Line line : lines) {
			Line defenceLine = getDefenceLine(lastCell, line);
			if (defenceLine == null) {
				continue;
			}
			
			Point forwardPoint = defenceLine.getForwardPoint();
			Point backwardPoint = defenceLine.getBackwardPoint();
			if (forwardPoint != null
					&& table.get(forwardPoint).getStone() == Stone.NONE) {
				return forwardPoint;
			}

			if (backwardPoint != null
					&& table.get(backwardPoint).getStone() == Stone.NONE) {
				return backwardPoint;
			}
		}
		
		return null;
	}
	
	private int getLinePriority(Line line) {
		Table table = tableHelper.getTable();
		int priority = 0;
		for (Point point : line.getPoints()) {
			if (table.get(point).getStone() == stone) {
				priority ++;
			}
		}
		
		return priority;
	}
	
	private Line getDefenceLine(Cell cell, Line line) {
		List<Line> subLines = new ArrayList<Line>();
		Stone oppositeStone = stone.getOpposite();
		int startIndex = -1;
		for (int i = 0; i < line.size(); i++) {
			Cell c = tableHelper.getTable().get(line.getPoint(i));
			if (c.getStone() == oppositeStone) {
				if (startIndex == -1) {
					startIndex = i;					
				}
				
				if (startIndex != -1 && i == line.size() - 1 && i - startIndex + 1 >= 3) {
					subLines.add(line.getSubLine(startIndex, i));
				}
			} else {
				if (startIndex != -1 && i - startIndex >= 3) {
					subLines.add(line.getSubLine(startIndex, i - 1));
				}
				startIndex = -1;
			}
		}
		
		Line defenceLine = null;
		for (Line subLine : subLines) {
			if (subLine.containsPoint(cell.getPoint())) {
				defenceLine = subLine;
				break;
			}
		}

		return defenceLine;
	}
	
	private Line getCandidateLine(Stone stone, Cell cell, Line line) {
		List<Line> subLines = new ArrayList<Line>();
		int startIndex = -1;
		for (int i = 0 ; i < line.size();i++) {
			Cell c = tableHelper.getTable().get(line.getPoint(i));
			if (c.getStone() != stone.getOpposite()) {
				if (startIndex == -1) {
					startIndex = i;					
				}
				
				if (startIndex != -1 && i == line.size() - 1 && i - startIndex + 1 >= 5) {
					subLines.add(line.getSubLine(startIndex, i));
				}
			} else {
				if (startIndex != -1 && i - startIndex >= 5) {
					subLines.add(line.getSubLine(startIndex, i - 1));
				}
				startIndex = -1;
			}
		}
		
		for (Line subLine : subLines) {
			if (subLine.containsPoint(cell.getPoint())) {
				return subLine;
			}
		}
		
		return null;
	}
}
