package org.study.class19;

/**
 * @author phil
 * @date 2021/7/30 9:30
 */
public class MainTest02 {

    private static int number(String str) {
        if (null == str || str.length() == 0) {
            return 0;
        }

        return process(str.toCharArray(), 0);
    }


    /**
     * 考虑从 index 开始有多少种方案
     * @date 2021-07-30 09:54:20
     */
    private static int process(char[] str, int index) {
        if (index == str.length) {
            return 1;
        }

        if (str[index] == '0') {
            return 0;
        }

        int way = process(str, index + 1);

        if (index + 1 < str.length && (str[index] - '0') * 10 + str[index + 1]-'0' < 27) {
            way += process(str, index + 2);
        }

        return way;

    }


    private static int dp1(String str){

        if (null == str || str.length() == 0) {
            return 0;
        }

        char[] strChar = str.toCharArray();
        int length = strChar.length;
        int[] dp = new int[length + 1];
        dp[length] = 1;

        for (int i = length - 1; i >=0; i--) {
            if(strChar[i] != '0'){
                int way = dp[i + 1];

                if (i + 1 < strChar.length && (strChar[i] - '0') * 10 + strChar[i + 1]-'0' < 27) {
                    way += dp[ i + 2];
                }

                dp[i] = way;
            }
        }


        return dp[0];
    }
    // 为了测试
    public static void main(String[] args) {
        int N = 30;
        int testTime = 1000000;
        System.out.println("测试开始");
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * N);
            String s = randomString(len);
            int ans0 = number(s);
            int ans1 = dp1(s);
            int ans2 = dp2(s);
            if (ans0 != ans1 || ans0 != ans2) {
                System.out.println(s);
                System.out.println(ans0);
                System.out.println(ans1);
                System.out.println(ans2);
                System.out.println("Oops!");
                break;
            }
        }
        System.out.println("测试结束");
    }

    // 为了测试
    public static String randomString(int len) {
        char[] str = new char[len];
        for (int i = 0; i < len; i++) {
            str[i] = (char) ((int) (Math.random() * 10) + '0');
        }
        return String.valueOf(str);
    }





    // ===================================================================== //



    // 经典动态规划
    // 从左往右的动态规划
    // dp[i]表示：str[0...i]有多少种转化方式
    public static int dp2(String s) {
        if (s == null || s.length() == 0) {
            return 0;
        }
        char[] str = s.toCharArray();
        int N = str.length;
        if (str[0] == '0') {
            return 0;
        }
        int[] dp = new int[N];
        dp[0] = 1;
        for (int i = 1; i < N; i++) {
            if (str[i] == '0') {
                // 如果此时str[i]=='0'，那么他是一定要拉前一个字符(i-1的字符)一起拼的，
                // 那么就要求前一个字符，不能也是‘0’，否则拼不了。
                // 前一个字符不是‘0’就够了嘛？不够，还得要求拼完了要么是10，要么是20，如果更大的话，拼不了。
                // 这就够了嘛？还不够，你们拼完了，还得要求str[0...i-2]真的可以被分解！
                // 如果str[0...i-2]都不存在分解方案，那i和i-1拼成了也不行，因为之前的搞定不了。
                if (str[i - 1] == '0' || str[i - 1] > '2' || (i - 2 >= 0 && dp[i - 2] == 0)) {
                    return 0;
                } else {
                    dp[i] = i - 2 >= 0 ? dp[i - 2] : 1;
                }
            } else {
                dp[i] = dp[i - 1];
                if (str[i - 1] != '0' && (str[i - 1] - '0') * 10 + str[i] - '0' <= 26) {
                    dp[i] += i - 2 >= 0 ? dp[i - 2] : 1;
                }
            }
        }
        return dp[N - 1];
    }


    // ====================================================================== //

}
