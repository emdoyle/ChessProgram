public class MoveInterpreter {

	public MoveInterpreter(){		
	}

	public static Move parseMove(String line){
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

	private static Move PawnMove(String line){return null;}
	private static Move KnightMove(String line){return null;}
	private static Move BishopMove(String line){return null;}
	private static Move RookMove(String line){return null;}
	private static Move QueenMove(String line){return null;}
	private static Move KingMove(String line){return null;}

}

