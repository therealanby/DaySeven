import java.io.*;
import java.util.*;

class Main {
  public static void main(String[] args) {
     HashMap<String, HashMap<String, Integer>> hM = getMap();
    //int x = ans();
    int y = insideGold(hM,hM.get("shinygold"), 0,1);
    System.out.println(y);
    
  }
  public static int insideGold(HashMap org, HashMap hm, int count, int mult){
    Set<String> kS = hm.keySet();
    for(String s:kS){
      if(org.get(s)==null){
        return count;
      }
      if(s.equals("noother")){
        return count;
      }
      mult = mult*(Integer)hm.get(s);
      count=insideGold(org,(HashMap)org.get(s),count,mult);
      count+=mult;
      mult = (int)mult/(Integer)hm.get(s);
    }
    return count;
  }
  public static Set<String> findGold(HashMap org, HashMap hm, Set<String> hs, Set<String> global){
    Set<String> glob = global;
    Set<String> hS = hs;
    Set<String> kS = hm.keySet();
    for(String s:kS){
      //System.out.println(s);
      if(s.equals("noother")){
        return glob;
      }
      if(s.equals("shinygold")){
        glob.addAll(hs);
        continue;
      }
      if(org.get(s)==null){
        return glob;
      }else{
        hS.add(s);
        glob.addAll(findGold(org,(HashMap)org.get(s),hS, glob));
      }
      hS.remove(s);
    }
    return glob;
  }

  public static HashMap<String, HashMap<String, Integer>> getMap(){
    HashMap<String, HashMap<String, Integer>> hm = new HashMap<String,HashMap<String,Integer>>();

    try(Scanner sc = new Scanner(new File("input.txt"))){
      while(sc.hasNextLine()){
        String str = sc.nextLine().replaceAll(" |bag[s]*|\\.|,", "");
        String[] splitArr = str.split("contain");

        HashMap<String, Integer> tmp = new HashMap<String, Integer>();
        if(splitArr[1].equals("noother")){
          tmp.put("noother",0);
          hm.put(splitArr[0], tmp);
          continue;
        }
        int first = -1;
        StringBuilder sb = new StringBuilder();
        for(char c:splitArr[1].toCharArray()){
          if(first ==-1&&c<=57&&c>=49){
            first = Character.getNumericValue(c);
          }else if(c<=57&&c>=49){
            tmp.put(sb.toString(),first);
            sb = new StringBuilder();
            first = Character.getNumericValue(c);
          }else{
            sb.append(c);
          } 
        }
        tmp.put(sb.toString(),first);
        hm.put(splitArr[0],tmp);
      }
    }catch(FileNotFoundException x){}
    return hm;
  }
  public static int ans(){
    HashMap<String, HashMap<String, Integer>> hM =  getMap();
    return findGold(hM, hM, new HashSet<String>(), new HashSet<String>()).size();
  }

}