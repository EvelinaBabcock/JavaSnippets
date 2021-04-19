import java.util.Map;
import java.util.HashMap;
import java.util.HashSet;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

/**
 * Given a list of valid words + a sequence of digits, implement an algorithm
 * that return a list of valid words those digits can make.
 */
class T9 {

  private HashSet<String> validWords;
  private Map<Integer, List<Character>> numToCharsMap;

  public T9(HashSet<String> words) {
    this.validWords = words;
    this.numToCharsMap = populateNumToCharsMap();
  }

  private Map<Integer, List<Character>> populateNumToCharsMap() {
    Map<Integer, List<Character>> numToCharsMap = new HashMap<>();
    numToCharsMap.put(0, new ArrayList<>());
    numToCharsMap.put(1, new ArrayList<>());
    numToCharsMap.put(2, Arrays.asList('a', 'b', 'c'));
    numToCharsMap.put(3, Arrays.asList('d', 'e', 'f'));
    numToCharsMap.put(4, Arrays.asList('g', 'h', 'i'));
    numToCharsMap.put(5, Arrays.asList('j', 'k', 'l'));
    numToCharsMap.put(6, Arrays.asList('m', 'n', 'o'));
    numToCharsMap.put(7, Arrays.asList('p', 'q', 'r', 's'));
    numToCharsMap.put(8, Arrays.asList('t', 'u', 'v'));
    numToCharsMap.put(9, Arrays.asList('w', 'x', 'y', 'z'));
    return numToCharsMap;
  }

  // Recursively build full strings, check if valid, then append
  public List<String> validWords(int input) throws Exception {
    List<String> validStrings = new ArrayList<>();
    getValidStrings(validStrings, "", Integer.toString(input));
    return validStrings;
  }

  private void getValidStrings(List<String> words, String prefix, String remainingStr) throws Exception {
    List<Character> chars = numToCharsMap.get(Integer.parseInt(remainingStr.substring(0, 1)));

    for (Character c : chars) {
      if (remainingStr.length() == 1) {
        if (validWords.contains(prefix + Character.toString(c)) {
          words.add(prefix + Character.toString(c));
        }
      } else {
        getValidStrings(words, prefix + Character.toString(c), remainingStr.substring(1));
      }
    }
  }
}
