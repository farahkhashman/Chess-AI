	import java.awt.BorderLayout;
	import java.awt.Color;
	import java.awt.Font;
	import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
	import java.awt.event.ActionListener;
	import java.awt.event.KeyEvent;
	import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Random;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
	import javax.swing.JFrame;
	import javax.swing.JPanel;

	public class Chess extends JPanel implements MouseListener, Runnable {
		
		// constants that are predefined and won't change
		private final int width = 600, height = 600;
		private final int square_width = width/8;
		private final int square_height = height/8;
		private final int img_width = square_width - 10;
		private final int img_height = square_width - 10;
		private final int maxsteps = 4;
		private Piece[][] board;
		private Piece last_clicked = null;
		private int lastx = 0,lasty = 0;
		private HashMap<String,Image> images;
		private int turn = 0;
		private boolean game_over = false, moved = false;
		Node starting = new Node(Integer.MAX_VALUE);
		private King[] kings;
		
		private boolean human1 = true;
		private boolean human2 = true;


		public Chess() {
			King king2 = new King(1,7,3,"BlackKing", -900);
			King king1 = new King(0,0,3,"WhiteKing", 900);
			kings = new King[2];
			kings[0] = king1;
			kings[1] = king2;
			
			Piece[] col1 = new Piece[8];
			col1[0] = new Rook(0,0,0,"WhiteRook1", 50);
			col1[1] = new Knight(0,0,1,"WhiteKnight1", 30);
			col1[2] = new Bishop(0,0,2,"WhiteBishop1", 30);
			col1[3] = king1;
			col1[4] = new Queen(0,0,4,"WhiteQueen", 90);
			col1[5] = new Bishop(0,0,5,"WhiteBishop2", 30);
			col1[6] = new Knight(0,0,6,"WhiteKnight2", 30);
			col1[7] = new Rook(0,0,7,"WhiteRook2", 50);
			
			Piece[] col8 = new Piece[8];
			col8[0] = new Rook(1,7,0,"BlackRook1", -50);
			col8[1] = new Knight(1,7,1,"BlackKnight1", -30);
			col8[2] = new Bishop(1,7,2,"BlackBishop1", -30);
			col8[3] = king2;
			col8[4] = new Queen(1,7,4,"BlackQueen", -90);
			col8[5] = new Bishop(1,7,5,"BlackBishop2", -30);
			col8[6] = new Knight(1,7,6,"BlackKnight2", -30);
			col8[7] = new Rook(1,7,7,"BlackRook2", -50);
				
			Piece[] col2 = new Piece[8];
			for (int i = 0; i < 8; i ++) 
				col2[i] = new Pawn(0,1,i,"WhitePawn" + i, 10);
			
			Piece[] col7 = new Piece[8];
			for (int i = 0; i < 8; i ++) 
				col7[i] = new Pawn(1,6,i,"BlackPawn" + i, -10);
			
			board = new Piece[8][];
			board[0] = col1;
			board[1] = col2;
			board[6] = col7;
			board[7] = col8;
			for (int i = 2; i < 6; i++) {
				Piece[] coli = new Piece[8];
				for (int j = 0; j < 8; j ++) {
					coli[j] = new Empty(i,j, 0);
				}
				board[i] = coli;
			}
			
			
			images = new HashMap<String, Image>();
			
			for (int i = 0; i < 8; i ++) {
				Image img = getToolkit().getImage("Images/" + col1[i].get_name() + ".png");	
				images.put(col1[i].get_name(), img.getScaledInstance(img_width,img_height, Image.SCALE_SMOOTH));
			}
			for (int i = 0; i < 8; i ++) {
				Image img = getToolkit().getImage("Images/" + col8[i].get_name() + ".png");	
				images.put(col8[i].get_name(), img.getScaledInstance(img_width,img_height, Image.SCALE_SMOOTH));
			}
			
			for (int i = 0; i < 8; i ++) {
				Image img = getToolkit().getImage("Images/BlackPawn.png");	
				images.put(col7[i].get_name(), img.getScaledInstance(img_width,img_height, Image.SCALE_SMOOTH));
			}
			for (int i = 0; i < 8; i ++) {
				Image img = getToolkit().getImage("Images/WhitePawn.png");	
				images.put(col2[i].get_name(), img.getScaledInstance(img_width,img_height, Image.SCALE_SMOOTH));
			}
		}
		
		public void run() {

		}
		
		// very simple main method to get the game going
		public static void main(String[] args) {
			Chess game = new Chess(); 
			game.start_game();
		}
	 
		// does complicated stuff to initialize the graphics and key listeners
		public void start_game() {
			
			JFrame frame = new JFrame();
			frame.setSize(width+2, height+24);
			frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			
			frame.add(this);
			frame.setLocationRelativeTo(null);
			frame.setResizable(false);
			frame.setVisible(true);
			this.addMouseListener(this);
			this.setFocusable(true);
			Thread t = new Thread(this);
			t.start();
		}

		// defines what we want to happen anytime we draw the game.
		public void paint(Graphics g) {
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, width, height);

			for (int i = 0; i < 8; i ++) {
				for (int j = 0; j < 8; j ++) {
					g.draw3DRect(i*square_width, j*square_height, square_width, square_height, false);
					if (!(board[i][j].get_name().equals("Empty"))) {
						if (board[i][j].equals(last_clicked)) {
							g.setColor(Color.yellow);
							g.fillRect(i*square_width +1, j*square_height+1, square_width-1, square_height-1);
						}
						g.drawImage(images.get(board[i][j].get_name()), i*square_width+5, j*square_height+5, this);
					}
				}
			}
		}

		@Override
		public void mouseClicked(MouseEvent e) {
		
			if (!game_over && (turn == 0 && human1)) {
				Piece clicked = board[e.getX()/square_width][e.getY()/square_height];
				
				if (clicked.get_team() == turn) {
					lastx = e.getX()/square_width;
					lasty = e.getY()/square_height;
					last_clicked = clicked;
				}
				
				else if (last_clicked != null)
					if (is_in(e.getX()/square_width, e.getY()/square_height, last_clicked.get_moves(board))) {
						if (clicked.equals(kings[0])) {
							System.out.println("Player 2 wins!");
							game_over = true;
						}
						if (clicked.equals(kings[1])) {
							System.out.println("Player 1 wins!");
							game_over = true;
						}
							
						board[e.getX()/square_width][e.getY()/square_height] = last_clicked;
						board[lastx][lasty] = new Empty(lastx,lasty, 0);
						last_clicked.move_to(e.getX()/square_width, e.getY()/square_height);
						last_clicked = null;
						if (game_over) {
							repaint();
							return;
						}
						check_board();
						turn = (turn + 1)%2;
						moved = true;
					}
			}
			
			if (!game_over && (!human1 && turn == 0) || (!human2 && turn == 1)) {
				check_board();
			}
			
			else if(!game_over && (human2 && turn == 1)) {
				computer_move();
			}
			
			repaint();
		}
		
		public void computer_move() {
			System.out.println("computer moves");

			int[] BestMove = new int[2];
			Piece BestPiece = null;
			
			Move use = DFS(1, 0, board, starting);
			BestPiece = use.piece;
			//System.out.println(BestPiece);
			//System.out.println(BestPiece.get_loc()[0] +" "+ BestPiece.get_loc()[1]);
			BestMove[0] = use.xloc;
			BestMove[1] = use.yloc;
			//System.out.println(BestMove[0] +" "+ BestMove[1]);
			
			lastx = BestPiece.get_loc()[0];
			lasty = BestPiece.get_loc()[1];
			
			BestPiece.move_to(BestMove[0], BestMove[1]);
			board[BestMove[0]][BestMove[1]] = BestPiece;
			board[lastx][lasty] = new Empty(lastx,lasty, 0);
			check_board();
			moved = true;
			turn = (turn + 1)%2;
			
			
			
		}
		
		public Move DFS(int team, int count, Piece[][] board, Node parent) {
			int best = Integer.MAX_VALUE;
			int worst = Integer.MIN_VALUE;
			int[] BestMove = new int[2];
			Piece BestPiece = null;
			boolean basecase = false;
			
			 ArrayList<Piece> white = new ArrayList<Piece>();
			 ArrayList<Piece> black = new ArrayList<Piece>();
			 
			 //generates the pieces for every copy
			 for(Piece[] p : board) {
				for(int i = 0; i<8; i++) {
					if(!p[i].is_empty() && p[i].get_team() == 1)
						black.add(p[i]);
				}
			}
			 
			 for(Piece[] p : board) {
				for(int i = 0; i<8; i++) {
					if(!p[i].is_empty() && (p[i].get_team() == 0))
						white.add(p[i]);
				}
			}
			 
			//base case 1 
			if(count > maxsteps) {
				int score = scoring(white, black);
				//System.out.println("score: "+score);
				//System.out.println("parent: "+parent.GetInfo());
				//System.out.println("team "+team);
				if(team == 1 && score < parent.GetInfo()) {
					parent.UpdateInfo(score, team);
					//System.out.println("first if in team one "+parent.GetInfo());
					basecase = true;
				}
				if(team == 0 && score > parent.GetInfo()) {
					parent.UpdateInfo(score, team);
					//System.out.println(parent.GetInfo());
					basecase = true;
				}
				Move m1 = new Move(score, basecase);
				return m1;
			}
			
			//base case 2
			if(check_board()) {
				boolean WhiteKing = true;
				boolean BlackKing = true;
				for(Piece p : white) {
					if(p.name.equals("WhiteKing")) {
						WhiteKing = false;
					}
				}
				
				if(WhiteKing) {
					Move m2 = new Move(Integer.MAX_VALUE);
					return m2;
				}
				
				for(Piece p : black) {
					if(p.name.equals("BlackKing")) {
						BlackKing = false;
					}
				}
				
				if(BlackKing) {
					Move m3 = new Move(Integer.MIN_VALUE);
					return m3;
				}
				
			}
			
			//if no base cases
			else {
				//black team
				if(team == 1) {
					for(Piece p : black) {
						ArrayList<int[]> possibleMoves = p.get_moves(board);
						if(!possibleMoves.isEmpty()) {
							for(int[] move : possibleMoves) {
								Piece[][] copy = newboard(board);
								Node one = new Node(Integer.MIN_VALUE, parent);
								parent.AddChild(one);
								lastx = p.x;
								lasty = p.y;
								//System.out.println("possible "+p+" x: " +lastx +"  and y: "+ lasty);
								//System.out.println(move[0] +" "+ move[1]);
								Piece copied = copy[p.x][p.y];
								copied.move_to(move[0], move[1]);
								copy[move[0]][move[1]] = copied;
								copy[lastx][lasty] = new Empty(lastx, lasty, 0);
								Move returned = DFS((team+1)%2, count+1, copy, one);
								//System.out.println(returned.val);
								if(returned.val <= best) {
									best = returned.val;
									BestMove[0] = move[0];
									BestMove[1] = move[1];
									BestPiece = p;
								}
								
								//alpha-beta pruning thing
								if(returned.basecase) {
									//System.out.println("team one");
									//System.out.println(one.GetInfo() +" "+ parent.getParent().GetInfo());
									if(one.GetInfo() < parent.getParent().GetInfo()) {
										//System.out.println(one.GetInfo());
										//System.out.println(parent);
										//System.out.println(one.hasParent());
										//System.out.println(parent.getParent());
										//System.out.println(parent.hasParent());
										break;	
									}
								}
							}
						}
					}
				}
				
				//white team
				if(team ==0) {
					for(Piece p : white) {
						ArrayList<int[]> possibleMoves = p.get_moves(board);
						if(!possibleMoves.isEmpty()) {
							for(int[] move : possibleMoves) {
								Piece[][] copy = newboard(board);
								Node one = new Node(Integer.MAX_VALUE, parent);
								parent.AddChild(one);
								lastx = p.x;
								lasty = p.y;
								Piece copied = copy[p.x][p.y];
								copied.move_to(move[0], move[1]);
								copy[move[0]][move[1]] = copied;
								copy[lastx][lasty] = new Empty(lastx, lasty, 0);	
								Move returned = DFS((team+1)%2, count+1, copy, one);
								if(returned.val >= worst) {
									worst = returned.val;
								}
								
								//alpha-beta pruning thing
								if(returned.basecase) {
									//System.out.println("team 0");
									//System.out.println("base case: "+returned.basecase);
									if(one.GetInfo() > parent.getParent().GetInfo()) {
										//System.out.println(one.GetInfo());
										//System.out.println(parent);
										//System.out.println(one.hasParent());
										//System.out.println(parent.getParent());
										//System.out.println(parent.hasParent());
										break;	
									}
								}
							}
						}
					}
				}	
			}
			//PrintTree(starting);
			
			//once reached the top of the tree, to return the final move to be used
			if(count == 0) {
				Move m = new Move(BestMove[0], BestMove[1], BestPiece);
				return m;	
			}
			
			else {
				if(team == 0) {
					Move m = new Move(worst);
					return m;
				}
				if(team == 1) {
					Move m = new Move(best);
					return m;
				}
			}
			System.out.println("didnt work");
			return null;
		}

		
		//generates the copies of the board
		public Piece[][] newboard(Piece[][] b) {
			Piece[][] copy = new Piece[8][8];
		
			for(int j = 0; j<b.length; j++) {
				for(int i = 0; i<b[j].length; i++) {
					if(!b[j][i].is_empty()) {
											
						if(b[j][i].get_name().contains("Rook")) {
							Rook ro = new Rook(b[j][i].get_team(), b[j][i].get_loc()[0], b[j][i].get_loc()[1], b[j][i].get_name(), b[j][i].get_value());	
							copy[j][i] = ro;
						}
						
						if(b[j][i].get_name().contains("Bishop")) {
							Bishop bish = new Bishop(b[j][i].get_team(), b[j][i].get_loc()[0], b[j][i].get_loc()[1], b[j][i].get_name(), b[j][i].get_value());	
							copy[j][i] = bish;
						}
						
						if(b[j][i].get_name().contains("Knight")) {
							Knight k = new Knight(b[j][i].get_team(), b[j][i].get_loc()[0], b[j][i].get_loc()[1], b[j][i].get_name(), b[j][i].get_value());	
							copy[j][i] = k;
						}
						
						if(b[j][i].get_name().contains("Pawn")) {
							Pawn p = new Pawn(b[j][i].get_team(), b[j][i].get_loc()[0], b[j][i].get_loc()[1], b[j][i].get_name(), b[j][i].get_value());	
							copy[j][i] = p;
						}
						
						if(b[j][i].get_name().contains("King")) {
							King k1 = new King(b[j][i].get_team(), b[j][i].get_loc()[0], b[j][i].get_loc()[1], b[j][i].get_name(), b[j][i].get_value());	
							copy[j][i] = k1;
						}
						
						if(b[j][i].get_name().contains("Queen")) {
							Queen q = new Queen(b[j][i].get_team(), b[j][i].get_loc()[0], b[j][i].get_loc()[1], b[j][i].get_name(), b[j][i].get_value());	
							copy[j][i] = q;	
						}						
					}
					else {
						Empty e = new Empty(j, i, 0);
						copy[j][i] = e;
					}
				}
			}
			/*for(int k = 0; k<8; k++) {
				for(int l = 0; l<8; l++) {
					System.out.println(copy[k][l]);
				}
				System.out.println();
			}*/
			return copy;
		}
		
		public void PrintTree(Node start) {
			ArrayList<Node> toVisit = new ArrayList<Node>();
			ArrayList<Node> visited = new ArrayList<Node>();
			Node v = null;
			Node initial = start;
			toVisit.add(initial);
			
			while(!toVisit.isEmpty()) {
				v = toVisit.get(0);
				System.out.println(v.GetInfo() +" "+ v.getChildren());
					for(Node n : v.getChildren()) {
						Node neighbour = n;
						if(!toVisit.contains(neighbour) && !visited.contains(neighbour)) {
							toVisit.add(neighbour);
						}
					}
				visited.add(toVisit.remove(0));
			}		
		}
		
		//black wants the most negative
		//white wants more positive
		public int scoring( ArrayList<Piece> white, ArrayList<Piece> black) {
			int score = 0;
				for(Piece p1 : black) {
					score += p1.value;
					if(p1.get_name().contains("Pawn") && p1.get_loc()[0]!= 6) {
						score -= 1;
					}
					if(p1.get_name().contains("Knight") && p1.get_loc()[0] <= 5 && p1.get_loc()[0] >=2) {
						score -= 2;
					}
				
				}
			
				for(Piece p2 : white) {
					score += p2.value;
				}
	
			return score;
		}
		
		
		public boolean is_in(int x, int y, ArrayList<int[]> moves) {
			for (int[] pair : moves) {
				if (x == pair[0] && y == pair[1])
					return true;
			}
			return false;
		}
		
		
		public boolean check_board() {
			// test for check
			for (int i = 0; i < 8; i ++)
				for (int j = 0; j < 8; j++) {
					if (board[i][j].get_team()!= -1)
						if (board[i][j].check(kings[(board[i][j].get_team()+1)%2], board)) {
							//System.out.println("Check!");
							return true;
						}
				}
			return false;
		}
		

		
		@Override
		public void mousePressed(MouseEvent e) {}

		@Override
		public void mouseReleased(MouseEvent e) {}

		@Override
		public void mouseEntered(MouseEvent e) {}

		@Override
		public void mouseExited(MouseEvent e) {}

	}
	
	
	/*ArrayList<Piece> eligible = new ArrayList<Piece>();
	for(Piece p : opponent) {
		if(p.get_moves(board) != null) {
			eligible.add(p);
		}
	}
	
	//int random = rand.nextInt(eligible.size());
	//Piece selected = eligible.get(random);
	//System.out.println(selected);
	if(selected.get_moves(board).size() == 1) {
		//System.out.println("one move available");
		int[] moving = selected.get_moves(board).get(0);
		lastx = selected.get_loc()[0];
		lasty = selected.get_loc()[1];
		selected.move_to(moving[0], moving[1]);
		//System.out.println(moving[0] +"  and  "+ moving[1]);
		board[moving[0]][moving[1]] = selected;
		board[lastx][lasty] = new Empty(lastx,lasty);
		check_board();
		moved = true;
		turn = (turn + 1)%2;
	}
	
	else if(selected.get_moves(board).size() > 1) {
		//System.out.println("more than one move available");
		//random = rand.nextInt(selected.get_moves(board).size());
		//int[] moving = selected.get_moves(board).get(random);
		//int [] moving = bestMove(turn, selected);
		int[] moving = BestMove(turn);
		lastx = selected.get_loc()[0];
		lasty = selected.get_loc()[1];
		//System.out.println(moving[0] +"  and  "+ moving[1]);
		selected.move_to(moving[0], moving[1]);
		board[moving[0]][moving[1]] = selected;
		board[lastx][lasty] = new Empty(lastx,lasty);
		check_board();
		moved = true;
		turn = (turn + 1)%2;
	}
	
	Piece selected = (Piece) BestMove(turn).get(0);
	int[] moving = (int[]) BestMove(turn).get(1);
	lastx = selected.get_loc()[0];
	lasty = selected.get_loc()[1];
	selected.move_to(moving[0], moving[1]);
	board[moving[0]][moving[1]] = selected;
	board[lastx][lasty] = new Empty(lastx,lasty);
	check_board();
	moved = true;
	turn = (turn + 1)%2;*/
	
	/*public int[] bestMove(int turn, Piece p) {
	int[] BestMove = null;
	int BestValue = 0;
	ArrayList<int[]> possibleMoves = p.get_moves(board);

	if(turn == 1 && human2) {
		for(int[] move : possibleMoves) {
			if(!board[move[0]][move[1]].is_empty()) {
				if(board[move[0]][move[1]].value > BestValue) {
					BestValue = board[move[0]][move[1]].value;
					BestMove = move;
				}
			}
		}
	}
	if(BestMove == null) {
		Random rand = new Random();
		int random = rand.nextInt(p.get_moves(board).size());
		BestMove = p.get_moves(board).get(random);
	}
	return BestMove;
}*/

/*public ArrayList<Object> BestMove(int turn) {
	int[] BestMove = null;
	int BestValue = 0;
	Piece BestPiece = null;
	Random rand = new Random();
	//HashMap<Piece, int[]> best = new HashMap<Piece, int[]>();
	ArrayList<Object> best = new ArrayList<Object>();
	
	for(Piece p : opponent) {
		ArrayList<int[]> possibleMoves = p.get_moves(board);
		for(int[] move : possibleMoves) {
			if(!board[move[0]][move[1]].is_empty()) {
				if(board[move[0]][move[1]].value > BestValue) {
					BestValue = board[move[0]][move[1]].value;
					BestMove = move;
					BestPiece = p;
				}
			}
		}
	}
	
	
	if(BestPiece == null) {
		ArrayList<Piece> eligible = new ArrayList<Piece>();
		for(Piece p : opponent) {
			if(p.get_moves(board) != null) {
				eligible.add(p);
			}
		}
		int random = rand.nextInt(eligible.size());
		BestPiece = eligible.get(random);
	}
	
	if(BestMove == null) {
		int random = rand.nextInt(BestPiece.get_moves(board).size());
		BestMove = BestPiece.get_moves(board).get(random);
	}
	//best.put(BestPiece, BestMove);
	best.add(BestPiece);
	best.add(BestMove);
	return best;
}*/
	
	
	
	//Random rand = new Random();
	
	/*ArrayList<Piece> black = new ArrayList<Piece>();
	for(Piece[] p : board) {
		for(int i = 0; i<8; i++) {
			if(p[i].get_team() == 1)
				black.add(p[i]);
		}
	}
	
	for(Piece p : black) {
		ArrayList<int[]> possibleMoves = p.get_moves(board);
		if(!possibleMoves.isEmpty()) {
			for(int[] move : possibleMoves) {
				if(!board[move[0]][move[1]].is_empty()) {
			
					if(board[move[0]][move[1]].value >= BestValue && board[move[0]][move[1]].value > 0) {
						BestValue = board[move[0]][move[1]].value;
						BestMove = move;
						BestPiece = p;
					}
				}	
			}
		}
	}
	
	
	
	if(BestPiece == null) {
		ArrayList<Piece> eligible = new ArrayList<Piece>();
		for(Piece p : black) {
			if(!p.get_moves(board).isEmpty()) {
				eligible.add(p);
			}
		}
		
		int random = rand.nextInt(eligible.size()-1);
		BestPiece = eligible.get(random);
		if(BestPiece.get_moves(board).size() == 1) {
			BestMove = BestPiece.get_moves(board).get(0);
		}
		else if(BestPiece.get_moves(board).size() > 1) {
			random = rand.nextInt((BestPiece.get_moves(board).size())-1);
			BestMove = BestPiece.get_moves(board).get(random);
		}
	}
	*/
	