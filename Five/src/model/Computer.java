package model;

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
		if (cells.size() < 3) {
			Cell lastCell = lastStep.getCell();
			List<Cell> available = tableHelper.getNearAvailable(lastCell.getPoint());
			if (!available.isEmpty()) {
				return available.get(Utils.random(available.size())).getPoint();
			}
		}
		
		List<Cell> nearAvailable = tableHelper.getNearAvailable();
		if (!nearAvailable.isEmpty()) {
			return nearAvailable.get(Utils.random(nearAvailable.size())).getPoint();			
		}
		
		return null;
	}
}
