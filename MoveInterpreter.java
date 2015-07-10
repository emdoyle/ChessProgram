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
	* Handle no-victim moves of both one and two spaces
	* Handle promotion at rank 0 and 7
	*/
		Move result = new Move();
		int firstFile = fileToInt(line.charAt(0));
		if(line.charAt(1) == 'x'){
			int secondFile = fileToInt(line.charAt(2));
			int secondRank = Character.getNumericValue(line.charAt(3))-1;

			if(currBoard.getWhiteTurn()){
				result.setAttacker(
				spaceArr[secondRank-1][firstFile].getPiece());
			}else{
				result.setAttacker(
				spaceArr[secondRank+1][firstFile].getPiece());
			}

			result.setVictim(spaceArr[secondRank][secondFile].getPiece());
			result.setBegin(result.getAttacker().getSpace());
			result.setEnd(result.getVictim().getSpace());

		}else{
			
		}
		return result;

	}


	private Move KnightMove(String line){return null;}
	private Move BishopMove(String line){return null;}
	private Move RookMove(String line){return null;}
	private Move QueenMove(String line){return null;}
	private Move KingMove(String line){return null;}

}

