/**
 * Created by test on 7/23/2015.
 */
class ScrabbleBestWordSelectorTest extends groovy.util.GroovyTestCase {
    void testSortString() {
        ScrabbleBestWordSelector selector= new ScrabbleBestWordSelector();
        assert(selector.sortWord("bcda")) == "abcd";
        assert(selector.sortWord("xyz")) == "xyz";
        assert(selector.sortWord("a")) == "a";
        assert(selector.sortWord("")) == "";
    }


    void testFindWordScore() {

    }

    void testGenerate() {

    }

    void testFindBestScore() {

    }

    void testMain() {

    }
}
