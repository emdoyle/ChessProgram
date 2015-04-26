
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
			for(int j = 0; j < 2; j++){
				if(j == 0){
					spaces[j*width + i].setPiece(new Pawn('w'));
					spaces[(height-1)*width + i].setPiece(new Pawn('b'));
				}else{
					addMinorPieces();
				}
			}
		}
	}

	private void addMinorPieces(){
		char black = 'b';
		char white = 'w';

		spaces[0].setPiece(new Rook(white));
		spaces[7].setPiece(new Rook(white));
		spaces[56].setPiece(new Rook(black));
		spaces[63].setPiece(new Rook(black));

		spaces[1].setPiece(new Knight(white));
		spaces[6].setPiece(new Knight(white));
		spaces[57].setPiece(new Knight(black));
		spaces[62].setPiece(new Knight(black));

		spaces[2].setPiece(new Bishop(white));
		spaces[5].setPiece(new Bishop(white));
		spaces[58].setPiece(new Bishop(black));
		spaces[61].setPiece(new Bishop(black));

		spaces[3].setPiece(new Queen(white));
		spaces[4].setPiece(new King(white));
		spaces[59].setPiece(new Queen(black));
		spaces[60].setPiece(new King(black));

	}

  public void displayBoard() {
    int spaceNum;
		for (int i = 0; i < 8; i++) { // files starting at 0
			for (int j = 7; j >= 0; j--) { // ranks starting at 7
				spaceNum = (8 * j) + i;
				Space cur = spaces[spaceNum];
				System.out.print("[");
				if (cur.isOccupied()) {
					System.out.print(cur.getPiece().symbol);
				} else {
					System.out.print(" ");
				}
			  System.out.print("]");
			}
			System.out.println();
		}
	}

	
				
				
			


}
