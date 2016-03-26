import java.util.*;

public class MoveRecorder{

  private String currentGame = "";
  private int numMoves = 0;
  private Board currBoard;

  public MoveRecorder(Board b){
    this.currBoard = b;
  }

  //if for some reason need to specify ivars
  public MoveRecorder(String s, int numMoves){
    currentGame = s;
    this.numMoves = numMoves;
  }

  public MoveRecorder(MoveRecorder mr){
    //copy constructor
    //I think since it's a String and int, no references to worry about
    this.currentGame = mr.currentGame;
    this.numMoves = mr.numMoves;
    //eventually should implement a deep copy of the Board
  }


  //records the move internally in currentGame string
  public void recordMove(String s){
    //recorded AFTER the move
    if(currBoard.getTurn() == 'b'){
      numMoves++;
      currentGame += numMoves + ". ";
      currentGame += s + ", ";
    }else{
      currentGame += s + " ";
    }
  }

  public String getMoveString(){
    return currentGame;
  }

  //should not be used under typical circumstances
  public void setMoveString(String s){
    currentGame = s;
  }
}
