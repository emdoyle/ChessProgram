import java.util.*;

public class ProfileHolder{

  private HashMap<String, Profile> profMap;

  public ProfileHolder(){
    profMap = new HashMap<String, Profile>();
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
