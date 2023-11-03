package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;
//67,42,111,255
public class ViewGameInformationPanel extends JPanel{
	
	public ViewGameInformationPanel() {
		this.setBounds(10, 10, 100, 100);
		this.setBackground(new Color(238,135,43,255));
//		this.setLayout();
		JPanel btn = new JPanel();
		btn.setBounds(10,10,90,90);
		this.add(btn);
	}
	
	

}
