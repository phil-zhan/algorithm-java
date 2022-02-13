package org.study;

import org.study.class05.Code003_QuickSort_2;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * @author phil
 * @date 2022/1/7 14:26
 */
public class MainTest {


    public static void main(String[] args) {
        String ans = new MainTest().longestPalindrome("aacabdkacaa");
        String ans2 = new MainTest().longestPalindrome2("aacabdkacaa");
        System.out.println(ans);
        System.out.println(ans2);
    }


    public String longestPalindrome2(String s) {


        char[] str = s.toCharArray();
        int maxLen = 1;
        int begin = 0;
        Map<String,Integer> map = new HashMap<>();
        map.put("maxLen",1);
        map.put("begin",0);
        boolean ans = process(str, 0, s.length() - 1,map);
        return s.substring(begin,begin+maxLen);
    }

    public boolean process(char[] str, int index, int len, Map<String,Integer> map) {
        if (len==1){
            return true;
        }


        int right = index + len - 1;
        if (right >= str.length) {
            return false;
        }

        if (len==2){
            if (map.get("maxLen") < 2){
                map.put("maxLen",len);
                map.put("begin",index);
            }
            return str[index] == str[right];
        }


        if (str[index] == str[right] && process(str,index+1,len-2,map)){
            if (map.get("maxLen") < len){
                map.put("maxLen",len);
                map.put("begin",index);
            }
            return true;
        }

        return false;
    }


    public String longestPalindrome(String s) {
        int len = s.length();
        if (len < 2) {
            return s;
        }

        int maxLen = 1;
        int begin = 0;
        // dp[i][j] 表示 s[i..j] 是否是回文串
        boolean[][] dp = new boolean[len][len];
        // 初始化：所有长度为 1 的子串都是回文串
        for (int i = 0; i < len; i++) {
            dp[i][i] = true;
        }

        char[] charArray = s.toCharArray();
        // 递推开始
        // 先枚举子串长度
        for (int L = 2; L <= len; L++) {
            // 枚举左边界，左边界的上限设置可以宽松一些
            for (int i = 0; i < len; i++) {
                // 由 L 和 i 可以确定右边界，即 j - i + 1 = L 得
                int j = L + i - 1;
                // 如果右边界越界，就可以退出当前循环
                if (j >= len) {
                    break;
                }

                if (charArray[i] != charArray[j]) {
                    dp[i][j] = false;
                } else {
                    if (j - i < 3) {
                        dp[i][j] = true;
                    } else {
                        dp[i][j] = dp[i + 1][j - 1];
                    }
                }

                // 只要 dp[i][L] == true 成立，就表示子串 s[i..L] 是回文，此时记录回文长度和起始位置
                if (dp[i][j] && j - i + 1 > maxLen) {
                    maxLen = j - i + 1;
                    begin = i;
                }
            }
        }
        return s.substring(begin, begin + maxLen);
    }


}
