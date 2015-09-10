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

	public void executeMove(Move m){
		if(m == null){
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
			System.out.println("Move failed");
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

		if(attacker.getSymbol() == 'K'){
			currBoard.setCastle(currBoard.getTurn(), 'k', false);
			currBoard.setCastle(currBoard.getTurn(), 'q', false);
		}

		if(attacker.getSymbol() == 'R'){
			if(currBoard.getTurn() == 'w' && begin.getRank() == 0){
				if(begin.getFile() == 0){
					currBoard.setCastle('w', 'q', false);
				}else if(begin.getFile() == 7){
					currBoard.setCastle('w', 'k', false);
				}
			}else if(currBoard.getTurn() == 'b' && begin.getRank() == 7){
				if(begin.getFile() == 0){
					currBoard.setCastle('b', 'q', false);
				}else if(begin.getFile() == 7){
					currBoard.setCastle('b', 'k', false);
				}
			}
		}

		if(currBoard.detectCheck(invertTeam(currBoard.getTurn()))){
			System.out.println("Check!");
		}
		currBoard.setSpacesArray(spaceArr);
		currBoard.setWhiteTurn(!currBoard.getWhiteTurn());
	}

	public void executeCastle(Move m){
		if(m.getAttacker() == null || m.getVictim() == null){
			System.out.println("Move failed");
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
			end = spaceArr[rank][1];
		}else if(m.getVictim().getSpace().getFile() == 7){
			file = 7;
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

		currBoard.setSpacesArray(spaceArr);
		currBoard.setWhiteTurn(!currBoard.getWhiteTurn());
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
		newAttacker.setSpace(end);
		spaceArr[end.getRank()][end.getFile()].setPiece(attacker);
		spaceArr[begin.getRank()][begin.getFile()].setPiece(null);

		currBoard.setSpacesArray(spaceArr);
		currBoard.setWhiteTurn(!currBoard.getWhiteTurn());
	}

}
