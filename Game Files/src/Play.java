import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;

@SuppressWarnings("serial")
public class Play extends JFrame {

	// Instance Variables
	public static JPanel box;
	private static Circle waiter;
	private static Customers[] customers = new Customers[7];
	private static int scores;
	private static int countLeftCustomers;
	private int mealCounter;
	private int tableColorCounter = 0;
	private static Thread[] allThreads = new Thread[2];
	private ArrayList<Color> color = new ArrayList<Color>();
	private Plates[] plates = new Plates[4];
	// -----------------------------------------------------
	
	
	
	// Instance Variables For Upper Panel
	private JPanel upperPanel = new JPanel();
	private JButton pause = new JButton("Pause");
	private static JLabel scoreLable = new JLabel("Score: 000", SwingConstants.CENTER);
	private JLabel time = new JLabel("Time: 10:00  ", SwingConstants.RIGHT);
	// -----------------------------------------------------------------------

	public Play() {

		// Basic Setting Of JFrame
		super(Constants.NameOfTheGame + " || Play Board");
		setSize(Constants.WIDTH, Constants.HEIGHT);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setResizable(false);
		setLocationRelativeTo(null);
		setUndecorated(true);
		getRootPane().setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));
		getContentPane().setBackground(Color.WHITE);
		// ---------------------------------------------------------------------------------

		// Upper Panel ( Time , Score , Pause button)
		// --------------------------------------
		upperPanel.setBackground(Color.WHITE);
		upperPanel.setLayout(new BorderLayout());
		upperPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, Color.BLACK));
		pause.setBackground(Color.WHITE);
		pause.setBorderPainted(false);
		pause.setContentAreaFilled(false);
		pause.setFocusPainted(false);
		scoreLable.setFont(new Font("", Font.BOLD, 24));
		time.setFont(new Font("", Font.BOLD, 24));
		upperPanel.add(pause, BorderLayout.LINE_START);
		upperPanel.add(scoreLable);
		upperPanel.add(time, BorderLayout.LINE_END);
		pause.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				// To pause all the threads
				for (int i = 0; i < allThreads.length; i++) {
					allThreads[i].suspend();
				}

				for (int i = 0; i < customers.length; i++)
					customers[i].getMoodThread().suspend();

				for (int i = 0; i < customers.length; i++)
					customers[i].getMoodThread2().suspend();
				for (int i = 0; i < customers.length; i++)
					customers[i].getMoodThread3().suspend();
				// ------------------------------------------
				
				// To ask the user if he really want to exit
				int result = -1;
				while (result == -1) {
					final JComponent[] inputs = new JComponent[] { new JLabel("Do you really want to exit?") };
					result = JOptionPane.showConfirmDialog(null, inputs, "Exit", JOptionPane.YES_NO_OPTION);
					if (result == 0) {
						dispose();
						new MainMenu();
					} else if (result == 1) {

						for (int i = 0; i < allThreads.length; i++)
							allThreads[i].resume();

						for (int i = 0; i < customers.length; i++)
							customers[i].getMoodThread().resume();

						for (int i = 0; i < customers.length; i++)
							customers[i].getMoodThread2().resume();
						for (int i = 0; i < customers.length; i++)
							customers[i].getMoodThread3().resume();

					}
				}
				//-----------------------------------------------------------------------------------------------------------
			}
		});
		add(upperPanel, BorderLayout.NORTH);
		// --------------------------------------------------------------------------------
		
		initainalize();
		add(box, BorderLayout.CENTER); // Paint Panel
		setVisible(true);

		// add to Table Colour list
		color.add(Color.RED);
		color.add(Color.YELLOW);
		color.add(Color.BLUE);
		color.add(Color.cyan);
		color.add(Color.PINK);
		color.add(Color.MAGENTA);
		color.add(Color.ORANGE);
		color.add(Color.BLACK);		
		
		// Start All Threads
		allThreads[0] = new Timer();
		allThreads[1] = new RandomCustomers();

		for (int i = 0; i < allThreads.length; i++)
			allThreads[i].start();
		// ------------------------------------------------------------------------------------

	}

	public void initainalize() {
		box = new PaintPanel();
		
		waiter = new Circle((Constants.KitchenX + Constants.KitchenWidth / 2) - 25, 340);

		int locationY = 0;
		for (int i = 0; i < customers.length; i++) {
			customers[i] = new Customers(Constants.carpetX + 4, Constants.carpetY + 20 + locationY);
			locationY += 60;
		}

		for (int i = 0; i < Constants.meals.length; i++)
			Constants.meals[i] = new Meals(0, 0, Color.WHITE, -1);

		Constants.tables[0] = new Table(Constants.table1and3X, Constants.table1and2Y);
		Constants.tables[1] = new Table(Constants.table2and4X, Constants.table1and2Y);
		Constants.tables[2] = new Table(Constants.table1and3X, Constants.table3and4Y);
		Constants.tables[3] = new Table(Constants.table2and4X, Constants.table3and4Y);

		for (int i = 0; i < plates.length; i++) 
			plates[i] = new Plates(Constants.table1and3X, Constants.table1and2Y);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {

			@Override
			public void run() {
				new Play();
			}

		});

	}

	public static void wentAngry(int tableNumber) {

		if (scores > 0) {
			scores = scores - 150;
			scoreLable.setText("Score: " + scores);
		}

		for (int i = 0; i < Constants.meals.length; i++) {
			if (Constants.meals[i].getOrderNumber() == tableNumber) {
				waiter.setColor(Color.GRAY);
				waiter.setSelected(false);
			}
		}

		waiter.setBusy(false);
		countLeftCustomers++;

		if (countLeftCustomers == 7) {
			for (int i = 0; i < allThreads.length; i++)
				allThreads[i].stop();
			addScore();
		}

		
	}

	public static void addScore() {

		JTextField name = new JTextField();
		final JComponent[] inputs = new JComponent[] { new JLabel("Congtatulation, You get " + scores + " points"),
				new JLabel("\nWhat Is your Name"), name };
		while (name.getText().equals("")) {
			int result = JOptionPane.showConfirmDialog(null, inputs, "My custom dialog", JOptionPane.PLAIN_MESSAGE);
			if (result == JOptionPane.OK_OPTION && !name.getText().equals("")) {

				FileWriter writer;
				try {
					writer = new FileWriter("scores.txt", true);
					BufferedWriter bufferedWriter = new BufferedWriter(writer);
					bufferedWriter.newLine();
					bufferedWriter.write(name.getText() + "," + scores);
					bufferedWriter.close();
				} catch (IOException e) {
					
					e.printStackTrace();
				}

				new MainMenu();

			}
		}

	}

	
	
	
	
	// The Main JPanel
	class PaintPanel extends JPanel {
		public PaintPanel() {
			setBackground(Color.WHITE);
			addMouseListener(new MouseAdapter() {
				public void mousePressed(MouseEvent e) {

					
					
					// if Customer Clicked Functions
					for (int i = 0; i < customers.length; i++) {
						if (customers[i].isClicked(e.getX(), e.getY()))
							if (!customers[i].isSelected()) {
								customers[i].setSelected(true);

								for (int j = 0; j < customers.length; j++)
									if ((j != i))
										customers[j].setSelected(false);

								waiter.setSelected(false);

							} else {
								customers[i].setSelected(false);

							}

						repaint();
					} // ---------------------------------------------------------------------

					
					
					
					
					
					// if Waiter Clicked Functions
					if (waiter.isClicked(e.getX(), e.getY())) {
						if (!waiter.isSelected()) {
							waiter.setSelected(true);
							for (int j = 0; j < customers.length; j++)
								customers[j].setSelected(false);

						} else {
							waiter.setSelected(false);

						}

						repaint();
					} // ---------------------------------------------------------------------

					
					
					// if Tables Clicked Functions
					for (int j = 0; j < Constants.tables.length; j++)

						if (Constants.tables[j].isClicked(e.getX(), e.getY())) {
							
							// if Tables Clicked  + Waiter is selected Functions
							if (waiter.isSelected()) {
								
								waiter.setCirclePosition(Constants.tables[j].getTableXPos() + 40,
										Constants.tables[j].getTableYPos() - 60);
								waiter.setSelected(false);
								
								// if Tables Clicked + Waiter is selected + Table is not Reservable(Customers sitting) 
								if (!Constants.tables[j].isReservable())
									
									// if Tables Clicked + Waiter is selected + Customres sitting hasn't made an order
									if (!Constants.tables[j].isOrdered() && waiter.getColor().equals(Color.GRAY)
											&& !Constants.tables[j].getColor().equals(Constants.brawn)) {
										
										waiter.setColor(Constants.tables[j].getColor());
										waiter.setOrderNumber(j);
										Constants.tables[j].setOrdered(true);
										customers[Constants.tables[j].getCustomerNumber()].getMoodThread2().stop();
										customers[Constants.tables[j].getCustomerNumber()].getMoodThread3().start();
										
										// if Tables Clicked + Waiter is selected + Customres sitting has made an order Functions
									} else if (Constants.tables[j].isOrdered() && waiter.getOrderNumber() == j) {
										
										customers[Constants.tables[j].getCustomerNumber()].getMoodThread3().stop();
										waiter.setColor(Color.GRAY);
										waiter.setBusy(false);
										plates[j] = new Plates(Constants.tables[j].getTableXPos() + 60 - 20,Constants.tables[j].getTableYPos() + 60 - 20);										
										plates[j].setDrawn(true);
										color.add(Constants.tables[j].getColor());
										
										// Time to eat Functions
										if (j == 0)
											new Thread(new Runnable() {
												public void run() {
													try {
														Thread.sleep(Constants.timeToEat);
													} catch (InterruptedException e) {
														
														e.printStackTrace();
													}
													customers[Constants.tables[0].getCustomerNumber()].setDrawn(false);
													Constants.tables[0].setOrdered(false);
													Constants.tables[0].setReservable(true);
													plates[0].setDrawn(false);
													repaint();

												}
											}).start();

										if (j == 1)
											new Thread(new Runnable() {

												public void run() {
													try {
														Thread.sleep(Constants.timeToEat);
													} catch (InterruptedException e) {
														
														e.printStackTrace();
													}
													customers[Constants.tables[1].getCustomerNumber()].setDrawn(false);
													Constants.tables[1].setOrdered(false);
													Constants.tables[1].setReservable(true);
													plates[1].setDrawn(false);
													repaint();
												}
											}).start();

										if (j == 2)
											new Thread(new Runnable() {
												public void run() {
													try {
														Thread.sleep(Constants.timeToEat);
													} catch (InterruptedException e) {
														
														e.printStackTrace();
													}
													customers[Constants.tables[2].getCustomerNumber()].setDrawn(false);
													Constants.tables[2].setOrdered(false);
													Constants.tables[2].setReservable(true);
													plates[2].setDrawn(false);
													repaint();
												}
											}).start();

										if (j == 3)
											new Thread(new Runnable() {
												public void run() {
													try {
														Thread.sleep(Constants.timeToEat);
													} catch (InterruptedException e) {
														
														e.printStackTrace();
													}
													customers[Constants.tables[3].getCustomerNumber()].setDrawn(false);
													Constants.tables[3].setOrdered(false);
													Constants.tables[3].setReservable(true);
													plates[3].setDrawn(false);
													repaint();
												}
											}).start();

										new Thread(new Runnable() {
											public void run() {
												try {
													Thread.sleep(Constants.timeToEat);
												} catch (InterruptedException e) {
													
													e.printStackTrace();
												}

												scores = scores + 150;
												scoreLable.setText("Score: " + scores);
												countLeftCustomers++;
												if (countLeftCustomers == 7) {
													for (int i = 0; i < allThreads.length; i++)
														allThreads[i].stop();
													addScore();
													dispose();
												}
											}
										}).start();

									}

								repaint();

							}
							// if Tables Clicked + Customers Selected
							for (int i = 0; i < customers.length; i++) {
								
								// if Tables Clicked + Table is Reservable Function
								if (customers[i].isSelected() && customers[i].isSelectable()
										&& Constants.tables[j].isReservable()) {
									
									customers[i].setCirclePosition(Constants.tables[j].getTableXPos() - 55,
											Constants.tables[j].getTableYPos() + 30);
									Constants.tables[j].setReservable(false);
									customers[i].setDifference(180);
									Constants.tables[j].setCustomerNumber(i);
									customers[i].setSelected(false);
									customers[i].setSelectable(false);
									customers[i].getMoodThread().stop();
									customers[i].getMoodThread2().start();
									customers[i].setTableNumber(j);
									repaint();

									Random r = new Random();

									
									//Time to order Function
									if (j == 0)
										new Thread(new Runnable() {
											public void run() {
												
												int x = r.nextInt(color.size());
												Color c = color.get(x);
												color.remove(x);
												try {
													Thread.sleep(Constants.timeToOrder);
												} catch (InterruptedException e) {
													tableColorCounter++;
												}
												Constants.tables[0].setColor(c);
												repaint();
											}
										}).start();

									if (j == 1)
										new Thread(new Runnable() {

											public void run() {
												int x1 = r.nextInt(color.size());
												Color c = color.get(x1);
												color.remove(x1);
												try {
													Thread.sleep(Constants.timeToOrder);
												} catch (InterruptedException e) {
													tableColorCounter++;
												}
												Constants.tables[1].setColor(c);
												repaint();
											}
										}).start();

									if (j == 2)
										new Thread(new Runnable() {
											public void run() {
												int x2 = r.nextInt(color.size());
												Color c = color.get(x2);
												color.remove(x2);
												try {
													Thread.sleep(Constants.timeToOrder);
												} catch (InterruptedException e) {
											}
												Constants.tables[2].setColor(c);
												repaint();
											}
										}).start();

									if (j == 3)
										new Thread(new Runnable() {
											public void run() {
												int x3 = r.nextInt(color.size());
												Color c = color.get(x3);
												color.remove(x3);
												try {
													Thread.sleep(Constants.timeToOrder);
												} catch (InterruptedException e) {

													tableColorCounter++;
												}
												Constants.tables[3].setColor(c);
												repaint();
											}
										}).start();

									if (tableColorCounter < 3)
										tableColorCounter++;
									else
										tableColorCounter = 0;

								}
							}

						} // ---------------------------------------------------------------------

					
					
					// if Kitchen Clicked Functions
					if(e.getX() > Constants.KitchenX && e.getX() < Constants.KitchenX + Constants.KitchenWidth && e.getY() > Constants.KitchenY && e.getY() < Constants.KitchenHeight + Constants.KitchenY){

						if (waiter.isSelected() && !(waiter.getColor().equals(Color.GRAY)) && !waiter.isBusy()) {
							waiter.setCirclePosition(Constants.KitchenX + (Constants.KitchenWidth / 2) - 25,
									Constants.KitchenHeight + 15);
							waiter.setSelected(false);
							waiter.setColor(Color.GRAY);

							if (mealCounter == 0) {
								Constants.meals[0] = new Meals(Constants.mealX + 10, Constants.mealY + 20,
										Constants.tables[waiter.getOrderNumber()].getColor(), waiter.getOrderNumber());
								Constants.meals[0].getMealThread().start();
								mealCounter++;

							} else if (mealCounter == 1) {
								Constants.meals[1] = new Meals(Constants.mealX + 90, Constants.mealY + 20,
										Constants.tables[waiter.getOrderNumber()].getColor(), waiter.getOrderNumber());
								Constants.meals[1].getMealThread().start();
								mealCounter++;

							} else if (mealCounter == 2) {
								Constants.meals[2] = new Meals(Constants.mealX + 170, Constants.mealY + 20,
										Constants.tables[waiter.getOrderNumber()].getColor(), waiter.getOrderNumber());
								Constants.meals[2].getMealThread().start();
								mealCounter++;

							} else if (mealCounter == 3) {
								Constants.meals[3] = new Meals(Constants.mealX + 250, Constants.mealY + 20,
										Constants.tables[waiter.getOrderNumber()].getColor(), waiter.getOrderNumber());
								Constants.meals[3].getMealThread().start();
								mealCounter = 0;
							}

							waiter.setOrderNumber(-1);
							repaint();

						}
						repaint();
					} // ---------------------------------------------------------------------------------------------------------------

					
					
					// if Meal Clicked Functions
					for (int i = 0; i < Constants.meals.length; i++) {

						if (Constants.meals[i].isClicked(e.getX(), e.getY())
								&& Constants.meals[i].isDrawn() && waiter.isSelected()
								&& waiter.getColor().equals(Color.GRAY)) {
							waiter.setCirclePosition(Constants.KitchenX + (Constants.KitchenWidth / 2) - 25,
									Constants.KitchenHeight + 15);
							waiter.setColor(Constants.meals[i].getColor());
							waiter.setOrderNumber(Constants.meals[i].getOrderNumber());
							waiter.setBusy(true);
							Constants.meals[i].setDraw(false);
							repaint();
						}
					}
					// -----------------------------------------------------------------------------------------
					
					
				}
			});

		}

		public void paintComponent(Graphics g) {
			super.paintComponent(g);

			// Kitchen Rectangle
			g.drawRect(Constants.KitchenX, Constants.KitchenY, Constants.KitchenWidth, Constants.KitchenHeight);
			// Reception Rectangle
			g.setColor(new Color(0, 100, 0));
			g.fillRect(Constants.receptionX, Constants.receptionY, Constants.receptionSide, Constants.receptionSide);
			// Carpet
			g.setColor(new Color(245, 245, 100)); // Beige color
			g.fillRect(Constants.carpetX, Constants.carpetY, Constants.carpetWidth, Constants.carpetHeight);

			// Table 1
			Constants.tables[0].draw(g);
			// Table 2
			Constants.tables[1].draw(g);
			// Table 3
			Constants.tables[2].draw(g);
			// Table 4
			Constants.tables[3].draw(g);
			// Waiter
			waiter.draw(g);
			// customers
			for (int i = 0; i < customers.length; i++)
				if (customers[i].isDrawn())
					customers[i].draw(g);

			for (int i = 0; i < Constants.meals.length; i++)
				if (Constants.meals[i].isDrawn())
					Constants.meals[i].draw(g);
						
			for (int i = 0; i < plates.length; i++) 		
			if(plates[i].isDrawn())
				plates[i].draw(g);
		}

	}
	// --------------------------------------------------------------------------------------------------------------------
	
	// Timer Thread
	class Timer extends Thread {
		int min, sec;

		public void run() {
			doNothing(1000);
			for (min = 9; min >= 0; min--)
				for (sec = 59; sec >= 0; sec--) {
					time.setText("Time " + min + " : " + sec + "  ");
					if (min == 0 && sec == 0){
						
					//	for (int i = 0; i < allThreads.length; i++) 
							allThreads[1].stop();
						
						for (int i = 0; i < customers.length; i++)
							customers[i].getMoodThread().stop();

						for (int i = 0; i < customers.length; i++)
							customers[i].getMoodThread2().stop();
						
						for (int i = 0; i < customers.length; i++)
							customers[i].getMoodThread3().stop();
						
						addScore();
						dispose();
						new MainMenu();
					}
					doNothing(1000);

				}
			
			
		}

	}
	// --------------------------------------------------------------------------------------------------------------------

	// Customer Queue Thread
	class RandomCustomers extends Thread {

		public void run() {

			Random r = new Random();
			Graphics g = box.getGraphics();

			for (int i = 0; i < customers.length; i++) {
				customers[i].draw(g);
				customers[i].getMoodThread().start();
				doNothing(r.nextInt(20000));
			}
		}

	}
	// --------------------------------------------------------------------------------------------------------------------

	// Sleep Function
	public void doNothing(int millisecond) {
		try {
			Thread.sleep(millisecond);
		} catch (InterruptedException e) {
			System.out.println("Unexpected interrupt");
			System.exit(0);
		}
	}

}