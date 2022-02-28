package org.study.system.class19;

import java.util.HashMap;

/**
 * @author phil
 * @date 2021/7/30 10:58
 */
public class MainTest03 {

    private static int minStickers1(String[] stickers, String target) {
        int ans = process1(stickers, target);

        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    private static int process1(String[] stickers, String target) {

        if (target.length() == 0) {
            return 0;
        }

        int min = Integer.MAX_VALUE;
        for (String first : stickers) {
            String rest = minus(target, first);
            if (rest.length() != target.length()) {
                min = Math.min(min, process1(stickers, rest));
            }
        }

        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }

    private static String minus(String target, String curStr) {

        char[] str1 = target.toCharArray();
        char[] str2 = curStr.toCharArray();

        int[] count = new int[26];

        for (char cur1 : str1) {
            count[cur1 - 'a']++;
        }

        for (char cur2 : str2) {
            count[cur2 - 'a']--;
        }

        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < 26; i++) {
            if (count[i] > 0) {
                for (int j = 0; j < count[i]; j++) {
                    builder.append((char) i + 'a');
                }
            }
        }
        return builder.toString();
    }


    private static int minStickers2(String[] stickers, String target) {
        int length = stickers.length;
        int[][] counts = new int[length][26];

        char[] chr;
        for (int i = 0; i < length; i++) {
            chr = stickers[i].toCharArray();
            for (char c : chr) {
                counts[i][c - 'a']++;
            }
        }
        int answer = process2(counts, target);
        return answer == Integer.MAX_VALUE ? -1 : answer;
    }

    private static int process2(int[][] counts, String target) {
        if (target.length() == 0) {
            return 0;
        }

        char[] targets = target.toCharArray();
        int[] targetCount = new int[26];
        for (char c : targets) {
            targetCount[c - 'a']++;
        }

        int min = Integer.MAX_VALUE;
        for (int i = 0; i < counts.length; i++) {
            int[] sticker = counts[i];

            if (sticker[targets[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (targetCount[j] > 0) {
                        int nums = targetCount[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }

                String rest = builder.toString();
                min = Math.min(min, process2(counts, rest));
            }
        }


        return min + (min == Integer.MAX_VALUE ? 0 : 1);
    }


    public static int minStickers3(String[] stickers, String target) {
        int N = stickers.length;
        int[][] counts = new int[N][26];
        for (int i = 0; i < N; i++) {
            char[] str = stickers[i].toCharArray();
            for (char cha : str) {
                counts[i][cha - 'a']++;
            }
        }
        HashMap<String, Integer> dp = new HashMap<>();
        dp.put("", 0);
        int ans = process3(counts, target, dp);
        return ans == Integer.MAX_VALUE ? -1 : ans;
    }

    public static int process3(int[][] stickers, String t, HashMap<String, Integer> dp) {
        if (dp.containsKey(t)) {
            return dp.get(t);
        }
        char[] target = t.toCharArray();
        int[] tcounts = new int[26];
        for (char cha : target) {
            tcounts[cha - 'a']++;
        }
        int N = stickers.length;
        int min = Integer.MAX_VALUE;
        for (int i = 0; i < N; i++) {
            int[] sticker = stickers[i];
            if (sticker[target[0] - 'a'] > 0) {
                StringBuilder builder = new StringBuilder();
                for (int j = 0; j < 26; j++) {
                    if (tcounts[j] > 0) {
                        int nums = tcounts[j] - sticker[j];
                        for (int k = 0; k < nums; k++) {
                            builder.append((char) (j + 'a'));
                        }
                    }
                }
                String rest = builder.toString();
                min = Math.min(min, process3(stickers, rest, dp));
            }
        }
        int ans = min + (min == Integer.MAX_VALUE ? 0 : 1);
        dp.put(t, ans);
        return ans;
    }

}
