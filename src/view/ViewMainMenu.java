package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;

public class ViewMainMenu extends JFrame {
	private JTextField nameField;
	private JButton startButton;
	private JComboBox<String> difficultyComboBox;
	private ViewMainFrame viewMainFrame;

	public ViewMainMenu() {
		this.setBackground(new Color(96, 64, 150, 255));
		setTitle("Menú Principal del Juego");
		setSize(400, 300);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		JPanel panel = new JPanel();
		panel.setLayout(new GridLayout(6, 2));

		JLabel nameLabel = new JLabel("Nombre:");
		nameField = new JTextField();
		panel.add(nameLabel);
		panel.add(nameField);

		startButton = new JButton("Iniciar Juego");
		startButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (nameField.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "El campo de texto es obligatorio.", "Error", JOptionPane.ERROR_MESSAGE);
                }else {
                	String playerName = nameField.getText();
    				String difficulty = (String) difficultyComboBox.getSelectedItem();
    				viewMainFrame = new ViewMainFrame(playerName, difficulty);
    				dispose();
                }
			}
		});

		JLabel difficultyLabel = new JLabel("Dificultad:");
		String[] difficulties = { "Fácil", "Medio", "Difícil" };
		difficultyComboBox = new JComboBox<>(difficulties);
		panel.add(difficultyLabel);
		panel.add(difficultyComboBox);
		panel.add(startButton);

		JButton developerInfoButton = new JButton("Información del Desarrollador");
		developerInfoButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				showDeveloperInfo();
			}
		});
		JButton socreHistoryTable = new JButton("Tabla de historial de puntajes");
		socreHistoryTable.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				new ViewScoreHistoryTable();
			}
		});
		panel.add(developerInfoButton);
		panel.add(socreHistoryTable);
		add(panel);
		setVisible(true);
	}

	private void showDeveloperInfo() {
	    JPanel panel = new JPanel(new BorderLayout());

	    ImageIcon developerLogo = new ImageIcon("Image\\logoUPTC.png");
	    JLabel logoLabel = new JLabel(developerLogo);

	    String developerInfo = 
	    		"Nombre: Sebastian Grosso\n" + "Código del Estudiante: 202010344\n" + "Facultad: Ingeniería\n"
						+ "Escuela: Ingenieria de Sistemas\n" + "Año: 2020\n" + "Curso: Programacion III";

	    JTextArea textArea = new JTextArea(developerInfo);
	    textArea.setEditable(false);
	    textArea.setWrapStyleWord(true);
	    textArea.setLineWrap(true);

	    panel.add(logoLabel, BorderLayout.NORTH);
	    panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

	    JOptionPane.showMessageDialog(this, panel, "Información del Desarrollador", JOptionPane.INFORMATION_MESSAGE);
	}

	public JTextField getNameField() {
		return nameField;
	}

	public static void main(String[] args) {
		ViewMainMenu menu = new ViewMainMenu();
	}
}
