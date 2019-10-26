import java.awt.Graphics;
import java.util.ArrayList;

public class Empty extends Piece{
	
	public Empty(int xloc,int yloc, int score) {
		super(-1,xloc,yloc,"Empty", 0);
	}

	@Override
	public ArrayList<int[]> get_moves(Piece[][] board) {
		
		return new ArrayList<int[]>();
	}


	@Override
	public boolean is_empty() {
		return true;
	}

	@Override
	public boolean check(King king, Piece[][] board) {
		// TODO Auto-generated method stub
		return false;
	}

}
