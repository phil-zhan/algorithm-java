package org.study.coding.class14;

/**
 * ce
 *
 * @author Administrator
 * @since 2022-05-05 09:34:52
 */
public class MainTest01 {
    public static void main(String[] args) {
        MainTest01 mainTest01 = new MainTest01();

        int times = 100000;
        int maxLen = 1000;
        for (int i = 0; i < times; i++) {

            String s = mainTest01.generateStr(maxLen);
            int ans1 = mainTest01.longestValidParentheses(s);
            int ans2 = Code01_Parentheses.longestValidParentheses(s);
            if (ans1 != ans2){
                System.out.println("s:"+s);
                System.out.println("ans1:"+ans1);
                System.out.println("ans2:"+ans2);
                break;
            }

            System.out.println("第 "+i+" 次测成功");
        }

        System.out.println("over");
    }

    public int longestValidParentheses(String s) {
        if (s == null || s.length() < 2) {
            return 0;
        }
        int ans = 0;
        int pre = 0;
        int[] dp = new int[s.length()];
        char[] str = s.toCharArray();

        for (int i = 1; i < str.length; i++) {
            if (str[i] == ')') {
                pre = i - dp[i - 1] - 1;
                if (pre >= 0 && str[pre] == '(') {
                    dp[i] = dp[i - 1] + 2 + (pre > 0 ? dp[pre - 1] : 0);
                }

            }
            ans = Math.max(ans, dp[i]);
        }
        return ans;
    }

    public String generateStr(int maxLen){
        int len = (int)(Math.random() *(maxLen+1));
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            if (Math.random() < 0.5){
                str[i] = '(';
            }else{
                str[i] = ')';
            }
        }
        return new String(str);
    }
}
