
public abstract class Piece {
	
	private Space currentSpace;
	private char symbol;
	private char team;
	
	public abstract boolean canReach(Space dest);

	public Space getSpace(){
		return currentSpace;
	}
	public void setSpace(Space dest){
		currentSpace = dest;
	}

}
