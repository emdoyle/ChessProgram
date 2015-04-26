
public class GameController {

	Board currentBoard;

	public static void main(String[] args){
		System.out.println("Chess program has started, about to display board\n");
		currentBoard = new Board(8, 8);
		currentBoard.displayBoard();
		System.out.print("Please enter a move:\n[1.(move), 1 (move), or (move)]\n");
	}

}
