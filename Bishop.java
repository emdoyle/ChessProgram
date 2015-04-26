/*
 * Extends abstract Piece class with instance vars:
 * Space currentSpace
 * char symbol, team
 */
public class Bishop extends Piece{

	//Note: constructor does not take a Space object
	//this is because it is set in Space.setPiece
	public Bishop(char team){
		symbol = 'B';
		this.team = team;
	}

	public boolean canReach(Space dest){
		//these are defined for convenience in the
		//return statement
		int currentFile = currentSpace.getFile();
		int currentRank = currentSpace.getRank();
		int destFile = dest.getFile();
		int destRank = dest.getRank();
		
		//converts rank/file into an index compatible
		//with the spaces array
		int currIndex = currentRank*8 + currentFile;
		int destIndex = destRank*8 + destFile;

		return (currIndex - destIndex) % 7 == 0 ||
			(currIndex - destIndex) % 9 == 0;
	}

}
