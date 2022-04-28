package org.study.coding.class12;

import java.util.HashMap;
import java.util.HashSet;

/**
 * 本题测试链接 : https://leetcode-cn.com/problems/longest-consecutive-sequence/
 * 给定一个未排序的整数数组 nums ，找出数字连续的最长序列（不要求序列元素在原数组中连续）的长度。请你设计并实现时间复杂度为O(n) 的算法解决此问题。
 * <p>
 * 方式1：两个hash表
 * 和消息盒子差不多
 * 准备两个hash表
 * 头表和尾表
 * 表中记录了所有连续区间的头（或尾）。以及该头（或尾）所对应链表的长度
 * <p>
 * 准备一个最大长度变量 max
 * 遍历数组，
 * 每个数来的时候，看看能不能插在某个头的前面。或者能不能插在某个为的后面。能的话，记得更新对应链表的长度，
 * 更新之后去和当前最大值 max PK 一下
 * <p>
 * 记得两个都要检查，也可能新来的数，能直接把两个链表连起来
 * <p>
 * <p>
 * <p>
 * 方式2：一个hash表嘚瑟版本。没什么具体意义。战略性放弃
 *
 * @since 2022-03-13 11:03:33
 */
public class Code03_LongestConsecutive {

    public static void main(String[] args) {
        System.out.println(new Code03_LongestConsecutive().longestConsecutive1(new int[]{-6, 8, -5, 7, -9, -1, -7, -6, -9, -7, 5, 7, -1, -8, -8, -2, 0}));
        System.out.println(new Code03_LongestConsecutive().longestConsecutive2(new int[]{-6, 8, -5, 7, -9, -1, -7, -6, -9, -7, 5, 7, -1, -8, -8, -2, 0}));
        System.out.println(new Code03_LongestConsecutive().longestConsecutive2(new int[]{100, 4, 200, 1, 3, 2}));
        System.out.println(new Code03_LongestConsecutive().longestConsecutive2(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));
        System.out.println(new Code03_LongestConsecutive().longestConsecutive2(new int[]{0, 3, 7, 2, 5, 8, 4, 6, 0, 1}));


        System.out.println(new Code03_LongestConsecutive().longestConsecutive2(new int[]{1, 2, 3, 4, 5, 6, 7, 8}));
    }

    public int longestConsecutive1(int[] nums) {

        // key : 对应一个数字
        // value：当前数字为头（尾）的链表长度
        HashMap<Integer, Integer> headMap = new HashMap<>();
        HashMap<Integer, Integer> tailMap = new HashMap<>();

        // 某个数字做完头或尾，就不要再对其长度为1了。只有一次出现的时候，才认为起长度是1
        HashSet<Integer> setHead = new HashSet<>();
        HashSet<Integer> setTail = new HashSet<>();
        int max = 0;

        for (int num : nums) {
            if (!headMap.containsKey(num) && !setHead.contains(num)) {
                headMap.put(num, 1);
                setHead.add(num);
            }
            if (!tailMap.containsKey(num) && !setTail.contains(num)) {
                tailMap.put(num, 1);
                setTail.add(num);
            }
            max = Math.max(max, 1);

            if (tailMap.containsKey(num - 1) && headMap.containsKey(num + 1)) {
                // 当前数可以插在中间
                int curLen = headMap.get(num + 1) + tailMap.get(num - 1) + 1;
                tailMap.put(num + headMap.get(num + 1), curLen);
                headMap.put(num - tailMap.get(num - 1), curLen);

                max = Math.max(max, curLen);

                headMap.remove(num + 1);
                tailMap.remove(num - 1);
                headMap.remove(num);
                tailMap.remove(num);
            }

            if (tailMap.containsKey(num - 1)) {
                // 当前数可以插在尾上

                tailMap.put(num, tailMap.get(num - 1) + 1);
                headMap.put(num - tailMap.get(num - 1), tailMap.get(num - 1) + 1);
                tailMap.remove(num - 1);
                max = Math.max(max, tailMap.get(num));

                headMap.remove(num);
            }

            if (headMap.containsKey(num + 1)) {
                // 当前数可以插在头上

                headMap.put(num, headMap.get(num + 1) + 1);
                tailMap.put(num + headMap.get(num + 1), headMap.get(num + 1) + 1);
                headMap.remove(num + 1);
                max = Math.max(max, headMap.get(num));

                tailMap.remove(num);
            }

        }


        return max;
    }


    public int longestConsecutive2(int[] nums) {

        HashSet<Integer> hashSet = new HashSet<>();
        for (Integer num : nums) {
            hashSet.add(num);
        }
        HashMap<Integer, Integer> headMap = new HashMap<>();
        HashMap<Integer, Integer> tailMap = new HashMap<>();
        int max = 0;

        for (int num : hashSet) {
            if (!headMap.containsKey(num)) {
                headMap.put(num, 1);
            }
            if (!tailMap.containsKey(num)) {
                tailMap.put(num, 1);
            }
            max = Math.max(max, 1);

            if (tailMap.containsKey(num - 1) && headMap.containsKey(num + 1)) {
                // 当前数可以插在中间
                int curLen = headMap.get(num + 1) + tailMap.get(num - 1) + 1;
                tailMap.put(num + headMap.get(num + 1), curLen);
                headMap.put(num - tailMap.get(num - 1), curLen);

                max = Math.max(max, curLen);

                headMap.remove(num + 1);
                tailMap.remove(num - 1);
                headMap.remove(num);
                tailMap.remove(num);
            }

            if (tailMap.containsKey(num - 1)) {
                // 当前数可以插在尾上

                tailMap.put(num, tailMap.get(num - 1) + 1);
                headMap.put(num - tailMap.get(num - 1), tailMap.get(num - 1) + 1);
                tailMap.remove(num - 1);
                max = Math.max(max, tailMap.get(num));

                headMap.remove(num);
            }

            if (headMap.containsKey(num + 1)) {
                // 当前数可以插在头上

                headMap.put(num, headMap.get(num + 1) + 1);
                tailMap.put(num + headMap.get(num + 1), headMap.get(num + 1) + 1);
                headMap.remove(num + 1);
                max = Math.max(max, headMap.get(num));

                tailMap.remove(num);
            }

        }


        return max;
    }

    /**
     * 一张hash表搞定。战略性放弃
     *
     * @since 2022-03-13 08:22:45
     */
    public static int longestConsecutive(int[] nums) {
        HashMap<Integer, Integer> map = new HashMap<>();
        int len = 0;
        for (int num : nums) {
            if (!map.containsKey(num)) {
                map.put(num, 1);
                int preLen = map.containsKey(num - 1) ? map.get(num - 1) : 0;
                int posLen = map.containsKey(num + 1) ? map.get(num + 1) : 0;
                int all = preLen + posLen + 1;
                map.put(num - preLen, all);
                map.put(num + posLen, all);
                len = Math.max(len, all);
            }
        }
        return len;
    }

}
