public class MoveInterpreter {

	/*
	* NEED TO FIX/DO
	* -LONG-TERM: Detecting check(mate) (this file?)
	* -LONG-TERM: Support castling q+k-side
	* -pawn promotion
	* -refactor redundant code (esp. Bishop/Knight/Rook)
	*/

	private Board currBoard;
	private Space[][] spaceArr;

	public MoveInterpreter(Board b){
		currBoard = b;
		spaceArr = b.getSpacesArray();	
	}

	public Move parseMove(String line){
		char firstChar;
		if ( line == "" || line.length() == 1 ) {
			return null;
		}
		if ( line.length() > 10 ) {
			return null;
		}

		firstChar = line.charAt(0);

		if ( firstChar >= 'a' && firstChar <= 'h' ) { //pawn move
			return PawnMove(line);
		}else if(firstChar == 'B'){
			return BishopMove(line);
		}else if(firstChar == 'N'){
			return KnightMove(line);
		}else if(firstChar == 'R'){
			return RookMove(line);
		}else if(firstChar == 'Q'){
			return QueenMove(line);
		}else if(firstChar == 'K'){
			return KingMove(line);
		}else{
			return null;
		}

	}

	private int fileToInt(char file){
		//97 is ASCII code for 'a'  ex:  97-'a' should equal 0
		return file-97;
	}

	private Move PawnMove(String line){
	/*
	* NEED TO DO:
	* Handle promotion at rank 0 and 7
	*/
		Move result = new Move();
		Pawn pwn = new Pawn('w');
		int firstFile = fileToInt(line.charAt(0));
		if(line.charAt(1) == 'x'){
		//this is a capture
			int secondFile = fileToInt(line.charAt(2));
			int secondRank = Character.getNumericValue(line.charAt(3))-1;
			
			//check if pawns are within one file
			if(Math.abs(firstFile - secondFile) != 1){
				return null;
			}

			if(currBoard.getWhiteTurn() &&
			secondRank >= 1 && spaceArr[secondRank-1][firstFile].getPiece()
			!= null &&
			spaceArr[secondRank-1][firstFile].getPiece().getTeam()=='w' &&
			spaceArr[secondRank][secondFile].getPiece().getTeam() == 'b'){
				//white pawn capturing up
				result.setBegin(
				spaceArr[secondRank-1][firstFile]);
			
			}else if(!currBoard.getWhiteTurn() &&
			secondRank <= 6 && spaceArr[secondRank+1][firstFile].getPiece()
			!= null &&
			spaceArr[secondRank+1][firstFile].getPiece().getTeam()=='b' &&
			spaceArr[secondRank][secondFile].getPiece().getTeam() == 'w'){
				//black pawn capturing down
				result.setBegin(
				spaceArr[secondRank+1][firstFile]);
			}

			result.setEnd(spaceArr[secondRank][secondFile]);
		}else{
			//not a capture
			int destRank= Character.getNumericValue(line.charAt(1))-1;

			//check if destination space is free
			if(spaceArr[destRank][firstFile].getPiece() != null){
				return null;
			}			

			if(destRank >=1 && 
			spaceArr[destRank-1][firstFile].getPiece() != null
			&& spaceArr[destRank-1][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank-1][firstFile].getPiece().getTeam()=='w'
			&& currBoard.getWhiteTurn()){
				//this executes when white pawn moves one space up board
				result.setBegin(spaceArr[destRank-1][firstFile]);

			}else if(spaceArr[destRank+1][firstFile].getPiece() != null
			&& spaceArr[destRank+1][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank+1][firstFile].getPiece().getTeam()=='b'
			&& !currBoard.getWhiteTurn()){
				//this executes when black pawn moves one space down board
				result.setBegin(spaceArr[destRank+1][firstFile]);

			}else if(destRank == 3 && spaceArr[destRank-2][firstFile].getPiece()
			!= null && spaceArr[destRank-2][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank-2][firstFile].getPiece().getTeam()=='w'
			&& currBoard.getWhiteTurn()){
				//this executes when white pawn moves two spaces up board
				result.setBegin(spaceArr[destRank-2][firstFile]);

			}else if(destRank == 4 && spaceArr[destRank+2][firstFile].getPiece()
			!= null && spaceArr[destRank+2][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank+2][firstFile].getPiece().getTeam()=='b'
			&& !currBoard.getWhiteTurn()){
				//this executes when black pawn moves two spaces down board
				result.setBegin(spaceArr[destRank+2][firstFile]);

			}else{
				return null;
			}

			result.setEnd(spaceArr[destRank][firstFile]);

		}
		//after either move, check for check/promotion?
		return result;

	}


	private Move KnightMove(String line){

		Move result = new Move();

		int destFile = -1;
		int destRank = -1;

		if(line.charAt(1) == 'x'){
		//dealing with a capture
			destFile = fileToInt(line.charAt(2));
			destRank = Character.getNumericValue(line.charAt(3))-1;

			if(spaceArr[destRank][destFile].getPiece() == null
				|| spaceArr[destRank][destFile].getPiece().getTeam()
				== currBoard.getTurn()){
				//if victim piece doesnt exist or is same team
				return null;
			}

		}else{
			destFile = fileToInt(line.charAt(1));
			destRank = Character.getNumericValue(line.charAt(2))-1;

			if(spaceArr[destRank][destFile].getPiece() != null){
				//if there is a piece at dest but not a capture
				return null;
			}
		}

		Space att = findKnight(destFile, destRank, currBoard);

		//check for null to avoid NPE
		if(att != null){
			result.setBegin(att);
			result.setEnd(spaceArr[destRank][destFile]);
		}else{
			result = null;
		}

		return result;

	}

	private Space findKnight(int destFile, int destRank, Board currBoard){
		/*
		* NOTE: Currently does not accommodate multiple
		* possible knights (must also be specified in code)
		* ex: Nac3 means Knight from a-file to c3
		*/
		int numPossibleKnights = 0;
		Knight kn = new Knight('w');
		Space result = null;

		for(int i = -2; i <= 2; i++){
			for(int j = -2; j <= 2; j++){
				if(i != 0 && j != 0
				&& !((i == 1 || i == -1)
				&& (j == 1 || j == -1))
				&& !(i%2 == 0 && j%2 == 0)
				&& destFile >= j*-1 && destFile <= 7-j
				&& destRank >= i*-1 && destRank <= 7-i){
				//i and j represent the offset from the destination horizontally
				//and vertically
					if(spaceArr[destRank+i][destFile+j].getPiece() != null
					&& spaceArr[destRank+i][destFile+j].getPiece().getClass()
					== kn.getClass() &&
					spaceArr[destRank+i][destFile+j].getPiece().getTeam()
					== currBoard.getTurn()){
					//conditions: there is a piece, it is a knight, it is on the team
					//having a turn now
						numPossibleKnights++;
						result = spaceArr[destRank+i][destFile+j];
					}
				}
			}
		}

		if(numPossibleKnights >= 2){
			return null;
		}

		return result;
	}


	private Move BishopMove(String line){

		Move result = new Move();

		int destFile = -1;
		int destRank = -1;

		if(line.charAt(1) == 'x'){
			//capture
			destFile = fileToInt(line.charAt(2));
			destRank = Character.getNumericValue(line.charAt(3))-1;

			if(spaceArr[destRank][destFile].getPiece() == null
				|| spaceArr[destRank][destFile].getPiece().getTeam()
				== currBoard.getTurn()){
				//no piece to capture or own piece on space
				return null;
			}
			
		}else{

			destFile = fileToInt(line.charAt(1));
			destRank = Character.getNumericValue(line.charAt(2))-1;

			if(spaceArr[destRank][destFile].getPiece() != null){
				//no capture but destination is occupied
				return null;
			}

		}

		Space att = findBishop(destFile, destRank, currBoard);

		//null check avoids NPE
		if(att != null){
			result.setBegin(att);
			result.setEnd(spaceArr[destRank][destFile]);
		}else{
			result = null;
		}

		return result;

	}

	private Space findBishop(int destFile, int destRank, Board currBoard){

		//This method does not accommodate multiple possible bishops
		int numPossBishops = 0;
		Bishop bsh = new Bishop('w');
		Space result = null;
		int currRank = destRank;
		int currFile = destFile;

		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
			//i and j are only allowed to be 1 or -1
			//this is to simulate each of the four diagonals
			//ex: i,j = 1 would follow the up-right diagonal
			//and i=1, j=-1 would follow the up-left diagonal
				currRank = destRank;
				currFile = destFile;

				if(i!=0 && j!=0){
					while(currRank >= 0 && currRank <= 7
					&& currFile >= 0 && currFile <= 7){

						currRank+=i;
						currFile+=j;
						if(currRank == -1 || currRank == 8 || currFile == -1
							|| currFile == 8){break;}

						if(spaceArr[currRank][currFile].getPiece() != null
						&& spaceArr[currRank][currFile].getPiece().getClass()
						!= bsh.getClass()){
							//this case is a collision on the diagonal
							break;
						}

						if(spaceArr[currRank][currFile].getPiece() != null
						&& spaceArr[currRank][currFile].getPiece().getClass()
						== bsh.getClass() &&
						spaceArr[currRank][currFile].getPiece().getTeam()
						== currBoard.getTurn()){
						//conditions: it is a piece, it is a bishop, its turn is
						//occurring now
							numPossBishops++;
							result = spaceArr[currRank][currFile];
						}
					}
				}
			}
		}

		if(numPossBishops >= 2){
			return null;
		}

		return result;

	}

	private Move RookMove(String line){

		Move result = new Move();

		int destFile = -1;
		int destRank = -1;

		if(line.charAt(1) == 'x'){
			//capture
			destFile = fileToInt(line.charAt(2));
			destRank = Character.getNumericValue(line.charAt(3))-1;

			if(spaceArr[destRank][destFile].getPiece() == null
				|| spaceArr[destRank][destFile].getPiece().getTeam()
				== currBoard.getTurn()){
				//no piece to capture or own piece on space
				return null;
			}
			
		}else{

			destFile = fileToInt(line.charAt(1));
			destRank = Character.getNumericValue(line.charAt(2))-1;

			if(spaceArr[destRank][destFile].getPiece() != null){
				//no capture but destination is occupied
				return null;
			}

		}

		Space att = findRook(destFile, destRank, currBoard);

		//null check avoids NPE
		if(att != null){
			result.setBegin(att);
			result.setEnd(spaceArr[destRank][destFile]);
		}else{
			result = null;
		}

		return result;
	}

	private Space findRook(int destFile, int destRank, Board currBoard){

		int numPossRooks = 0;
		Rook rk = new Rook('w');
		Space result = null;
		int currRank = destRank;
		int currFile = destFile;

		for(int i = -1; i <=1; i++){
			for(int j = -1; j <=1; j++){
				//reset currRank and currFile
				currRank = destRank;
				currFile = destFile;

				if(!(Math.abs(i) == 1 && Math.abs(j) == 1)
					&& !(i == 0 && j == 0)){
					while(currRank >= 0 && currRank <= 7
						&& currFile >= 0 && currFile <= 7){
						currRank += i;
						currFile += j;

						if(currRank == -1 || currRank == 8 || currFile == -1
							|| currFile == 8){break;}

						if(spaceArr[currRank][currFile].getPiece() != null
							&& spaceArr[currRank][currFile].getPiece().getClass()
							!= rk.getClass()){
							//this case is a collision between possible rook and destination
							break;
						}

						if(spaceArr[currRank][currFile].getPiece() != null
							&& spaceArr[currRank][currFile].getPiece().getClass()
							== rk.getClass() &&
							spaceArr[currRank][currFile].getPiece().getTeam()
							== currBoard.getTurn()){
							//conditions: is a piece, is a rook, has a turn
							numPossRooks++;
							result = spaceArr[currRank][currFile];
						}
					}
				}
			}
		}
		return result;
	}


	private Move QueenMove(String line){return null;}
	private Move KingMove(String line){return null;}

}

