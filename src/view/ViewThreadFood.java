package view;

public class ViewThreadFood extends Thread{
	
	private ViewSnakePanel panel;
	private boolean state;
	private int time;
	
	public ViewThreadFood(ViewSnakePanel panel, int time) {
		this.panel = panel;
		this.time = time;
		state = true;
	}
	
	@Override
	public void run() {
		while(state) {
			panel.generateFood();
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
