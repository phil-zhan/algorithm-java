package org.study.system.class48;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

/**
 * @author phil
 * @since 2022-0308 20:19:47
 */
public class Code29_140_WordBreak {

    public static void main(String[] args) {
        List<String> list = new ArrayList<>();
        list.add("cat");
        list.add("cats");
        list.add("and");
        list.add("sand");
        list.add("dog");
        List<String> ans = new Code29_140_WordBreak().wordBreak("catsanddog", list);
        System.out.println(ans);
    }

    public List<String> wordBreak(String s, List<String> wordDict) {
        ArrayList<String> ans = new ArrayList<>();
        process(s, 0, "", new HashSet<>(wordDict), ans);

        return ans;
    }

    public void process(String str, int index, String sentence, HashSet<String> wordDict, List<String> ans) {
        if (index == str.length()) {
            ans.add(sentence.substring(0, sentence.length() - 1));
        }
        for (int end = index; end < str.length(); end++) {
            String pre = str.substring(index, end + 1);
            if (wordDict.contains(pre)) {
                sentence = sentence + pre + " ";
                process(str, end + 1, sentence, wordDict, ans);
                sentence = sentence.substring(0, sentence.length() - pre.length() - 1);
            }
        }
    }


}
