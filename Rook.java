// Rook chess piece & subclass of Piece.java
public class Rook extends Piece {

  // constructs a Rook object in white or black
  public Rook( char color ) {
    this.team = color;
    this.symbol = 'R';
  }

	// determines whether a space is reachable
	public boolean canReach( Space dest ) {
		if (currentSpace.getRank() == dest.getRank()) return true;
    if (currentSpace.getFile() == dest.getFile()) return true;
    return false;
  }
}
