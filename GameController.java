import java.util.Scanner;

public class GameController {

	public static void main(String[] args){
		Scanner input = new Scanner( System.in );
		boolean end = false;
		clearConsole();	
		System.out.println("Chess program has started, about to display board\n");

		Board currentBoard = new Board(8, 8);
		ChessView view = new ChessView(currentBoard);
		Space[][] spaceArr = currentBoard.getSpacesArray();
		String selectedMove;
		Move currMove;
		MoveInterpreter interp = new MoveInterpreter(currentBoard);
		MoveExecuter exec = new MoveExecuter(currentBoard, input);
    		MoveRecorder mrec = new MoveRecorder(currentBoard);
		ProfileHolder phold = new ProfileHolder("Profiles.txt");

		System.out.println("Enter your profile name, or if you have not created a profile,"
		+ "\nenter a name for a new profile:");
		String profName = input.nextLine();
		Profile currProf;
		if(!phold.contains(profName)){currProf = phold.createProf(profName);}
		else{currProf = phold.getProf(profName);}
		
		System.out.println("Type a move code or type 'quit' to quit.");
		selectedMove = input.nextLine();

		while(!selectedMove.equals("quit") && !end){
			clearConsole();
      			//System.out.println(mrec.getMoveString());
			currMove = interp.parseMove(selectedMove);
			if(exec.executeMove(currMove, true, currentBoard.getTurn())==1){
        			mrec.recordMove(selectedMove);
      			}
			System.out.println(mrec.getMoveString());
			//currentBoard.displayBoard();
			view.repaint();
			if(currentBoard.getCheckMate()){
				System.out.println("Winner: " + currentBoard.getTurn());
				System.out.println("Enter anything to end.");
				end = true;
			}
			selectedMove = input.nextLine();
		}
		currProf.addGame(mrec.getMoveString());
		phold.writeProfiles();
		clearConsole();
		System.out.println(currProf.getGameAt(0));
    
		//clearConsole();
    		System.exit(0);
	}

	private final static void clearConsole(){
		//credit: coderanch.com/t/631492/java/java/clear-console-screen
		//\033 is escape code in ANSI, H moves to top, 2J clears all
		System.out.print("\033[H\033[2J");
	}

	
}
