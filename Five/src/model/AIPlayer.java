package model;

import java.util.List;

import controller.Utils;

public class AIPlayer implements IPlayer{
	
	private Stone stone;
	private BoardHelper boardHelper;
	public AIPlayer(Stone stone, BoardHelper boardHelper) {
		this.stone = stone;
		this.boardHelper = boardHelper;
	}
	
	public Cell getMove() {
		Step lastStep = boardHelper.getLastStep();
		if (lastStep == null) {
			Point point = Utils.random(Point.get(Board.COLUMN / 2, Board.COLUMN / 2), 2);
			return boardHelper.getBoard().get(point);
		}
		
		List<Cell> cells = boardHelper.getProgress().getCells(stone);
		if (cells.size() < 1) {
			Cell lastCell = lastStep.getCell();
			List<Cell> available = boardHelper.getNearAvailable(lastCell.getPoint());
			if (!available.isEmpty()) {
				return available.get(Utils.random(available.size()));
			}
		}
		
		List<Cell> nearAvailable = boardHelper.getNearAvailable();
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
