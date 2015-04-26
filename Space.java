
public class Space {
	
	private int rank, file;
	private boolean occupied;
	private Piece occupyingPiece;
	
	public Space(){
		//blank constructor should not be used,
		//cannot judge occupied status of position
		//here
		rank = -1;
		file = -1;
		occupied = false;
		
	}
	
	public Space(int rank, int file){
		
		this.rank = rank;
		this.file = file;
		occupied = false;
		
	}
	
	public int getRank(){
		
		return rank;
		
	}
	
	public int getFile(){
		
		return file;
		
	}

	public Piece getPiece(){

		return occupyingPiece;

	}

	public void setPiece(Piece piece){

		occupyingPiece = piece;

	}
	
	public boolean isOccupied(){
		
		return occupied;
		
	}
	
	public void setPosition(int rank, int file){
		
		this.rank = rank;
		this.file = file;
		
	}
	
	public void setOccupied(boolean status){
		
		this.occupied = status;
	}
	
	@Override
	public boolean equals(Object o){
		
		return ((Space) o).getRank() == this.rank && ((Space) o).getFile() == this.file;
	
	}

}
