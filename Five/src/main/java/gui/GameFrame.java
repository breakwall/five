package gui;

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
import ez.layout.formlayout.core.Alignment;
import ez.layout.formlayout.core.FormAttachment;
import ez.layout.formlayout.core.FormData;
import ez.layout.formlayout.core.FormLayout;
import ez.layout.formlayout.core.FormLayoutHelper;
/**
 * the main frame of the game
 * @author justis.ren
 *
 */
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
		FormLayout layout = new FormLayout();
		layout.margin = 10;
		layout.padding = 10;
		mainPanel.setLayout(layout);
		setContentPane(mainPanel);

		FormLayoutHelper helper = new FormLayoutHelper();

		panel = new BoardPanel(boardHelper, this);
		helper.addComponent(panel, FormLayoutHelper.TOP, FormLayoutHelper.LEFT);

		JScrollPane scrollPane = new JScrollPane();
		tree = new JTree(Statistics.getTreeNode());
		tree.setCellRenderer(new NodeRenderer());
		scrollPane.getViewport().add(tree);

		FormData fd = new FormData();
		fd.left = new FormAttachment(panel);
		fd.right = new FormAttachment(panel, 160, Alignment.RIGHT);
		fd.top = new FormAttachment(0, 0);
		fd.bottom = new FormAttachment(panel, 0, Alignment.BOTTOM);
		helper.addComponent(scrollPane, fd);
		helper.fillPanel(mainPanel);

//		this.setSize(800, 600);
		this.pack();
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		this.setLocationRelativeTo(null);
//		this.setResizable(false);
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
