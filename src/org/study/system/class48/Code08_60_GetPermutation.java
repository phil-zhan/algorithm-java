package org.study.system.class48;

import java.util.ArrayList;
import java.util.List;

/**
 * https://leetcode-cn.com/problems/permutation-sequence/
 *
 * @author phil
 * @date 2022/2/15 12:58
 */
public class Code08_60_GetPermutation {

    public static void main(String[] args) {
        new Code08_60_GetPermutation().getPermutation(3, 3);
    }

    public String getPermutation(int n, int k) {

        List<String> ans = new ArrayList<>();

        ArrayList<Character> rest = new ArrayList<>();
        for (int i = 1; i <=n ; i++) {
            rest.add((char) (i+'0'));
        }

        process(rest, "", ans);
        ans.sort((String::compareTo));
        return ans.get(k-1);
    }

    public void process(ArrayList<Character> rest, String path, List<String> ans) {
        if (rest.isEmpty()) {
            ans.add(path);
        } else {
            int n = rest.size();
            for (int i = 0; i < n; i++) {
                char cur = rest.get(i);
                rest.remove(i);
                process(rest, path + cur, ans);
                rest.add(i,cur);
            }
        }
    }
}
