/*
* REMEMBER BOARD LAYOUT:  (REVISE)
* [0][0] = a1,  [0][7] = h1
* [7][0] = a8,  [7][7] = h8
*/

public class MoveExecuter{

	private Board currBoard;
	private Space[][] spaceArr;

	public MoveExecuter(Board b){
		currBoard = b;
		spaceArr = b.getSpacesArray();
	}

	public void executeMove(Move m){
		Piece attacker = m.getAttacker();
		Piece victim = m.getVictim();
		Space begin = m.getBegin();
		Space end = m.getEnd();

		begin.setOccupied(false);
		attacker.setSpace(end);
		spaceArr[end.getRank()][end.getFile()].setPiece(attacker);
		if(victim != null){
			//use null space as flag for captured pieces
			victim.setSpace(null);
			spaceArr[begin.getRank()][begin.getFile()].setPiece(null);
		}

		currBoard.setSpacesArray(spaceArr);
		currBoard.setWhiteTurn(!currBoard.getWhiteTurn());
	}

}
