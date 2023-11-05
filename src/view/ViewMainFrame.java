package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class ViewMainFrame extends JFrame {

	private ViewBackgroudPanel viewBackgroudPanel;
	private ViewSnakePanel viewSnakePanel;
	private String playerName;
	private String difficulty;
	private JLabel scorePlayer;

	public ViewMainFrame(String playerName, String difficulty) {
		
		scorePlayer = new JLabel("Puntaje: 0");
		this.setBounds(10, 10, 650, 700);
		viewSnakePanel = new ViewSnakePanel(680, 30, this, playerName, difficulty);
		viewBackgroudPanel = new ViewBackgroudPanel(680, 30);
		this.playerName = playerName;
		this.difficulty = difficulty;
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
		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout());
		panel.setBounds(10, 10, 680, 100);
		JLabel playerNameLabel = new JLabel("Jugador: "+playerName);
		panel.add(playerNameLabel);
		JLabel difficultyLabel = new JLabel("Dificultad: "+difficulty);
		panel.add(scorePlayer);
		panel.add(difficultyLabel);
		panel.setBackground(new Color(96,64,150,255));
		add(panel, BorderLayout.SOUTH);
	}

	public JLabel getScorePlayer() {
		return scorePlayer;
	}

	public void setScorePlayer(JLabel scorePlayer) {
		this.scorePlayer = scorePlayer;
	}
	
	
}
