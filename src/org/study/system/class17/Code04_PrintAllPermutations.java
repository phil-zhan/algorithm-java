package org.study.system.class17;

import java.util.ArrayList;
import java.util.List;

public class Code04_PrintAllPermutations {

    public static List<String> permutation1(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        ArrayList<Character> rest = new ArrayList<Character>();
        for (char cha : str) {
            rest.add(cha);
        }
        String path = "";
        f(rest, path, ans);
        return ans;
    }

    public static void f(ArrayList<Character> rest, String path, List<String> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
        } else {
            int N = rest.size();
            for (int i = 0; i < N; i++) {
                char cur = rest.get(i);

                // 考虑的是排列问题，当前位置选了的元素，下一个位置不能选，所以要移除
                rest.remove(i);
                f(rest, path + cur, ans);
                // 当前位置的所有后序步骤都考虑完了。想要考虑当前位置的下一种可能性，
                // 需要把当前位置的值补上，不然当前的循环无法正常跳到下一个位置
                rest.add(i, cur);
            }
        }
    }

    public static List<String> permutation2(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g1(str, 0, ans);
        return ans;
    }

    public static void g1(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {
            for (int i = index; i < str.length; i++) {

                // 也就是在所有的待选字符中，选一个做第一个字符
                // 第一轮循环，i=0,。 0位置和0位置交换。也就表示第一个位置的字符做第一个字符
                // 第二轮 也就是第二个位置的字符做第一个字符
                // ......一次类推


                // 第一个位置选定之后，剩下的 n-1个元素也执行同样的过程
                // 在第一个位置的情况都考虑完成之后，在考虑第二个元素之前，需要将之前的交换还原回来。
                // 不然当前的循环跳到下一个位置的时候，还是当前元素，会影响最终结果

                swap(str, index, i);
                g1(str, index + 1, ans);
                swap(str, index, i);
            }
        }
    }

    public static List<String> permutation3(String s) {
        List<String> ans = new ArrayList<>();
        if (s == null || s.length() == 0) {
            return ans;
        }
        char[] str = s.toCharArray();
        g2(str, 0, ans);
        return ans;
    }

    public static void g2(char[] str, int index, List<String> ans) {
        if (index == str.length) {
            ans.add(String.valueOf(str));
        } else {


            boolean[] visited = new boolean[256];
            visited['a'] = true;


            for (int i = index; i < str.length; i++) {
                // 循环可能的取值，没有出现过的才能选
                if (!visited[str[i]]) {
                    // 取 str[i] 字符的ascii码值为下标
                    visited[str[i]] = true;
                    swap(str, index, i);
                    g2(str, index + 1, ans);
                    swap(str, index, i);
                }
            }
        }
    }

    public static void swap(char[] chs, int i, int j) {
        char tmp = chs[i];
        chs[i] = chs[j];
        chs[j] = tmp;
    }

    public static void main(String[] args) {
        String s = "123456789";
        List<String> ans1 = permutation1(s);
        for (String str : ans1) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans2 = permutation2(s);
        for (String str : ans2) {
            System.out.println(str);
        }
        System.out.println("=======");
        List<String> ans3 = permutation3(s);
        for (String str : ans3) {
            System.out.println(str);
        }

    }

}
