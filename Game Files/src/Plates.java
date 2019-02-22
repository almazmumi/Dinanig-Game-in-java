import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

public class Plates {
	
	private int plateXPos;
	private int plateYPos;
	private boolean drawn = false;
	

	public Plates(int x, int y) {
		plateXPos = x;
		plateYPos = y;
		
	}
	
	public void setDrawn(boolean x) {
		drawn = x;
	}

	public boolean isDrawn() {
		return drawn;
	}

	public void draw(Graphics g) {

		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);	
		g2d.setColor(Color.BLACK);
		g2d.fillOval(plateXPos - 3, plateYPos - 3, 46, 46);
		g2d.setColor(Color.WHITE);
		g2d.fillOval(plateXPos , plateYPos , 40, 40);
		drawn = true;
	}

}
