package controller;

import java.util.HashMap;
import java.util.Map;

import model.Cell;
import model.Computer;
import model.Stone;
import model.Table;
import model.TableHelper;

public class Game {
	private Table table;
	private TableHelper tableHelper;
	
	public boolean start() {
		table = new Table();
		tableHelper = new TableHelper(table);
		
		Computer c1 = new Computer(Stone.BLACK, tableHelper);
		Computer c2 = new Computer(Stone.WHITE, tableHelper);
		Map<Stone, Computer> players = new HashMap<>();
		players.put(Stone.BLACK, c1);
		players.put(Stone.WHITE, c2);
		
		Stone currentStone = Stone.BLACK;
		while(true) {
			Computer player = players.get(currentStone);
			Cell cell = player.getStepCell();
			if (cell == null) {
				// game over
				System.out.println(currentStone + " lose!");
				table.print();
				break;
			}
			
			if (!table.isAvailable(cell.getPoint())) {
				throw new RuntimeException("not available");
			}
			
			tableHelper.move(currentStone, cell);
			table.print();
			
			if (tableHelper.checkWin(cell)) {
				System.out.println(currentStone + " win!");
				return true;
			}
			
			currentStone = currentStone.getOpposite();
//			if (tableHelper.getProgress().getCells().size() == 20) {
//				break;
//			}
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
}
