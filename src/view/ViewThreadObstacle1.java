package view;

public class ViewThreadObstacle1 extends Thread {
	
	private ViewSnakePanel panel;
	private boolean state;
	private int time;

	public ViewThreadObstacle1(ViewSnakePanel panel, int time) {
		this.panel = panel;
		this.time = time;
		state = true;
	}

	@Override
	public void run() {
		while (state) {
			panel.generateObstacle1();
			panel.repaint();
			try {
				Thread.sleep(time);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopThread() {
		this.state = false;
	}
}
