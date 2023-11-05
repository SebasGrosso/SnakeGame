package view;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class ViewScoreHistoryTable extends JFrame {
	private JTable table;
	private DefaultTableModel model;

	public ViewScoreHistoryTable() {
		setTitle("Historial de puntajes");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(550, 300);
		setLocationRelativeTo(null);

		model = new DefaultTableModel();
		table = new JTable(model);
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);

		model.addColumn("Historial de puntajes");

		try (BufferedReader reader = new BufferedReader(
				new FileReader("scoreHistory.ser"))) {
			String line;
			while ((line = reader.readLine()) != null) {
				String[] parts = line.split(",");
				model.addRow(parts);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		add(scrollPane);
		setVisible(true);
	}
}
