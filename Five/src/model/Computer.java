package model;

import java.util.ArrayList;
import java.util.List;

import controller.Utils;

public class Computer {
	
	public Stone stone;
	
	public Computer(Stone stone) {
		this.stone = stone;
	}
	
	public Point getStepPoint(TableHelper tableHelper) {
		Step lastStep = tableHelper.getLastStep();
		if (lastStep == null) {
			return Utils.random(Point.get(Table.COLUMN / 2, Table.COLUMN / 2), 3);
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
//			return nearAvailable.get(Utils.random(nearAvailable.size())).getPoint();			
		}
		
		Point candidatePoint = null;
		int candidateLinePriority = 0;
		
		for (Cell ne : nearAvailable) {
			List<Line> lines = Utils.getReferenceLines(ne.getPoint());
			for (Line line : lines) {
				Line tmpLine = getCandidateLine(ne, line, tableHelper);
//				System.out.println("candidate line " + tmpLine);
				if (tmpLine != null) {
					int linePriority = getLinePriority(line, tableHelper);
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
	
	private int getLinePriority(Line line, TableHelper tableHelper) {
		Table table = tableHelper.getTable();
		int priority = 0;
		for (Point point : line.getPoints()) {
			if (table.get(point).getStone() == stone) {
				priority ++;
			}
		}
		
		return priority;
	}
	
	private Line getCandidateLine(Cell cell, Line line, TableHelper tableHelper) {
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
				if (startIndex != -1 && i - startIndex + 1 >= 5) {
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
