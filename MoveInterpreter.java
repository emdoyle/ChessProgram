
import java.util.Scanner;

public class MoveInterpreter {
	
	private Scanner input;

	public MoveInterpreter(){
		
		input = new Scanner(System.in);

	}
	
	public Move interpretMoves(String line){
		if (line == "" || line.length() == 1) return null; // empty string / bad input
		if (line.length() > 10) return null; // optimization for now (until accept whole games)
		char firstChar = line.charAt(0);
		if ( firstChar >= 'a' && firstChar <= 'h' ) { // we know its pawn move
			}
		
		return null;
	}
	
}
