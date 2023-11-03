package view;

public class ViewThreadFood extends Thread{
	
	private ViewSnakePanel panel;
	private boolean state;
	
	public ViewThreadFood(ViewSnakePanel panel) {
		this.panel = panel;
		state = true;
	}
	
	@Override
	public void run() {
		while(state) {
			panel.generateFood();
			panel.repaint();
			try {
				Thread.sleep(6000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void stopThread() {
		this.state = false;
	}

}
