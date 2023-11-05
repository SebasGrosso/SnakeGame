package view;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ViewSnakePanel extends JPanel {

	private int changeColor;
	private int quantity;
	private int size;
	private int score;
	private int[] food;
	private List<int[]> snake;
	private List<int[]> obstacle1;
	private List<int[]> obstacle2;
	private List<int[]> obstacle3;
	private boolean state;
	private String playerName;
	private String difficulty;
	private int timeFood;
	private int timeMove;
	private int timeObstacles;
	private String direction;
	private String proxDirection;
	private ViewMainFrame frame;
	private ViewThreadMove moveThread;
	private Thread threadMove;
	private ViewThreadFood threadFood;
	private ViewThreadColor threadColor;
	private ViewThreadObstacle1 threadObstacle1;
	private ViewThreadObstacle2 threadObstacle2;
	private ViewThreadObstacle3 threadObstacle3;

	public ViewSnakePanel(int maxSize, int quantity, ViewMainFrame frame, String playerName, String difficulty) {
		this.setOpaque(false);
		this.setBounds(10, 10, 700, 700);
		this.quantity = quantity;
		this.size = maxSize / quantity;
		this.frame = frame;
		this.playerName = playerName;
		this.difficulty = difficulty;
		chooseDifficulty();
		initializeVariables();
		createSnake();
		generateFood();
		threadScore();
		startThreads();
	}

	public void startThreads() {
		moveThread = new ViewThreadMove(this, timeMove);
		threadMove = new Thread(moveThread);
		threadFood = new ViewThreadFood(this, timeFood);
		threadObstacle3 = new ViewThreadObstacle3(this, timeObstacles);
		threadObstacle2 = new ViewThreadObstacle2(this, timeObstacles);
		threadObstacle1 = new ViewThreadObstacle1(this, timeObstacles);
		threadColor = new ViewThreadColor(this);
		threadColor.start();
		threadObstacle3.start();
		threadObstacle2.start();
		threadObstacle1.start();
		threadFood.start();
		threadMove.start();
	}

	public void initializeVariables() {
		snake = new ArrayList<>();
		obstacle1 = new ArrayList<>();
		obstacle2 = new ArrayList<>();
		obstacle3 = new ArrayList<>();
		food = new int[2];
		direction = "right";
		proxDirection = "right";
		state = true;
	}

	public void createSnake() {
		int startSnake = (int) (Math.random() * quantity);
		int[] firstPart = { startSnake - 1, startSnake - 1 };
		int[] secondPart = { startSnake, startSnake - 1 };
		int[] thirdPart = { startSnake + 1, startSnake - 1 };
		snake.add(firstPart);
		snake.add(secondPart);
		snake.add(thirdPart);
	}

	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(new Color(216, changeColor, 255, 255));
		for (int i = 0; i < snake.size(); i++) {
			g.setColor(new Color(216, changeColor, 255, 255));
			g.fillRect(snake.get(i)[0] * size, snake.get(i)[1] * size, size - 1, size - 1);
		}
		Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(new Color(231, 71, 29, 255));
		g2d.fillOval(food[0] * size, food[1] * size, (int) (size + score * 0.3), (int) (size + score * 0.3));

		g.setColor(new Color(52, 27, 69, 255));
		for (int i = 0; i < obstacle1.size(); i++) {
			g.fillRect(obstacle1.get(i)[0] * size, obstacle1.get(i)[1] * size, size - 1, size - 1);
		}
		for (int i = 0; i < obstacle2.size(); i++) {
			g.fillRect(obstacle2.get(i)[0] * size, obstacle2.get(i)[1] * size, size - 1, size - 1);
		}
		for (int i = 0; i < obstacle3.size(); i++) {
			g.fillRect(obstacle3.get(i)[0] * size, obstacle3.get(i)[1] * size, size - 1, size - 1);
		}

	}

	public void move() {
		sameDirection();
		int[] last = snake.get(snake.size() - 1);
		int addX = 0;
		int addY = 0;
		switch (direction) {
		case "right":
			addX = 1;
			break;
		case "left":
			addX = -1;
			break;
		case "up":
			addY = -1;
			break;
		case "down":
			addY = 1;
			break;
		}
		int[] newPart = { Math.floorMod(last[0] + addX, quantity), Math.floorMod(last[1] + addY, quantity) };
		boolean exist = false;
		for (int i = 0; i < snake.size(); i++) {
			if (newPart[0] == snake.get(i)[0] && newPart[1] == snake.get(i)[1]) {
				exist = true;
				break;
			}
		}

		for (int i = 0; i < obstacle1.size(); i++) {
			if (snake.get(snake.size() - 1)[0] == obstacle1.get(i)[0]
					&& snake.get(snake.size() - 1)[1] == obstacle1.get(i)[1]) {
				exist = true;
			}
		}

		for (int i = 0; i < obstacle2.size(); i++) {
			if (snake.get(snake.size() - 1)[0] == obstacle2.get(i)[0]
					&& snake.get(snake.size() - 1)[1] == obstacle2.get(i)[1]) {
				exist = true;
			}
		}

		for (int i = 0; i < obstacle3.size(); i++) {
			if (snake.get(snake.size() - 1)[0] == obstacle3.get(i)[0]
					&& snake.get(snake.size() - 1)[1] == obstacle3.get(i)[1]) {
				exist = true;
			}
		}

		if (exist) {
			moveThread.stopThread();
			threadFood.stopThread();
			state = false;
			threadObstacle1.stopThread();
			threadObstacle2.stopThread();
			threadObstacle3.stopThread();
			JOptionPane.showMessageDialog(this, "Te has chocado, el juego ha terminado:(\n"+playerName+" tu puntaje ha sido de "+score+" naranjas.");
			ViewMainMenu menu = new ViewMainMenu();
			menu.getNameField().setText(playerName);
			frame.dispose();
			WriteHistory();

		} else {
			if (newPart[0] == food[0] && newPart[1] == food[1]) {
				snake.add(newPart);
				generateFood();
				if (score > 10) {
					score++;
				}
				if (score > 20) {
					score++;
				}
				score++;
			} else {
				snake.add(newPart);
				snake.remove(0);
			}
		}
	}

	public void generateFood() {
		boolean exist = false;
		int x = (int) (Math.random() * quantity);
		int y = (int) (Math.random() * quantity);
		for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i)[0] == x && snake.get(i)[1] == y) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			food[0] = x;
			food[1] = y;
		}
	}

	public void generateObstacle1() {
		boolean exist = false;
		int coordenadeX = (int) (Math.random() * quantity - 2);
		int coordenadeY = (int) (Math.random() * quantity - 2);

		for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY
					|| snake.get(i)[0] == coordenadeX + 1 && snake.get(i)[1] == coordenadeY
					|| snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY + 1) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			obstacle1.clear();
			int[] firstPart = { coordenadeX, coordenadeY };
			int[] secondPart = { coordenadeX + 1, coordenadeY };
			int[] thirdPart = { coordenadeX, coordenadeY + 1 };
			obstacle1.add(firstPart);
			obstacle1.add(secondPart);
			obstacle1.add(thirdPart);
		}
	}

	public void generateObstacle2() {
		boolean exist = false;
		int coordenadeX = (int) (Math.random() * quantity - 3) + 1;
		int coordenadeY = (int) (Math.random() * quantity - 2);

		for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY
					|| snake.get(i)[0] == coordenadeX + 1 && snake.get(i)[1] == coordenadeY
					|| snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY + 1) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			obstacle2.clear();
			int[] firstPart = { coordenadeX, coordenadeY };
			int[] secondPart = { coordenadeX + 1, coordenadeY };
			int[] thirdPart = { coordenadeX, coordenadeY + 1 };
			int[] fourthPart = { coordenadeX - 1, coordenadeY };
			obstacle2.add(firstPart);
			obstacle2.add(secondPart);
			obstacle2.add(thirdPart);
			obstacle2.add(fourthPart);
		}
	}

	public void generateObstacle3() {
		boolean exist = false;
		int coordenadeX = (int) (Math.random() * quantity - 2);
		int coordenadeY = (int) (Math.random() * quantity - 2);

		for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY
					|| snake.get(i)[0] == coordenadeX + 1 && snake.get(i)[1] == coordenadeY
					|| snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY + 1) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			obstacle3.clear();
			int[] firstPart = { coordenadeX, coordenadeY };
			int[] secondPart = { coordenadeX + 1, coordenadeY };
			int[] thirdPart = { coordenadeX, coordenadeY + 1 };
			int[] fourthPart = { coordenadeX + 1, coordenadeY + 1 };
			obstacle3.add(firstPart);
			obstacle3.add(secondPart);
			obstacle3.add(thirdPart);
			obstacle3.add(fourthPart);
		}
	}

	public void changeDirecction(String dir) {
		switch (dir) {
		case "up":
			if (!direction.equals("up") && !direction.equals("down")) {
				proxDirection = dir;
			}
			break;
		case "down":
			if (!direction.equals("down") && !direction.equals("up")) {
				proxDirection = dir;
			}
			break;
		case "right":
			if (!direction.equals("right") && !direction.equals("left")) {
				proxDirection = dir;
			}
			break;
		case "left":
			if (!direction.equals("left") && !direction.equals("right")) {
				proxDirection = dir;
			}
			break;
		default:
			break;
		}
	}

	public void threadScore() {
		Thread threadScore = new Thread(new Runnable() {
			@Override
			public void run() {
				while (state) {
					try {
						frame.getScorePlayer().setText("Puntaje: "+score);;
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
		});
		threadScore.start();
	}

	public void chooseDifficulty() {
		Properties properties = new Properties();
		InputStream input = null;
		try {
			input = new FileInputStream("config.properties");
			properties.load(input);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		switch (difficulty) {
		case "Fácil":
			timeFood = Integer.parseInt(properties.getProperty("modeEasyTimeFood"));
			timeMove = Integer.parseInt(properties.getProperty("modeEasyTimeMove"));
			timeObstacles = Integer.parseInt(properties.getProperty("modeEasyTimeObstacles"));
			break;
		case "Medio":
			timeFood = Integer.parseInt(properties.getProperty("modeMediumTimeFood"));
			timeMove = Integer.parseInt(properties.getProperty("modeMediumTimeMove"));
			timeObstacles = Integer.parseInt(properties.getProperty("modeMediumTimeObstacles"));
			break;
		case "Difícil":
			timeFood = Integer.parseInt(properties.getProperty("modeDifficultTimeFood"));
			timeMove = Integer.parseInt(properties.getProperty("modeDifficultTimeMove"));
			timeObstacles = Integer.parseInt(properties.getProperty("modeDifficultTimeObstacles"));
			break;
		}
	}

	public void WriteHistory() {
		LocalDateTime localDateTime = LocalDateTime.now();

		try (FileWriter writer = new FileWriter("scoreHistory.ser", true)) {
			writer.write("\nEl jugador: '"+playerName+"' obtuvo un puntaje: '"+score+"' En el modo: "+difficulty+" "+localDateTime);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void sameDirection() {
		direction = proxDirection;
	}

	public int getScore() {
		return score;
	}

	public void setChangeColor(int changeColor) {
		this.changeColor = changeColor;
	}

	public int getChangeColor() {
		return changeColor;
	}

}