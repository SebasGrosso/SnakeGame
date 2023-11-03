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
	private int coordenadeX;
	private int coordenadeY;
	private List<int[]> snake;
	private List<int[]> obstacle;
	private int[] food;
	private boolean state;
	private String direccion;
	private String direccionProxima;
	private ViewThreadMove moveThread;
	private Thread threadMove;
	private ViewThreadFood threadFood;
	private ViewThreadObstacle threadObstacle;

	public ViewSnakePanel(int maxSize, int quantity) {
		this.setOpaque(false);
		this.setBounds(10, 10, 700, 700);
		this.quantity = quantity;
		this.size = maxSize / quantity;
		snake = new ArrayList<>();
		obstacle = new ArrayList<>();
		food = new int[2];
		direccion = "right";
		direccionProxima = "right";
		state = true;
		createSnake();
		generateFood();
		generateObstacle();
		threadScore();
		moveThread = new ViewThreadMove(this);
		threadMove = new Thread(moveThread);
		threadFood = new ViewThreadFood(this);
		threadObstacle = new ViewThreadObstacle(this);
		threadObstacle.start();
		threadFood.start();
		threadMove.start();
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
		
		g.setColor(Color.black);
		for (int i = 0; i < obstacle.size(); i++) {
			g.fillRect(obstacle.get(i)[0] * size,  obstacle.get(i)[1] * size, size - 1, size - 1);
		}

	}

	public void move() {
		igualarDireccion();
		int[] last = snake.get(snake.size() - 1);
		int addX = 0;
		int addY = 0;
		switch (direccion) {
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
	
	public void generateObstacle() {
		boolean exist = false;
		coordenadeX = (int) (Math.random() * quantity-2);
		coordenadeY = (int) (Math.random() * quantity-2);
		int [] firstPart= {coordenadeX, coordenadeY};
		int [] secondPart= {coordenadeX+1, coordenadeY};
		int [] thirdPart= {coordenadeX, coordenadeY+1};
		obstacle.add(firstPart);
		obstacle.add(secondPart);
		obstacle.add(thirdPart);
		/*for (int i = 0; i < snake.size(); i++) {
			if (snake.get(i)[0] == coordenade && snake.get(i)[1] == coordenade && snake.get(i)[0] == coordenade+1 && snake.get(i)[1] == coordenade+1 && snake.get(i)[0] == coordenade+2 && snake.get(i)[1] == coordenade+2) {
				exist = true;
				break;
			}
		}
		/*for (int[] part : snake) {
			if (part[0] == a && part[1] == b) {
				exist = true;
				break;
			}
		}*/
		/*if (!exist) {
			obstacle.get(0)[0]=coordenade;
			obstacle.get(0)[1]=coordenade;
			obstacle.get(1)[0]=coordenade;
			obstacle.get(1)[1]=coordenade;
			obstacle.get(2)[0]=coordenade;
			obstacle.get(2)[1]=coordenade;
		}*/
	}

	public void changeDirecction(String dir) {
		switch(dir) {
		case "up":
			if(!direccion.equals("up")&&!direccion.equals("down")) {
				direccionProxima = dir;
			}
			break;
		case "down":
			if(!direccion.equals("down")&&!direccion.equals("up")) {
				direccionProxima = dir;
			}
			break;
		case "right":
			if(!direccion.equals("right")&&!direccion.equals("left")) {
				direccionProxima = dir;
			}
			break;
		case "left":
			if(!direccion.equals("left")&&!direccion.equals("right")) {
				direccionProxima = dir;
			}
			break;
		default:
			break;
		}
	}
	
	public void threadScore() {
		Thread hiloPuntaje = new Thread(new Runnable() {
            @Override
            public void run() {
                while (state) {
                    System.out.println(score);
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        hiloPuntaje.start();
	}

	public void igualarDireccion() {
		this.direccion = this.direccionProxima;
	}
}