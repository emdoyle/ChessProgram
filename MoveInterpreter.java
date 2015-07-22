public class MoveInterpreter {

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
			int secondFile = fileToInt(line.charAt(2));
			int secondRank = Character.getNumericValue(line.charAt(3))-1;

			if(currBoard.getWhiteTurn() &&
			secondRank >= 1 && spaceArr[secondRank-1][firstFile].getPiece()
			!= null &&
			spaceArr[secondRank-1][firstFile].getPiece().getTeam()=='w'){
				result.setBegin(
				spaceArr[secondRank-1][firstFile]);
			}else if(!currBoard.getWhiteTurn() &&
			secondRank <= 6 && spaceArr[secondRank+1][firstFile].getPiece()
			!= null &&
			spaceArr[secondRank+1][firstFile].getPiece().getTeam()=='b'){
				result.setBegin(
				spaceArr[secondRank+1][firstFile]);
			}

			result.setEnd(spaceArr[secondRank][secondFile]);
		}else{
			int destRank= Character.getNumericValue(line.charAt(1))-1;
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
					== kn.getClass()){
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

		if(line.charAt(1) == 'x'){

		}else{
			
		}

	}

	private Move RookMove(String line){return null;}
	private Move QueenMove(String line){return null;}
	private Move KingMove(String line){return null;}

}

