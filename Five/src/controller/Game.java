package controller;

import java.util.HashMap;
import java.util.Map;

import logger.GameLogger;
import model.AIPlayer;
import model.Board;
import model.BoardHelper;
import model.Cell;
import model.Stone;

public class Game {
	private Board board;
	private BoardHelper boardHelper;

	public boolean start() {
		board = new Board();
		boardHelper = new BoardHelper(board);

		AIPlayer c1 = new AIPlayer(Stone.BLACK, boardHelper);
		AIPlayer c2 = new AIPlayer(Stone.WHITE, boardHelper);
		Map<Stone, AIPlayer> players = new HashMap<Stone, AIPlayer>();
		players.put(Stone.BLACK, c1);
		players.put(Stone.WHITE, c2);

		Stone currentStone = Stone.BLACK;
		while(true) {
			AIPlayer player = players.get(currentStone);
			Cell cell = player.getMove();
			if (cell == null) {
				// game over
				GameLogger.getInstance().logInfo(currentStone + " give up!");
				GameLogger.getInstance().logDebug(boardHelper.getBoardStr());
				break;
			}

			if (!board.isAvailable(cell.getPoint())) {
				throw new RuntimeException("not available");
			}

			boardHelper.move(currentStone, cell);
			GameLogger.getInstance().logDebug(boardHelper.getBoardStr());

			if (boardHelper.checkWin(cell)) {
				GameLogger.getInstance().logInfo(currentStone + " win!");
				return true;
			}

			currentStone = currentStone.getOpposite();
		}

		return false;
	}

	public static void main(String[] args) {
		long time = System.currentTimeMillis();
		Game game = new Game();
		game.start();
		System.out.println((System.currentTimeMillis() - time) / 1000);
	}
}
