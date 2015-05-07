import java.util.Scanner;
public class MoveInterpreter {

	private Scanner input;

	public MoveInterpreter(){		
		input = new Scanner(System.in);
	}

	public boolean isValidMove( String line ) {
		char firstChar, secondChar, thirdChar, fourthChar;
		if ( line == "" || line.length() == 1 ) {
			return false;
		}

		if ( line.length() > 10 ) {
			return false;
		}

		firstChar = line.charAt(0);
		secondChar = line.charAt(1);

		if ( firstChar >= 'a' && firstChar <= 'h' ) { // we know its a pawn move
			if ( secondChar == 'x' ) { // we know its a capture
				if ( line.length() != 4 ) { // must be size 4
					return false;
				}
				thirdChar = line.charAt(2);
				fourthChar = line.charAt(3);
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
		return true;
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

