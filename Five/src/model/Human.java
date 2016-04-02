package model;

import gui.GameFrame;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Human implements IPlayer {

	private Stone stone;
	private GameFrame gameFrame;
	private Object lock;

	public Human(Stone stone, GameFrame gameFrame, Object lock) {
		this.stone = stone;
		this.gameFrame = gameFrame;
		this.lock = lock;
	}

	@Override
	public Cell getMove() {
//		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
//		String inputStr;
//		try {
//			inputStr = br.readLine();
//			String[] xy = inputStr.split(",");
//			Cell cell = boardHelper.getBoard().get(Integer.parseInt(xy[0].trim()),
//					Integer.parseInt(xy[1].trim()));
//			
//			return cell;
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		return null;
		synchronized (lock) {
			try {
				lock.wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Cell cell = gameFrame.getUserSelected();
		return cell;
	}

}
