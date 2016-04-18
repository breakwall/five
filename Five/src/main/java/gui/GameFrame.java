package gui;

import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.SwingUtilities;
import javax.swing.tree.DefaultTreeModel;

import logger.Statistics;
import logger.Statistics.NodeRenderer;
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
	private JTree tree;

	public GameFrame(BoardHelper boardHelper, Object lock) {
		this.lock = lock;
		this.setTitle("GoBang");
		JPanel mainPanel = new JPanel();
		GridLayout layout = new GridLayout();
		mainPanel.setLayout(layout);
		add(mainPanel);
		
		panel = new BoardPanel(boardHelper, this);
		mainPanel.add(panel);
		
		JScrollPane scrollPane = new JScrollPane();
		tree = new JTree(Statistics.getTreeNode());
		tree.setCellRenderer(new NodeRenderer());
		tree.expandRow(6);
		scrollPane.getViewport().add(tree);
		mainPanel.add(scrollPane);
		
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
			((DefaultTreeModel)tree.getModel()).reload();
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
