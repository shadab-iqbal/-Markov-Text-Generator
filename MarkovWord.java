import java.util.*;
import java.lang.*;

public class MarkovWord implements IMarkovModel{
    String[] myText;
    Random myRandom;
    int myOrder;
    
    public MarkovWord(int order) {
        myRandom = new Random();
        myOrder = order;
    }
    
    public void setTraining(String text) {
        myText = text.split("\\s+");
    }
    
    public void setRandom(int seed) {
        myRandom = new Random(seed);
    }
    
    public String getRandomText(int numWords) {
    StringBuilder sb = new StringBuilder();
    int index = myRandom.nextInt(myText.length-myOrder);  // random word to start with
    WordGram key = new WordGram(myText, index, myOrder); 
    sb.append(key);
    sb.append(" ");
    
    for(int k=0; k < numWords-myOrder; k++){
        ArrayList<String> follows = getFollows(key);
        // System.out.println(key + " " + follows);
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
        ArrayList<String> follows = new ArrayList<String>();
        int start = 0;
        while (indexOf(myText, kGram, start) != -1) {
            int idx = indexOf(myText, kGram, start);
            follows.add(myText[idx + myOrder]);
            start = idx+1;
        }
        return follows;
    }
    
    public int indexOf(String[] words, WordGram target, int start) {
        for (int i = start; i < words.length-myOrder; ++i) {
            WordGram temp = new WordGram(words, i, myOrder);
            if (temp.equals(target)) return i;
        }
        return -1;
    }
    
    public void buildMap(){
        // System.out.println("OK");
    }
}
