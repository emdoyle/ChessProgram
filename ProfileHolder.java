import java.util.*;
import java.io.*;

public class ProfileHolder{

  private HashMap<String, Profile> profMap;
  private String fileName;

  public ProfileHolder(String fileName){
    this.fileName = fileName;
    loadProfiles();
  }

  private void loadProfiles(){
    profMap = new HashMap<String, Profile>();
    try{
      FileInputStream fstream = new FileInputStream(fileName);
      DataInputStream in = new DataInputStream(fstream);
      BufferedReader read = new BufferedReader(new InputStreamReader(in));
      //it looks like this is the way to go from fileName -> reader
      //using just a FileReader + BufferedReader apparently is machine-
      //dependent (because of default encoding)
      String currStr, currName, currGame;
      currName = "";
      Profile currProf = new Profile("");
      //should be able to initialize to null to save space
      //but would rather not have exceptions thrown
      while((currStr = read.readLine()) != null){
        if(currStr.startsWith("Name:")){
	  currName = currStr.substring(5);
	  //always index 5 because 'Name:' has five characters
	  currProf = new Profile(currName);
	}else if(currStr.startsWith("1.")){
	  currGame = currStr;
	  currProf.addGame(currGame);
          profMap.put(currName, currProf);
	}else{
	  break;
	}
      }
      read.close();
    }catch(Exception e){
      System.err.println("Could not open file: " + fileName);
    }
  }

  public void writeProfiles(){
    try{
      FileWriter fWrite = new FileWriter(fileName);
      BufferedWriter writer = new BufferedWriter(fWrite);

      Iterator it = profMap.entrySet().iterator();
      Profile currProf;
      ArrayList<String> gameStrings;
      while(it.hasNext()){
        Map.Entry pair = (Map.Entry)it.next();
        currProf = (Profile)pair.getValue();
        writer.write("Name:" + currProf.getName());
        writer.newLine();
	gameStrings = currProf.getGameList();
        for(int i = 0; i < gameStrings.size(); i++){
          writer.write(gameStrings.get(i));
	  writer.newLine();
        }
      }
      writer.close();
    }catch(Exception e){
      System.err.println("Could not write to file: " + fileName);
    }
  }

  public boolean contains(String name){
    return profMap.containsKey(name);
  }

  public Profile createProf(String name){
    if(contains(name)){
      System.out.println("Profile already exists!");
      return null;
    }
    Profile newProf = new Profile(name);
    profMap.put(name, newProf);
    return newProf;
  }

  public void deleteProf(String name){
    if(!contains(name)){
      System.out.println("Profile does not exist!");
    }
    profMap.remove(name);
  }

  public Profile getProf(String name){
    if(!contains(name)){
      System.out.println("Profile not found.");
      return null;
    }

    return profMap.get(name);
  }

};
