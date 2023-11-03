package view;

public class ViewThreadScore extends Thread {

	private ViewSnakePanel panel;

	public ViewThreadScore(ViewSnakePanel panel) {
		this.panel = panel;
	}

	@Override
	public void run() {
		while (true) {
			
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
