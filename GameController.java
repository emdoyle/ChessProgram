import java.util.Scanner;

public class GameController {

	public static void main(String[] args){
		Scanner input = new Scanner( System.in );
		boolean end = false;
		clearConsole();	
		System.out.println("Chess program has started, about to display board\n");
		Board currentBoard = new Board(8, 8);
		currentBoard.displayBoard();
		Space[][] spaceArr = currentBoard.getSpacesArray();
		String selectedMove;
		Move currMove;
		MoveInterpreter interp = new MoveInterpreter(currentBoard);
		MoveExecuter exec = new MoveExecuter(currentBoard, input);
		System.out.println("Type a move code or type 'quit' to quit.");
		selectedMove = input.nextLine();
		while(!selectedMove.equals("quit") && !end){
			clearConsole();
			currMove = interp.parseMove(selectedMove);
			exec.executeMove(currMove, true, currentBoard.getTurn());
			currentBoard.displayBoard();
			if(currentBoard.getCheckMate()){
				System.out.println("Winner: " + currentBoard.getTurn());
				System.out.println("Enter anything to end.");
				end = true;
			}
			selectedMove = input.nextLine();
		}
		clearConsole();
	}

	private final static void clearConsole(){
		//credit: coderanch.com/t/631492/java/java/clear-console-screen
		//\033 is escape code in ANSI, H moves to top, 2J clears all
		System.out.print("\033[H\033[2J");
	}

	
}
