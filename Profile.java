import java.util.*;

public class Profile{

  private String name;
  private int gamesPlayed;
  private ArrayList<String> gameStrings;

  public Profile(String name){
    this.name = name;
    this.gameStrings = new ArrayList<String>();
  }

  public String getName(){
    return name;
  }

  public int numGamesPlayed(){
    return gamesPlayed;
  }

  public void addGame(String gameString){
    gameStrings.add(gameString);
    gamesPlayed++;
  }

  public String getGameAt(int index){
    if(index < 0 || index >= gameStrings.size()){
      System.out.println("Invalid game index.");
      return "";
    }
    return gameStrings.get(index);
  }

};
