
public class Move {
	
	//private double startTime, endTime;
	private Piece attacker, victim;
	private Space beginSpace, endSpace;
	
	public Move(Piece attacker, Piece victim, Space beginSpace,
			Space endSpace){

		this.attacker = attacker;
		this.victim = victim;
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
	}

	public void setEnd(Space end){
		endSpace = end;
	}

	public Space getBegin(){
		return beginSpace;
	}

	public Space getEnd(){
		return endSpace;
	}

}
