/*
 * Extends abstract Piece class with instance vars:
 * Space currentSpace
 * char symbol, team
 */
public class Pawn extends Piece{

	//Note: constructor does not take a Space object
	//this is because it is set in Space.setPiece
	public Pawn(char team){
		symbol = 'P';
		value = 1;
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
		
		//must check team because behavior is different
		//based on team color
		//e.g. white pawns move the opposite direction of
		//black pawns
		if(team == 'w'){
			//case of 2 space move forward
			if((destIndex - currIndex) == 16 && !dest.isOccupied()){
				return currentRank == 2;
			//case of 1 space move forward
			}else if((destIndex - currIndex) == 8 && !dest.isOccupied()){
				return true;
			//case of diagonal capture
			}else{
				return ((destIndex - currIndex) == 7 || (destIndex - currIndex) == 9)
									&& dest.isOccupied() && (dest.getPiece().getSymbol() == 'b');
			}
		}else{
			if((currIndex - destIndex) == 16 && !dest.isOccupied()){
				return currentRank == 2;
			}else if((currIndex - destIndex) == 8 && !dest.isOccupied()){
				return true;
			}else{
				return ((currIndex - destIndex) == 7 || (currIndex - destIndex) == 9)
									&& dest.isOccupied() && (dest.getPiece().getSymbol() == 'w');
			}

		}

	}
}
