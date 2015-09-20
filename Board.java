/*
 * Defines an object that can see all spaces and sets up chess pieces on them.
 * Does not extend or implement anything
 *
 */
import java.util.ArrayList;
import java.util.Scanner;

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
	private King whiteKing = new King('w');
	private King blackKing = new King('b');
	private boolean whiteTurn = true;
	private boolean whiteQCastle = true;
	private boolean whiteKCastle = true;
	private boolean blackQCastle = true;
	private boolean blackKCastle = true;
	private boolean whiteInCheck = false;
	private Check whiteCheck = null;
	private Check blackCheck = null;
	private boolean blackInCheck = false;
	//if enPassantW = 'c', then the white c-pawn is vulnerable to en passant
	private char enPassantFileW = 'x'; //x indicates no e.p.
	private char enPassantFileB = 'x';
	private int width, height;
	private boolean checkMate = false;
	
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
		Check resultCheck = new Check();
		

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
			resultCheck.setNumChecking(1);
		}

		if(checkKnightSpots(kingRank, kingFile, team)){
			setCheck(team, true);
			resultCheck.setNumChecking(resultCheck.getNumChecking()+1);
		}
		if(resultCheck.getNumChecking() == 0){
		int currentRank = -1;
		int currentFile = -1;

		boolean diagonal = false;
		boolean horizOrVert = false;
		boolean blocked = false;

		int currNumChecking = 0;

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

						currNumChecking = resultCheck.getNumChecking();

						if(checkSpace.getPiece() != null){
							checkPiece = checkSpace.getPiece();
							
							if(diagonal && checkPiece.getSymbol() == 'B' &&
								checkPiece.getTeam() != team){
								setCheck(team, true);
								resultCheck.setNumChecking(currNumChecking+1);
								currNumChecking++;
								resultCheck.setCheckPiece(currNumChecking, checkPiece);
							}
							if(horizOrVert && checkPiece.getSymbol() == 'R' &&
								checkPiece.getTeam() != team){
								setCheck(team, true);
								resultCheck.setNumChecking(currNumChecking+1);
								currNumChecking++;
								resultCheck.setCheckPiece(currNumChecking, checkPiece);
							}
							if(checkPiece.getSymbol() == 'Q' &&
								checkPiece.getTeam() != team){
								setCheck(team, true);
								resultCheck.setNumChecking(currNumChecking+1);
								currNumChecking++;
								resultCheck.setCheckPiece(currNumChecking, checkPiece);
							}
							blocked = true;
						}
					}
				}
			}
		}
		}

		resultCheck = addCheckSpacesTo(resultCheck, team);

		if(resultCheck.getNumChecking() >= 1){
			if(team == 'w'){
				whiteCheck = resultCheck;
			}else if(team == 'b'){
				blackCheck = resultCheck;
			}
			return true;
		}

		setCheck(team, false);
		return false;
	}

	private Check addCheckSpacesTo(Check result, char team){
		Piece piece1 = result.getCheckPiece(1);
		Piece piece2 = result.getCheckPiece(2);

		ArrayList<Space> checkSpaces = new ArrayList<Space>();

		if(piece1 == null && piece2 == null){
			return result;
		}
		Piece currPiece;
		for(int i = 0; i < 2; i++){
			if(i == 0){currPiece = piece1;}else{currPiece = piece2;}
			if(currPiece != null){
				if(team == 'w'){
					checkSpaces.addAll(spacesBetween(currPiece, whiteKing));
				}else{
					checkSpaces.addAll(spacesBetween(currPiece, blackKing));
				}
			}
		}

		result.setCheckSpaces(checkSpaces);
		return result;
	}

	private ArrayList<Space> spacesBetween(Piece piece1, Piece piece2){
		ArrayList<Space> checkSpaces = new ArrayList<Space>();	

		Space p1Space = piece1.getSpace();
		int p1Rank = p1Space.getRank();
		int p1File = p1Space.getFile();

		Space p2Space = piece2.getSpace();
		int p2Rank = p2Space.getRank();
		int p2File = p2Space.getFile();

	//directions are in terms of p1 -> p2
	//horizDir = 1 means right, horizDir = -1 means left, 0 means no motion horiz
	//vertDir = 1 means up, vertDir = -1 means down, 0 means no motion vert
		int horizDir = 0;
		int vertDir = 0;

		if(p1File < p2File){
			horizDir = 1;
		}else{
			horizDir = -1;
		}

		if(p1Rank < p2Rank){
			vertDir = 1;
		}else{
			vertDir = -1;
		}

		int currRank = p1Rank;
		int currFile = p1File;
		int length = Math.max(Math.abs(p1Rank-p2Rank),Math.abs(p1File-p2File));
		for(int i = 0; i < length-1; i++){

			checkSpaces.add(spaces[currRank][currFile]);
			currRank+=vertDir;
			currFile+=horizDir;

		}

		return checkSpaces;
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
		if(!detectCheck(team)){return false;}
		Check check = new Check();
		if(team == 'w'){
			check = whiteCheck;
		}else if(team == 'b'){
			check = blackCheck;
		}else{
			return false;
		}

		ArrayList<Space> spaces = check.getCheckSpaces();
		if(checkKingMoves(team)){
			return false;
		}
		if(spaces != null){
		for(Space space : spaces){
			if(reachable(team, space)){
				return false;
			}
		}
		}

		return true;
	}

	private boolean checkKingMoves(char team){
		King currKing = new King('w');
		Move attemptMove = new Move();
		if(team == 'w'){
			currKing = whiteKing;
		}else if(team == 'b'){
			currKing = blackKing;
		}

		attemptMove.setBegin(currKing.getSpace());
		Space kingSpace = currKing.getSpace();
		Space attemptSpace;

		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
				if(!(i == 0 && j == 0) && inRange(kingSpace.getRank()+i) &&
				inRange(kingSpace.getFile()+j)){
					attemptSpace = spaces[kingSpace.getRank()+i][kingSpace.getFile()+j];
					if(attemptSpace.getPiece() == null ||
					attemptSpace.getPiece().getTeam() != team){
						attemptMove.setEnd(attemptSpace);
						if(isValidMove(attemptMove, team)){return true;}
					}
				}
			}
		}
		return false;
	}

	private boolean isValidMove(Move attemptMove, char team){
		//checks if move is into check or not
		MoveExecuter tempExec =new MoveExecuter(this, new Scanner(System.in));
		boolean invalidMove = false;
		try{
			tempExec.executeMove(attemptMove, false, team);
		}catch(IllegalStateException e){
			invalidMove = true;
		}
		return !invalidMove;
	}

	private boolean reachable(char team, Space space){

		Move attemptMove = new Move();
		attemptMove.setEnd(space);
		int currentRank = -1;
		int currentFile = -1;

		boolean diagonal = false;
		boolean horizOrVert = false;
		boolean blocked = false;
		boolean invalidMove = false;

		Space pawnSpot = new Space(0,0);
		if(team == 'w' && space.getRank() > 0){
			pawnSpot = spaces[space.getRank()-1][space.getFile()];
		}else if(team == 'b' && space.getFile() < 7){
			pawnSpot = spaces[space.getRank()+1][space.getFile()];
		}

		attemptMove.setBegin(pawnSpot);
		if(isValidMove(attemptMove, team)){return true;}

		Space knightSpot;
		for(int i = -2; i <= 2; i++){
			for(int j = -2; j <= 2; j++){
				if(i != 0 && j != 0
				&& !((Math.abs(i) == 1)
				&& (Math.abs(j) == 1))
				&& !(i%2 == 0 && j%2 == 0)
				&& space.getFile() >= j*-1 && space.getFile() <= 7-j
				&& space.getRank() >= i*-1 && space.getRank() <= 7-i){
					if(spaces[space.getRank()+i][space.getFile()+j].getPiece() != null &&
					spaces[space.getRank()+i][space.getFile()+j].getPiece().getTeam()
					!= team &&
					spaces[space.getRank()+i][space.getFile()+j].getPiece().getSymbol()
					== 'N'){
						knightSpot = spaces[space.getRank()+i][space.getFile()+j];
						attemptMove.setBegin(knightSpot);
						if(isValidMove(attemptMove, team)){return true;}
					}
				}
			}
		}


		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <=1; j++){
				currentRank = space.getRank();
				currentFile = space.getFile();

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
								attemptMove.setBegin(checkSpace);
								if(isValidMove(attemptMove, team)){return true;}
							}
							if(horizOrVert && checkPiece.getSymbol() == 'R' &&
								checkPiece.getTeam() != team){
								attemptMove.setBegin(checkSpace);
								if(isValidMove(attemptMove, team)){return true;}
							}
							if(checkPiece.getSymbol() == 'Q' &&
								checkPiece.getTeam() != team){
								attemptMove.setBegin(checkSpace);
								if(isValidMove(attemptMove, team)){return true;}
							}
							blocked = true;
						}
					}
				}
			}
		}

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

	public boolean getCheckMate(){
		return checkMate;
	}

	public void setCheckMate(boolean flag){
		checkMate = flag;
	}
}
