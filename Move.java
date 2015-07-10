
public class Move {
	
	//private double startTime, endTime;
	private Piece attacker, victim;
	private Space beginSpace, endSpace;
	//this ivar isn't used yet, not sure if necessary.
	//first issue to resolve is whether moves should be
	//constructed with a code param or if they should 'figure out'
	//the code based on given info (but this task sounds better
	//suited to MoveInterpreter)
	private String code;

	public Move(){
		attacker = null;
		victim = null;
		beginSpace = null;
		endSpace = null;
	}	

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

	public String getCode(){
		return code;
	}

	public void setCode(String newCode){
		code = newCode;
	}

}
