package model;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class User implements IPlayer {

	private Stone stone;
	private BoardHelper boardHelper;

	public User(Stone stone, BoardHelper boardHelper) {
		this.stone = stone;
		this.boardHelper = boardHelper;
	}

	@Override
	public Cell getMove() {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		String inputStr;
		try {
			inputStr = br.readLine();
			String[] xy = inputStr.split(",");
			Cell cell = boardHelper.getBoard().get(Integer.parseInt(xy[0].trim()),
					Integer.parseInt(xy[1].trim()));
			
			return cell;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

}
