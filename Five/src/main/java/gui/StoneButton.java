package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;

import javax.swing.JButton;

import model.Stone;

public class StoneButton extends JButton {

	/**
	 *
	 */
	private static final long serialVersionUID = -4991051499073244288L;

	private Stone stone = Stone.NONE;
	private boolean isLastMove = false;
	public StoneButton() {
		this.setContentAreaFilled(false);
//		this.setOpaque(true);
		this.setBorderPainted(false);
	}

	public void setStone(Stone stone, boolean isLastMove) {
		this.stone = stone;
		this.isLastMove = isLastMove;
	}

	public Stone getStone() {
		return stone;
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		int width = getWidth();
		int height = getHeight();

		Graphics2D g2 = (Graphics2D) g;
		g2.setStroke(new BasicStroke(1));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
//		g2.setColor(Color.gray);
//		g2.drawLine(0, height / 2, width, height / 2);
//		g2.drawLine(width / 2, 0, width / 2, height);

		int radius = width < height ? width * 2 / 3 : height * 2 / 3;

		Ellipse2D e = new Ellipse2D.Float((width - radius) / 2, (height - radius) / 2, radius, radius);
		if (stone == Stone.BLACK) {
			g.setColor(Color.BLACK);
			g2.fill(e);
		} else if (stone == Stone.WHITE){
			g.setColor(Color.WHITE);
			g2.fill(e);
			g.setColor(Color.BLACK);
			g2.draw(e);
		}

		if (isLastMove) {
			g2.setColor(Color.RED);
			int rectangle = 6;
			Ellipse2D e2 = new Ellipse2D.Float((width - rectangle) / 2f, (height - rectangle) / 2f, rectangle, rectangle);
			g2.fill(e2);
		}
	}
}
