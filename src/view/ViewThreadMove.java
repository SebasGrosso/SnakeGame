package view;

public class ViewThreadMove implements Runnable{
	
	private ViewSnakePanel panel;
	private boolean state = true;
	private int time;
	
	public ViewThreadMove(ViewSnakePanel panel) {
		this.panel = panel;
	}

	@Override
	public void run() {
		while(state) {
			panel.move();
			panel.repaint();
			try {
				Thread.sleep(100);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopThread() {
		this.state = false;
	}

}
