import java.util.Scanner;

public class GameController {

	public static void main(String[] args){
		Scanner input = new Scanner( System.in );	
		System.out.println("Chess program has started, about to display board\n");
		Board currentBoard = new Board(8, 8);
		currentBoard.displayBoard();
		Space[][] spaceArr = currentBoard.getSpacesArray();
		String selectedMove;
		Move currMove;
		MoveInterpreter interp = new MoveInterpreter(currentBoard);
		MoveExecuter exec = new MoveExecuter(currentBoard);
		System.out.println("Type a move code or type 'quit' to quit.");
		selectedMove = input.nextLine();
		while(!selectedMove.equals("quit")){
			currMove = interp.parseMove(selectedMove);
			exec.executeMove(currMove);
			currentBoard.displayBoard();
			selectedMove = input.nextLine();
		}

		/*System.out.println("Board[4][3] has rank: " +
		spaceArr[4][3].getRank() + " and file: " +
		spaceArr[4][3].getFile() + "\nand code: " +
		spaceArr[4][3].getCode());*/

		/*Move testMove = interp.parseMove("a4");
		exec.executeMove(testMove);
		currentBoard.displayBoard();

		testMove = interp.parseMove("b5");
		exec.executeMove(testMove);
		currentBoard.displayBoard();

		testMove = interp.parseMove("c4");
		exec.executeMove(testMove);
		currentBoard.displayBoard();

		testMove = interp.parseMove("d6");
		exec.executeMove(testMove);
		currentBoard.displayBoard();

		System.out.println("axb5");
		testMove = interp.parseMove("axb5");
		exec.executeMove(testMove);
		currentBoard.displayBoard();*/

	}

}
