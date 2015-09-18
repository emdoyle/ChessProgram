import java.util.ArrayList;
public class Check{
	private int numChecking = 0;
	private ArrayList<Space> checkSpaces;
	private Piece checkPiece1 = null;
	private Piece checkPiece2 = null;
	
	public Check(){
		//nothing in no-arg constructor
	}

	public Check(int numChecking, ArrayList<Space> checkSpaces){
	 	this.numChecking = numChecking;
		this.checkSpaces = checkSpaces;
	}

	public Check(int numChecking, Piece checkPiece1, Piece checkPiece2){
		this.numChecking = numChecking;
		this.checkPiece1 = checkPiece1;
		this.checkPiece2 = checkPiece2;
	}

	public ArrayList<Space> getCheckSpaces(){
		return checkSpaces;
	}

	public void setCheckSpaces(ArrayList<Space> spaces){
		checkSpaces = spaces;
	}

	public int getNumChecking(){
		return numChecking;
	}

	public void setNumChecking(int num){
		numChecking = num;
	}

	public void setCheckPiece(int num, Piece piece){
		if(num == 1){
			checkPiece1 = piece;
		}else if(num == 2){
			checkPiece2 = piece;
		}
	}

	public Piece getCheckPiece(int num){
		if(num == 1){
			return checkPiece1;
		}else if(num == 2){
			return checkPiece2;
		}
		return null;
	}

}
