import java.awt.Graphics;
import java.util.ArrayList;

public class Bishop extends Piece{
	
	public int value;
	
	public Bishop(int turn, int xloc, int yloc, String piece_name, int score) {
		super(turn,xloc,yloc,piece_name, score);
	}
	
	@Override
	// returns a list of possible moves, represented as x,y coordinates. 
	public ArrayList<int[]> get_moves(Piece[][] board) {
		
		ArrayList<int[]> moves = new ArrayList<int[]>();
		
		for (int dist = 1; dist < 8; dist ++) {
			if (x + dist > 7 || y + dist > 7)
				break;
			if (board[x + dist][y + dist].get_team() == team)
				break;
			int[] move = {x + dist, y + dist};
			moves.add(move);
			if (board[x + dist][y + dist].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 8; dist ++) {
			if (x - dist < 0 || y + dist > 7)
				break;
			if (board[x - dist][y + dist].get_team() == team)
				break;
			int[] move = {x - dist, y + dist};
			moves.add(move);
			if (board[x - dist][y + dist].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 8; dist ++) {
			if (x - dist < 0 || y - dist < 0)
				break;
			if (board[x - dist][y - dist].get_team() == team)
				break;
			int[] move = {x - dist, y - dist};
			moves.add(move);
			if (board[x - dist][y - dist].get_team() == (team+1)%2)
				break;
		}
		for (int dist = 1; dist < 8; dist ++) {
			if (x + dist > 7 || y - dist < 0)
				break;
			if (board[x + dist][y - dist].get_team() == team)
				break;
			int[] move = {x + dist, y - dist};
			moves.add(move);
			if (board[x + dist][y - dist].get_team() == (team+1)%2)
				break;
		}
		return moves;
	}

	@Override
	public boolean check(King king, Piece[][] board) {
		int x_dist = king.get_loc()[0] - x;
		int y_dist = king.get_loc()[1] - y;
		if (Math.abs(x_dist) == Math.abs(y_dist)) {
			int x_dir = x_dist/Math.abs(x_dist);
			int y_dir = y_dist/Math.abs(y_dist);
			for (int i = 1; i < x_dist; i ++)
				for (int j = 1; j < y_dist; j++) {
					if (board[x + x_dir*i][y + y_dir*j].get_team() != -1)
						return false;
				}
			return true;
		}
		return false;
	}

}
