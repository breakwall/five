package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import model.BoardHelper;
import model.Cell;

public class GameFrame extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = -4113990757408217780L;

	private BoardPanel panel;
	private Cell userSelected = null;
	private Object lock;
	private BoardHelper boardHelper;

	public GameFrame(BoardHelper boardHelper, Object lock) {
		this.lock = lock;
		this.boardHelper = boardHelper;
		this.setTitle("gobang");
		panel = new BoardPanel(boardHelper, this);
		add(panel);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
	}


	public Cell getUserSelected() {
		return userSelected;
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		userSelected = panel.getCellOfButton((JButton)e.getSource());
		synchronized (lock) {
			lock.notify();
		}
	}


	public void refresh() {
		panel.refresh();
	}


	public void popWin(Cell cell) {
		JOptionPane.showMessageDialog(this, cell.getStone() + " wins",
				"game over", JOptionPane.NO_OPTION);
	}
}
