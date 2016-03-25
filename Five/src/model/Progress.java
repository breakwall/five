package model;

import java.util.ArrayList;
import java.util.List;

public class Progress {
	private List<Step> steps = new ArrayList<Step>();
	
	public Step getLastStep() {
		if (steps.size() == 0) {
			return null;
		}
		
		return steps.get(steps.size() - 1);
	}
	
	public Stone getCurrentStone() {
		if (steps.size() % 2 == 0) {
			return Stone.BLACK;
		} else {
			return Stone.WHITE;
		}
	}
	
	public void addStep(Cell cell) {
		Step step = new Step(steps.size(), cell);
		System.out.println(step);
		steps.add(step);
	}
	
	public Step getStep(int index) {
		return steps.get(index);
	}
	
	public List<Cell> getCells(Stone stone) {
		List<Cell> cells = new ArrayList<Cell>();
		for (Step step : steps) {
			if (step.getCell().getStone().equals(stone)) {
				cells.add(step.getCell());
			}
		}
		
		return cells;
	}
	
	public List<Cell> getCells() {
		List<Cell> cells = new ArrayList<Cell>();
		for (Step step : steps) {
			cells.add(step.getCell());
		}
		
		return cells;
	}
	
	public int size() {
		return steps.size();
	}
	
	public void removeStep(int index) {
		steps.remove(index);
	}
}
