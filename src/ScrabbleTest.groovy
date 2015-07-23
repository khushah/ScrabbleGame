/**
 * Created by test on 7/23/2015.
 */
class ScrabbleTest extends groovy.util.GroovyTestCase {
    void testSortString() {
        Scrabble scrabble = new Scrabble();
        assert(scrabble.sortString("bcda")) == "abcd";
        assert(scrabble.sortString("xyz")) == "xyz";
        assert(scrabble.sortString("a")) == "a";
        assert(scrabble.sortString("")) == "";
    }


    void testFindWordScore() {

    }

    void testGenerate() {
        Scrabble scrabble = new Scrabble();
        ArrayList<String> subsets = new ArrayList();
        subsets.add('a');
        subsets.add('b');
        subsets.add('c');
        subsets.add('ab');
        subsets.add('ac');
        subsets.add('bc');
        subsets.add('abc');
        assert Arrays.equals(subsets, scrabble.generate("abc"))
    }

    void testFindBestScore() {

    }

    void testMain() {

    }
}
