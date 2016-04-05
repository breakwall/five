package gui;

import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.JButton;
import javax.swing.JPanel;

import model.Board;
import model.BoardHelper;
import model.Cell;
import model.Stone;

public class BoardPanel extends JPanel {
	/**
	 * 
	 */
	private static final long serialVersionUID = 2648411181261245485L;

	private BoardHelper boardHelper;
	private Map<StoneButton, Cell> cellButtonMap = new HashMap<StoneButton, Cell>();

	public BoardPanel(BoardHelper boardHelper, ActionListener listener) {
		this.boardHelper = boardHelper;
		GridLayout layout = new GridLayout(Board.COLUMN, Board.COLUMN);
		this.setLayout(layout);
		for (int y = 0; y < Board.COLUMN; y++) {
			for (int x = 0; x < Board.COLUMN; x++) {
				Cell cell = boardHelper.getBoard().get(x, y);
				StoneButton button = new StoneButton();
				button.addActionListener(listener);
				cellButtonMap.put(button, cell);
				add(button);
			}
		}
	}

	public void refresh() {
		Cell lastMove = boardHelper.getProgress().getLastCell();
		for (Entry<StoneButton, Cell> e : cellButtonMap.entrySet()) {
			Cell cell = e.getValue();
			StoneButton button = e.getKey();
			boolean isLastMove = (lastMove == cell);
			button.setStone(cell.getStone(), isLastMove);
			if (isLastMove) {
				button.setEnabled(false);
			}
			button.repaint();
		}
	}
	
	public Cell getCellOfButton(JButton button) {
		return cellButtonMap.get(button);
	}
}
