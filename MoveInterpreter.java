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

			if(currBoard.getWhiteTurn()){
				result.setBegin(
				spaceArr[secondRank-1][firstFile]);
			}else{
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
			currBoard.getWhiteTurn()){

				result.setBegin(spaceArr[destRank-1][firstFile]);

			}else if(spaceArr[destRank+1][firstFile].getPiece() != null
			&& spaceArr[destRank+1][firstFile].
			getPiece().getClass() == pwn.getClass()
			&& !currBoard.getWhiteTurn()){

				result.setBegin(spaceArr[destRank+1][firstFile]);

			}else if(destRank == 3 && spaceArr[destRank-2][firstFile].getPiece()
			!= null && spaceArr[destRank-2][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			currBoard.getWhiteTurn()){

				result.setBegin(spaceArr[destRank-2][firstFile]);

			}else if(destRank == 4 && spaceArr[destRank+2][firstFile].getPiece()
			!= null && spaceArr[destRank+2][firstFile].
			getPiece().getClass() == pwn.getClass()
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

		if(line.charAt(1) == 'x'){
		//dealing with a capture
			int destFile = fileToInt(line.charAt(2));
			int destRank = Character.getNumericValue(line.charAt(3))-1;

			Space att = findKnight(destFile, destRank, currBoard);

			if(att != null){
				//System.out.println(spaceArr[destRank][destFile]==null);
				result.setBegin(att);
				result.setEnd(spaceArr[destRank][destFile]);
			}else{
				return null;
			}

			return result;
		}else{
			return null;
		}

	}

	private Space findKnight(int destFile, int destRank, Board currBoard){
		int numPossibleKnights = 0;
		Knight kn = new Knight('w');
		Space result = null;
		System.out.println("entered findKnight");

		for(int i = -2; i <= 2; i++){
			for(int j = -2; j <= 2; j++){
			/*System.out.println("destFile:" + destFile + " destRank:" + destRank +
			" j*-1:" + (j*-1) + " i*-1:" + (i*-1) + " 7-j:" + (7-j) + " 7-i:" + (7-i));*/
				if(i != 0 && j != 0
				&& destFile >= j*-1 && destFile <= 7-j
				&& destRank >= i*-1 && destRank <= 7-i){
					if(spaceArr[destRank+i][destFile+j].getPiece() != null
					&& spaceArr[destRank+i][destFile+j].getPiece().getClass()
					== kn.getClass()){
						numPossibleKnights++;
						result = spaceArr[destRank+i][destFile+j];
						System.out.println(destRank+i + " : " + (destFile+j));
					}
				}
			}
		}

		if(numPossibleKnights >= 2){
			return null;
		}

		return result;
		
		/*if(destFile >= 2 && destRank <= 6 && 
		spaceArr[destRank+1][destFile-2].getPiece() != null){
			numPossibleKnights++;
			mostRecentKnight = spaceArr[destFile-2][destRank+1].getPiece();
		}
		if(destFile >= 2 && destRank >= 1 && 
		spaceArr[destRank-1][destFile-2].getPiece() != null){
			numPossibleKnights++;
			mostRecentKnight = spaceArr[destRank-1][destFile-2].getPiece();
		}
		if(destFile >= 1 && destRank <= 5 && 
		spaceArr[destRank+2][destFile-1].getPiece() != null){
			numPossibleKnights++;
			mostRecentKnight = spaceArr[destRank+2][destFile-1].getPiece();
		}
		if(destFile >= 1 && destRank >= 2 && 
		spaceArr[destRank-2][destFile-1].getPiece() != null){
			numPossibleKnights++;
			mostRecentKnight = spaceArr[destRank-2][destFile-1].getPiece();
		}
		if(destFile <= 6 && destRank <= 5 && 
		spaceArr[destRank+2][destFile+1].getPiece() != null){
			numPossibleKnights++;
			mostRecentKnight = spaceArr[destRank+2][destFile+1].getPiece();
		}
		if(destFile <= 5 && destRank <= 6 && 
		spaceArr[destRank+1][destFile+2].getPiece() != null){
			numPossibleKnights++;
			mostRecentKnight = spaceArr[destRank+1][destFile+2].getPiece();
		}
		if(destFile <= 5 && destRank >= 1 && 
		spaceArr[destRank-1][destFile+2].getPiece() != null){
			numPossibleKnights++;
			mostRecentKnight = spaceArr[destRank-1][destFile+2].getPiece();
		}
		if(destFile <= 6 && destRank >= 2 && 
		spaceArr[destRank-2][destFile+1].getPiece() != null){
			numPossibleKnights++;
			mostRecentKnight = spaceArr[destRank-2][destFile+1].getPiece();
		}*/

	}


	private Move BishopMove(String line){return null;}
	private Move RookMove(String line){return null;}
	private Move QueenMove(String line){return null;}
	private Move KingMove(String line){return null;}

}

