/*
 * Defines an object that can see all spaces and sets up chess pieces on them.
 * Does not extend or implement anything
 *
 */
public class Board {

/*
 * TODO: Add check-detection
 */
	
	private static final int A_FILE=0;
	private static final int B_FILE=1;
	private static final int C_FILE=2;
	private static final int D_FILE=3;
	private static final int E_FILE=4;
	private static final int F_FILE=5;
	private static final int G_FILE=6;
	private static final int H_FILE=7;

	private Space[][] spaces;
	private King whiteKing = new King('w');
	private King blackKing = new King('b');
	private boolean whiteTurn = true;
	private boolean whiteQCastle = true;
	private boolean whiteKCastle = true;
	private boolean blackQCastle = true;
	private boolean blackKCastle = true;
	private boolean whiteInCheck = false;
	private boolean blackInCheck = false;
	//if enPassantW = 'c', then the white c-pawn is vulnerable to en passant
	private char enPassantFileW = 'x'; //x indicates no e.p.
	private char enPassantFileB = 'x';
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

	private boolean inRange(int num){
		return num >= 0 && num <= 7;
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
				  spaces[6][i].setPiece(new Pawn('b'));
				  spaces[1][i].setPiece(new Pawn('w'));
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
		
		spaces[7][A_FILE].setPiece(new Rook(black));
		spaces[7][H_FILE].setPiece(new Rook(black));
		spaces[0][A_FILE].setPiece(new Rook(white));
		spaces[0][H_FILE].setPiece(new Rook(white));

		spaces[7][B_FILE].setPiece(new Knight(black));
		spaces[7][G_FILE].setPiece(new Knight(black));
		spaces[0][B_FILE].setPiece(new Knight(white));
		spaces[0][G_FILE].setPiece(new Knight(white));

		spaces[7][C_FILE].setPiece(new Bishop(black));
		spaces[7][F_FILE].setPiece(new Bishop(black));
		spaces[0][C_FILE].setPiece(new Bishop(white));
		spaces[0][F_FILE].setPiece(new Bishop(white));

		spaces[7][D_FILE].setPiece(new Queen(black));
		spaces[7][E_FILE].setPiece(blackKing);
		spaces[0][D_FILE].setPiece(new Queen(white));
		spaces[0][E_FILE].setPiece(whiteKing);

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
					System.out.print(cur.getPiece().getSymbol());
					System.out.print(cur.getPiece().getTeam());
				} else {
					System.out.print("  ");
				}
			  System.out.print("]");
			}
			System.out.println();
		}

		System.out.print(" ");
		for (char let = 'a'; let < 'i'; ++let) {
			System.out.print("   " + let);
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

	public char getTurn(){
		if(whiteTurn){
			return 'w';
		}
		return 'b';
	}

	public void setWhiteTurn(boolean flag){
		whiteTurn = flag;
	}

	public void setCastle(char team, char side, boolean flag){
		team = Character.toLowerCase(team);
		side = Character.toLowerCase(side);

		if(team == 'w'){
			if(side == 'q'){
				whiteQCastle = flag;
			}else if(side == 'k'){
				whiteKCastle = flag;
			}
		}else if(team == 'b'){
			if(side == 'q'){
				blackQCastle = flag;
			}else if(side == 'k'){
				blackKCastle = flag;
			}
		}
	}

	public boolean getCastle(char team, char side){
		team = Character.toLowerCase(team);
		side = Character.toLowerCase(side);

		if(team == 'w'){
			if(side == 'q'){
				return whiteQCastle;
			}else if(side == 'k'){
				return whiteKCastle;
			}
		}else if(team == 'b'){
			if(side == 'q'){
				return blackQCastle;
			}else if(side == 'k'){
				return blackKCastle;
			}
		}

		return false;
	}

	public boolean detectCheck(char team){
		King currentKing = null;
		if(team == 'w'){
			currentKing = whiteKing;
		}else if(team == 'b'){
			currentKing = blackKing;
		}else{
			System.out.println("invalid team character in detectCheck");
			return false;
		}

		Space kingSpace = currentKing.getSpace();
		int kingRank = kingSpace.getRank();
		int kingFile = kingSpace.getFile();

		if(checkPawnSpots(kingRank, kingFile, team)){
			setCheck(team, true);
			return true;
		}

		if(checkKnightSpots(kingRank, kingFile, team)){
			setCheck(team, true);
			return true;
		}

		int currentRank = -1;
		int currentFile = -1;

		boolean diagonal = false;
		boolean horizOrVert = false;
		boolean blocked = false;

		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <=1; j++){
				currentRank = kingRank;
				currentFile = kingFile;

				diagonal = Math.abs(i) == 1 && Math.abs(j) == 1;
				horizOrVert = Math.abs(i) == 1 || Math.abs(j) == 1 && !diagonal;

				blocked = false;

				if(!(i == 0 && j == 0)){
					while(!blocked && inRange(currentRank+i) && inRange(currentFile+j)){
						currentRank += i;
						currentFile += j;

						Space checkSpace = spaces[currentRank][currentFile];
						Piece checkPiece = null;

						if(checkSpace.getPiece() != null){
							checkPiece = checkSpace.getPiece();
							if(diagonal && checkPiece.getSymbol() == 'B' &&
								checkPiece.getTeam() != team){
								setCheck(team, true);
								return true;
							}
							if(horizOrVert && checkPiece.getSymbol() == 'R' &&
								checkPiece.getTeam() != team){
								setCheck(team, true);
								return true;
							}
							if(checkPiece.getSymbol() == 'Q' &&
								checkPiece.getTeam() != team){
								setCheck(team, true);
								return true;
							}
							blocked = true;
						}
					}
				}
			}
		}
		return false;
	}

	private boolean checkPawnSpots(int kingRank, int kingFile, char team){
		int checkRank = -1;
		if(team == 'w'){
			checkRank = kingRank + 1;
		}else{
			checkRank = kingRank - 1;
		}

		if(checkRank < 0 || checkRank > 7){
			return false;
		}
		for(int i = -1; i <= 1; i++){
			if(i != 0 && spaces[checkRank][kingFile+i].getPiece() != null &&
				spaces[checkRank][kingFile+i].getPiece().getTeam() != team &&
				spaces[checkRank][kingFile+i].getPiece().getSymbol() == 'P'){
				return true;
			}
		}

		return false;
	}

	private boolean checkKnightSpots(int kingRank, int kingFile, char team){
		for(int i = -2; i <= 2; i++){
			for(int j = -2; j <= 2; j++){
				if(i != 0 && j != 0
				&& !((Math.abs(i) == 1)
				&& (Math.abs(j) == 1))
				&& !(i%2 == 0 && j%2 == 0)
				&& kingFile >= j*-1 && kingFile <= 7-j
				&& kingRank >= i*-1 && kingRank <= 7-i){
					if(spaces[kingRank+i][kingFile+j].getPiece() != null &&
					spaces[kingRank+i][kingFile+j].getPiece().getTeam() != team &&
					spaces[kingRank+i][kingFile+j].getPiece().getSymbol() == 'N'){
						return true;
					}
				}
			}
		}
		return false;
	}

	public boolean detectCheckMate(char team){
		if(!getCheck(team)){return false;}
		
		return false;
	}

	public boolean getCheck(char team){
		if(team == 'w'){
			return whiteInCheck;
		}else if(team == 'b'){
			return blackInCheck;
		}else{
			System.out.println("Improperly called getCheck");
			return false;
		}
	}

	public void setCheck(char team, boolean flag){
		if(team == 'w'){
			whiteInCheck = flag;
		}else if(team == 'b'){
			blackInCheck = flag;
		}else{
			System.out.println("Improperly called setCheck");
		}
	}

	public char getEnPassant(char team){
		if(team == 'w'){
			return enPassantFileW;
		}else if(team == 'b'){
			return enPassantFileB;
		}else{
			return 'x';
		}
	}

	public void setEnPassant(char team, char file){
		if(team == 'w'){
			enPassantFileW = file;
		}else if(team == 'b'){
			enPassantFileB = file;
		}
	}
}
