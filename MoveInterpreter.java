import java.util.Scanner;
public static class MoveInterpreter {

	private Scanner input;

	public MoveInterpreter(){		
		input = new Scanner(System.in);
	}

	public boolean isValidMove( String line ) {
		char firstChar, secondChar, thirdChar, fourthChar;
		if ( line == "" || line.length() == 1 ) {
			return false;
		}
		//originally > 10?
		if ( line.length() > 4 ) {
			return false;
		}

		firstChar = line.charAt(0);
		secondChar = line.charAt(1);

		if ( firstChar >= 'a' && firstChar <= 'h' ) { //pawn move
			return isValidPawnMove(line);
		}else if(firstChar == 'B'){
			return isValidBishopMove(line);
		}
		return true;
	}

	private boolean isValidPawnMove(String line){
		char secondChar = line.charAt(1);
		if ( secondChar == 'x' ) { // we know its a capture
			if ( line.length() != 4 ) { // must be size 4
				return false;
			}
			char thirdChar = line.charAt(2);
			char fourthChar = line.charAt(3);
			if ( thirdChar < 'a' || thirdChar > 'h' ) {
				return false;
			}
			if ( fourthChar < '1' || fourthChar > '8' ) {
				return false;
			}
		}
		if ( secondChar < '1' || secondChar > '8' ) {
			return false;
		}
	}

	private boolean isValidBishopMove(String line){
		
	}

	private boolean isValidKnightMove(String line){

	}

	private boolean isValidQueenMove(String line){

	}

	private boolean isValidKingMove(String line){

	}

	private boolean isValidRookMove(String line){

	}
		
	
	public Move interpretMove ( String move, Board board, char team ) {
		char firstChar = move.charAt(0);
		char secondChar = move.charAt(1);
		char thirdChar, fourthChar;
		int columnAdder = (firstChar - 'a') + 1;
		int rankAdder, currentRank = 0;
		if ( secondChar != 'x' ) {
			currentRank = ( secondChar - '0' ) - 1;
			currentRank *= 8;
		}
		int moveSpace = currentRank + columnAdder;
		Space currentSpace = board.getSpacesArray()[moveSpace];
		if ( currentSpace.isOccupied() ) {
			System.out.println( "Cannot move to an occupied space!" );
			return null;
		}	

		return null;
	}
}

