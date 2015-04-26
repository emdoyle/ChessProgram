/** Holds the rank and file of a particular space on the board, as well as
 *  if that space is currently occupied by a piece, and if so, the occupying
 *  piece */
public class Space {
	
	private int rank, file;
	private boolean occupied;
	private Piece occupyingPiece;

	// Space constructor which requires the rank and file of the board	
	public Space(int file, int rank){
		
		this.rank = rank;
		this.file = file;
		occupied = false;
		
	}
	
	public int getRank(){ // rank getter
		
		return rank;
		
	}
	
	public int getFile(){ // file getter
		
		return file;
		
	}

	public Piece getPiece(){ // Piece getter

		return occupyingPiece;

	}

	public void setPiece(Piece piece){ // Piece setter

		occupyingPiece = piece;
		if(piece != null){piece.setSpace(this);}
		occupied = !(piece == null);

	}
	
	// returns true if the space is occupied, false otherwise
	public boolean isOccupied(){
		
		return occupied;
		
	}
	
	// changes the position of current space
	public void setPosition(int rank, int file){
		
		this.rank = rank;
		this.file = file;
		
	}
	
	// sets the occupancy of the current space
	public void setOccupied(boolean status){
		
		this.occupied = status;
	}
	
	@Override
	public boolean equals(Object o){
		
		return ((Space) o).getRank() == this.rank && ((Space) o).getFile() == this.file;
	
	}

}
