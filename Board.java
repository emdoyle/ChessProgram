/*
 * Defines an object that can see all spaces and sets up chess pieces on them.
 * Does not extend or implement anything
 *
 * Note about 'spaces':
 *   
 *   this array is indexed in 1D from 0...(width*height)
 *   index 0 represents the bottom left corner
 *   the next index represents a move of one space to the
 *   right on the board, or wrapping around to the first of the
 *   next row if necessary.
 *
 *   Example: in an 8x8 board, spaces[19] refers to d3 in chess terms
 */
public class Board {
	
	private static final int A_FILE=0;
	private static final int B_FILE=1;
	private static final int C_FILE=2;
	private static final int D_FILE=3;
	private static final int E_FILE=4;
	private static final int F_FILE=5;
	private static final int G_FILE=6;
	private static final int H_FILE=7;

	private Space[][] spaces;
	private boolean whiteTurn = true;
	private int width, height;
	
  //constructor creates a board with the specified width and height
  //in current implementation, chess can only be played in 8x8
	public Board(int width, int height){
		
		this.width = width;
		this.height = height;
		spaces = new Space[height][width];
		initSpaces(width, height);
		addChessPieces();
		
	}
	
  //initializes the spaces array with Space objects
	private void initSpaces(int width, int height){
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
			
				spaces[i][j] = new Space(i, j);
				
			}
		}
		
	}

  //checks for appropriate size, adds pawns and calls
  //addMinorPieces
	public void addChessPieces(){
		if(width == 8 && height == 8){
			  for(int i = A_FILE; i <= H_FILE; i++){
				  spaces[6][i].setPiece(new Pawn('w'));
				  spaces[1][i].setPiece(new Pawn('b'));
				}
				addMinorPieces();
		}
	}

  //assumes appropriate size because should already be
  //checked
  //adds minor pieces on the first rank for each player
	private void addMinorPieces(){
		char black = 'b';
		char white = 'w';

		spaces[7][A_FILE].setPiece(new Rook(white));
		spaces[7][H_FILE].setPiece(new Rook(white));
		spaces[0][A_FILE].setPiece(new Rook(black));
		spaces[0][H_FILE].setPiece(new Rook(black));

		spaces[7][B_FILE].setPiece(new Knight(white));
		spaces[7][G_FILE].setPiece(new Knight(white));
		spaces[0][B_FILE].setPiece(new Knight(black));
		spaces[0][G_FILE].setPiece(new Knight(black));

		spaces[7][C_FILE].setPiece(new Bishop(white));
		spaces[7][F_FILE].setPiece(new Bishop(white));
		spaces[0][C_FILE].setPiece(new Bishop(black));
		spaces[0][F_FILE].setPiece(new Bishop(black));

		spaces[7][D_FILE].setPiece(new Queen(white));
		spaces[7][E_FILE].setPiece(new King(white));
		spaces[0][D_FILE].setPiece(new Queen(black));
		spaces[0][E_FILE].setPiece(new King(black));

	}

  //accesses Space objects on an 8x8 board,
  //accesses their respective Pieces if occupied,
  //and prints the symbol var of the Piece at
  //the correct location
  public void displayBoard() {
    int spaceNum;
		for (int j = 7; j >= 0; j--) { // ranks from 8th to 1st
			System.out.print((j+1) + ":");
			for (int i = 0; i < 8; i++) { // files from a to h
				spaceNum = (8 * j) + i;
				Space cur = spaces[j][i];
				System.out.print("[");
				if (cur.isOccupied()) {
					System.out.print(cur.getPiece().symbol);
				} else {
					System.out.print(" ");
				}
			  System.out.print("]");
			}
			System.out.println();
		}

		System.out.print(" ");
		for (char let = 'a'; let < 'i'; ++let) {
			System.out.print("  " + let);
		}

		System.out.println();
	}

	public Space[][] getSpacesArray(){
		return spaces;
	}

	public void setSpacesArray(Space[][] spaceArr){
		spaces = spaceArr;
	}

	//returns true if white is up, false if black is up
	public boolean getWhiteTurn(){
		return whiteTurn;
	}

	public void setWhiteTurn(boolean flag){
		whiteTurn = flag;
	}

}
