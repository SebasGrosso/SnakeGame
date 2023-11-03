package view;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.JFrame;

//rgba(238,135,43,255) naranja
//183,76,236,255 morado

public class ViewMainFrame extends JFrame {

	private ViewBackgroudPanel viewBackgroudPanel;
	private ViewGameInformationPanel viewGameInformationPanel;
	private ViewSnakePanel viewSnakePanel;

	public ViewMainFrame() {
		this.setBounds(10, 10, 650, 700);
		viewSnakePanel = new ViewSnakePanel(680, 30);
		viewBackgroudPanel = new ViewBackgroudPanel(680, 30);
		viewGameInformationPanel = new ViewGameInformationPanel();
		initComponents();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) { 
				switch (e.getKeyCode()) {
				case KeyEvent.VK_UP:
					viewSnakePanel.changeDirecction("up");
					break;
				case KeyEvent.VK_DOWN:
					viewSnakePanel.changeDirecction("down");
					break;
				case KeyEvent.VK_LEFT:
					viewSnakePanel.changeDirecction("left");
					break;
				case KeyEvent.VK_RIGHT:
					viewSnakePanel.changeDirecction("right");
					break;
				case KeyEvent.VK_W:
					viewSnakePanel.changeDirecction("up");
					break;
				case KeyEvent.VK_S:
					viewSnakePanel.changeDirecction("down");
					break;
				case KeyEvent.VK_A:
					viewSnakePanel.changeDirecction("left");
					break;
				case KeyEvent.VK_D:
					viewSnakePanel.changeDirecction("right");
					break;
				default:
					break;
				}
			}
		});
	}

	public void initComponents() {
		this.requestFocus(true);
		this.setSize(694, 800);
		this.setVisible(true);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		this.setLayout(new BorderLayout());
		this.setLayout(new BorderLayout());
		this.add(viewSnakePanel, BorderLayout.CENTER);
		this.add(viewBackgroudPanel, BorderLayout.CENTER);
	}

	public static void main(String[] args) {
		new ViewMainFrame();
	}
}
