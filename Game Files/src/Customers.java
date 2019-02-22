import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Customers extends Circle implements Runnable {
	
	private Thread th = new Thread(this);
	private Thread th2 = new Thread(this);
	private Thread th3 = new Thread(this);
	private boolean drawn = false;
	private boolean isSelectable = true;
	private int tableNumber = 0;
	private int difference = 55;
	
	public Customers(int x, int y) {
		super(x, y);
		setColor(Color.green);
	}

	public Thread getMoodThread() {
		return th;
	}

	public Thread getMoodThread2() {
		return th2;
	}
	
	public Thread getMoodThread3() {
		return th3;
	}

	public void setSelectable(boolean isSelectable) {
		this.isSelectable = isSelectable;
	}
	
	public boolean isSelectable() {
		return isSelectable;
	}

	public void setDrawn(boolean x) {
		drawn = x;
	}

	public boolean isDrawn() {
		return drawn;
	}
	
	
	public void setDifference(int Xcordi) {
		difference = Xcordi;
	}
	
	public void setTableNumber(int tableNumber) {
		this.tableNumber = tableNumber;
	}
	

	public boolean isClicked(int xMouse, int yMouse) {

		if (xMouse > getCircleXPos() && xMouse < getCircleXPos() + 110 && yMouse > getCircleYPos() && yMouse < getCircleYPos() + 50 && isSelectable())
			return true;

		return false;
	}

	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (isSelected() && isSelectable()) {
			g2d.setColor(Color.BLACK);
			g2d.fillOval(getCircleXPos() - 5, getCircleYPos() - 5, 60, 60);
			g2d.fillOval(getCircleXPos() + 50, getCircleYPos() - 5, 60, 60);
			g2d.setColor(getColor());
			g2d.fillOval(getCircleXPos(), getCircleYPos(), 50, 50);
			g2d.fillOval(getCircleXPos() + 55, getCircleYPos(), 50, 50);

		} else {
			g2d.setColor(getColor());
			g2d.fillOval(getCircleXPos(), getCircleYPos(), 50, 50);
			g2d.fillOval(getCircleXPos() + difference, getCircleYPos(), 50, 50);
		}

		drawn = true;
	}

	public void run() {
		setColor(Color.GREEN);
		Play.box.repaint();
		doNothing(Constants.timeForGrean);
		setColor(Color.YELLOW);
		Play.box.repaint();
		doNothing(Constants.timeForYellow);
		setColor(Color.red);
		Play.box.repaint();
		doNothing(Constants.timeToHide);
		drawn = false;
		Play.box.repaint();
		
		if (!isSelectable()) {
			Constants.tables[tableNumber].setReservable(true);
			Constants.tables[tableNumber].setOrdered(false);
			for (int i = 0; i < Constants.meals.length; i++) {
				if (Constants.meals[i].getOrderNumber() == tableNumber)
					Constants.meals[i].setDraw(false);
			}
			Play.wentAngry(tableNumber);
		}
		else
			Play.wentAngry(-2);
		
		
		
	}

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
