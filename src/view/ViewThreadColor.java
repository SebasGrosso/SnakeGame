package view;

public class ViewThreadColor extends Thread{
	private ViewSnakePanel panel;
	private boolean state;

	public ViewThreadColor(ViewSnakePanel panel) {
		this.panel = panel;
		state = true;
	}

	@Override
	public void run() {
		while (state) {
			if (panel.getChangeColor() <= 250) {
				panel.setChangeColor(panel.getChangeColor() + 5);
			} else {
				panel.setChangeColor(0);
			}

			panel.repaint();

			try {
				Thread.sleep(6);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopThread() {
		this.state = false;
	}
}
