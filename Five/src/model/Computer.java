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
	
	public Cell getStepCell() {
		Step lastStep = tableHelper.getLastStep();
		if (lastStep == null) {
			Point point = Utils.random(Point.get(Table.COLUMN / 2, Table.COLUMN / 2), 2);
			return tableHelper.getTable().get(point);
		}
		
		List<Cell> cells = tableHelper.getProgress().getCells(stone);
		if (cells.size() < 1) {
			Cell lastCell = lastStep.getCell();
			List<Cell> available = tableHelper.getNearAvailable(lastCell.getPoint());
			if (!available.isEmpty()) {
				return available.get(Utils.random(available.size()));
			}
		}
		
		List<Cell> nearAvailable = tableHelper.getNearAvailable();
		if (nearAvailable.isEmpty()) {
			return null;
		}
		
		Cell defencePoint = getDefenceCell(nearAvailable);
		if (defencePoint != null) {
			return defencePoint;
		}
		
		return null;
	}
	
	private Cell getDefenceCell(List<Cell> nearAvailable) {
		int maxWeight = 0;
		Cell defenceCell = null;
		Stone targetStone = stone.getOpposite();
		for (Cell cell : nearAvailable) {
			int weight = Utils.getWeightOfCell(cell, targetStone);
			if (weight > maxWeight) {
				maxWeight = weight;
				defenceCell = cell;
			}
		}
		

		int maxWeight2 = 0;
		Cell attackCell = null;
		targetStone = stone;
		for (Cell cell : nearAvailable) {
			int weight = Utils.getWeightOfCell(cell, targetStone);
			if (weight > maxWeight2) {
				maxWeight2 = weight;
				attackCell = cell;
			}
		}
		
		System.out.println(maxWeight + ":" + maxWeight2);
		if (maxWeight > maxWeight2) {
			System.out.println("defence");
			return defenceCell;
		} else {
			System.out.println("attack");
			return attackCell;
		}
	}
}
