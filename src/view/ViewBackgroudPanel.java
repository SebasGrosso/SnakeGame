package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

public class ViewBackgroudPanel extends JPanel {

	private static final Color backgroudColor = new Color(67,42,111,255);
	private int maxSize;
	private int quantity;
	private int size;
	private int res;

	public ViewBackgroudPanel(int maxSize, int quantity) {
		this.setBounds(10, 10, 700, 700);
		this.maxSize = maxSize;
		this.quantity = quantity;
		this.size = maxSize/quantity;
		this.res = 	maxSize%quantity;
		this.setBackground(new Color(96,64,150,255));
	}

	@Override
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(backgroudColor);
		for (int i = 0; i < quantity; i++) {
			for (int j = 0; j < quantity; j++) {
				g.fillRect(res/2+i * size, res/2+j * size, size - 1, size - 1);
			}
		}
	}

}
