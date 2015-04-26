// King chess piece & subclass of Piece.java
public class King extends Piece {

  // constructor
  public King( char color ) {
    this.team = color;
    this.symbol = 'K';
  }

	public boolean canReach( Space dest ) {
		if (Math.abs( currentSpace.getRank() - dest.getRank() ) > 1) {
			return false;
		}

		if (Math.abs( currentSpace.getFile() - dest.getFile() ) > 1) {
			return false;
		}

		return true;
	}
}
