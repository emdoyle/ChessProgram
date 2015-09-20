/*
* REMEMBER BOARD LAYOUT:  (REVISE)
* [0][0] = a1,  [0][7] = h1
* [7][0] = a8,  [7][7] = h8
*/

import java.util.Scanner;

public class MoveExecuter{

	private Board currBoard;
	private Space[][] spaceArr;
	private Scanner input;

	public MoveExecuter(Board b, Scanner input){
		currBoard = b;
		spaceArr = b.getSpacesArray();
		this.input = input;
	}

	private char invertTeam(char team){
		if(team == 'w'){
			return 'b';
		}else if(team == 'b'){
			return 'w';
		}else{
			System.out.println("Improper call of invertTeam");
			return 'w';
		}
	}

	public void executeMove(Move m, boolean printCheckMessages, char team){
		if(m == null && printCheckMessages){
			System.out.println("Move failed");
			return;
		}

		if(m.getCastle()){
			executeCastle(m);
			return;
		}

		if(m.isPromotion()){
			executePromotion(m);
			return;
		}

		if(m.getAttacker() == null
		|| m.getBegin() == null || m.getEnd()
		== null){
			if(printCheckMessages){System.out.println("Move failed");}
			return;
		}

		Piece attacker = m.getAttacker();
		Piece victim = m.getVictim();
		Space begin = m.getBegin();
		Space end = m.getEnd();

		begin.setOccupied(false);
		attacker.setSpace(end);
		spaceArr[end.getRank()][end.getFile()].setPiece(attacker);
		spaceArr[begin.getRank()][begin.getFile()].setPiece(null);

		if(m.isEnPassant() && team == 'w'){
			spaceArr[end.getRank()-1][end.getFile()].setPiece(null);
		}else if(m.isEnPassant()){
			spaceArr[end.getRank()+1][end.getFile()].setPiece(null);
		}

		if(attacker.getSymbol() == 'K'){
			currBoard.setCastle(team, 'k', false);
			currBoard.setCastle(team, 'q', false);
		}

		if(attacker.getSymbol() == 'R'){
			if(team == 'w' && begin.getRank() == 0){
				if(begin.getFile() == 0){
					currBoard.setCastle('w', 'q', false);
				}else if(begin.getFile() == 7){
					currBoard.setCastle('w', 'k', false);
				}
			}else if(team == 'b' && begin.getRank() == 7){
				if(begin.getFile() == 0){
					currBoard.setCastle('b', 'q', false);
				}else if(begin.getFile() == 7){
					currBoard.setCastle('b', 'k', false);
				}
			}
		}

		if(currBoard.detectCheck(team)){
			if(printCheckMessages){
				System.out.println("Cannot move into check.");
			}else{
				revertMove(begin, end, attacker, victim);
				throw new IllegalStateException();
			}
			revertMove(begin, end, attacker, victim);
			return;
		}

		if(currBoard.detectCheck(invertTeam(team))){
			if(printCheckMessages){
				if(currBoard.detectCheckMate(invertTeam(team))){
					System.out.println("Checkmate!");
					currBoard.setCheckMate(true);
					return;
				}else{
					System.out.println("Check!");
				}
			}
		}
		if(!printCheckMessages){
			revertMove(begin, end, attacker, victim);
		}else{
		currBoard.setEnPassant(invertTeam(team), 'x');
		currBoard.setSpacesArray(spaceArr);
		currBoard.setWhiteTurn(!(team == 'w'));
		}
	}

	public void revertMove(Space begin, Space end, Piece attacker, Piece victim){
		begin.setOccupied(true);
		attacker.setSpace(begin);
		if(victim != null){ victim.setSpace(end); }
		spaceArr[begin.getRank()][begin.getFile()].setPiece(attacker);
		if(victim != null){ spaceArr[end.getRank()][end.getFile()].setPiece(victim); }else{
			spaceArr[end.getRank()][end.getFile()].setPiece(null);
		}
		currBoard.setSpacesArray(spaceArr);
	}

	public void executeCastle(Move m){
		if(m.getAttacker() == null || m.getVictim() == null){
			System.out.println("Move failed");
			return;
		}

		if(currBoard.detectCheck(currBoard.getTurn())){
			System.out.println("Cannot castle while in check.");
			return;
		}

		Piece attacker = m.getAttacker();
		Piece victim = m.getVictim();
		Space begin = attacker.getSpace();
		Space end = null;
		int rank;
		int file = -1;

		if(currBoard.getTurn() == 'w'){
			rank = 0;
		}else{
			rank = 7;
		}

		if(m.getVictim().getSpace().getFile() == 0){
			file = 0;
			if(!checkSpotsBetween(begin, spaceArr[rank][1], -1, attacker)){
				System.out.println("Cannot castle through or into check.");
				return;
			}
			end = spaceArr[rank][1];
		}else if(m.getVictim().getSpace().getFile() == 7){
			file = 7;
			if(!checkSpotsBetween(begin, spaceArr[rank][6], 1, attacker)){
				System.out.println("Cannot castle through or into check.");
				return;
			}
			end = spaceArr[rank][6];
		}else{
			System.out.println("Move failed");
			return;
		}

		begin.setOccupied(false);
		attacker.setSpace(end);
		spaceArr[end.getRank()][end.getFile()].setPiece(attacker);
		spaceArr[begin.getRank()][begin.getFile()].setPiece(null);

		begin = victim.getSpace();
		attacker = victim;

		if(file == 0){
			end = spaceArr[rank][2];
		}else if(file == 7){
			end = spaceArr[rank][5];
		}

		begin.setOccupied(false);
		attacker.setSpace(end);
		spaceArr[end.getRank()][end.getFile()].setPiece(attacker);
		spaceArr[begin.getRank()][begin.getFile()].setPiece(null);

		if(currBoard.detectCheck(invertTeam(currBoard.getTurn()))){
			System.out.println("Check!");
		}

		currBoard.setSpacesArray(spaceArr);
		currBoard.setWhiteTurn(!currBoard.getWhiteTurn());
	}

	private boolean checkSpotsBetween(Space begin, Space end, int direction,
		Piece movingKing){
		//this checks horizontally only (for castling)
		int currEndFile = begin.getFile();
		for(int i = 0; i < Math.abs(begin.getFile() - end.getFile()); i++){
			currEndFile += direction;
			movingKing.setSpace(spaceArr[end.getRank()][i]);
			spaceArr[end.getRank()][currEndFile].setPiece(movingKing);
			spaceArr[begin.getRank()][currEndFile-direction].setPiece(null);
			if(currBoard.detectCheck(currBoard.getTurn())){
				revertMove(begin, spaceArr[end.getRank()][currEndFile], movingKing, null);
				return false;
			}
		}
		revertMove(begin, spaceArr[end.getRank()][currEndFile], movingKing, null);
		return true;
	}

	public void executePromotion(Move m){
		if(m == null || m.getAttacker() == null
		|| m.getBegin() == null || m.getEnd()
		== null){
			System.out.println("Move failed");
			return;
		}

		Piece attacker = m.getAttacker();
		Piece victim = m.getVictim();
		Space begin = m.getBegin();
		Space end = m.getEnd();

		System.out.println("Enter code for piece to promote to: (N, B, R, Q)");
		Piece newAttacker = null;

		String selectedPiece = input.nextLine();
		while(!selectedPiece.equals("quit")){
			switch(selectedPiece.charAt(0)){
				case 'N':
					newAttacker = new Knight(currBoard.getTurn());
					break;
				case 'B':
					newAttacker = new Bishop(currBoard.getTurn());
					break;
				case 'R':
					newAttacker = new Rook(currBoard.getTurn());
					break;
				case 'Q':
					newAttacker = new Queen(currBoard.getTurn());
					break;
				default:
					System.out.println("Not recognized");
					break;
			}
			if(newAttacker == null){
				selectedPiece = input.nextLine();
			}else{
				break;
			}
		}

		if(newAttacker == null){
			System.out.println("Please type 'quit' once more");
			return;
		}

		begin.setOccupied(false);
		attacker = newAttacker;
		attacker.setSpace(end);
		spaceArr[end.getRank()][end.getFile()].setPiece(attacker);
		spaceArr[begin.getRank()][begin.getFile()].setPiece(null);
		if(currBoard.detectCheck(currBoard.getTurn())){
			System.out.println("Cannot move into check!");
			revertMove(begin, end, m.getAttacker(), null);
			return;
		}

		if(currBoard.detectCheck(invertTeam(currBoard.getTurn()))){
			System.out.println("Check!");
		}

		currBoard.setSpacesArray(spaceArr);
		currBoard.setWhiteTurn(!currBoard.getWhiteTurn());
	}

}
