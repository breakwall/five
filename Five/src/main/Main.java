package main;

import gui.GameFrame;

import java.util.HashMap;
import java.util.Map;

import logger.GameLogger;
import model.AIPlayer;
import model.Board;
import model.BoardHelper;
import model.Cell;
import model.IPlayer;
import model.Stone;
import argorithm.MinMaxAlgorithm;

public class Main {
	private GameLogger logger = GameLogger.getInstance();

	public void start() {
		//inial data
		Board board = new Board();
		BoardHelper boardHelper = new BoardHelper(board);
		Object lock = new Object();
		GameFrame gameFrame = new GameFrame(boardHelper, lock);

		//init players
		Map<Stone, IPlayer> players = new HashMap<Stone, IPlayer>();
		IPlayer c1 = new AIPlayer(Stone.WHITE, boardHelper);
		players.put(Stone.WHITE, c1);
		
//		IPlayer c2 = new Human(Stone.BLACK, gameFrame, lock);
		IPlayer c2 = new AIPlayer(Stone.BLACK, boardHelper);
		players.put(Stone.BLACK, c2);
		
		//loop, black first
		Stone currentStone = Stone.BLACK;
		while(true) {
			IPlayer player = players.get(currentStone);
			Cell cell = player.getMove();
			if (cell == null) {
				// game over
				GameLogger.getInstance().logInfo(currentStone + " give up!");
				GameLogger.getInstance().logDebug(boardHelper.getBoardStr());
				break;
			}

			boardHelper.move(currentStone, cell);
			gameFrame.refresh();
			GameLogger.getInstance().logInfo(boardHelper.getBoardStr());
			if (player instanceof AIPlayer) {
				GameLogger.getInstance().logInfo("AI spent: " + AIPlayer.onceTime / 1000.0);
			}

			if (boardHelper.checkWin(cell)) {
				gameFrame.popWin(cell);
				GameLogger.getInstance().logInfo(currentStone + " win!");
				break;
			}

//			if (boardHelper.getProgress().size() == GameConstants.moves) {
//				System.out.println("break;");
//				gameFrame.setVisible(false);
//				gameFrame.dispose();
//				break;
//			}

			currentStone = currentStone.getOpposite();
		}
		
		double time = AIPlayer.totalTime / 1000.0;
		logger.logInfo("total time:" + time + "s");
		logger.logInfo((int)MinMaxAlgorithm.evaluationCount / time + "  count/s");
	}

	public static void main(String[] args) {
		Main game = new Main();
		game.start();
	}
}
