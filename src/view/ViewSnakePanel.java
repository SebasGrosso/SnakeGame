package view;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class ViewSnakePanel extends JPanel {

	private static final Color snakeColor = new Color(200, 60, 150, 255);
	private int quantity;
	private int size;
	private int score;
	private List<int[]> snake;
	private List<int[]> obstacle1;
	private List<int[]> obstacle2;
	private List<int[]> obstacle3;
	private int[] food;
	private boolean state;
	private String direction;
	private String proxDirection;
	private ViewThreadMove moveThread;
	private Thread threadMove;
	private ViewThreadFood threadFood;
	private ViewThreadObstacle1 threadObstacle1;
	private ViewThreadObstacle2 threadObstacle2;
	private ViewThreadObstacle3 threadObstacle3;

	public ViewSnakePanel(int maxSize, int quantity) {
		this.setOpaque(false);
		this.setBounds(10, 10, 700, 700);
		this.quantity = quantity;
		this.size = maxSize / quantity;
		/*snake = new ArrayList<>();
		obstacle1 = new ArrayList<>();
		obstacle2 = new ArrayList<>();
		obstacle3 = new ArrayList<>();
		food = new int[2];
		direction = "right";
		proxDirection = "right";
		state = true;*/
		initializeVariables();
		createSnake();
		generateFood();
		threadScore();
		startThreads();
		/*moveThread = new ViewThreadMove(this);
		threadMove = new Thread(moveThread);
		threadFood = new ViewThreadFood(this);
		threadObstacle3 = new ViewThreadObstacle3(this);
		threadObstacle2 = new ViewThreadObstacle2(this);
		threadObstacle1 = new ViewThreadObstacle1(this);
		threadObstacle3.start();
		threadObstacle2.start();
		threadObstacle1.start();
		threadFood.start();
		threadMove.start();*/
	}
	
	public void startThreads() {
		moveThread = new ViewThreadMove(this);
		threadMove = new Thread(moveThread);
		threadFood = new ViewThreadFood(this);
		threadObstacle3 = new ViewThreadObstacle3(this);
		threadObstacle2 = new ViewThreadObstacle2(this);
		threadObstacle1 = new ViewThreadObstacle1(this);
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
		threadMove = new Thread(moveThread);
		moveThread = new ViewThreadMove(this);
		threadFood = new ViewThreadFood(this);
		threadObstacle3 = new ViewThreadObstacle3(this);
		threadObstacle2 = new ViewThreadObstacle2(this);
		threadObstacle1 = new ViewThreadObstacle1(this);
	}
	
	public void createSnake() {
		int startSnake = (int) (Math.random()*quantity);
		int [] firstPart= {startSnake-1, startSnake-1};
		int [] secondPart= {startSnake, startSnake-1};
		int [] thirdPart= {startSnake+1, startSnake-1};
		snake.add(firstPart);
		snake.add(secondPart);
		snake.add(thirdPart);
	}
	
	public void paint(Graphics g) {
		super.paint(g);
		g.setColor(snakeColor);
		for (int i = 0; i < snake.size(); i++) {
			g.fillRect(snake.get(i)[0] * size,  snake.get(i)[1] * size, size - 1, size - 1);
		}
		g.setColor(Color.red);
		g.fillRect(food[0] * size, food[1] * size, size - 1, size - 1);
		
		g.setColor(new Color(52, 27, 69, 255));
		for (int i = 0; i < obstacle1.size(); i++) {
			g.fillRect(obstacle1.get(i)[0] * size,  obstacle1.get(i)[1] * size, size - 1, size - 1);
		}
		for (int i = 0; i < obstacle2.size(); i++) {
			g.fillRect(obstacle2.get(i)[0] * size,  obstacle2.get(i)[1] * size, size - 1, size - 1);
		}
		for (int i = 0; i < obstacle3.size(); i++) {
			g.fillRect(obstacle3.get(i)[0] * size,  obstacle3.get(i)[1] * size, size - 1, size - 1);
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

		int[] nuevo = { Math.floorMod(last[0] + addX, quantity), Math.floorMod(last[1] + addY, quantity) };
		boolean exist = false;
		for (int i = 0; i < snake.size(); i++) {
			if(snake.get(0)[0]==2&&snake.get(0)[1]==2) {
				exist = true;
			}
			if (nuevo[0] == snake.get(i)[0] && nuevo[1] == snake.get(i)[1]) {
				exist = true;
				break;
			}
		}

		if (exist) {
			moveThread.stopThread();
			threadFood.stopThread();
			state = false;
			threadObstacle1.stopThread();
			threadObstacle2.stopThread();
			threadObstacle3.stopThread();
			JOptionPane.showMessageDialog(this, "Has perdido");

		} else {
			if (nuevo[0] == food[0] && nuevo[1] == food[1]) {
				snake.add(nuevo);
				generateFood();
				score++;
			} else {
				snake.add(nuevo);
				snake.remove(0);
			}
		}
	}

	public void generateFood() {
		boolean exist = false;
		int a = (int) (Math.random() * quantity);
		int b = (int) (Math.random() * quantity);
		for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i)[0] == a && snake.get(i)[1] == b) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			food[0] = a;
			food[1] = b;
		}
	}
	
	public void generateObstacle1() {
		boolean exist = false;
		int coordenadeX = (int) (Math.random() * quantity-2);
		int coordenadeY = (int) (Math.random() * quantity-2);
		
		for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY || snake.get(i)[0] == coordenadeX+1 && snake.get(i)[1] == coordenadeY || snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY+1) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			obstacle1.clear();
			int [] firstPart= {coordenadeX, coordenadeY};
			int [] secondPart= {coordenadeX+1, coordenadeY};
			int [] thirdPart= {coordenadeX, coordenadeY+1};
			obstacle1.add(firstPart);
			obstacle1.add(secondPart);
			obstacle1.add(thirdPart);
		}
	}
	
	public void generateObstacle2() {
		boolean exist = false;
		int coordenadeX = (int) (Math.random() * quantity-3)+1;
		int coordenadeY = (int) (Math.random() * quantity-2);
		
		for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY || snake.get(i)[0] == coordenadeX+1 && snake.get(i)[1] == coordenadeY || snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY+1) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			obstacle2.clear();
			int [] firstPart= {coordenadeX, coordenadeY};
			int [] secondPart= {coordenadeX+1, coordenadeY};
			int [] thirdPart= {coordenadeX, coordenadeY+1};
			int [] fourthPart= {coordenadeX-1, coordenadeY};
			obstacle2.add(firstPart);
			obstacle2.add(secondPart);
			obstacle2.add(thirdPart);
			obstacle2.add(fourthPart);
		}
	}
	
	public void generateObstacle3() {
		boolean exist = false;
		int coordenadeX = (int) (Math.random() * quantity-2);
		int coordenadeY = (int) (Math.random() * quantity-2);
		
		for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY || snake.get(i)[0] == coordenadeX+1 && snake.get(i)[1] == coordenadeY || snake.get(i)[0] == coordenadeX && snake.get(i)[1] == coordenadeY+1) {
				exist = true;
				break;
			}
		}
		if (!exist) {
			obstacle3.clear();
			int [] firstPart= {coordenadeX, coordenadeY};
			int [] secondPart= {coordenadeX+1, coordenadeY};
			int [] thirdPart= {coordenadeX, coordenadeY+1};
			int [] fourthPart= {coordenadeX+1, coordenadeY+1};
			obstacle3.add(firstPart);
			obstacle3.add(secondPart);
			obstacle3.add(thirdPart);
			obstacle3.add(fourthPart);
		}
	}

	public void changeDirecction(String dir) {
		switch(dir) {
		case "up":
			if(!direction.equals("up")&&!direction.equals("down")) {
				proxDirection = dir;
			}
			break;
		case "down":
			if(!direction.equals("down")&&!direction.equals("up")) {
				proxDirection = dir;
			}
			break;
		case "right":
			if(!direction.equals("right")&&!direction.equals("left")) {
				proxDirection = dir;
			}
			break;
		case "left":
			if(!direction.equals("left")&&!direction.equals("right")) {
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
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        threadScore.start();
	}

	public void sameDirection() {
		direction = proxDirection;
	}
}