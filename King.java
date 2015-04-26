// King chess piece & subclass of Piece.java
public class King extends Piece {

  // constructs a King object in either white or black
  public King( char color ) {
	this.team = color;
	this.symbol = 'K';
  }

	// determines whether a space is reachable
	public boolean canReach( Space dest ) {
    Space kingSpace = this.getSpace();

		// if the absolute value is off by more than 1, the space is unreachable
		if (Math.abs( this.currentSpace.getRank() - dest.getRank() ) > 1) {
			return false;
		}

		if (Math.abs( this.currentSpace.getFile() - dest.getFile() ) > 1) {
			return false;
		}

		return true;
	}
}
