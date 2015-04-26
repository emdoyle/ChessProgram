
public abstract class Piece {
	
	private Space currentSpace;
	private char symbol;
	private char team;
	
	public abstract boolean canReach(Space dest);

	public abstract getSpace();
	public abstract setSpace(Space dest);

}
