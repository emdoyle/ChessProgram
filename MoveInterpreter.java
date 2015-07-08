public class MoveInterpreter {

	public MoveInterpreter(){		
	}

	public static Move parseMove(String move){
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
		return true;

	}

}

