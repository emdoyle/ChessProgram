
public class Pawn extends Piece{

	public Pawn(char team){
		symbol = 'P';
		this.team = team;
	}	

	public boolean canReach(Space dest){

		int currentFile = currentSpace.getFile();
		int currentRank = currentSpace.getRank();
		int destFile = dest.getFile();
		int destRank = dest.getRank();

		int currIndex = currentRank*8 + currentFile;
		int destIndex = destRank*8 + destFile;

		if(team == 'w'){
			if((destIndex - currIndex) == 16 && !dest.isOccupied()){
				return currentRank == 2;
			}else if((destIndex - currIndex) == 8 && !dest.isOccupied()){
				return true;
			}else{
				return ((destIndex - currIndex) == 7 || (destIndex - currIndex) == 9);
			}
		}else{
			if((currIndex - destIndex) == 16 && !dest.isOccupied()){
				return currentRank == 2;
			}else if((currIndex - destIndex) == 8 && !dest.isOccupied()){
				return true;
			}else{
				return ((currIndex - destIndex) == 7 || (currIndex - destIndex) == 9);
			}

		}

	}

}
