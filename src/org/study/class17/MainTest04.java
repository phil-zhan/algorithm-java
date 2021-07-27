package org.study.class17;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author phil
 * @date 2021/7/15 10:47
 */
public class MainTest04 {

    private static List<String> permutation1(String source) {

        List<String> list = new ArrayList<>();
        if (null == source || source.length() == 0) {
            return list;
        }
        List<Character> rests = new ArrayList<>();
        char[] strs = source.toCharArray();
        for (char str : strs) {
            rests.add(str);
        }

        String path = "";
        process(rests, path, list);


        return list;
    }

    private static void process(List<Character> rests, String path, List<String> list) {
        if (rests.isEmpty()) {
            list.add(path);
        } else {
            for (int i = 0; i < rests.size(); i++) {
                char cur = rests.get(i);
                rests.remove(i);
                process(rests, path + cur, list);
                rests.add(i, cur);
            }
        }
    }


    private static List<String> permutation2(String source) {
        List<String> answer = new ArrayList<>();
        if (null == source || source.length() == 0) {
            return answer;
        }
        process2(source.toCharArray(), 0, answer);
        return answer;
    }

    private static void process2(char[] strArr, int index, List<String> answer) {
        if (index == strArr.length) {
            answer.add(new String(strArr));
        } else {
            for (int i = index; i < strArr.length; i++) {
                swap(strArr, index, i);
                process2(strArr, index + 1, answer);
                swap(strArr, index, i);

            }
        }
    }

    private static List<String> permutation3(String source) {
        List<String> answer = new ArrayList<>();

        if (null == source || source.length() == 0) {
            return answer;
        }
        process3(source.toCharArray(), 0, answer);

        return answer;
    }

    private static void process3(char[] strArr, int index, List<String> answer) {
        if (index == strArr.length) {
            answer.add(new String(strArr));
        } else {
            boolean[] visited = new boolean[256];
            for (int i = index; i < strArr.length; i++) {
                if (!visited[strArr[i]]) {
                    visited[strArr[i]] = true;
                    swap(strArr, index, i);
                    process3(strArr, index + 1, answer);
                    swap(strArr, index, i);
                }
            }
        }
    }

    private static void swap(char[] strArr, int i, int j) {
        char temp = strArr[i];
        strArr[i] = strArr[j];
        strArr[j] = temp;
    }


    public static void main(String[] args) {
        String s = "1224";
        List<String> ans1 = permutation1(s);

        ans1.forEach(System.out::println);

        System.out.println("=======");
        List<String> ans2 = permutation2(s);

        ans2.forEach(System.out::println);

        System.out.println("=======");
        List<String> ans3 = permutation3(s);
        for (String str : ans3) {
            System.out.println(str);
        }

    }

}
