package view;

public class ViewThreadObstacle extends Thread {
	
	private ViewSnakePanel panel;
	private boolean state;

	public ViewThreadObstacle(ViewSnakePanel panel) {
		this.panel = panel;
		state = true;
	}

	@Override
	public void run() {
		while (state) {
			panel.generateObstacle();
			panel.repaint();
			try {
				Thread.sleep(8000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopThread() {
		this.state = false;
	}
}
