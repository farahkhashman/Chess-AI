
public class Move {
	
	public int xloc, yloc;
	public int val;
	public String nm;
	public Piece piece;
	boolean basecase = false;

	public Move(int x, int y, int value, Piece p) {
		xloc = x;
		yloc = y;
		val = value; 
		piece = p;
	}
	
	public Move(int value) {
		val = value; 
	}
	
	public Move(int x, int y) {
		xloc = x;
		yloc = y;
	}
	
	public Move(int x, int y, Piece p) {
		xloc = x;
		yloc = y;
		piece = p;
	}
	
	public Move(int x, int y, int value) {
		xloc = x;
		yloc = y;
		val = value; 
	}
	
	public Move(int x, int y, String name) {
		xloc = x;
		yloc = y;
		nm = name;
	}
	
	public Move(int value, boolean base) {
		val = value;
		basecase = base;
	}
	
}
