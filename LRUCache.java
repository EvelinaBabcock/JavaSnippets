import java.util.Map;
import java.util.HashMap;
import java.lang.StringBuilder;

class LRUCache<T> {
  private static int max_entries;

  private Map<String, ValueNode<T>> valueMap;
  private ValueNode<T> head;
  private ValueNode<T> tail;

  private int count = 0;

  public LRUCache(int max_entries) {
    this.max_entries = max_entries;
    this.valueMap = new HashMap<>();
  }

  public void insert(String key, T value) {
    if (valueMap.containsKey(key)) {
      reinsertExisting(key, value);
    } else {
      insertNew(key, value);
    }
  }

  // Reinserts an existing value to the beginning of our list
  // taking special care if the the value is already at the head,
  // if the existing next value was set, or if it was the tail
  private void reinsertExisting(String key, T value) {
    ValueNode n = valueMap.get(key);
    n.setValue(value);
    if (!head.equals(n)) {
      n.getPrev().setNext(n.getNext());
      if (n.getNext() != null) {
        n.getNext().setPrev(n.getPrev());
      }
      head.setPrev(n);
      if (n.equals(tail)) {
        tail = n.getPrev();
      }
      n.setNext(head);
      n.setPrev(null);
      head = n;
    }
  }

  // Inserts new value, evicting the LRU value if we are at capacity
  private void insertNew(String key, T value) {
    if (count == max_entries) {
      tail.getPrev().setNext(null);
      valueMap.remove(tail.getKey());
    } else {
      count++;
    }

    ValueNode<T> node = new ValueNode(value, key);
    valueMap.put(key, node);
    if (head != null) {
      head.setPrev(node);
      node.setNext(head);
    }
    head = node;
    if (tail == null) {
      tail = node;
    }
  }

  // Gets a value if it exists in the map, reinserting it if it does
  public T get(String key) {
    if (valueMap.containsKey(key)) {
      insert(key, valueMap.get(key).getValue());
      return valueMap.get(key).getValue();
    } 
    return null;
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    ValueNode n = head;
    while (n != null) {
      sb.append(n.getKey() + " ");
      n = n.getNext();
    }
    return sb.toString();
  }

  private class ValueNode<T> {
    private ValueNode<T> prev;
    private ValueNode<T> next;

    private T value;
    private String key;

    public ValueNode(
      T value,
      String key
    ) {
      this.value = value;
      this.key = key;
    }

    String getKey() {
      return this.key;
    }

    ValueNode<T> getPrev() {
      return prev;
    }

    ValueNode<T> getNext() {
      return next;
    }

    void setPrev(ValueNode prev) {
      this.prev = prev;
    }

    void setNext(ValueNode next) {
      this.next = next;
    }

    void setValue(T value) {
      this.value = value;
    }

    T getValue() {
      return this.value;
    }
  }
}
