
public class Bishop extends Piece{

	public Bishop(char team){
		symbol = 'B';
		this.team = team;
	}

	public boolean canReach(Space dest){
		int currentFile = currentSpace.getFile();
		int currentRank = currentSpace.getRank();
		int destFile = dest.getFile();
		int destRank = dest.getRank();

		int currIndex = currentRank*8 + currentFile;
		int destIndex = destRank*8 + destFile;

		return (currIndex - destIndex) % 7 == 0 ||
			(currIndex - destIndex) % 9 == 0;
	}

}
