package org.study.coding.class22;

import java.util.HashMap;

/**
 * 5. 你正在安装一个广告牌，并希望它高度最大。这块广告牌将有两个钢制支架，两边各一个。每个钢支架的高度必须相等。
 * 你有一堆可以焊接在一起的钢筋 rods。举个例子，如果钢筋的长度为 1、2 和 3，则可以将它们焊接在一起形成长度为 6 的支架。
 * 返回广告牌的最大可能安装高度。如果没法安装广告牌，请返回 0。
 * Leetcode题目：https://leetcode.com/problems/tallest-billboard/
 * <p>
 * 题意：
 * 找到两个不相交的字集，累加和在一样大的情况下，尽可能的大。
 * <p>
 * 解法：
 * 定义一个map；随意选两个子集出来，产生的差值是key，可能会有多个集合对产生同样的差值【一对集合的差值】，记录同等差值下，累加和尽可能大的那一对中的较小集合的累加和。
 * 在遍历之前。map[0]=0
 * 遍历数组
 * 加工出一个新的map出来
 * 新来的元素，可以加入到原有的任何一个集合中【】
 * 可能形成新的key【差值】，也可能和之前的key重合
 * 这样重复下去，就能得到所有可能的差值，以及在对应差值下的较小集合的累加和。
 * 当新产生的差值【key】已经存在的时候。看value【基线】。如果新有的value比之前的大，就替换掉。不比之前的大，就舍弃当前的
 * <p>
 * 推完整个数组后。差值为0的那一个key对应的value。就是要的答案
 *
 * @since 2022-03-24 22:34:37
 */
public class Code05_TallestBillboard {

    public int tallestBillboard(int[] rods) {
        // key 集合对的某个差
        // value 满足差值为key的集合对中，最好的一对，较小集合的累加和
        // 较大 -> value + key
        HashMap<Integer, Integer> dp = new HashMap<>(), cur;

        // 空集 和 空集
        dp.put(0, 0);
        for (int num : rods) {
            if (num != 0) {
                // cur 内部数据完全和dp一样
                // 考虑x之前的集合差值状况拷贝下来
                cur = new HashMap<>(dp);
                for (int d : cur.keySet()) {

                    // 最好的一对，较小集合的累加和
                    int diffMore = cur.get(d);
                    // x决定放入，比较大的那个
                    dp.put(d + num, Math.max(diffMore, dp.getOrDefault(num + d, 0)));
                    
                    // x决定放入，比较小的那个
                    // 新的差值 Math.abs(x - d)
                    // 之前差值为Math.abs(x - d)，的那一对，就要和这一对，决策一下
                    // 之前那一对，较小集合的累加和diffXD
                    int diffXD = dp.getOrDefault(Math.abs(num - d), 0);

                    // x决定放入比较小的那个, 但是放入之后，没有超过这一对较大的那个
                    if (d >= num) {
                        dp.put(d - num, Math.max(diffMore + num, diffXD));
                    } else { // x决定放入比较小的那个, 但是放入之后，没有超过这一对较大的那个
                        dp.put(num - d, Math.max(diffMore + d, diffXD));
                    }
                }
            }
        }
        return dp.get(0);
    }

}
