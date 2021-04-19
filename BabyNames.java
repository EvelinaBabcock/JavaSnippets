import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.Optional;

/**
 * Problem from Cracking the Coding Interview
 * Given a list of name counts + a list of pairs of synonyms, generate a list of "true" counts
 * using counts of all synonyms. Any synonym name can be used.
 *  e.g. John (15), Jon(12), Chris (13), Kris (4), Christopher (19)
 *  Syn: (Jon, John), (John, Johnny), (Chris, Kris), (Chris, Christopher)
 *  Res: John (27), Kris (36)
 */
class BabyNames {

  private Map<String, Integer> nameCountMap;
  private Map<String, Set<String>> synonymMap;

  public BabyNames() {
    nameCountMap = new HashMap<>();
    synonymMap = new HashMap<>();
  }

  // Name count file in format - name=count
  // Name synonym file in format - name,synonym
  public void init(String nameCountFile, String nameSynonymFile) {
    readInNameCounts(nameCountFile);
    readInSynonyms(nameSynonymFile);
  }

  private void readInNameCounts(String fileName) throws Exception {
    File f = new File(fileName);
    BufferedReader buffer = new BufferedReader(new FileReader(f));
    String line;
    while ((line = buffer.readLine()) != null) {
      String[] parts = line.trim().split("=");
      nameCountMap.put(parts[0], Integer.parseInt(parts[1]));
    }
    buffer.close();
  }

  /*
   * Read in synonyms, handling special cases:
   *  - If both keys already exist, merge the sets + update all related keys' val ref 
   *  - If only the first or second key exists, add the other key as a synonym
   *  - If neither exist, create a new synonym set and add boths keys
   * Put both keys if absent with the synonym set
   */
  private void readInSynonyms(String fileName) throws Exception {
    File f = new File(fileName);
    BufferedReader buffer = new BufferedReader(new FileReader(f));
    String line;
    while ((line = buffer.readLine()) != null) {
      String[] parts = line.trim().split(",");
      Set<String> synonyms;
      if (synonymMap.containsKey(parts[0]) && synonymMap.containsKey(parts[1])) {
        mergeSets(parts[0], parts[1]);
        continue;          
      } else if (synonymMap.containsKey(parts[0])) {
        synonyms = synonymMap.get(parts[0]);
        synonyms.add(parts[1]);
      } else if (synonymMap.containsKey(parts[1])) {
        synonyms = synonymMap.get(parts[1]);
        synonyms.add(parts[0]);
      } else {
        synonyms = new HashSet<>();
        synonyms.add(parts[0]);
        synonyms.add(parts[1]);
      }
      synonymMap.putIfAbsent(parts[0], synonyms);
      synonymMap.putIfAbsent(parts[1], synonyms);
    }
    buffer.close();
  }

  private void mergeSets(String key1, String key2) {
    Set<String> synonyms1 = synonymMap.get(key1);
    Set<String> synonyms2 = synonymMap.get(key2);
    synonyms1.addAll(synonyms2);
    for (String name : synonyms1) {
      synonymMap.put(name, synonyms1);
    } 
  }

  /*
   * Get real counts - aggregated synonym counts
   *  - If name as no synonyms, just add the count
   *  - If name has synonyms - use the first entry in the set as the aggregate
   *    name, and put/increment count 
   */
  public void getRealNameCounts() throws Exception {
    Map<String, Integer> realCounts = new HashMap<>();
    for (Map.Entry<String, Integer> entry : nameCountMap.entrySet()) {
      Set<String> names = synonymMap.get(entry.getKey());
      if (names != null) { 
        Optional<String> realName = names.stream().filter(s -> realCounts.containsKey(s)).findFirst();
        if (realName.isPresent()) {
          realCounts.put(realName.get(), realCounts.get(realName.get()) + entry.getValue());
        } else {
          realCounts.put(entry.getKey(), entry.getValue());
        }
      } else {
        realCounts.put(entry.getKey(), entry.getValue());  
      }
    }

    for (Map.Entry<String, Integer> entry : realCounts.entrySet()) {
      System.out.println(String.format("(%s) (%d)", entry.getKey(), entry.getValue()));
    }
  }
}
