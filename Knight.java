
public class Knight extends Piece{

	public Knight(char team){
		symbol = 'N';
		this.team = team;
	}	

	public boolean canReach(Space dest){

		int currentFile = currentSpace.getFile();
		int currentRank = currentSpace.getRank();
		int destFile = dest.getFile();
		int destRank = dest.getRank();

		int currIndex = currentRank*8 + currentFile;
		int destIndex = destRank*8 + destFile;

		int diff = Math.abs(currIndex-destIndex);
		return diff == 6 || diff == 10 || diff == 15 || diff == 17;

	}

}
