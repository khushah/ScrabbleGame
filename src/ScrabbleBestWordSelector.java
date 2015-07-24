import java.util.Arrays;
import java.io.BufferedReader;
import java.io.*;
import java.util.*;
/**
 * Created by test on 7/23/2015.
 */
public class ScrabbleBestWordSelector {
    private Map<String, String> AnagramCollection = new HashMap<String, String>();

    public static void main(String[] args) {
        ScrabbleBestWordSelector bestWordSelector = new ScrabbleBestWordSelector();
        bestWordSelector.populateDictionary(args[0]);
        System.out.println(bestWordSelector.findBestScore(args[1]));
    }

    public void populateDictionary(String path){
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(path));
            while ((sCurrentLine = br.readLine()) != null) {
                this.addToHash(sCurrentLine);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (br != null)br.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public String sortWord(String originalWord) {
        char[] chars = originalWord.toCharArray();
        Arrays.sort(chars);
        String sortedWord = new String(chars);
        return sortedWord;
    }

    public void addToHash(String word){
        String sortedWord = sortWord(word);
        if (AnagramCollection.containsKey(sortedWord)) {
            AnagramCollection.put(sortedWord, AnagramCollection.get(sortedWord) + " " + word);
        } else {
            AnagramCollection.put(sortedWord, word);
        }
    }

    public int findWordScore(String word) {
        int score = 0;
        char[] wordLetters = word.toCharArray();
        for (int pos = 0; pos < wordLetters.length; pos++){
            score += wordLetters[pos] - 'a' + 1;
        }
        return score;
    }

    public static <Integer> Set<Set<Integer>> powerSet(Set<Integer> originalSet) {
        Set<Set<Integer>> sets = new HashSet<Set<Integer>>();
        if (originalSet.isEmpty()) {
            sets.add(new HashSet<Integer>());
            return sets;
        }
        List<Integer> list = new ArrayList<Integer>(originalSet);
        Integer head = list.get(0);
        Set<Integer> rest = new HashSet<Integer>(list.subList(1, list.size()));
        for (Set<Integer> set : powerSet(rest)) {
            Set<Integer> newSet = new HashSet<Integer>();
            newSet.add(head);
            newSet.addAll(set);
            sets.add(newSet);
            sets.add(set);
        }
        return sets;
    }
    
    

    public String findBestScore(String rackAsString) {
        char[] rackLetters = rackAsString.toCharArray();
        Set<Integer> rackLetterSet = new HashSet<Integer>();
        int maxScore = 0;
        String maxScoreWord = "";
        for (int idx = 0; idx < rackLetters.length; idx++) {
            rackLetterSet.add(idx);
        }
        for (Set<Integer> s : powerSet(rackLetterSet)) {
            String rackSubset = "";
            for (Integer idx: s) {
                rackSubset += rackLetters[idx];
                String sortedWord = sortWord(rackSubset);
                if (AnagramCollection.containsKey(sortedWord)) {
                    int newScore = findWordScore(sortedWord);
                    if (maxScore < newScore) {
                        maxScore = newScore;
                        maxScoreWord = sortedWord;
                    }
                }
            }
        }
        if (maxScoreWord == ""){
            return "No possibilities";
        }
        else {
            return maxScore + " " + AnagramCollection.get(maxScoreWord);
        }
    }
}