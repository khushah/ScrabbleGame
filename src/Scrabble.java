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

    public String findBest(String word) {
        char[] letters = word.toCharArray();
        int maxScore = 0;
        String maxScoreWord = "";
        word = sortString(word);
        ArrayList<Integer> spaceIndices = new ArrayList();
        int spaces = 0;
        for (int pos = 0; pos < letters.length; pos++){
            if (letters[pos] == ' '){
                spaceIndices.add(pos);
                spaces++;
            }
        }

        if (spaces == 0) {
            maxScoreWord = findBestScore(word);
            maxScore = findWordScore(maxScoreWord);
        }

        else if(spaces == 1){
            for (int charIdx1 = 0; charIdx1 < 26; charIdx1++) {
                letters[spaceIndices.get(0)] = (char) (97 + charIdx1);
                String newWord = new String(letters);
                //System.out.println(newWord);
                newWord = findBestScore(newWord);
                int newScore = findWordScore(newWord);
                if (newScore > maxScore) {
                    maxScore =  newScore;
                    maxScoreWord = newWord;
                }
            }
        }

        else {
            for (int charIdx1 = 0; charIdx1 < 26; charIdx1++) {
                for (int charIdx2 = 0; charIdx2 < 26; charIdx2++) {
                    letters[spaceIndices.get(0)] = (char) (97 + charIdx1);
                    letters[spaceIndices.get(1)] = (char) (97 + charIdx2);
                    String newWord = new String(letters);
                    newWord = findBestScore(newWord);
                    int newScore = findWordScore(newWord);
                    if (newScore > maxScore) {
                        maxScore = newScore;
                        maxScoreWord = newWord;
                    }
                }
            }
        }
        if (maxScoreWord == ""){
            return "No Possibilities";
        }
        return maxScore + " : " + map.get(maxScoreWord);
    }


    public ArrayList<String> generate(String word) {
        ArrayList<String> outputs = new ArrayList<String>();
        if (word.length() == 1) {
            outputs.add(word);
        }else{
            outputs.add(word);
            generate(word.substring(0, word.length()-1));
            generate(word.substring(1, word.length()));
        }
        return outputs;
    }

    public String findBestScore(String word) {
        int maxScore = 0;
        String maxScoreWord = "";
        for (String wordSubset : generate(word)) {
            wordSubset = sortString(wordSubset);
            if (map.containsKey(wordSubset)) {
                int newScore = findWordScore(wordSubset);
                if (maxScore < newScore) {
                    maxScore = newScore;
                    maxScoreWord = wordSubset;
                }
            }
        }
        return maxScoreWord;
    }

    public static void main(String[] args) {
        Scrabble scrabble = new Scrabble();
        scrabble.populateDictionary("C:\\sowpods.txt");
        System.out.println(scrabble.findBest("c t "));
    }
}