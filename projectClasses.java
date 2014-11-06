//What it would look like if it were in java and without game engine
//Amos, Anne, Jess, Jon, Kevin

public abstract class Player extends Thread{
	//GUI related things
	mainWin win;
	MiniMap playerMap; 

	//Networking shit
	PrintWriter pw; 
	BufferedReader br; 

	//Non gui related things
	private String type; //Player's type
	private int posX; //player's x position
	private int posY; //player's y position
	private int attempts; //attack attempts
	private int sucessfuls; //successful attacks
	private int hp; //health points
	private int energy;
	private boolean[] hasItems; //List of possible items stored here, true if has it
	private int strength; //player's strength.
	private boolean sentAttack;
	
	public abstract void run();
	//The following two are abstract b/c printwriter and bufferedreader. 
	public abstract String showAttack(); //Tells the server I attacked someone
	public abstract void wasAttacked(); //Lets me know I was attacked and decreases HP
	public abstract void attack();  //Produces the projectile and reduces my energy 

	public void updateEnergy(){
		
	}
	public void updateX(){

	}
	public void updateY(){

	}
	public int getPosX(){
		return posX;
	}
	public int getPosY(){
		return posY;
	}
	public String getHealth(){
		return hp;
	}
	public String getAttempts(){
		return attempts;
	}
	public boolean validMove(){
		//check if there's something blocking their way 
	}
	

}
public class Archer extends Player{
	public Archer(){
		pw = new PrintWriter(); 
		br = new BufferedReader();
	}
	public String showAttack(){

	}
	public void wasAttacked(){

	}
	public void attack(){

	}
	public void run(){

	}

}
public class Wizard extends Player{
	public Wizard(){
		pw = new PrintWriter(); 
		br = new BufferedReader();
	}
	public String showAttack(){

	}
	public void wasAttacked(){
		
	}
	public void attack(){

	}
	public void run(){

	}
}
public class Gunner extends Player{
	public Gunner(){
		pw = new PrintWriter(); 
		br = new BufferedReader();
	}
	public String showAttack(){

	}
	public void wasAttacked(){
		
	}
	public void attack(){

	}
	public void run(){

	}
}
public class Assassin extends Player{
	public Assassin(){
		pw = new PrintWriter(); 
		br = new BufferedReader();
	}
	public String showAttack(){

	}
	public void wasAttacked(){
		
	}
	public void attack(){

	}
	public void run(){

	}
}

public abstract class Projectile{
	private ImageIcon img;
	private int posX;
	private int posY;

	public abstract void intersect();
}

public abstract class Item{
	private String type; 
	private ImageIcon itemImage; 
	private int posX; 
	private int posY; 
	public returnType(){
		return type;
	}
	
}
public class SpeedItem extends Item{
	public speedItem(){
		type = "Speed"; 
		//etc
	}
	public int doubleSpeed(){
		return 2;
	}
}
public class StrengthItem extends Item{
	public strengthItem(){
		type = "Strength"; 
		//etc
	}
	public int doubleStrength(){
		return 2;
	}
}
public class HealthItem extends Item{
	public healthItem(){
		type = "Strength"; 
		//etc
	}
	public int heal(){
		return 20;
	}
}

public abstract class Obstacle{
	
	private int width;
	private int height;
	private int posX;
	private int posY; 
}
public class Rock extends Obstacle{
	public Rock(){

	}
}
public class Tree extends 

public class Server{
	public Tree(){

	}
	//need to figure out later
}

public class chatBox extends Thread{
	//GUI variables 
	JPanel chatPanel; 
	JTextArea textDisplay; 
	JTextArea textEntry; 
	JScrollPane scrollText;
	JButton send;
	PrintWriter pw; 
	BufferedRead br; 
	Player[] playerList; 
	public chatBox(){ 

	}
	public void sendMessage(){

	}
	public void receiveMessage(){

	}
	private void setColors(){
		//set what colors the players will be when you speak to them individually
	}
	public void run(){

	}
}
public class mainWin extends JFrame implements Runnable{
	//GUI variables
	FullMap map;
	MiniMap playerMap;

	//Non gui variables
	Player[] players;

	public mainWin(){

	}
	public static void main(String [] args){
		mainWin w = new mainWin(); 
	}
	public void run(){

	}
}

public class MiniMap extends FullMap{

}

public class FullMap extends JFrame{

}