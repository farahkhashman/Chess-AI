import java.awt.Graphics;
import java.util.ArrayList;

public class Rook extends Piece{
	
	public int value;
	
	public Rook(int turn, int xloc, int yloc, String piece_name, int score) {
		super(turn,xloc,yloc,piece_name, score);
	}
	
	public int get_value(int turn) {
		if(turn == 1) {
			value = -50;
		}
		else if(turn == 0) {
			value = 50;
		}
		return value;
	}

	@Override
	public ArrayList<int[]> get_moves(Piece[][] board) {
		
		
	ArrayList<int[]> moves = new ArrayList<int[]>();
		
		for (int dist = 1; dist < 8; dist ++) {
			if (x + dist > 7 )
				break;
			if (board[x + dist][y].get_team() == team)
				break;
			int[] move = {x + dist,y};
			moves.add(move);
			if (board[x + dist][y].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 8; dist ++) {
			if (x - dist < 0 )
				break;
			if (board[x - dist][y].get_team() == team)
				break;
			int[] move = {x - dist, y};
			moves.add(move);
			if (board[x - dist][y].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 8; dist ++) {
			if ( y - dist < 0)
				break;
			if (board[x ][y - dist].get_team() == team)
				break;
			int[] move = {x, y - dist};
			moves.add(move);
			if (board[x][y - dist].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 8; dist ++) {
			if ( y + dist > 7)
				break;
			if (board[x][y + dist].get_team() == team)
				break;
			int[] move = {x , y + dist};
			moves.add(move);
			if (board[x][y + dist].get_team() == (team+1)%2)
				break;
		}
		return moves;
	}

	@Override
	public boolean check(King king, Piece[][] board) {
		int x_dist = king.get_loc()[0] - x;
		int y_dist = king.get_loc()[1] - y;
		
		if (y_dist == 0) {
			int x_dir = x_dist/Math.abs(x_dist);
			for (int i = 1; i < x_dist; i ++) {
				if (board[x + i*x_dir][y].get_team() != -1)
					return false;
			}	
			return true;
		}
		if (x_dist == 0) {
			int y_dir = y_dist/Math.abs(y_dist);
			for (int i = 1; i < y_dist; i ++) {
				if (board[x][y + i*y_dir].get_team() != -1)
					return false;
			}
			return true;
		}
		return false;
	}

}
