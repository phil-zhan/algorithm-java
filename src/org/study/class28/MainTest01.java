package org.study.class28;

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
                if(str1[i+pArr[i]] == str1[i-pArr[i]]){
                    pArr[i]++;
                }else{
                    break;
                }
            }

            if(i+pArr[i] > R ){
                R = i+pArr[i];
                C = i;
            }


            max = Math.max(max, pArr[i]);
        }


        return max-1;
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

    public static void main(String[] args) {
        System.out.println(manacher("aaaasssddgsgdgewgewfwefw"));
        System.out.println(Code01_Manacher.manacher("aaaasssddgsgdgewgewfwefw"));
    }
}
