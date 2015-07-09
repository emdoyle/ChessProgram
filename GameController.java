import java.util.Scanner;

public class GameController {

	public static void main(String[] args){
		Scanner input = new Scanner( System.in );	
		System.out.println("Chess program has started, about to display board\n");
		Board currentBoard = new Board(8, 8);
		currentBoard.displayBoard();
		Space[][] spaceArr = currentBoard.getSpacesArray();
		String selectedMove;
		MoveInterpreter interp = new MoveInterpreter();
		MoveExecuter exec = new MoveExecuter(currentBoard);
		char currentMove = 'w';
		/*System.out.println("Board[0][4] has rank: " +
		spaceArr[0][4].getRank() + " and file: " +
		spaceArr[0][4].getFile() + "\nand code: " +
		spaceArr[0][4].getCode());*/

		Piece att = spaceArr[1][3].getPiece();
		System.out.println(att.getSpace().getCode() + " to " +
		spaceArr[3][3].getCode());
		Move testMove = new Move(att, null, spaceArr[1][3],
		spaceArr[3][3]);
		exec.executeMove(testMove);
		currentBoard.displayBoard();

		att = spaceArr[6][4].getPiece();
		System.out.println(att.getSpace().getCode() + " to " +
		spaceArr[4][4].getCode());
		testMove = new Move(att, null, spaceArr[6][4],
		spaceArr[4][4]);
		exec.executeMove(testMove);
		currentBoard.displayBoard();

		att = spaceArr[4][4].getPiece();
		Piece vic = spaceArr[3][3].getPiece();
		System.out.println(att.getSpace().getCode() + " to " +
		vic.getSpace().getCode());
		testMove = new Move(att, vic, spaceArr[4][4],
		spaceArr[3][3]);
		exec.executeMove(testMove);
		currentBoard.displayBoard();

	}

}
