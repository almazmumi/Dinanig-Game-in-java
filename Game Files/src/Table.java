import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Table {

	private int tableXPos;
	private int tableYPos;
	private Color color;
	private boolean isReservable = true;
	private int customerNumber = 0;
	private boolean isOrdered = false;

	public Table(int x, int y) {
		setPosition(x, y);
		setColor(Constants.brawn);
	}
	
	public void setCustomerNumber(int customerNumber) {
		this.customerNumber = customerNumber;
	}
	
	public int getCustomerNumber() {
		return customerNumber;
	}
	
	public boolean isReservable() {
		return isReservable;
	}

	public void setReservable(boolean isReservable) {
		this.isReservable = isReservable;
		if(isReservable()){
			setColor(Constants.brawn);
			
		}
	}

	public int getTableXPos() {
		return tableXPos;
	}

	public int getTableYPos() {
		return tableYPos;
	}
	
	public void setPosition(int x, int y) {
		this.tableXPos = x;
		this.tableYPos = y;
	}

	public Color getColor() {
		return color;
	}
	
	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isOrdered() {
		return isOrdered;
	}
	
	public void setOrdered(boolean isOrdered) {
		this.isOrdered = isOrdered;
	}



	
	public boolean isClicked(int xMouse, int yMouse) {

		if(xMouse > getTableXPos() && xMouse < getTableXPos() + Constants.tableSide && yMouse > getTableYPos() && yMouse < getTableYPos() + Constants.tableSide)

			return true;

		return false;
	}



	public void draw(Graphics g) {
			Graphics2D g2d = (Graphics2D) g;
			g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
			g.setColor(color);
			g.fillRect(tableXPos, tableYPos, 120, 120);
		
	}

}
