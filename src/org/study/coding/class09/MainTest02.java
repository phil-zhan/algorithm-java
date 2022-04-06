package org.study.coding.class09;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phil
 * @date 2022-04-06 10:47:43
 */
public class MainTest02 {

    public static void main(String[] args) {
        System.out.println(removeInvalidParentheses("(((()()()((("));
    }

    public static List<String> removeInvalidParentheses(String str) {
        List<String> ans = new ArrayList<>();
        if (str == null || str.length() == 0) {
            return ans;
        }

        remove(str, ans, 0, 0, new char[]{'(', ')'});

        return ans;
    }

    public static void remove(String str, List<String> ans, int checkIndex, int deleteIndex, char[] param) {
        for (int count = 0, cur = checkIndex; cur < str.length(); cur++) {
            if (str.charAt(cur) == param[0]) {
                count++;
            }
            if (str.charAt(cur) == param[1]) {
                count--;
            }

            if (count < 0) {
                // consider delete
                for (int modifyIndex = deleteIndex; modifyIndex <= cur; modifyIndex++) {
                    if (str.charAt(modifyIndex) == param[1] && (modifyIndex == deleteIndex || str.charAt(modifyIndex - 1) != param[1])) {
                        remove(str.substring(0, modifyIndex) + str.substring(modifyIndex + 1), ans, cur, modifyIndex, param);
                    }
                }
                return;
            }
        }

        String reversed = new StringBuilder(str).reverse().toString();
        if (param[0] == '(') {
            remove(reversed, ans, 0, 0, new char[]{')', '('});
        } else {
            ans.add(reversed);
        }
    }
}
