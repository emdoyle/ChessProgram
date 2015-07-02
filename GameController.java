import java.util.Scanner;

public class GameController {

	public static void main(String[] args){
		Scanner input = new Scanner( System.in );	
		System.out.println("Chess program has started, about to display board\n");
		Board currentBoard = new Board(8, 8);
		currentBoard.displayBoard();
		String selectedMove;
		MoveInterpreter interp = new MoveInterpreter();
		char currentMove = 'w';
		/*
		while ( true ) {
			currentBoard.displayBoard();
			System.out.print("Please enter a move:\n[1.(move), 1 (move), or (move)]\n");
			selectedMove = input.next();
			if ( !interp.isValidMove( selectedMove ) ) {
				System.err.println( "You entered an invalid move!");
				continue;
			}

			currentMove = ( currentMove == 'w' ) ? 'b' : 'w';
		}*/
	
			
		/*Move move1 = new Move(currentBoard.getSpacesArray()[12].getPiece(), null,
				currentBoard.getSpacesArray()[12], currentBoard.getSpacesArray()[28]);
		executeMove(currentBoard, move1);
		currentBoard.displayBoard();


		Move move2 = new Move(currentBoard.getSpacesArray()[51].getPiece(), null,
				currentBoard.getSpacesArray()[51], currentBoard.getSpacesArray()[35]);
		executeMove(currentBoard, move2);
		currentBoard.displayBoard();


		Move move3 = new Move(currentBoard.getSpacesArray()[28].getPiece(), currentBoard.getSpacesArray()[35].getPiece(), currentBoard.getSpacesArray()[28], currentBoard.getSpacesArray()[35]);
		executeMove(currentBoard, move3);
		currentBoard.displayBoard();

		/*MoveInterpreter interp = new MoveInterpreter();
		while(input.hasNext()){
			Move currentMove = interp.interpret(input.next());
			executeMove();
		}*/
	}

	private static void executeMove(Board board, Move m){
			/*Space begin = m.getBegin();
			Space end = m.getEnd();
			Piece p = m.getAttacker();

			int begRank = begin.getRank();
			int begFile = begin.getFile();
			int begIndex = begRank*8 + begFile;

			int endRank = end.getRank();
			int endFile = end.getFile();
			int endIndex = endRank*8 + endFile;

			Space[][] sp = board.getSpacesArray();
			sp[begIndex].setPiece(null);
			sp[endIndex].setPiece(p);*/
	}

}
