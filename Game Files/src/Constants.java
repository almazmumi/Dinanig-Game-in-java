import java.awt.Color;

public class Constants {
	public static final String NameOfTheGame = "Delicious Meal";
	public static final int WIDTH = 1240;
	public static final int HEIGHT = 720;
	public static final int receptionSide = 150;
	public static final int receptionX = 40;
	public static final int receptionY = 0;

	public static final int KitchenX = 350;
	public static final int KitchenY = -1;
	public static final int KitchenWidth = 600;
	public static final int KitchenHeight = 100;
	public static final int carpetX = 60;
	public static final int carpetY = 150;
	public static final int carpetWidth = 110;
	public static final int carpetHeight = HEIGHT;

	public static final int tableSide = 120;
	public static final int table1and3X = KitchenX;
	public static final int table2and4X = KitchenX + KitchenWidth - tableSide;
	public static final int table1and2Y = KitchenY + 200;
	public static final int table3and4Y = KitchenY + 400;

	public static final int mealX = KitchenX + KitchenWidth/5;
	public static final int mealY = KitchenY + KitchenHeight /4;
	
	
	public static final int timeForMeals = 10000;
	public static final int timeForGrean = 25000;
	public static final int timeForYellow = 10000;
	public static final int timeToHide = 5000;
	public static final int timeToOrder = 5000;
	public static final int timeToEat = 10000;
	
	
	
	public static final Color brawn = new Color(165, 75, 75);
	
	public static Table[] tables = new Table[4];
	public static Meals[] meals = new Meals[4];
	
}
