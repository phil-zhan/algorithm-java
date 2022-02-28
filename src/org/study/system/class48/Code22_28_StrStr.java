package org.study.system.class48;

/**
 * @author Dell
 */
public class Code22_28_StrStr {

    public static void main(String[] args) {

        int index = new Code22_28_StrStr().strStr("bbababaaaababbaabbbabbbaaabbbaaababbabaabbaaaaabbaaabbbbaaabaabbaababbbaabaaababbaaabbbbbbaabbbbbaaabbababaaaaabaabbbababbaababaabbaa", "bbabba");
        System.out.println(index);
    }
    public int strStr(String haystack, String needle) {
        if (null == haystack || needle == null || haystack.length() < needle.length()) {
            return -1;
        }

        char[] str1 = haystack.toCharArray();
        char[] str2 = needle.toCharArray();


        int[] next = getNext(str2);

        int x = 0;
        int y = 0;

        while (x < str1.length && y < str2.length){
            if (str1[x] == str2[y]){
                x++;
                y++;
            }else if (next[y] != -1){
                y = next[y];
            }else{
                x++;
            }
        }


        return y == needle.length() ? x - y : -1;
    }


    public int[] getNext(char[] str2) {
        if (str2.length == 0){
            return new int[0];
        }
        if (str2.length == 1) {
            return new int[]{-1};
        }

        if (str2.length == 2) {
            return new int[]{-1, 0};
        }

        int[] next = new int[str2.length];
        next[0] = -1;
        next[1] = 0;

        int cur = 2;
        int cn = 0;

        while (cur < str2.length){
            if (str2[cur-1] == str2[cn]){
                next[cur++] = ++cn;
            }else if (cn > 0){
                cn = next[cn];
            }else{
                next[cur++] = 0;
            }
        }


        return next;
    }
}
