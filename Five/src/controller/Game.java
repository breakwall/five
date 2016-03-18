package controller;

import java.util.HashMap;
import java.util.Map;

import model.Computer;
import model.Point;
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
			Point point = player.getStepPoint();
			if (point == null) {
				// game over
				System.out.println(currentStone + " lose!");
				table.print();
				break;
			}
			
			if (!table.isAvailable(point)) {
				throw new RuntimeException("not available");
			}
			
			tableHelper.move(currentStone, point);
			table.print();
			
			if (tableHelper.checkWin(currentStone, point)) {
				System.out.println(currentStone + " win!");
				return true;
			}
			
			currentStone = currentStone.getOpposite();
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		game.start();
	}
}
