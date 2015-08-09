public class MoveInterpreter {

	/*
	* NEED TO FIX/DO
	* -Bishop capturing own pieces
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

				result.setBegin(spaceArr[destRank-1][firstFile]);

			}else if(spaceArr[destRank+1][firstFile].getPiece() != null
			&& spaceArr[destRank+1][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank+1][firstFile].getPiece().getTeam()=='b'
			&& !currBoard.getWhiteTurn()){

				result.setBegin(spaceArr[destRank+1][firstFile]);

			}else if(destRank == 3 && spaceArr[destRank-2][firstFile].getPiece()
			!= null && spaceArr[destRank-2][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank-2][firstFile].getPiece().getTeam()=='w'
			&& currBoard.getWhiteTurn()){

				result.setBegin(spaceArr[destRank-2][firstFile]);

			}else if(destRank == 4 && spaceArr[destRank+2][firstFile].getPiece()
			!= null && spaceArr[destRank+2][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank+2][firstFile].getPiece().getTeam()=='b'
			&& !currBoard.getWhiteTurn()){

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
		Knight kn = new Knight('w');

		int destFile = -1;
		int destRank = -1;

		if(line.charAt(1) == 'x'){
		//dealing with a capture
			destFile = fileToInt(line.charAt(2));
			destRank = Character.getNumericValue(line.charAt(3))-1;

			if(spaceArr[destRank][destFile].getPiece() == null
				|| spaceArr[destRank][destFile].getPiece().getTeam()
				== currBoard.getTurn()){
				return null;
			}

		}else{
			destFile = fileToInt(line.charAt(1));
			destRank = Character.getNumericValue(line.charAt(2))-1;

			if(spaceArr[destRank][destFile].getPiece() != null){
				return null;
			}
		}

		Space att = findKnight(destFile, destRank, currBoard);

		if(att != null){
			result.setBegin(att);
			result.setEnd(spaceArr[destRank][destFile]);
		}else{
			result = null;
		}

		return result;

	}

	private Space findKnight(int destFile, int destRank, Board currBoard){
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
					if(spaceArr[destRank+i][destFile+j].getPiece() != null
					&& spaceArr[destRank+i][destFile+j].getPiece().getClass()
					== kn.getClass() &&
					spaceArr[destRank+i][destFile+j].getPiece().getTeam()
					== currBoard.getTurn()){
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
		Bishop bsh = new Bishop('w');

		int destFile = -1;
		int destRank = -1;

		if(line.charAt(1) == 'x'){
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
				return null;
			}

		}

		Space att = findBishop(destFile, destRank, currBoard);

		if(att != null){
			result.setBegin(att);
			result.setEnd(spaceArr[destRank][destFile]);
		}else{
			result = null;
		}

		return result;

	}

	private Space findBishop(int destFile, int destRank, Board currBoard){
		int numPossBishops = 0;
		Bishop bsh = new Bishop('w');
		Space result = null;
		int currRank = destRank;
		int currFile = destFile;
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
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
		Rook rk = new Rook('w');

		int destRank = -1;
		int destFile = -1;

		if(line.charAt(1) == 'x'){
			destFile = fileToInt(line.charAt(2));
			destRank = Character.getNumericValue(line.charAt(3))-1;

			if(spaceArr[destRank][destFile].getPiece() == null){
				return null;
			}
			
		}else{
			destFile = fileToInt(line.charAt(1));
			destRank = Character.getNumericValue(line.charAt(2))-1;

			if(spaceArr[destRank][destFile].getPiece() != null){
				return null;
			}

		}

		Space att = findRook(destFile, destRank, currBoard);

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

		//for(int i 
		return null;
	}


	private Move QueenMove(String line){return null;}
	private Move KingMove(String line){return null;}

}

