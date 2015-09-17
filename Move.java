
public class Move {
	
	//private double startTime, endTime;
	private Piece attacker, victim;
	private Space beginSpace, endSpace;
	private boolean promotion, check, castle, enPassant;

	public Move(){
		attacker = null;
		victim = null;
		beginSpace = null;
		endSpace = null;
		promotion = false;
		check = false;
		castle = false;
		enPassant = false;
	}	

	public Move(Space beginSpace, Space endSpace){

		this.attacker = beginSpace.getPiece();
		this.victim = endSpace.getPiece();
		this.beginSpace = beginSpace;
		this.endSpace = endSpace;
		
	}

	public Piece getAttacker(){
		return attacker;
	}

	public Piece getVictim(){
		return victim;
	}

	public void setAttacker(Piece att){
		attacker = att;
	}
	
	public void setVictim(Piece vic){
		victim = vic;
	}

	public void setBegin(Space beg){
		beginSpace = beg;
		//I believe in every scenario, we should set attacker to the
		//piece on the beginning square
		setAttacker(beginSpace.getPiece());
	}

	public void setEnd(Space end){
		endSpace = end;
		//THIS IS NOT CORRECT FOR EN PASSANT
		//In en passant scenario, setEnd then setVictim
		setVictim(endSpace.getPiece());
	}

	public Space getBegin(){
		return beginSpace;
	}

	public Space getEnd(){
		return endSpace;
	}

	public boolean isCheck(){
		return check;
	}

	public void setCheck(boolean flag){
		check = flag;
	}

	public boolean isPromotion(){
		return promotion;
	}

	public void setPromotion(boolean flag){
		promotion = flag;
	}

	public void setCastle(boolean flag){
		castle = flag;
	}

	public boolean getCastle(){
		return castle;
	}

	public void setEnPassant(boolean flag){
		enPassant = flag;
	}

	public boolean isEnPassant(){
		return enPassant;
	}

}
