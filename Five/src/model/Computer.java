package model;

import java.util.List;

import controller.Utils;

public class Computer {
	
	public Stone chessman;
	
	public Computer(Stone chessman) {
		this.chessman = chessman;
	}
	
	public Point getStepPoint(TableHelper tableHelper) {
		Step lastStep = tableHelper.getLastStep();
		if (lastStep == null) {
			return Utils.random(Point.get(Table.COLUMN / 2, Table.COLUMN / 2), 3);
		}
		
		Cell lastCell = lastStep.getCell();
		
		List<Cell> available = tableHelper.getNearAvailable(lastCell.getPoint());
		if (!available.isEmpty()) {
			return available.get(Utils.random(available.size())).getPoint();
		}
		return null;
	}
}
