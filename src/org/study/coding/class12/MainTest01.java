package org.study.coding.class12;

/**
 * @author phil
 * @since 2022-0313 22:29:53
 */
public class MainTest01 {

    public boolean checkInclusion(String s1,String s2){
        if (s1 == null || s2 == null || s1.length() == 0 || s2.length() < s1.length()) {
            return false;
        }

        return containExactly3(s2,s1) != -1;
    }

    public int containExactly3(String s1, String s2) {


        char[] str1 = s1.toCharArray();
        char[] str2 = s2.toCharArray();

        int len1 = str1.length;
        int len2 = str2.length;

        int[] count = new int[256];
        for (char ch : str2) {
            count[ch]++;
        }

        int all = len2;

        int right = 0;
        for (; right < len2; right++) {
            if (count[str1[right]]-- > 0) {
                all--;
            }
        }

        for (; right < len1; right++) {
            if (all == 0) {
                return right - len2;
            }

            if (count[str1[right]]-- > 0) {
                all--;
            }

            if (count[str1[right - len2]]++ >= 0) {
                all++;
            }
        }


        return all == 0 ? right - len2 : -1;
    }
}
