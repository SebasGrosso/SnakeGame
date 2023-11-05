package view;

public class ViewThreadObstacle2 extends Thread {
	
	private ViewSnakePanel panel;
	private boolean state;
	private int time;

	public ViewThreadObstacle2(ViewSnakePanel panel, int time) {
		this.panel = panel;
		this.time = time;
		state = true;
	}

	@Override
	public void run() {
		while (state) {
			panel.generateObstacle2();
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
