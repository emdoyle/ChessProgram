// King chess piece & subclass of Piece.java
public class King extends Piece {

  // constructor
  public King( char color ) {
    this.team = color;
    this.symbol = 'K';
  }

	public boolean canReach( Space dest ) {
    Space kingSpace = this.getSpace();
		if (Math.abs( this.currentSpace.getRank() - dest.getRank() ) > 1) {
			return false;
		}

		if (Math.abs( this.currentSpace.getFile() - dest.getFile() ) > 1) {
			return false;
		}

		return true;
	}
}
