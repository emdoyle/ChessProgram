
public class Board {
	
	private Space[] spaces;
	private boolean whiteTurn = true;
	private int width, height;
	
	public Board(int width, int height){
		
		this.width = width;
		this.height = height;
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

	public void addChessPieces(){
		if(width == 8 && height == 8){
			for(int i = 0; i < width){
				for(int j = 0; j < 2; j++){
					if(j == 0){
						spaces[j*width + i].setPiece(new Pawn());
						spaces[(height-1)*width + i].setPiece(new Pawn());
					}else{
						addMinorPieces();
					}
				}
			}
		}
	}

	private void addMinorPieces(){

		spaces[0].setPiece(new Rook());
		spaces[7].setPiece(new Rook());
		spaces[56].setPiece(new Rook());
		spaces[63].setPiece(new Rook());

		spaces[1].setPiece(new Knight());
		spaces[6].setPiece(new Knight());
		spaces[57].setPiece(new Knight());
		spaces[62]setPiece(new Knight());

		spaces[2].setPiece(new Bishop());
		spaces[5].setPiece(new Bishop());
		spaces[58].setPiece(new Bishop());
		spaces[61].setPiece(new Bishop());

		spaces[59].setPiece(new Queen());
		spaces[60].setPiece(new King());

	}

}
