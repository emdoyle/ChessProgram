
public class Knight extends Piece{

	public Knight(char team){
		symbol = 'N';
		this.team = team;
	}	

	public boolean canReach(Space dest){

		currentFile = currentSpace.getFile();
		currentRank = currentSpace.getRank();
		destFile = dest.getFile();
		destRank = dest.getRank();

		currIndex = currentRank*8 + currentFile;
		destIndex = destRank*8 + destFile;

		diff = Math.abs(currIndex-destIndex);
		return diff == 6 || diff == 10 || diff == 15 || diff == 17;

	}

}
