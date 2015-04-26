
public class Board {
	
	private Space[] spaces;
	private boolean whiteTurn = true;
	
	public Board(int width, int height){
		
		spaces = new Space[width*height];
		initSpaces(width, height);
		
	}
	
	private void initSpaces(int width, int height){
		
		for(int i = 0; i < width; i++){
			for(int j = 0; j < height; j++){
			
				spaces[j*width + i] = new Space(i, j);
				
			}
		}
		
	}

}
