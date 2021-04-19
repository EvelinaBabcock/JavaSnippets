import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashSet;
import java.util.Set;

/**
 * Class that loads a file of key value pairs, de-dupes duplicate entries
 * and sorts them by value.
 *
 * charles=3918
 * alice=0
 * bob=100
 * charles=3918
 * django=77
 * alice=0 
 *
 * expected to output
 *  alice=0
 *  django=77
 *  bob=100
 *  charles=3918
 *
 * ?s:
 *  - Values all integers?
 *  - Dedupe lines, what about dupe keys with diff values?
 */
class DedupeSorter {

  private Set<LogEntry> loadAndDedupe(String file) throws Exception {
    File f = new File(file);
    BufferedReader buf = new BufferedReader(new FileReader(f));
    String line;
    Set<LogEntry> entries = new HashSet<>();
    while ((line = buf.readLine()) != null) {
      String[] parts = line.trim().split("=");
      entries.add(new LogEntry(parts[0], Integer.parseInt(parts[1])));
    }
    buf.close();
    return entries;
  }

  public void process(String file) throws Exception {
    Set<LogEntry> entries = loadAndDedupe(file);
    entries.stream().sorted().forEach(System.out::println);
  }

  // TODO - needs to use generics for different value types?
  private class LogEntry implements Comparable<LogEntry> {
    private String key;
    private int value;

    public LogEntry(String key, int value) {
      this.key = key;
      this.value = value;
    }

    @Override
    public int hashCode() {
      // Googled this :P
      final int prime = 31;
      int result = 1;
      result = prime * result + value;
      result = prime * result + ((key == null) ? 0 : key.hashCode());
      return result;
    }

    @Override
    public int compareTo(LogEntry other) {
      if (this.value > other.value) {
        return 1;
      } else if (this.value < other.value) {
        return -1;
      }
      return 0;
    }

    @Override
    public String toString() {
      return String.format("%s=%s", key, value);
    }
  }
}
