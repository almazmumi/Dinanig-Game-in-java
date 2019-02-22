import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Circle {

	private int circleXPos;
	private int circleYPos;
	private boolean selected;
	private Color color;
	private int orderNumber=-1;
	private boolean busy = false;
	
	public Circle(int x, int y) {
		setCirclePosition(x, y);
		setColor(Color.gray);
		setSelected(false);
	}

	public int getCircleXPos() {
		return circleXPos;
	}

	public int getCircleYPos() {
		return circleYPos;
	}
	
	public void setCirclePosition(int xCircle, int yCircle) {
		this.circleXPos = xCircle;
		this.circleYPos = yCircle;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}

	public void setOrderNumber(int j) {
		orderNumber = j;
	}
	public int getOrderNumber() {
		return orderNumber;
	}
	
	public void setBusy(boolean b) {
		busy = b;
	}
	
	public boolean isBusy() {
		return busy;
	}
	
	public boolean isClicked(int xMouse, int yMouse) {

		if (xMouse > getCircleXPos() && xMouse < getCircleXPos() + 50 && yMouse > getCircleYPos()
				&& yMouse < getCircleYPos() + 50)
			return true;

		return false;
	}

	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		if (selected) {
			g2d.setColor(Color.BLACK);
			g2d.fillOval(circleXPos - 5, circleYPos - 5, 60, 60);
			g2d.setColor(color);
			g2d.fillOval(circleXPos, circleYPos, 50, 50);
		} else {
			g2d.setColor(color);
			g2d.fillOval(circleXPos, circleYPos, 50, 50);
		}
	}

}
