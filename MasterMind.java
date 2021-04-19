import java.util.ArrayList;
import java.util.List;

/**
 * From Cracking the Coding Interview
 *  Computer has 4 slots R, G, B, Y
 *  User tries to guess the solution, if the character at and index matches it 
 *  is a "hit", but if the color exists in another slot it is a "pseudohit"
 * Solution RGBY, guess GGRR = 1 hit, 1 pseudohit
 */
class MasterMind {

  char[] solution = new char[] {'R', 'G', 'B', 'Y'};

  public Hits calculateAllHits(char[] guess) {
    List<Character> availableCharacters = new ArrayList<>();
    List<Integer> availableIndexes = new ArrayList<>();
    int hits = 0;
    
    for (int i = 0; i < solution.length; i++) {
      if (solution[i] == guess[i]) {
        hits++;
      } else {
        availableCharacters.add(solution[i]);
        availableIndexes.add(i);
      }
    }

    int pseudohits = 0;
    for (int index : availableIndexes) {
      Character g = guess[index];
      if (availableCharacters.contains(g)) {
        pseudohits++;
        availableCharacters.remove(g);
      }
    } 

    return new Hits(hits, pseudohits);
  }

  class Hits {
    int hits;
    int pseudohits;

    public Hits(int hits, int pseudohits) {
      this.hits = hits;
      this.pseudohits = pseudohits;
    }
  }
}
