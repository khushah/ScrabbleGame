# Scrabble Best Word Selector

-   Every letter has an associated score and the score of a word is comouted as the sum of the scores of its constituent letters.
-   given a rack of 7 letters, suggests the word with the best score.

Algorithm used

-   From the list of words in the dictionary, generate a Hash Map wherein key value is the sorted sequence of letters in that       word and value is a list of anagrams.
-   Out of all the characters in the rack, compute a power set.
   (Power set is set of all possible combinations of the letters on the rack).
-   Sort the power set in the descending order of their length.
-   Compare the power set with the keys in Hash Map to find the list of possible words.
-   Find the word with maximum score.


