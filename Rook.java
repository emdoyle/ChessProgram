// King chess piece & subclass of Piece.java
public class Rook extends Piece {

  // constructor
  public Rook( Space space ) {
    this.currentSpace = space;
    this.symbol = 'R';
  }

	public boolean canReach( Space dest ) {
		if (currentSpace.rank == dest.rank) return true;
    if (currentSpace.file == dest.file) return true;
    return false;
  }
}
