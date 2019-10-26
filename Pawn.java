import java.awt.Graphics;
import java.util.ArrayList;

public class Pawn extends Piece{
	
	public int value;
	
	
	public Pawn(int turn, int xloc, int yloc, String piece_name, int score) {
		super(turn,xloc,yloc,piece_name, score);
	}

	@Override
	// returns a list of possible moves, represented as x,y coordinates. 
	public ArrayList<int[]> get_moves(Piece[][] board) {
		ArrayList<int[]> moves = new ArrayList<int[]>();
		
		int i;
		if (team == 0)
			i = 1;
		else
			i = -1;
		
		if (board[x + i][y].get_team() == -1) {
			int[] move = {x+i,y};
			moves.add(move);
		}
		if (team == 0 && x == 1 && board[x+1][y].get_team() == -1) {
			int[] move = {x + 2, y};
			moves.add(move);
		}
		if (team == 1 && x == 6 && board[x-1][y].get_team() == -1) {
			int[] move = {x - 2, y};
			moves.add(move);
		}
		if (y+1 < 8)
			if (board[x + i][y+1].get_team() == (team+1)%2) {
				int[] move = {x+i,y+1};
				moves.add(move);
			}
		if (y-1 >= 0)
			if (board[x + i][y-1].get_team() == (team+1)%2) {
				int[] move = {x+i,y-1};
				moves.add(move);
			}
		
		
		return moves;
	}

	@Override
	public boolean check(King king, Piece[][] board) {
		if (team == 0) {
			if (king.get_loc()[0] - x == 1)
				if (Math.abs(king.get_loc()[1] - y) == 1)
					return true;
		}
		if (team == 1) {
			if (king.get_loc()[0] - x == -1)
				if (Math.abs(king.get_loc()[1] - y) == 1)
					return true;
		}
		return false;
	}
	

}
