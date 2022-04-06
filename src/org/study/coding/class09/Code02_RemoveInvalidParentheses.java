package org.study.coding.class09;

import java.util.ArrayList;
import java.util.List;


/**
 * // 测试链接 : https://leetcode.com/problems/remove-invalid-parentheses/
 * 给你一个由若干括号和字母组成的字符串 s ，
 * 删除最小数量的无效括号，使得输入的字符串有效。返回所有可能的结果。
 * 答案可以按任意顺序返回
 * 在删除数量尽可能少的情况下。可能出现的结果不一样。将所有可能的结果都返回【删除的数量必须最少】
 * <p>
 * 可以考虑暴力的收集所有可能性。最后去重就好
 * 当然，也可以在递归过程中减枝
 * <p>
 * 括号字符串是否是有效的
 * <p>
 * 考虑右括号多得情况
 * 从头到尾遍历字符串
 * 当前来到 i 位置【检查的位置】
 * 可能删除的位置 j
 * <p>
 * i、j 都从0开始
 * 设计一个count变量，统计沿途的左括号数量
 * 遇到左括号的时候，i++，count++
 * 当遇到右括号的时候。i++ 。count-- 【一个右括号可以消费一个左括号】
 * 当 count小于0的时候，也就是右括号比左括号多得时候。就要考虑删除一个了
 * 在要考虑删除的时候， j 就要开始移动了。删除谁呢？
 * 可以考虑删除沿途的[j...i]右括号.
 * 如果发现 j-1 位置也是右括号，不要删除当前 j 位置的右括号。因为删除 j 位置的右括号 和删除 j-1 位置的右括号，得到的结果是一样的
 * 也就是相邻的右括号，无论删掉哪一个，得到的结果都是一样的【代表的是，用当前位置之前的当前位置往前推，最左侧的左括号去和 多余的右括号配对】
 * 【往前推的时候，沿途不要经过右括号】
 * <p>
 * 如果 j-1 位置不是右括号，就可以考虑删除当前 j 位置的右括号
 * <p>
 * <p>
 * 将该过程调过来。从右往左遍历，就能搞定左括号多得情况
 * <p>
 * 递归：
 * 考虑当前来到 index 位置，删除的位置来到 deleteIndex 位置。从index 位置开始，检查后续括号的有效性。如果要删除，从 deleteIndex位置开始往后检查
 * 删除之后，字符前移。如果当前来到index位置，删除一个字符之后，下一层递归还是要从 index 位置开始
 *
 * @since 2022-03-10 08:11:31
 */
public class Code02_RemoveInvalidParentheses {
    public static void main(String[] args) {
        System.out.println(removeInvalidParentheses("(()((())))"));
    }

    /**
     * 来自leetcode投票第一的答案，实现非常好，我们来赏析一下
     *
     * @since 2022-03-10 08:48:26
     */
    public static List<String> removeInvalidParentheses(String s) {
        List<String> ans = new ArrayList<>();
        remove(s, ans, 0, 0, new char[]{'(', ')'});
        return ans;
    }

    /**
     * modifyIndex <= checkIndex
     * 只查s[checkIndex....]的部分，因为之前的一定已经调整对了
     * 但是之前的部分是怎么调整对的，调整到了哪？就是modifyIndex
     * 比如：
     * ( ( ) ( ) ) ) ...
     * 0 1 2 3 4 5 6
     * 一开始当然checkIndex = 0，modifyIndex = 0
     * 当查到6的时候，发现不对了，
     * 然后可以去掉2位置、4位置的 )，都可以
     * 如果去掉2位置的 ), 那么下一步就是
     * ( ( ( ) ) ) ...
     * 0 1 2 3 4 5 6
     * checkIndex = 6 ，modifyIndex = 2
     * 如果去掉4位置的 ), 那么下一步就是
     * ( ( ) ( ) ) ...
     * 0 1 2 3 4 5 6
     * checkIndex = 6 ，modifyIndex = 4
     * 也就是说，
     * checkIndex和modifyIndex，分别表示查的开始 和 调整的开始，之前的都不用管了  par[0] = '('     par[0] = ')‘
     *
     * @since 2022-03-10 08:48:50
     */
    public static void remove(String s, List<String> ans, int checkIndex, int deleteIndex, char[] par) {
        for (int count = 0, i = checkIndex; i < s.length(); i++) {
            if (s.charAt(i) == par[0]) {
                count++;
            }
            if (s.charAt(i) == par[1]) {
                count--;
            }
            if (count < 0) {

                // i check计数<0的第一个位置
                // 在 [j...i] 的范围上找可能删除的位置。【相邻的右括号，只删除一个】【代表的都是当前范围上的最左侧的左括号去消费多余的那个右括号】
                // 每次删除之后，会形成新的字符串，考虑当前的 i、j 组合。去调递归
                for (int j = deleteIndex; j <= i; ++j) {
                    // 比如
                    if (s.charAt(j) == par[1] && (j == deleteIndex || s.charAt(j - 1) != par[1])) {

                        // 去搞定后续无效位置
                        remove(s.substring(0, j) + s.substring(j + 1, s.length()), ans, i, j, par);
                    }
                }

                // 删除第一个不合法的之后，直接结束。不用再往后动了。后面的由前面的递归去搞定
                return;
            }
        }

        // 循环结束都没有return。左括号数量大于等于右括号数量
        // 将整个字符串逆序一下
        // 认为右括号是加1，左括号是减1.重新丢到递归里面去。删掉必要的左括号
        String reversed = new StringBuilder(s).reverse().toString();

        // 我们是先检查左括号，再检查右括号。如果 par[0] == '(' 。
        // 如果传进来的 par[0] 是左括号，说明是第一次反转了。有必要进行反转
        // 如果传进来的 par[0] 不是左括号，说明是第二次反转了。就没必要进行，直接收集答案。【防止递归转不完】【此时的左右括号数量相等】

        if (par[0] == '(') {
            remove(reversed, ans, 0, 0, new char[]{')', '('});
        } else {
            ans.add(reversed);
        }
    }

}
