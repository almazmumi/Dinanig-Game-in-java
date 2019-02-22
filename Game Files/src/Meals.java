import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Meals implements Runnable {

	private int mealXPos;
	private int mealYPos;
	private Color color;
	private boolean drawn = false;
	private int orderNumber;
	private Thread mealThread = new Thread(this);


	public Meals(int x, int y, Color c , int z) {
		setPosition(x, y);
		setColor(c);
		orderNumber = z;
	}

	public void setPosition(int x, int y) {
		this.mealXPos = x;
		this.mealYPos = y;

	}

	public int getMealXPos() {
		return mealXPos;
	}

	public int getMealYPos() {
		return mealYPos;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public Color getColor() {
		return color;
	}

	public boolean isDrawn() {
		return drawn;
	}

	public void setDraw(boolean x) {
		drawn = x;
	}

	public Thread getMealThread() {
		return mealThread;
	}
	
	public int getOrderNumber() {
		return orderNumber;
	}
	
	public void setOrderNumber(int orderNumber) {
		this.orderNumber = orderNumber;
	}

	public boolean isClicked(int xMouse, int yMouse) {

		if(xMouse > mealXPos && xMouse < mealXPos + 50 && yMouse > mealYPos && yMouse < mealYPos + 50)
			return true;

		return false;
	}
	
	
	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(color);
			g.fillRect(mealXPos, mealYPos, 50, 50);
			drawn = true;
		
	}

	public void run() {
		Graphics g = Play.box.getGraphics();
		doNothing(Constants.timeForMeals);
		draw(g);

	}

	public void doNothing(int millisecond) {
		try {
			Thread.sleep(millisecond);
		} catch (InterruptedException e) {
			System.out.println("Unexpected interrupt");
			System.exit(0);
		}
	}


}
