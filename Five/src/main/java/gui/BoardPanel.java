package gui;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPanel;

import model.Board;
import model.BoardHelper;
import model.Cell;
import utils.Utils;

public class BoardPanel extends JPanel {
	/**
	 *
	 */
	private static final long serialVersionUID = 2648411181261245485L;

	private BoardHelper boardHelper;
	private Map<StoneButton, Cell> cellButtonMap = new HashMap<StoneButton, Cell>();

	public BoardPanel(BoardHelper boardHelper, ActionListener listener) {
		this.boardHelper = boardHelper;
		this.setPreferredSize(new Dimension(535, 535));
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

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		ImageIcon icon = new ImageIcon(Utils.getResourceURL("board.jpg"));
	    Image img = icon.getImage();
		int width = getWidth();
		int height = getHeight();
		Graphics2D g2 = (Graphics2D) g;
		g2.drawImage(img, 0, 0, width,
	    		height, icon.getImageObserver());
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
