// King chess piece & subclass of Piece.java
public class Queen extends Piece {

  // constructor
  public Queen( char color ) {
    this.team = color;
    this.symbol = 'Q';
  }

	public boolean canReach( Space dest ) {

		int currentFile = currentSpace.getFile();
		int currentRank = currentSpace.getRank();
		int destFile = dest.getFile();
		int destRank = dest.getRank();

		int currIndex = currentRank*8 + currentFile;
		int destIndex = destRank*8 + destFile;

		if ((currIndex - destIndex) % 7 == 0 || 
			(currIndex = destIndex) % 9 == 0) return true;

    if (currentSpace.getRank() == dest.getRank()) return true;
    if (currentSpace.getFile() == dest.getFile()) return true;

		return false;
	}
}
