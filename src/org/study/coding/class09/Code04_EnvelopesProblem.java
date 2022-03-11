package org.study.coding.class09;

import java.util.Arrays;
import java.util.Comparator;

/**
 * // 本题测试链接 : https://leetcode.com/problems/russian-doll-envelopes/
 * 给你一个二维整数数组 envelopes ，其中 envelopes[i] = [wi, hi] ，表示第 i 个信封的宽度和高度。
 * 当另一个信封的宽度和高度都比这个信封大的时候，这个信封就可以放进另一个信封里，如同俄罗斯套娃一样。
 * 请计算 最多能有多少个 信封能组成一组“俄罗斯套娃”信封（即可以把一个信封放到另一个信封里面）。
 * 注意：不允许旋转信封。
 * <p>
 * <p>
 * 示例 1：
 * <p>
 * 输入：envelopes = [[5,4],[6,4],[6,7],[2,3]]
 * 输出：3
 * 解释：最多信封的个数为 3, 组合为: [2,3] => [5,4] => [6,7]。
 * 示例 2：
 * <p>
 * 输入：envelopes = [[1,1],[1,1],[1,1]]
 * 输出：1
 * <p>
 * 解法：
 * 题意：只能是宽度和高度都小于前面一层，才能被套进去
 * <p>
 * 排序：
 * 宽度小到大，宽度一样的时候，高度由大到小
 * 排完序后，将所有高度都拿出来组成一个一维数组
 * 结论：在这个数组中，最长递增子序列长度就是套娃的层数
 * <p>
 * 递增子序列，那么高度这个层面肯定是能套进去的。
 * 宽度这个层面。因为排序的时候是先按照宽度从小到大的。【那么当前位置前面的所有数，其宽度都必定小于自己】
 * 也就是说，在这个一维数组中，高度和自己一样，但是宽度不如自己的，肯定在自己的后面【只要宽度不如自己的，都在当前位置的后面】【宽度小到大】
 *
 * @since 2022-03-10 09:26:45
 */
public class Code04_EnvelopesProblem {

    public static int maxEnvelopes(int[][] matrix) {
        Envelope[] arr = sort(matrix);
        int[] ends = new int[matrix.length];
        ends[0] = arr[0].h;
        int right = 0;
        int l;
        int r;
        int m;
        for (int i = 1; i < arr.length; i++) {
            l = 0;
            r = right;
            while (l <= r) {
                m = (l + r) / 2;
                if (arr[i].h > ends[m]) {
                    l = m + 1;
                } else {
                    r = m - 1;
                }
            }
            right = Math.max(right, l);
            ends[l] = arr[i].h;
        }
        return right + 1;
    }

    public static class Envelope {
        public int l;
        public int h;

        public Envelope(int weight, int hight) {
            l = weight;
            h = hight;
        }
    }

    public static class EnvelopeComparator implements Comparator<Envelope> {
        @Override
        public int compare(Envelope o1, Envelope o2) {
            return o1.l != o2.l ? o1.l - o2.l : o2.h - o1.h;
        }
    }

    public static Envelope[] sort(int[][] matrix) {
        Envelope[] res = new Envelope[matrix.length];
        for (int i = 0; i < matrix.length; i++) {
            res[i] = new Envelope(matrix[i][0], matrix[i][1]);
        }
        Arrays.sort(res, new EnvelopeComparator());
        return res;
    }

}
