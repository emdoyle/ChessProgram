// Queen chess piece & subclass of Piece.java
public class Queen extends Piece {

  // constructs a Queen object in either white or black
  public Queen( char color ) {
		this.team = color;
		this.symbol = 'Q';
  }
	
	// determines whether a space is reachable
	public boolean canReach( Space dest ) {

		int currentFile = currentSpace.getFile();
		int currentRank = currentSpace.getRank();
		int destFile = dest.getFile();
		int destRank = dest.getRank();

		int currIndex = currentRank*8 + currentFile;
		int destIndex = destRank*8 + destFile;

		// diagonally reachable
		if ((currIndex - destIndex) % 7 == 0 || 
			(currIndex = destIndex) % 9 == 0) return true;
	
		// horizontally / vertically reachable
    if (currentSpace.getRank() == dest.getRank()) return true;
    if (currentSpace.getFile() == dest.getFile()) return true;

		return false;
	}
}
