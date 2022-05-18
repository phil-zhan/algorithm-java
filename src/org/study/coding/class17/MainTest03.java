package org.study.coding.class17;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class MainTest03 {
    public static void main(String[] args) {
        System.out.println(new MainTest03().palindromePairs(new String[]{"abcd", "dcba", "lls", "s", "sssll"}));
    }


    public List<List<Integer>> palindromePairs(String[] words) {
        if (words == null || words.length == 0) {
            return new ArrayList<>();
        }

        HashMap<String, Integer> hashMap = new HashMap<>();
        for (int i = 0; i < words.length; i++) {
            hashMap.put(words[i], i);
        }

        List<List<Integer>> res = new ArrayList<>();

        for (int i = 0; i < words.length; i++) {
            res.addAll(findAll(hashMap, words[i], i));
        }
        return res;
    }

    public List<List<Integer>> findAll(HashMap<String, Integer> hashMap, String word, Integer index) {
        List<List<Integer>> res = new ArrayList<>();
        int len = word.length();
        String reverse = reverse(word);
        Integer rest = hashMap.get("");

        if (rest != null && rest != index && reverse.equals(word)) {
            addRecord(res, rest, index);
            addRecord(res, index, rest);
        }
        int[] radius = manacher(word);

        // #a#b#c#d#c#b#a# e#f#g#
        int mid = radius.length >> 1;

        for (int i = 1; i < mid; i++) {
            if (i - radius[i] == -1) {
                // abcdcba efg
                // gfe abcdcba
                rest = hashMap.get(reverse.substring(0,mid - i));
                if (rest != null && rest != index) {
                    addRecord(res, rest, index);
                }
            }
        }

        for (int i = mid + 1; i < radius.length; i++) {
            if (i + radius[i] == radius.length) {
                rest = hashMap.get(reverse.substring((mid << 1) - i));
                if (rest != null && rest != index) {
                    addRecord(res, index, rest);
                }
            }
        }


        return res;
    }

    private void addRecord(List<List<Integer>> res, Integer left, Integer right) {
        List<Integer> record = new ArrayList<>();
        record.add(left);
        record.add(right);
        res.add(record);
    }

    private String reverse(String word) {
        char[] str = word.toCharArray();
        int left = 0;
        int right = str.length - 1;
        while (left < right) {
            char tmp = str[left];
            str[left++] = str[right];
            str[right--] = tmp;
        }

        return String.valueOf(str);
    }


    public int[] manacher(String word) {
        if (word == null || word.length() == 0) {
            return new int[0];
        }
        char[] str = getManacherStr(word);
        int[] radius = new int[str.length];
        int center = -1;
        int right = -1;
        for (int i = 0; i < str.length; i++) {
            radius[i] = right > i ? Math.min(right - i, radius[(center << 1) - i]) : 1;
            while (i + radius[i] < str.length && i - radius[i] >= 0) {
                if (str[i + radius[i]] != str[i - radius[i]]) {
                    break;
                }
                radius[i]++;
            }
            if (i + radius[i] > right) {
                right = i + radius[i];
                center = i;
            }
        }


        return radius;
    }

    public char[] getManacherStr(String word) {
        char[] str = word.toCharArray();
        char[] res = new char[(str.length << 1) + 1];

        for (int i = 0; i < res.length; i++) {
            res[i] = ((i & 1) == 0) ? '#' : str[i >> 1];
        }

        return res;
    }
}
