/** Creates an abstract object Piece with basic implementations of what a piece
 *  should contain--including the space the piece is on, the symbol of the piece,
 *  and what team (white or black) that piece is part of.
 */
public abstract class Piece {
	
  // Note: these instance variables were made protected to allow
 	// subclasses to access them without setters/getters
	Space currentSpace;
	char symbol;
	char team;
	int value;
	
	public abstract boolean canReach(Space dest);

	public Space getSpace(){
		return currentSpace;
	}

	public char getTeam(){ // team getter
		return team;
	}
	public char getSymbol(){ // symbol getter
		return symbol;
	}
	public void setSymbol(char sym){ // symbol setter
		symbol = sym;
	}
	public void setSpace(Space dest){ // space setter
		currentSpace = dest;
	}

	public int getValue(){
		return value;
	}

}
