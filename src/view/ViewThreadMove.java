package view;

public class ViewThreadMove implements Runnable{
	
	private ViewSnakePanel panel;
	private boolean state = true;
	private int time;
	
	public ViewThreadMove(ViewSnakePanel panel, int time) {
		this.panel = panel;
		this.time = time;
	}

	@Override
	public void run() {
		while(state) {
			panel.move();
			panel.repaint();
			try {
				Thread.sleep(time-panel.getScore()*2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopThread() {
		this.state = false;
	}

}
