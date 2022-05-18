package org.study.system.class28;

/**
 * @author phil
 * @date 2021/10/2 10:25
 */
public class MainTest01 {

    /**
     * 求最大回文半径
     *
     * @date 2021-10-02 10:26:11
     */
    public static int manacher(String s1) {
        if (null == s1 || s1.length() < 1) {
            return 0;
        }

        char[] str1 = getManacherString(s1);

        int[] pArr = new int[str1.length];

        int C = -1;
        int R = -1;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < str1.length; i++) {
            pArr[i] = R > i ? Math.min(pArr[(C << 1) - i], R - i) : 1;

            while (i + pArr[i] < str1.length && i - pArr[i] > -1) {
                if (str1[i + pArr[i]] == str1[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }


            max = Math.max(max, pArr[i]);
        }


        return max - 1;
    }


    public static char[] getManacherString(String s1) {
        char[] str1 = s1.toCharArray();
        char[] res = new char[str1.length * 2 + 1];
        int index = 0;
        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : str1[index++];
        }

        return res;
    }

    public static String manacher2(String s1) {
        if (s1 == null || "".equals(s1)) {
            return "";
        }
        char[] processStr = getManacherString2(s1);

        // 回文半径
        int[] pArr = new int[processStr.length];

        int R = -1;
        int c = -1;

        int max = Integer.MIN_VALUE;
        int begin = 0;

        for (int i = 0; i < processStr.length; i++) {
            pArr[i] = R > i ? Math.min(pArr[(c << 1) - i], R - i) : 1;

            while (i + pArr[i] < processStr.length && i - pArr[i] >= 0) {
                if (processStr[i + pArr[i]] == processStr[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }

            if (i + pArr[i] > R) {
                R = i + pArr[i];
                c = i;
            }

            if (pArr[i] > max) {
                max = Math.max(max, pArr[i]);
                begin = i - pArr[i] + 1;
            }

        }


        return s1.substring(begin >> 1, (begin >> 1) + max - 1);
    }

    public static char[] getManacherString2(String s1) {
        char[] str = s1.toCharArray();
        char[] res = new char[(str.length << 1) + 1];

        for (int i = 0; i < res.length; i++) {
            res[i] = (i & 1) == 0 ? '#' : str[i >> 1];
        }

        return res;
    }


    public static int manacher3(String s1) {
        if (s1 == null || s1.length() == 0) {
            return 0;
        }
        char[] str = getManacherString3(s1);
        int max = Integer.MIN_VALUE;
        int[] pArr = new int[str.length];
        int C = -1;
        int R = -1;

        for (int i = 0; i < str.length; i++) {
            pArr[i] = R > i ? Math.min(R - i, pArr[(C << 1) - i]) : 1;
            while (i + pArr[i] < str.length && i - pArr[i] >= 0) {
                if (str[i + pArr[i]] == str[i - pArr[i]]) {
                    pArr[i]++;
                } else {
                    break;
                }
            }
            if (i + pArr[i] > R) {
                R = i + pArr[i];
                C = i;
            }
            max = Math.max(max, pArr[i]);
        }
        return max - 1;
    }


    public static char[] getManacherString3(String s1) {
        char[] str = s1.toCharArray();
        char[] res = new char[(str.length << 1) + 1];

        for (int i = 0; i < res.length; i++) {
            res[i] = ((i & 1) == 0) ? '#' : str[i >> 1];
        }

        return res;
    }

    public static void main(String[] args) {
        System.out.println(manacher("babadada"));
        System.out.println(manacher2("babadada"));
        System.out.println(manacher3("babadada"));

    }
}
