package org.study.coding.class28;

/**
 * 有效的括号
 * <p>
 * 解法：
 * 准备一个栈
 * 遇到 '(' '['  '{'
 * 否则就弹出 .弹出的刚好和自己配对，就继续去下一个位置，不配对就返回false
 *
 * @since 2022-04-11 23:24:21
 */
public class Problem_0020_ValidParentheses {

    public static boolean isValid(String s) {
        if (s == null || s.length() == 0) {
            return true;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        char[] stack = new char[N];
        int size = 0;
        for (char cha : str) {
            if (cha == '(' || cha == '[' || cha == '{') {

                // 为了方便比较，这里压的时候，压和自己配对的字符
                stack[size++] = cha == '(' ? ')' : (cha == '[' ? ']' : '}');
            } else {

                // 当前不是左括号，栈里又没有字符，直接false
                if (size == 0) {
                    return false;
                }
                char last = stack[--size];
                if (cha != last) {
                    return false;
                }
            }
        }

        // 遍历完的时候，栈里不空也返回false
        return size == 0;
    }

}
