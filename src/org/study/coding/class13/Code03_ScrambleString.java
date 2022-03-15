package org.study.coding.class13;


/**
 * // 本题测试链接 : https://leetcode.com/problems/scramble-string/
 * 玄变串
 * 判断两个字符串是否互为玄变串
 * 旋变字符串
 * 使用下面描述的算法可以扰乱字符串 s 得到字符串 t ：
 * 如果字符串的长度为 1 ，算法停止
 * 如果字符串的长度 > 1 ，执行下述步骤：
 * 在一个随机下标处将字符串分割成两个非空的子字符串。即，如果已知字符串 s ，则可以将其分成两个子字符串 x 和 y ，且满足 s = x + y 。
 * 随机 决定是要「交换两个子字符串」还是要「保持这两个子字符串的顺序不变」。即，在执行这一步骤之后，s 可能是 s = x + y 或者 s = y + x 。
 * 在 x 和 y 这两个子字符串上继续从步骤 1 开始递归执行此算法。
 * 给你两个 长度相等 的字符串 s1 和s2，判断s2是否是s1的扰乱字符串。如果是，返回 true ；否则，返回 false 。
 * <p>
 * <p>
 * 如果两个字符串的种类不一样。直接返回 false
 * 如果两个字符串的每种字符的个数不一样，也直接返回false
 * <p>
 * 解法：
 * 考虑 str1[L1...R1] str2[L2...R2] 是否互为玄变串
 * 一定保证这两段是等长的！
 * 普遍情况
 * 虑str1 可以切分的所有位置。
 * 1) 在切分位置，str1的左侧和str2的对应左侧是旋变串。且 str1的右侧和str2的对应右侧也是旋变串 。那么该字符串是旋变串【也就是第一刀切开后，没交换了】
 * 2) 在切分位置. str1的左侧和 str2的右侧对应长度 是旋变串。 且 str1的右侧和 str2的左侧对应长度 是旋变串 。那么该字符串是旋变串 【也就是第一刀切开后，交换了】
 *
 * @since 2022-03-14 10:49:55
 */
public class Code03_ScrambleString {

    /**
     * @since 2022-03-14 11:05:07
     */
    public static boolean isScramble0(String s1, String s2) {

        // 过滤掉无效参数
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }


        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        // 如果两个字符串的种类不一样。直接返回 false
        // 如果两个字符串的每种字符的个数不一样，也直接返回false
        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        return process0(str1, 0, str1.length - 1, str2, 0, str2.length - 1);
    }

    // str1[L1...R1] str2[L2...R2] 是否互为玄变串
    // 一定保证这两段是等长的！
    public static boolean process0(char[] str1, int L1, int R1, char[] str2, int L2, int R2) {

        // base case
        if (L1 == R1) {
            return str1[L1] == str2[L2];
        }

        // 普遍情况【枚举第一刀的位置】
        // 考虑str1 可以切分的所有位置。
        // 1) 在切分位置，str1的左侧和str2的对应左侧是旋变串。且 str1的右侧和str2的对应右侧也是旋变串 。那么该字符串是旋变串【也就是第一刀切开后，没交换了】
        // 2) 在切分位置. str1的左侧和 str2的右侧对应长度 是旋变串。 且 str1的右侧和 str2的左侧对应长度 是旋变串 。那么该字符串是旋变串 【也就是第一刀切开后，交换了】
        for (int leftEnd = L1; leftEnd < R1; leftEnd++) {

            // 1) 左对左。右对右
            boolean p1 = process0(str1, L1, leftEnd, str2, L2, L2 + leftEnd - L1)
                    && process0(str1, leftEnd + 1, R1, str2, L2 + leftEnd - L1 + 1, R2);

            // 1) 左对右。右对左
            boolean p2 = process0(str1, L1, leftEnd, str2, R2 - (leftEnd - L1), R2)
                    && process0(str1, leftEnd + 1, R1, str2, L2, R2 - (leftEnd - L1) - 1);


            if (p1 || p2) {
                // 只有有一刀满足，就无需再往后切了
                return true;
            }
        }
        return false;
    }

    public static boolean sameTypeSameNumber(char[] str1, char[] str2) {
        if (str1.length != str2.length) {
            return false;
        }
        int[] map = new int[256];
        for (int i = 0; i < str1.length; i++) {
            map[str1[i]]++;
        }
        for (int i = 0; i < str2.length; i++) {
            if (--map[str2[i]] < 0) {
                return false;
            }
        }
        return true;
    }

    /**
     * 时间复杂度 O(N^4)
     *
     * @since 2022-03-15 08:03:59
     */
    public static boolean isScramble1(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        int N = s1.length();

        // 因为要求 str1 从L1开始到 R1的长度和 str2从L2开始到 R2的长度必须一样
        // 这里就省去R1，R2.用长度 N代替
        return process1(str1, str2, 0, 0, N);
    }

    /**
     * 返回str1[从L1开始往右长度为size的子串]和str2[从L2开始往右长度为size的子串]是否互为旋变字符串
     * 在str1中的这一段和str2中的这一段一定是等长的，所以只用一个参数size
     *
     * @since 2022-03-15 07:36:29
     */
    public static boolean process1(char[] str1, char[] str2, int L1, int L2, int size) {
        if (size == 1) {
            return str1[L1] == str2[L2];
        }
        // 枚举每一种情况，有一个计算出互为旋变就返回true。都算不出来最后返回false
        for (int leftPart = 1; leftPart < size; leftPart++) {

            // 如果1左对2左，并且1右对2右
            boolean p1 = (process1(str1, str2, L1, L2, leftPart)
                    && process1(str1, str2, L1 + leftPart, L2 + leftPart, size - leftPart));

            // 如果1左对2右，并且1右对2左
            boolean p2 = (process1(str1, str2, L1, L2 + size - leftPart, leftPart)
                    && process1(str1, str2, L1 + leftPart, L2, size - leftPart));

            if (p1 || p2) {
                // 只有有一刀满足，就无需再往后切了
                return true;
            }
        }
        return false;
    }


    public static boolean isScramble2(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        int N = s1.length();
        int[][][] dp = new int[N][N][N + 1];
        // dp[i][j][k] = 0 processDP(i,j,k)状态之前没有算过的
        // dp[i][j][k] = -1 processDP(i,j,k)状态之前算过的,返回值是false
        // dp[i][j][k] = 1 processDP(i,j,k)状态之前算过的,返回值是true
        return process2(str1, str2, 0, 0, N, dp);
    }

    public static boolean process2(char[] str1, char[] str2, int L1, int L2, int size, int[][][] dp) {
        if (dp[L1][L2][size] != 0) {
            return dp[L1][L2][size] == 1;
        }
        boolean ans = false;
        if (size == 1) {
            ans = str1[L1] == str2[L2];
        } else {
            for (int leftPart = 1; leftPart < size; leftPart++) {

                boolean p1 = (process2(str1, str2, L1, L2, leftPart, dp)
                        && process2(str1, str2, L1 + leftPart, L2 + leftPart, size - leftPart, dp));

                boolean p2 = (process2(str1, str2, L1, L2 + size - leftPart, leftPart, dp)
                        && process2(str1, str2, L1 + leftPart, L2, size - leftPart, dp));

                if (p1 || p2) {
                    // 只有有一刀满足，就无需再往后切了
                    ans = true;
                    break;
                }
            }
        }
        dp[L1][L2][size] = ans ? 1 : -1;
        return ans;
    }

    public static boolean isScramble3(String s1, String s2) {
        if ((s1 == null && s2 != null) || (s1 != null && s2 == null)) {
            return false;
        }
        if (s1 == null && s2 == null) {
            return true;
        }
        if (s1.equals(s2)) {
            return true;
        }
        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();
        if (!sameTypeSameNumber(str1, str2)) {
            return false;
        }
        int N = s1.length();
        boolean[][][] dp = new boolean[N][N][N + 1];
        for (int L1 = 0; L1 < N; L1++) {
            for (int L2 = 0; L2 < N; L2++) {
                dp[L1][L2][1] = str1[L1] == str2[L2];
            }
        }
        // 第一层for循环含义是：依次填size=2层、size=3层..size=N层，每一层都是一个二维平面
        // 第二、三层for循环含义是：在具体的一层，整个面都要填写，所以用两个for循环去填一个二维面
        // L1的取值氛围是[0,N-size]，因为从L1出发往右长度为size的子串，L1是不能从N-size+1出发的，这样往右就不够size个字符了
        // L2的取值范围同理
        // 第4层for循环完全是递归函数怎么写，这里就怎么改的
        for (int size = 2; size <= N; size++) {
            for (int L1 = 0; L1 <= N - size; L1++) {
                for (int L2 = 0; L2 <= N - size; L2++) {
                    for (int leftPart = 1; leftPart < size; leftPart++) {
                        if ((dp[L1][L2][leftPart] && dp[L1 + leftPart][L2 + leftPart][size - leftPart])
                                || (dp[L1][L2 + size - leftPart][leftPart] && dp[L1 + leftPart][L2][size - leftPart])) {
                            dp[L1][L2][size] = true;
                            break;
                        }
                    }
                }
            }
        }
        return dp[0][0][N];
    }

}
