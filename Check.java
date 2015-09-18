public class Check{
	private int numChecking = 0;
	private Space[] checkSpaces;
	
	public Check(){
		//nothing in no-arg constructor
	}

	public Check(int numChecking, Space[] checkSpaces){
	 	this.numChecking = numChecking;
		this.checkSpaces = checkSpaces;
	}

	public Space[] getCheckSpaces(){
		return checkSpaces;
	}

	public void setCheckSpaces(Space[] spaces){
		checkSpaces = spaces;
	}

	public int getNumChecking(){
		return numChecking;
	}

	public void setNumChecking(int num){
		numChecking = num;
	}
}
