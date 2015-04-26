// King chess piece & subclass of Piece.java
public class Queen extends Piece {

  // constructor
  public Queen( char color ) {
    this.team = color;
    this.symbol = 'Q';
  }

	public boolean canReach( Space dest ) {

		currentFile = currentSpace.getFile();
		currentRank = currentSpace.getRank();
		destFile = dest.getFile();
		destRank = dest.getRank();

		currIndex = currentRank*8 + currentFile;
		destIndex = destRank*8 + destFile;

		if ((currIndex - destIndex) % 7 == 0 || 
			(currIndex = destIndex) % 9 == 0) return true;

    if (currentSpace.rank == dest.getRank()) return true;
    if (currentSpace.file == dest.getFile()) return true;

		return false;
	}
}
