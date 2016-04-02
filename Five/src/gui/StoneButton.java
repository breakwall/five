package gui;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RadialGradientPaint;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.awt.geom.RoundRectangle2D;

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
		this.setBackground(Color.WHITE);
		this.setBorderPainted(false);
		this.setPreferredSize(new Dimension(40, 40));
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
		g2.setStroke(new BasicStroke(3));
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,RenderingHints.VALUE_ANTIALIAS_ON);
		g2.setColor(Color.WHITE);
		g2.fill3DRect(0, 0, width, height, true);
		
		int radius = width < height ? width / 2 : height / 2;

		switch (stone) {
		case BLACK:
			g.setColor(Color.BLACK);
			break;
		case WHITE:
			g.setColor(Color.BLACK);
			break;
		default:
			return;
		}
		RoundRectangle2D e = new RoundRectangle2D.Float((width - radius) / 2, (height - radius) / 2, radius, radius, 8, 8);
		if (stone == Stone.BLACK) {
			g2.fill(e);
		} else {
			g2.draw(e);
		}
		
		if (isLastMove) {
			g2.setColor(Color.RED);
			int rectangle = 6;
			g2.fillRect((width - rectangle) / 2, (height - rectangle) / 2, rectangle, rectangle);
		}
	}
}
