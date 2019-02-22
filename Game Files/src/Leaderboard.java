import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

@SuppressWarnings("serial")
public class Leaderboard extends JFrame {
	
	// Instance Variables 
	private static ArrayList<ScoresSystem> scoreArray = new ArrayList<ScoresSystem>();
	static Object[][] tableData;
	private static DefaultTableModel tableModel = new DefaultTableModel();
	private static JRadioButton byNameRadioButton;
	private static JRadioButton byScoreRadioButton;
	private static JRadioButton ascendingOrder;
	private static JRadioButton descendingOrder;
	// -------------------
	
	
	
	public Leaderboard() {

		// Basic Setting Of JFrame
		// -------------------------------------------------------
		super(Constants.NameOfTheGame + " || Leader Board");
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setLocationRelativeTo(null); // To Centre the Frame
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		getContentPane().setBackground(Color.WHITE);
		setUndecorated(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		setLayout(new BorderLayout());
		// -------------------------------------------------------

		// Title of The JFrame ----------------------------------------
		JLabel Title = new JLabel("Leaderboard", SwingConstants.CENTER);
		Title.setFont(new Font("arial", Font.BOLD, 100));
		add(Title, BorderLayout.NORTH);
		// Title of The JFrame ----------------------------------------

		// Option Panel -----------------------------------------------------
		JPanel optionPanel = new JPanel();
		optionPanel.setBorder(new EmptyBorder(20, 20, 20, 20)); // Padding to the Option panel
		optionPanel.setLayout(new BoxLayout(optionPanel, BoxLayout.Y_AXIS));
		optionPanel.setBackground(Color.WHITE);
		Font font1 = new Font("", Font.PLAIN, 20);

		JButton backButton = new JButton("Back");
		ButtonGroup sortingOptionGroup = new ButtonGroup();
		JLabel sortByLabel = new JLabel("Sort By:");

		byNameRadioButton = new JRadioButton("Name", true);
		byScoreRadioButton = new JRadioButton("Score");

		byNameRadioButton.setBackground(Color.WHITE);
		byScoreRadioButton.setBackground(Color.WHITE);

		sortingOptionGroup.add(byScoreRadioButton);
		sortingOptionGroup.add(byNameRadioButton);

		ButtonGroup sortingOrderGroup = new ButtonGroup();
		ascendingOrder = new JRadioButton("Ascending Order", true);
		descendingOrder = new JRadioButton("Descending Order");
		ascendingOrder.setBackground(Color.WHITE);
		descendingOrder.setBackground(Color.WHITE);

		sortingOrderGroup.add(descendingOrder);
		sortingOrderGroup.add(ascendingOrder);

		sortByLabel.setFont(font1);
		byNameRadioButton.setFont(font1);
		byScoreRadioButton.setFont(font1);
		ascendingOrder.setFont(font1);
		descendingOrder.setFont(font1);

		optionPanel.add(sortByLabel);
		optionPanel.add(byNameRadioButton);
		optionPanel.add(byScoreRadioButton);
		optionPanel.add(Box.createVerticalStrut(30));
		optionPanel.add(ascendingOrder);
		optionPanel.add(descendingOrder);
		optionPanel.add(Box.createVerticalStrut(15));
		optionPanel.add(backButton);
		add(optionPanel, BorderLayout.WEST);
		// Option Panel -----------------------------------------------------

		// Table ------------------------------------------------------------
		JTable table = new JTable(tableModel) {
			@Override
			public boolean isCellEditable(int rowIndex, int colIndex) {
				return false; // Disable to edit any cell
			}
		};

		tableModel.setColumnIdentifiers(new String[] { "Name", "Score" });

		// to change the design of the table
		table.getTableHeader().setBackground(Color.darkGray);
		table.getTableHeader().setForeground(Color.WHITE);
		table.setBackground(Color.WHITE);
		table.setGridColor(Color.WHITE);
		table.setRowHeight(30);
		((DefaultTableCellRenderer) table.getTableHeader().getDefaultRenderer()).setHorizontalAlignment(JLabel.CENTER);
		table.getTableHeader().setFont(font1);
		table.setFont(font1);
		//-------------------------------------
		
		
		// to disable to select any cells
		table.setFocusable(false);
		table.setRowSelectionAllowed(false);
		table.getTableHeader().setReorderingAllowed(false);
		// -------------------------------------------------
		
		// Scroll Pane 
		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.getViewport().setBackground(Color.WHITE);
		add(scrollPane);
		// ------------------------------------------------
		
		
		getScores();
		setVisible(true);

		
		
		byNameRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sortedFunction();

			}

		});

		byScoreRadioButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sortedFunction();

			}

		});

		ascendingOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sortByOrder();

			}

		});

		descendingOrder.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {
				sortByOrder();
			}

		});

		backButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				new MainMenu();
				dispose();

			}

		});

	}

	public static void main(String[] args) {
		new Leaderboard();
	}

	
	// Sort by name or score 
	public void sortedFunction() {
		if (byNameRadioButton.isSelected()) {
			Collections.sort(scoreArray);
			sortByOrder();
		} else if (byScoreRadioButton.isSelected()) {
			Collections.sort(scoreArray, new Comparator<ScoresSystem>() {
				@Override
				public int compare(ScoresSystem o1, ScoresSystem o2) {

					if (o1.getScore() > o2.getScore())
						return 1;
					else if (o1.getScore() == o2.getScore())
						return 0;
					else
						return -1;
				}
			});
			sortByOrder();

		}
	}
	//--------------------------
	
	
	
	// Sort By order 
	public static void sortByOrder() {
		if (ascendingOrder.isSelected()) {
			for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
				tableModel.removeRow(i);

			for (int i = 0; i < tableData.length; i++) {
				tableData[i][0] = scoreArray.get(i).getName();
				tableData[i][1] = scoreArray.get(i).getScore();
				tableModel.addRow(tableData[i]);
			}

		}

		else if (descendingOrder.isSelected()) {
			for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
				tableModel.removeRow(i);

			for (int i = 0; i < tableData.length; i++) {
				tableData[i][0] = scoreArray.get(i).getName();
				tableData[i][1] = scoreArray.get(i).getScore();
			}

			for (int i = tableData.length - 1; i >= 0; i--)
				tableModel.addRow(tableData[i]);

		}
	}
	//---------------------------------------------------------
	
	
	public static void getScores() {
		// to delete everything in the table 
		scoreArray.clear();
		for (int i = tableModel.getRowCount() - 1; i >= 0; i--)
			tableModel.removeRow(i);
		//------------------------------
		
		// To read the scores from the file and sorted and add them to table 
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader("scores.txt"));
			String line = null;
			
			while ((line = br.readLine()) != null) {
				String[] values = line.split(",");
				scoreArray.add(new ScoresSystem(values[0], Integer.parseInt(values[1])));
			}
			br.close();
		} catch (IOException e) {
			System.out.println(e);
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		
		Collections.sort(scoreArray);

		
		tableData = new Object[scoreArray.size()][2];
		

		for (int i = 0; i < tableData.length; i++) {
			tableData[i][0] = scoreArray.get(i).getName();
			tableData[i][1] = scoreArray.get(i).getScore();
			tableModel.addRow(tableData[i]);
		}
		
		// ----------------------------------------------------------------------------

	}
}
