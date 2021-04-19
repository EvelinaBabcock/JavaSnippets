/**
 * Given an array of integers, write a method to find indices m an n 
 * such that if you sorted elements m through n, the entire array
 * would be sorted. Minimize n - m (smallest sequence).
 */
class SubSort {

  /*
   * Iterate, finding the minimum value that is less than the previous value
   * If we don't find any, the array is sorted and return -1.
   * Otherwise, find the latest index that would be <= the minimum value.
   */
  int findM(int[] input) {
    int minVal = 0;
    int minIdx = -1;
    for (int index = 0; index < input.length; index++) {
      int value = input[index];
      if (index + 1 < input.length) {
        int nextVal = input[index + 1];
        if (nextVal < value) {
          if (minIdx != -1 && minVal < nextVal) {
            continue;
          }
          minVal = nextVal;
          minIdx = index+1;
        }
      }
    }

    // Array is sorted!
    if (minIdx == -1) {
      return minIdx;
    }

    int tmpMinIdx = minIdx;
    for (int index = 0; index < minIdx; index++) {
      if (input[index] <= minVal) {
        tmpMinIdx = index+1;
      } else if (input[index] > minVal) {
        break;
      }
    }

    if (tmpMinIdx == minIdx) {
      return 0;
    }
    return tmpMinIdx;
  }

  /*
   * Starting at the idx after the start (m)
   *   check if current value is less than the max (increment idx)
   *   if current value is greater, then set the new max
   */
  int findN(int[] input, int startIdx) {
    int startValue = input[startIdx];
    int max = startValue;
    int endIdx = input.length-1;
    for (int index = startIdx+1; index < input.length; index++) {
      int currentVal = input[index];
      if (currentVal < max) {
        endIdx = index;
      }
      if (currentVal > max) {
        max = currentVal;
      }
    }
    return endIdx;
  }

  public void findMinSortIndexes(int[] input) {
    if (input == null || input.length == 0) {
      System.out.println("Empty list"); 
    } else {
      int m = findM(input);
      if (m == -1) {
        System.out.println("Already sorted!");
      } else {
        int n = findN(input, m);
        System.out.println(String.format("(%d, %d)", m, n));
      }
    }
  }
}
