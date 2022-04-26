package org.study.coding.class12;

/**
 * @author phil
 * @date 2022-04-15 16:36:58
 */
public class MainTest04 {

    public static void main(String[] args) {
        // "bbbba"
        // ".*a*a"
        System.out.println(isMatch1("bbbba",".*a*a"));
    }

    public static boolean isMatch1(String s1, String e1) {
        if (s1 == null || e1 == null) {
            return false;
        }

        char[] str = s1.toCharArray();
        char[] exp = e1.toCharArray();

        return isValid(str, exp) && process1(str, 0, exp, 0);
    }

    public static boolean process1(char[] str, int si, char[] exp, int ei) {

        if (ei == exp.length) {
            // 没有表达式了
            return si == str.length;
        }

        // 还有表达式
        // 1) ei+1 不是 '*' 【当前字符需要自己消化】
        if (ei + 1 == exp.length || exp[ei + 1] != '*') {


            return si != str.length && (str[si] == exp[ei] || exp[ei] == '.') && process1(str, si + 1, exp, ei + 1);
        }

        // 2) ei+1 是 '*'【】
        while (si != str.length && (exp[ei] == str[si] || exp[ei] == '.')) {
            // ei 和 ei+1 搞定 0 1 2 3 ...  【这些字符必须是一样的】
            if (process1(str, si, exp, ei + 2)) {
                return true;
            }

            si++;
        }

        // 没有字符串了，或者ei位置搞不定
        // ei 和 ei+1 只能搞定 0 个。
        return process1(str, si, exp, ei + 2);
    }

    public static boolean isMatch2(String s1, String e1) {
        if (s1 == null || e1 == null) {
            return false;
        }

        char[] str = s1.toCharArray();
        char[] exp = e1.toCharArray();

        return isValid(str, exp) && process1(str, 0, exp, 0);
    }


    public static boolean isValid(char[] str, char[] exp) {
        // str不能有 '.' 和 '*'
        for (char c : str) {
            if (c == '.' || c == '*') {
                return false;
            }
        }

        // exp 不能是'*'开头.
        // 两个 '*' 不能挨在一起
        for (int i = 0; i < exp.length; i++) {
            if (exp[i] == '*' && (i == 0 || exp[i - 1] == '*')) {
                return false;
            }
        }

        return true;
    }
}
