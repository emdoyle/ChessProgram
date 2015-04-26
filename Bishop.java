
public class Bishop extends Piece{

	public Bishop(char team){
		symbol = 'B';
		this.team = team;
	}

	public boolean canReach(Space dest){
		currentFile = currentSpace.getFile();
		currentRank = currentSpace.getRank();
		destFile = dest.getFile();
		destRank = dest.getRank();

		currIndex = currentRank*8 + currentFile;
		destIndex = destRank*8 + destFile;

		return (currIndex - destIndex) % 7 == 0 ||
			(currIndex - destIndex) % 9 == 0;
	}

}
