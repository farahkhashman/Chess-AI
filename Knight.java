import java.awt.Graphics;
import java.awt.Image;
import java.util.ArrayList;

public class Knight extends Piece {
	
	public int value;

	public Knight(int turn, int xloc, int yloc, String piece_name, int score) {
		super(turn,xloc,yloc,piece_name, score);
	}
	
	public int get_value(int turn) {
		if(turn == 1) {
			value = -30;
		}
		else if(turn == 0) {
			value = 30;
		}
		return value;
	}
	
	
	@Override
	public ArrayList<int[]> get_moves(Piece[][] board) {
		
		ArrayList<int[]> moves = new ArrayList<int[]>();
		int[] move = new int[2];
		if(x+1<8 &&y-2>=0) {
		if(board[x+1][y-2].get_team()!=team) {
			move[0] = x+1;
			move[1] = y-2;	
			moves.add(move);
		}
		}
		
		move = new int[2];
		if(x+1<8 &&y+2<8)
		if(board[x+1][y+2].get_team()!=team) {
			move[0] = x+1;
			move[1] = y+2;
			moves.add(move);
		}
		move = new int[2];
		if(x-1>=0 &&y+2<8)
		if(board[x-1][y+2].get_team()!=team) {
			move[0] = x-1;
			move[1] = y+2;
			moves.add(move);
		}
		move = new int[2];
		if(x-1>=0 &&y-2>=0)
		if(board[x-1][y-2].get_team()!=team) {
			move[0] = x-1;
			move[1] = y-2;
			moves.add(move);
		}
		move = new int[2];
		if(x+2<8&&y-1>=0)
		if(board[x+2][y-1].get_team()!=team) {
			move[0] = x+2;
			move[1] = y-1;
			moves.add(move);
		}
		move = new int[2];
		if(x+2<8 &&y+1<8)
		if(board[x+2][y+1].get_team()!=team) {
			move[0] = x+2;
			move[1] = y+1;
			moves.add(move);
		}
		move = new int[2];
		if(x-2>=0&&y+1<8)
		if(board[x-2][y+1].get_team()!=team) {
			move[0] = x-2;
			move[1] = y+1;
			moves.add(move);
		}
		move = new int[2];
		if(x-2>=0&&y-1>=0)
		if(board[x-2][y-1].get_team()!=team) {
			move[0] = x-2;
			move[1] = y-1;
			moves.add(move);
		}
			
			
		return moves;
	}

	@Override
	public boolean check(King king, Piece[][] board) {
		
		int x_dist = king.get_loc()[0] - x;
		int y_dist = king.get_loc()[1] - y;
		
		if (Math.abs(x_dist) == 2 && Math.abs(y_dist) == 1)
			return true;
		else if (Math.abs(x_dist) == 1 && Math.abs(y_dist) == 2)
			return true;
		return false;
	}
	
	

}
