// King chess piece & subclass of Piece.java
public class Rook extends Piece {

  // constructor
  public Rook( char color ) {
    this.team = color;
    this.symbol = 'R';
  }

	public boolean canReach( Space dest ) {
		if (currentSpace.rank == dest.getRank()) return true;
    if (currentSpace.file == dest.getFile()) return true;
    return false;
  }
}
