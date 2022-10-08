import java.util.*;
import java.lang.*;

public class EfficientMarkovWord implements IMarkovModel{
    String[] myText;
    Random myRandom;
    int myOrder;
    private HashMap<Integer, ArrayList<String>> mp;
    
    public EfficientMarkovWord(int order) {
        mp = new HashMap<Integer, ArrayList<String>>();
        myRandom = new Random();
        myOrder = order;
    }
    
    public void setTraining(String text) {
        myText = text.split("\\s+");
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public void buildMap() {
        
        int i = 0;
        WordGram key = null;
        for (i = 0; i < myText.length-myOrder; ++i) {
            key = new WordGram(myText, i, myOrder);
            ArrayList<String> follows = new ArrayList<String>();
            if (mp.containsKey(key.hashCode())) follows = mp.get(key.hashCode());
            follows.add(myText[i + myOrder]);
            mp.put(key.hashCode(), follows);
        }
        key = new WordGram(myText, i, myOrder); 
        mp.put(key.hashCode(), new ArrayList<String>());
    }
    
    public String getRandomText(int numWords) {
    StringBuilder sb = new StringBuilder();
    int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
    WordGram key = new WordGram(myText, index, myOrder); 
    sb.append(key);
    sb.append(" ");
    
    for(int k=0; k < numWords-myOrder; k++){
        ArrayList<String> follows = getFollows(key);
        if (follows.size() == 0) {
            break;
        }
        index = myRandom.nextInt(follows.size());
        String next = follows.get(index);
        sb.append(next);
        sb.append(" ");
        key.shiftAdd(next);
    }
        
    return sb.toString().trim();
    }
    
    public ArrayList<String> getFollows(WordGram kGram) {
        return mp.get(kGram.hashCode());
    }
    
    public int indexOf(String[] words, WordGram target, int start) {
        for (int i = start; i < words.length-myOrder; ++i) {
            WordGram temp = new WordGram(words, i, myOrder);
            if (temp.equals(target)) return i;
        }
        return -1;
    }
    
    public void printHashMapInfo() {
        System.out.println("Number of keys in the map are: " + mp.size());
        int maxVal = 0;
        for (int x : mp.keySet()) {
            maxVal = Math.max(maxVal, mp.get(x).size());
        }
        System.out.println("The maximum number of keys following a key is: " + maxVal);
        System.out.println();
    }
}
