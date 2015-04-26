import java.util.Scanner;

public class GameController {

	public static void main(String[] args){
		System.out.println("Chess program has started, about to display board\n");
		Board currentBoard = new Board(8, 8);
		currentBoard.displayBoard();
		System.out.print("Please enter a move:\n[1.(move), 1 (move), or (move)]\n");
		Scanner input = new Scanner(System.in);
		/*MoveInterpreter interp = new MoveInterpreter();
		while(input.hasNext()){
			Move currentMove = interp.interpret(input.next());
			executeMove();
		}*/
	}

	private static void executeMove(){

	}

}
