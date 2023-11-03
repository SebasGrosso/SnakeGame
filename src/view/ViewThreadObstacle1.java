package view;

public class ViewThreadObstacle1 extends Thread {
	
	private ViewSnakePanel panel;
	private boolean state;

	public ViewThreadObstacle1(ViewSnakePanel panel) {
		this.panel = panel;
		state = true;
	}

	@Override
	public void run() {
		while (state) {
			panel.generateObstacle1();
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
