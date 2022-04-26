package org.study.coding.class12;

import java.util.HashMap;
import java.util.HashSet;

/**
 * @author phil
 * @date 2022-04-15 14:21:47
 */
public class MainTest03 {
    public static void main(String[] args) {
        System.out.println(new Code03_LongestConsecutive().longestConsecutive2(new int[]{-6, -5, -9, -7, -6, -7, -8}));
        System.out.println(new Code03_LongestConsecutive().longestConsecutive2(new int[]{1, 2, 3, 4, 5, 6, 7, 8}));
    }

    public static int longestConsecutive(int[] nums) {
        HashMap<Integer, Integer> headMap = new HashMap<>();
        HashMap<Integer, Integer> tailMap = new HashMap<>();
        HashSet<Integer> headSet = new HashSet<>();
        HashSet<Integer> tailSet = new HashSet<>();
        int max = 0;
        for (int num : nums) {
            if (!headMap.containsKey(num) && !headSet.contains(num)) {
                headMap.put(num, 1);
                headSet.add(num);
            }
            if (!tailMap.containsKey(num) && !tailSet.contains(num)) {
                tailMap.put(num, 1);
                tailSet.add(num);
            }
            max = Math.max(max, 1);

            // 2 3 4 5 6 7 8  num  10 11 12 13 14
            if (headMap.containsKey(num + 1) && tailMap.containsKey(num - 1)) {

                max = Math.max(max, tailMap.get(num - 1) + headMap.get(num + 1) + 1);
                headMap.remove(num + 1);
                tailMap.remove(num - 1);

                headMap.remove(num);
                tailMap.remove(num);
            }

            if (headMap.containsKey(num + 1)) {
                headMap.put(num, headMap.get(num + 1) + 1);
                tailMap.put(num + headMap.get(num + 1), headMap.get(num + 1) + 1);
                headMap.remove(num + 1);
                tailMap.remove(num);

                max = Math.max(max, headMap.get(num));
            }

            if (tailMap.containsKey(num - 1)) {

                headMap.put(num - tailMap.get(num - 1), tailMap.get(num - 1) + 1);
                tailMap.put(num, tailMap.get(num - 1) + 1);
                headMap.remove(num);
                tailMap.remove(num - 1);

                max = Math.max(max, tailMap.get(num));
            }
        }

        return max;
    }
}
