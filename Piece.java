import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Piece {
	
	protected int team, value;
	protected int x, y;
	protected String name;
	
	public Piece(int turn, int xloc, int yloc, String piece_name, int score) {
		team = turn;
		x = xloc;
		y = yloc;
		name = piece_name;
		value = score;
	}
	public int get_value() {
		return value;  
	}
	public ArrayList<int[]> get_moves(Piece[][] board) {
		return null;
	}
	
	public void move_to(int i, int j) {
		x = i;
		y = j;
	}
	
	public int get_team() {
		return team;
	}
	
	public String get_name() {
		return name;
	}
	
	public int[] get_loc() {
		int[] loc = {x,y};
		return loc;
	}
	
	public boolean is_empty() {
		return false;
	}
	
	public boolean check(King king, Piece[][] board) {
		return false;
	}

}
