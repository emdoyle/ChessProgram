// King chess piece & subclass of Piece.java
public class King extends Piece {

  // constructor
  public King( Space space ) {
    this.currentSpace = space;
    this.symbol = 'K';
  }

  public boolean canReach( Space dest ) {
    if (Math.abs( currentSpace.rank - dest.rank ) > 1) {
      return false;
    }

    if (Math.abs( currentSpace.file - dest.file ) > 1) {
      return false;
    }

    return true;
  }
}
