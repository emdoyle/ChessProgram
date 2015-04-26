
public abstract class Piece {
	
	Space currentSpace;
	char symbol;
	char team;
	
	public abstract boolean canReach(Space dest);

	public Space getSpace(){
		return currentSpace;
	}

	public char getTeam(){
		return team;
	}
	public char getSymbol(){
		return symbol;
	}
	public void setSymbol(char sym){
		symbol = sym;
	}
	public void setSpace(Space dest){
		currentSpace = dest;
	}

}
