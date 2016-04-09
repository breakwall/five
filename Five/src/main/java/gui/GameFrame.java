package gui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.BoardHelper;
import model.Cell;
import utils.GameConstants;

public class GameFrame extends JFrame implements ActionListener {
	/**
	 *
	 */
	private static final long serialVersionUID = -4113990757408217780L;

	private BoardPanel panel;
	private Cell userSelected = null;
	private Object lock;

	public GameFrame(BoardHelper boardHelper, Object lock) {
		this.lock = lock;
		this.setTitle("GoBang");
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
		if (GameConstants.START_UI) {
			panel.refresh();
		}
	}


	public void popWin(final Cell cell) {
			SwingUtilities.invokeLater(new Runnable() {

				@Override
				public void run() {
					JOptionPane.showMessageDialog(GameFrame.this, cell.getStone() + " wins",
							"game over", JOptionPane.NO_OPTION);
				}
			});
	}
}
