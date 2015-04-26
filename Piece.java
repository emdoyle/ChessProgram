
public abstract class Piece {
	
	private Space currentSpace;
	private char symbol;
	private char team;
	
	public abstract boolean canReach(Space dest);

	public abstract Space getSpace();
	public abstract void setSpace(Space dest);

}
