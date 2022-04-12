package org.study.coding.class28;

/**
 * 题解：
 * 依次遍历字符串，从左到右
 * 准备一个res
 * 每一个字符来的时候，都将 res = res*10 + (str[i]-'0')
 * 如果在这个过程中，某一步溢出来，直接返回0.最后都没溢出，返回 res
 * <p>
 * 将需要转换的数，先弄成负数。因为系统最小值的绝对值比系统最大值的绝对值要多一个，表示的范围会大一点
 * 因为负数可以最后兼顾正数，但是正数不能兼顾负数。
 * 如果要转的就是系统最小值，用正数接不住，系统最小值的绝对值比系统最大值的绝对值还要大
 * <p>
 * 当然，在这之前要清洗掉无效的字符串。如0开头的，或者压根就不是一个有效的数字字符串
 * <p>
 * 注意：
 * 转完之后，如果等于系统最小值。但是原始字符串代表的又是正数，那么直接返回0.因为取绝对值后会溢出
 * <p>
 * <p>
 * 参数清洗很麻烦
 * 如： 给的是 "abc12345df" 需要将中间的12345截出来转
 * 给的是 "abc123df45gg" 只需要转123
 *
 * @since 2022-04-11 22:16:51
 */
public class Problem_0008_StringToInteger {

    public static int myAtoi(String s) {
        if (s == null || "".equals(s)) {
            return 0;
        }
        s = removeHeadZero(s.trim());
        if ("".equals(s)) {
            return 0;
        }
        char[] str = s.toCharArray();
        if (!isValid(str)) {
            return 0;
        }
        // str 是符合日常书写的，正经整数形式
        boolean posi = str[0] != '-';
        int minq = Integer.MIN_VALUE / 10;
        int minr = Integer.MIN_VALUE % 10;
        int res = 0;
        int cur = 0;
        for (int i = (str[0] == '-' || str[0] == '+') ? 1 : 0; i < str.length; i++) {
            // 3  cur = -3   '5'  cur = -5    '0' cur = 0
            cur = '0' - str[i];
            if ((res < minq) || (res == minq && cur < minr)) {
                return posi ? Integer.MAX_VALUE : Integer.MIN_VALUE;
            }
            res = res * 10 + cur;
        }
        // res 负
        if (posi && res == Integer.MIN_VALUE) {

            // 超过了系统最大，题目要求，直接返回系统最大
            return Integer.MAX_VALUE;
        }
        return posi ? -res : res;
    }

    public static String removeHeadZero(String str) {
        boolean r = (str.startsWith("+") || str.startsWith("-"));
        int s = r ? 1 : 0;
        for (; s < str.length(); s++) {
            if (str.charAt(s) != '0') {
                break;
            }
        }
        // s 到了第一个不是'0'字符的位置
        int e = -1;
        // 左<-右
        for (int i = str.length() - 1; i >= (r ? 1 : 0); i--) {
            if (str.charAt(i) < '0' || str.charAt(i) > '9') {
                e = i;
            }
        }
        // e 到了最左的 不是数字字符的位置
        return (r ? String.valueOf(str.charAt(0)) : "") + str.substring(s, e == -1 ? str.length() : e);
    }

    public static boolean isValid(char[] chas) {
        if (chas[0] != '-' && chas[0] != '+' && (chas[0] < '0' || chas[0] > '9')) {
            return false;
        }
        if ((chas[0] == '-' || chas[0] == '+') && chas.length == 1) {
            return false;
        }
        // 0 +... -... num
        for (int i = 1; i < chas.length; i++) {
            if (chas[i] < '0' || chas[i] > '9') {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        System.out.println(Integer.MAX_VALUE);
    }


}
