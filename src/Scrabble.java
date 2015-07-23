import java.util.Arrays;
import java.io.BufferedReader;
import java.io.*;
import java.util.*;
/**
 * Created by test on 7/23/2015.
 */
public class Scrabble {
    private Map<String, String> map = new HashMap<String, String>();

    public String sortString(String original) {
        char[] chars = original.toCharArray();
        Arrays.sort(chars);
        String sorted = new String(chars);
        return sorted;
    }

    public void addToHash(String word){
        String sortedWord = sortString(word);
        if (map.containsKey(sortedWord)) {
            map.put(sortedWord, map.get(sortedWord) + " " + word);
        } else {
            map.put(sortedWord, word);
        }
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

    public String findBestScore(String letterString) {
        char[] letters = letterString.toCharArray();
        Set<Integer> mySet = new HashSet<Integer>();
        int maxScore = 0;
        String maxScoreWord = "";
        for (int idx = 0; idx < letters.length; idx++) {
            mySet.add(idx);
        }
        for (Set<Integer> s : powerSet(mySet)) {
            String subset = "";
            for (Integer idx: s) {
                subset += letters[idx];
                String sortedString = sortString(subset);
                if (map.containsKey(sortedString)) {
                    int newScore = findWordScore(sortedString);
                    if (maxScore < newScore) {
                        maxScore = newScore;
                        maxScoreWord = sortedString;
                    }
                }
            }
        }
        if (maxScoreWord == ""){
            return "No possibilities";
        }
        else {
            return maxScore + " " + map.get(maxScoreWord);
        }
    }


    public static void main(String[] args) {
        Scrabble scrabble = new Scrabble();
        scrabble.populateDictionary("C:\\sowpods.txt");
        System.out.println(scrabble.findBestScore("quizzer"));
    }
}