package controller;

import java.util.HashMap;
import java.util.Map;

import model.Computer;
import model.Point;
import model.Progress;
import model.Stone;
import model.Table;
import model.TableHelper;

public class Game {
	private Table table;
	private TableHelper tableHelper;
	
	public boolean start() {
		table = new Table();
		tableHelper = new TableHelper(table);
		
		Computer c1 = new Computer(Stone.BLACK);
		Computer c2 = new Computer(Stone.WHITE);
		Map<Stone, Computer> players = new HashMap<>();
		players.put(Stone.BLACK, c1);
		players.put(Stone.WHITE, c2);
		
		Stone currentStone = Stone.BLACK;
		while(true) {
			Computer player = players.get(currentStone);
			Point point = player.getStepPoint(tableHelper);
			if (point == null) {
				// game over
				System.out.println(currentStone + " lose!");
				break;
			}
			
			if (!table.isAvailable(point)) {
				throw new RuntimeException("not available");
			}
			
			tableHelper.move(currentStone, point);
			
			if (tableHelper.checkWin(currentStone, point)) {
				System.out.println(currentStone + "win!");
				table.print();
				return true;
			}
			
			currentStone = currentStone.getOpposite();
		}
		
		return false;
	}
	
	public static void main(String[] args) {
		Game game = new Game();
		while(!game.start()) {
			//
		}
	}
}
