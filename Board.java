
public class Board {
	
	private Space[] spaces = new Space[64];
	private boolean whiteTurn = true;
	
	public Board(){
		
		initSpaces();
		
	}
	
	private void initSpaces(){
		
		for(int i = 0; i < 8; i++){
			for(int j = 0; j < 8; j++){
			
				spaces[i*j + i] = new Space(i, j, false);
				
			}
		}
		
	}

}
