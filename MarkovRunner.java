import edu.duke.*;
import java.util.*;

public class MarkovRunner {
    public void runModel(IMarkovModel markov, String text, int size){ 
        markov.setTraining(text); 
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            String st = markov.getRandomText(size); 
            printOut(st); 
        } 
    } 

    public void runModel(IMarkovModel markov, String text, int size, int seed){ 
        markov.setTraining(text); 
        markov.setRandom(seed);
        markov.buildMap(); 
        
        System.out.println("running with " + markov); 
        for(int k=0; k < 3; k++){ 
            markov.setRandom(seed);
            String st = markov.getRandomText(size); 
            printOut(st); 
        }
        
    } 

    public void runMarkov() { 
    	Scanner sc = new Scanner(System.in);
        FileResource fr = new FileResource(); 
        String st = fr.asString(); 
        st = st.replace('\n', ' '); 
        System.out.println("Please select the number of words based on which the next word will be generated");
        int model = sc.nextInt();
        EfficientMarkovWord mkv = new EfficientMarkovWord(model); 
        runModel(mkv, st, 500, 50); // dont change these parameters
    } 

    private void printOut(String s){
        String[] words = s.split("\\s+");
        int psize = 0;
        System.out.println("----------------------------------");
        for(int k=0; k < words.length; k++){
            System.out.print(words[k]+ " ");
            psize += words[k].length() + 1;
            if (psize > 60) {
                System.out.println(); 
                psize = 0;
            } 
        } 
        System.out.println("\n----------------------------------");
    } 
    
    public void testHashMap() {
        String st = "this is a test yes this is really a test yes a test this is wow"; 
        EfficientMarkovWord mkv = new EfficientMarkovWord(2); 
        runModel(mkv, st, 50, 42); 
    }

}
