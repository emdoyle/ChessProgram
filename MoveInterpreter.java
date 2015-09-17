public class MoveInterpreter {

	/*
	* NEED TO FIX/DO
	* -LONG-TERM: Detecting check(mate)
	*/

	private Board currBoard;
	private Space[][] spaceArr;

	public MoveInterpreter(Board b){
		currBoard = b;
		spaceArr = b.getSpacesArray();	
	}

	public Move parseMove(String line){
		char firstChar;
		if ( line == "" || line.length() == 1 ) {
			return null;
		}
		if ( line.length() > 6 ) {
			return null;
		}

		firstChar = line.charAt(0);

		if ( firstChar >= 'a' && firstChar <= 'h' ) { //pawn move
			return PawnMove(line);
		}else if(firstChar == 'B'){
			return minorPieceMove('B', line);
		}else if(firstChar == 'N'){
			return minorPieceMove('N', line);
		}else if(firstChar == 'R'){
			return minorPieceMove('R', line);
		}else if(firstChar == 'Q'){
			return minorPieceMove('Q', line);
		}else if(firstChar == 'K'){
			return minorPieceMove('K', line);
		}else if(firstChar == 'O' || firstChar == '0'){
			return castle(line);
		}else{
			return null;
		}

	}

	private Move castle(String line){
		Move result = new Move();
		
		if(line.equals("O-O")){
			if(currBoard.getCastle(currBoard.getTurn(), 'k')){
				if(currBoard.getTurn() == 'w'){
					if(spaceArr[0][6].getPiece() == null &&
						spaceArr[0][5].getPiece() == null){
						result.setCastle(true);
						result.setAttacker(spaceArr[0][4].getPiece());
						result.setVictim(spaceArr[0][7].getPiece());
					}
				}else{
					if(spaceArr[7][6].getPiece() == null &&
						spaceArr[7][5].getPiece() == null){
						result.setCastle(true);
						result.setAttacker(spaceArr[7][4].getPiece());
						result.setVictim(spaceArr[7][7].getPiece());
					}
				}
			}
		}else if(line.equals("O-O-O")){
			if(currBoard.getCastle(currBoard.getTurn(), 'q')){
				if(currBoard.getTurn() == 'w'){
					if(spaceArr[0][1].getPiece() == null &&
						spaceArr[0][2].getPiece() == null &&
						spaceArr[0][3].getPiece() == null){
						result.setCastle(true);
						result.setAttacker(spaceArr[0][4].getPiece());
						result.setVictim(spaceArr[0][0].getPiece());
					}
				}else{
					if(spaceArr[7][1].getPiece() == null &&
						spaceArr[7][2].getPiece() == null &&
						spaceArr[7][3].getPiece() == null){
						result.setCastle(true);
						result.setAttacker(spaceArr[7][4].getPiece());
						result.setVictim(spaceArr[7][0].getPiece());
					}
				}
			}
		}
		return result;

	}

	private int fileToInt(char file){
		//97 is ASCII code for 'a'  ex:  97-'a' should equal 0
		return file-97;
	}

	private char intToFile(int integerFile){
		char charFile = (char) integerFile;
		return (char)(charFile+97);
	}

	private boolean inRange(int num){
		return num <= 7 && num >= 0;
	}

	private Move PawnMove(String line){
		Move result = new Move();
		Pawn pwn = new Pawn('w');
		int firstFile = fileToInt(line.charAt(0));
		if(line.charAt(1) == 'x'){
		//this is a capture
			int secondFile = fileToInt(line.charAt(2));
			int secondRank = Character.getNumericValue(line.charAt(3))-1;
			
			//check if pawns are within one file
			if(Math.abs(firstFile - secondFile) != 1){
				return null;
			}

			if(currBoard.getWhiteTurn() &&
			inRange(secondRank-1) && spaceArr[secondRank-1][firstFile].getPiece()
			!= null &&
			spaceArr[secondRank-1][firstFile].getPiece().getTeam()=='w'){
				//white pawn capturing up
				if(spaceArr[secondRank][secondFile].isOccupied() &&
				spaceArr[secondRank][secondFile].getPiece().getTeam() == 'b'){ 
					result.setBegin(spaceArr[secondRank-1][firstFile]);
				}else if(currBoard.getEnPassant('b') == intToFile(secondFile)
				&& secondRank == 5){
					result.setBegin(spaceArr[secondRank-1][firstFile]);
					result.setEnPassant(true);
				}
			}else if(!currBoard.getWhiteTurn() &&
			inRange(secondRank+1) && spaceArr[secondRank+1][firstFile].getPiece()
			!= null &&
			spaceArr[secondRank+1][firstFile].getPiece().getTeam()=='b'){
				//black pawn capturing down
				if(spaceArr[secondRank][secondFile].isOccupied() &&
				spaceArr[secondRank][secondFile].getPiece().getTeam() == 'w'){ 
					result.setBegin(spaceArr[secondRank+1][firstFile]);
				}else if(currBoard.getEnPassant('w') == intToFile(secondFile)
				&& secondRank == 3){
					result.setBegin(spaceArr[secondRank+1][firstFile]);
					result.setEnPassant(true);
				}
			}

			result.setEnd(spaceArr[secondRank][secondFile]);
		}else{
			//not a capture
			int destRank= Character.getNumericValue(line.charAt(1))-1;

			//check if destination space is free
			if(inRange(destRank) && spaceArr[destRank][firstFile].getPiece() != null){
				return null;
			}			

			if(inRange(destRank-1) && 
			spaceArr[destRank-1][firstFile].getPiece() != null
			&& spaceArr[destRank-1][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank-1][firstFile].getPiece().getTeam()=='w'
			&& currBoard.getWhiteTurn()){
				//this executes when white pawn moves one space up board
				result.setBegin(spaceArr[destRank-1][firstFile]);

			}else if(inRange(destRank+1) &&
			spaceArr[destRank+1][firstFile].getPiece() != null
			&& spaceArr[destRank+1][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank+1][firstFile].getPiece().getTeam()=='b'
			&& !currBoard.getWhiteTurn()){
				//this executes when black pawn moves one space down board
				result.setBegin(spaceArr[destRank+1][firstFile]);

			}else if(destRank == 3 && spaceArr[destRank-2][firstFile].getPiece()
			!= null && spaceArr[destRank-2][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank-2][firstFile].getPiece().getTeam()=='w'
			&& currBoard.getWhiteTurn()){
				//this executes when white pawn moves two spaces up board
				result.setBegin(spaceArr[destRank-2][firstFile]);
				currBoard.setEnPassant('w', intToFile(firstFile));

			}else if(destRank == 4 && spaceArr[destRank+2][firstFile].getPiece()
			!= null && spaceArr[destRank+2][firstFile].
			getPiece().getClass() == pwn.getClass() &&
			spaceArr[destRank+2][firstFile].getPiece().getTeam()=='b'
			&& !currBoard.getWhiteTurn()){
				//this executes when black pawn moves two spaces down board
				result.setBegin(spaceArr[destRank+2][firstFile]);
				currBoard.setEnPassant('b',intToFile(firstFile));

			}else{
				return null;
			}

			result.setEnd(spaceArr[destRank][firstFile]);

		}
		//after either move, check for check/promotion?

		if((currBoard.getTurn() == 'w' && result.getEnd().getRank() == 7) ||
			(currBoard.getTurn() == 'b' && result.getEnd().getRank() == 0)){
			result.setPromotion(true);
		}

		return result;

	}

	private Move minorPieceMove(char piece, String line){

		Move result = new Move();

		int destFile = -1;
		int destRank = -1;
		int beginFile = -1;
		int beginRank = -1;

		if(line.indexOf('x') != -1){
		//dealing with a capture
			String destLine = line.substring(line.indexOf('x')+1);
			destFile = fileToInt(destLine.charAt(0));
			destRank = Character.getNumericValue(destLine.charAt(1))-1;

			if(spaceArr[destRank][destFile].getPiece() == null
				|| spaceArr[destRank][destFile].getPiece().getTeam()
				== currBoard.getTurn()){
				//if victim piece doesnt exist or is same team
				return null;
			}

			String beginLine = line.substring(1, line.indexOf('x'));
			//beginLine is everything before the 'x'

			if(beginLine.length() == 2){
				beginFile = fileToInt(beginLine.charAt(0));
				beginRank = Character.getNumericValue(beginLine.charAt(1))-1;
			}else if(beginLine.length() == 1){
				beginFile = fileToInt(beginLine.charAt(0));
			}

		}else{
			String shortLine = line.substring(1);
			//cuts off piece symbol (ex: 'N')

			if(shortLine.length() == 2){
				//line length of two indicates only one possible
				//attacker. ex: a3 (two characters)
				destFile = fileToInt(shortLine.charAt(0));
				destRank = Character.getNumericValue(shortLine.charAt(1))-1;
			}else if(shortLine.length() == 3){
				beginFile = fileToInt(shortLine.charAt(0));

				destFile = fileToInt(shortLine.charAt(1));
				destRank = Character.getNumericValue(shortLine.charAt(2)-1);
			}else if(shortLine.length() == 4){
				beginFile = fileToInt(shortLine.charAt(0));
				beginRank = Character.getNumericValue(shortLine.charAt(1)-1);

				destFile = fileToInt(shortLine.charAt(2));
				destRank = Character.getNumericValue(shortLine.charAt(3))-1;
			}
			

			if(spaceArr[destRank][destFile].getPiece() != null){
				//if there is a piece at dest but not a capture
				return null;
			}
		}
		Space att = null;
		if(beginFile == -1 || beginRank == -1){
			switch(piece){
				case 'N':
					att = findKnight(beginFile, destFile, destRank);
					break;
				case 'B':
					att = findBishop(beginFile, destFile, destRank);
					break;
				case 'R':
					att = findRook(beginFile, destFile, destRank);
					break;
				case 'Q':
					att = findQueen(beginFile, destFile, destRank);
					break;
				case 'K':
					att = findKing(beginFile, destFile, destRank);
					break;
				default:
					att = null;
					break;
			}
		}else{
			//initialized both
			//need to make sure begin is proper piece, no collision
			if(piece == 'N' && 
			piece == spaceArr[beginRank][beginFile].getPiece().getSymbol()
			&& spaceArr[beginRank][beginFile].getPiece().canReach(spaceArr[destRank][destFile])){
				att = spaceArr[beginRank][beginFile];
			}else if(minorCanReach(beginFile, beginRank, destFile, destRank, piece)){
				att = spaceArr[beginRank][beginFile];
			}else{
				att = null;
			}
		}

		//check for null to avoid NPE
		if(att != null){
			result.setBegin(att);
			result.setEnd(spaceArr[destRank][destFile]);
		}else{
			result = null;
		}

		return result;

	}

	private boolean minorCanReach(int beginFile, int beginRank, int destFile, int destRank,
		char piece){
		//Need to check in a straight line in one of eight directions.
		//Direction held in i, j variables.
		//+i means up, -i means down
		//+j means right, -j means left
		//Direction determined by comparison of begin/dest

		if(piece == 'N'){
			//Knight check failed
			return false;
		}

		int i;
		int j;

		if(beginFile < destFile){
			j = 1;
		}else if(beginFile > destFile){
			j = -1;
		}else{
			j = 0;
		}

		if(beginRank < destRank){
			i = 1;
		}else if(beginRank > destRank){
			i = -1;
		}else{
			i = 0;
		}

		int fileDiff = Math.max(beginFile, destFile) - Math.min(beginFile, destFile);
		int rankDiff = Math.max(beginRank, destRank) - Math.min(beginRank, destRank);

		//check if conditions make sense for piece, e.g. diagonal is not rook/king,
		//king has diffs <= 1

		if(Math.abs(i) == 1 && Math.abs(j) == 1 && (piece == 'R' || piece == 'K')){
			return false;
		}

		if(piece == 'K' && (fileDiff > 1 || rankDiff > 1)){
			return false;
		}

		if(spaceArr[beginRank][beginFile].getPiece().getSymbol() != piece){
			return false;
		}

		Space currSpace = null;
		int currRank = beginRank;
		int currFile = beginFile;

		for(int p = 1; p < Math.max(fileDiff, rankDiff); p++){
			currRank += i;
			currFile += j;
			currSpace = spaceArr[currRank][currFile];
			if(currSpace.getPiece() != null){
				//there is a collision
				return false;
			}
		}

		return true;

	}

	private Space findKnight(int beginFile, int destFile, int destRank){
		int numPossibleKnights = 0;
		Knight kn = new Knight('w');
		Space result = null;
		for(int i = -2; i <= 2; i++){
			for(int j = -2; j <= 2; j++){
				if(i != 0 && j != 0
				&& !((Math.abs(i) == 1)
				&& (Math.abs(j) == 1))
				&& !(i%2 == 0 && j%2 == 0)
				&& destFile >= j*-1 && destFile <= 7-j
				&& destRank >= i*-1 && destRank <= 7-i){
				//i and j represent the offset from the destination horizontally
				//and vertically
					if(beginFile == -1 && spaceArr[destRank+i][destFile+j].getPiece() != null
					&& spaceArr[destRank+i][destFile+j].getPiece().getClass()
					== kn.getClass() &&
					spaceArr[destRank+i][destFile+j].getPiece().getTeam()
					== currBoard.getTurn()){
					//conditions: there is a piece, it is a knight, it is on the team
					//having a turn now
						numPossibleKnights++;
						result = spaceArr[destRank+i][destFile+j];
					}else if(destFile+j == beginFile && spaceArr[destRank+i][destFile+j].getPiece() != null
					&& spaceArr[destRank+i][destFile+j].getPiece().getClass()
					== kn.getClass() &&
					spaceArr[destRank+i][destFile+j].getPiece().getTeam()
					== currBoard.getTurn()){
						numPossibleKnights++;
						result = spaceArr[destRank+i][destFile+j];
					}
				}
			}
		}
		if(numPossibleKnights >= 2){
			return null;
		}

		return result;
	}


	private Space findBishop(int beginFile, int destFile, int destRank){

		//This method does not accommodate multiple possible bishops
		int numPossBishops = 0;
		Bishop bsh = new Bishop('w');
		Space result = null;
		int currRank = destRank;
		int currFile = destFile;
		boolean left = beginFile < destFile;
		int fileDiff = Math.abs(beginFile - destFile);
		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
			//i and j are only allowed to be 1 or -1
			//this is to simulate each of the four diagonals
			//ex: i,j = 1 would follow the up-right diagonal
			//and i=1, j=-1 would follow the up-left diagonal
				currRank = destRank;
				currFile = destFile;

				if(i!=0 && j!=0){
					while(currRank >= 0 && currRank <= 7
					&& currFile >= 0 && currFile <= 7){

						currRank+=i;
						currFile+=j;
						if(currRank == -1 || currRank == 8 || currFile == -1
							|| currFile == 8){break;}

						if(spaceArr[currRank][currFile].getPiece() != null
						&& spaceArr[currRank][currFile].getPiece().getClass()
						!= bsh.getClass()){
							//this case is a collision on the diagonal
							break;
						}

						if(beginFile == -1 && spaceArr[currRank][currFile].getPiece() != null
						&& spaceArr[currRank][currFile].getPiece().getClass()
						== bsh.getClass() &&
						spaceArr[currRank][currFile].getPiece().getTeam()
						== currBoard.getTurn()){
						//conditions: it is a piece, it is a bishop, its turn is
						//occurring now
							numPossBishops++;
							result = spaceArr[currRank][currFile];
						}else if(currFile == beginFile && spaceArr[currRank][currFile].getPiece() != null
						&& spaceArr[currRank][currFile].getPiece().getClass()
						== bsh.getClass() &&
						spaceArr[currRank][currFile].getPiece().getTeam()
						== currBoard.getTurn()){
							numPossBishops++;
							result = spaceArr[currRank][currFile];
						}
					}
				}
			}
		}

		if(numPossBishops >= 2){
			return null;
		}

		return result;

	}

	private Space findRook(int beginFile, int destFile, int destRank){

		int numPossRooks = 0;
		Rook rk = new Rook('w');
		Space result = null;
		int currRank = destRank;
		int currFile = destFile;
		boolean vert = beginFile == destFile;
		for(int i = -1; i <=1; i++){
			for(int j = -1; j <=1; j++){
				//reset currRank and currFile
				currRank = destRank;
				currFile = destFile;

				if(!(Math.abs(i) == 1 && Math.abs(j) == 1)
					&& !(i == 0 && j == 0)){
					while(currRank >= 0 && currRank <= 7
						&& currFile >= 0 && currFile <= 7){
						currRank += i;
						currFile += j;

						if(currRank == -1 || currRank == 8 || currFile == -1
							|| currFile == 8){break;}

						if(spaceArr[currRank][currFile].getPiece() != null
							&& spaceArr[currRank][currFile].getPiece().getClass()
							!= rk.getClass()){
							//this case is a collision between possible rook and destination
							break;
						}

						if(beginFile == -1 && spaceArr[currRank][currFile].getPiece() != null
							&& spaceArr[currRank][currFile].getPiece().getClass()
							== rk.getClass() &&
							spaceArr[currRank][currFile].getPiece().getTeam()
							== currBoard.getTurn()){
							//conditions: is a piece, is a rook, has a turn
							numPossRooks++;
							result = spaceArr[currRank][currFile];
						}else if(currFile == beginFile && spaceArr[currRank][currFile].getPiece() != null
							&& spaceArr[currRank][currFile].getPiece().getClass()
							== rk.getClass() &&
							spaceArr[currRank][currFile].getPiece().getTeam()
							== currBoard.getTurn()){
							numPossRooks++;
							result = spaceArr[currRank][currFile];
						}
					}
				}
			}
		}

		if(numPossRooks >= 2){
			return null;
		}

		return result;
	}


	
	private Space findQueen(int beginFile, int destFile, int destRank){
		//This method does not accommodate multiple possible queens
		int numPossQueens = 0;
		Queen qn = new Queen('w');
		Space result = null;
		int currRank = destRank;
		int currFile = destFile;

		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
			//i and j are allowed to be 1, 0, and -1
			//this is to simulate each of the four diagonals
			//and the four straight lines
			//ex: i,j = 1 would follow the up-right diagonal
			//and i=1, j=-1 would follow the up-left diagonal
				currRank = destRank;
				currFile = destFile;

				while(!(i == 0 && j == 0) &&
				currRank >= 0 && currRank <= 7
				&& currFile >= 0 && currFile <= 7){

					currRank+=i;
					currFile+=j;
					if(currRank == -1 || currRank == 8 || currFile == -1
						|| currFile == 8){break;}

					if(spaceArr[currRank][currFile].getPiece() != null
					&& spaceArr[currRank][currFile].getPiece().getClass()
					!= qn.getClass()){
						//this case is a collision on the diagonal
						break;
					}

					if(beginFile == -1 && spaceArr[currRank][currFile].getPiece() != null
					&& spaceArr[currRank][currFile].getPiece().getClass()
					== qn.getClass() &&
					spaceArr[currRank][currFile].getPiece().getTeam()
					== currBoard.getTurn()){
					//conditions: it is a piece, it is a queen, its turn is
					//occurring now
						numPossQueens++;
						result = spaceArr[currRank][currFile];
					}else if(currFile == beginFile && spaceArr[currRank][currFile].getPiece() != null
					&& spaceArr[currRank][currFile].getPiece().getClass()
					== qn.getClass() &&
					spaceArr[currRank][currFile].getPiece().getTeam()
					== currBoard.getTurn()){
						numPossQueens++;
						result = spaceArr[currRank][currFile];
					}
				}
			}
		}

		if(numPossQueens >= 2){
			return null;
		}

		return result;

	}
	
	private Space findKing(int beginFile, int destFile, int destRank){
		//This method does not accommodate multiple possible queens
		int numPossKings = 0;
		King kng = new King('w');
		Space result = null;
		int currRank = destRank;
		int currFile = destFile;

		for(int i = -1; i <= 1; i++){
			for(int j = -1; j <= 1; j++){
			//i and j are allowed to be 1, 0, and -1
			//this is to simulate each of the four diagonals
			//and the four straight lines
			//ex: i,j = 1 would follow the up-right diagonal
			//and i=1, j=-1 would follow the up-left diagonal
				currRank = destRank;
				currFile = destFile;

				if(!(i == 0 && j == 0) &&
				currRank >= 0 && currRank <= 7
				&& currFile >= 0 && currFile <= 7){

					currRank+=i;
					currFile+=j;
					if(currRank == -1 || currRank == 8 || currFile == -1
						|| currFile == 8){break;}

					if(spaceArr[currRank][currFile].getPiece() != null
					&& spaceArr[currRank][currFile].getPiece().getClass()
					== kng.getClass() &&
					spaceArr[currRank][currFile].getPiece().getTeam()
					== currBoard.getTurn()){
					//conditions: it is a piece, it is a queen, its turn is
					//occurring now
						numPossKings++;
						result = spaceArr[currRank][currFile];
					}
				}
			}
		}

		if(numPossKings >= 2){
			return null;
		}

		return result;
	}

}

