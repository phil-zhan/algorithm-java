package org.study.coding.class23;

import java.util.*;
import java.util.Map.Entry;
import java.util.stream.Collectors;

/**
 * 4. 超级水王问题
 * 给定一个数组arr，长度为N，如果某个数出现次数大于N/2【等于都不行】，称该数为水王数，如果arr中有水王数，打印这个数；如果没有水王数，打印没有水王数
 * 要求时间复杂度O(N)，额外空间复杂度O(1)
 * 扩展1：摩尔投票
 * 扩展2：给定一个正数K，返回所有出现次数>N/K的数
 * <p>
 * <p>
 * 解法：
 * 空间复杂度 O(1)。不能用hash表做词频统计
 * <p>
 * 对于arr数组。一次删除掉两个不同的数。如果arr里面有水王的话，这个水王最后肯定会剩下来。
 * 【就算是除水王之外的每个数都和水王PK。最后剩下的，还是水王。更何况非水王数之间还可能内战PK掉呢】
 * <p>
 * 有水王肯定会剩下来。但是剩下来的不一定是水王。【如[1,2,3,4,5] ,最后会剩下一个5 ，它不是水王】
 * <p>
 * 思路：
 * 一次删两个不同的数。
 * 如果没有数剩下来，那么就是没有水王。
 * 如果有一个以上的数剩下来【剩下的数肯定是一种数】。【假设剩下来的这个数是x】，
 * 再去遍历一遍数组，看其出现的次数。用出现的次数去和N/2对比。看看它是不是水王
 * 如果剩下来的数出现的次数都没PK过N/2 .那么就没有水王
 * <p>
 * 删除操作该怎么实现：
 * 设置两个变量，一个候选变量。第二个变量叫血量hp。初始值都是0。【表示当前候选值还多出多少】
 * 规定：当血量hp等于0的时候。候选变量无论等于什么，都认为是没有候选。
 * 变量数组
 * 1）如果无候选。当前的数被设置为候选。血量设置为1
 * 2）如果有候选
 * ①：当前数和候选不一样。血量hp减1
 * ②：当前数和候选一样。血量hp加1
 * <p>
 * 变历完成后。
 * 如果血量hp等于0，表示什么数也没剩下来
 * 如果血量hp不等于0，候选就是剩下来的数
 * <p>
 * <p>
 * <p>
 * 解释：
 * 候选就相当于一个靶子。
 * 如果新来的数不等于靶子。血量hp减1.将相当于将靶子和当前数删掉了
 * 如果新来的数等于靶子。血量hp加1.将相当于靶子的个数增加了1个
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * <p>
 * 扩展1：摩尔投票法
 * 给定一个正数K，返回所有出现次数>N/K的数. 要的是次数大于 N/K 的。所以最多有 k-1 个
 * 思路是搞 k-1 个候选。
 * 设计一个hash表。key是候选数，value是其对应的血量
 * 遍历数组。
 * 新的数来的时候。
 * 查看候选表。看看是否存在。
 * 1）不存在的话，看看候选表是否满k-1个了。
 *      ①没满的话就将新来的数加入到候选表。
 *      ②满了的话。候选表中的所有候选数，对应的血量都减1，当前数也不要【如果血量减为0的话，直接从候选表中移除】
 * 2）存在的话，就将对应的血量加1
 *
 *
 *
 * @since 2022-03-28 21:11:11
 */
public class Code04_FindKMajority {

    /**
     * 超级水王问题
     * @since 2022-03-28 21:50:45
     */
    public static void printHalfMajor(int[] arr) {
        int cand = 0;
        int HP = 0;
        for (int i = 0; i < arr.length; i++) {
            if (HP == 0) {
                cand = arr[i];
                HP = 1;
            } else if (arr[i] == cand) {
                HP++;
            } else {
                HP--;
            }
        }
        if (HP == 0) {
            System.out.println("no such number.");
            return;
        }

        // 复用hp。在这里，hp不在是血量。而是剩下来的这种数，真实出现的次数
        HP = 0;
        for (int i = 0; i < arr.length; i++) {
            if (arr[i] == cand) {
                HP++;
            }
        }
        if (HP > arr.length / 2) {
            System.out.println(cand);
        } else {
            System.out.println("no such number.");
        }
    }

    /**
     * 给定一个正数K，返回所有出现次数>N/K的数. 要的是次数大于 N/K 的。所以最多有 k-1 个
     * k等于2的时候，就是水王问题
     *
     * @since 2022-03-28 21:51:07
     */
    public static void printKMajor(int[] arr, int K) {
        if (K < 2) {
            System.out.println("the value of K is invalid.");
            return;
        }
        // 攒候选，cands，候选表，最多K-1条记录！ > N / K次的数字，最多有K-1个
        HashMap<Integer, Integer> cands = new HashMap<Integer, Integer>();
        for (int i = 0; i != arr.length; i++) {
            if (cands.containsKey(arr[i])) {
                cands.put(arr[i], cands.get(arr[i]) + 1);
            } else {
                // arr[i] 不是候选

                // 当前数肯定不要！，每一个候选付出1点血量，血量变成0的候选，要删掉！
                if (cands.size() == K - 1) {
                    allCandsMinusOne(cands);
                } else {
                    cands.put(arr[i], 1);
                }
            }
        }


        // 所有可能的候选，都在cands表中！遍历一遍arr，每个候选收集真实次数
        HashMap<Integer, Integer> reals = getReals(arr, cands);
        boolean hasPrint = false;
        for (Entry<Integer, Integer> set : cands.entrySet()) {
            Integer key = set.getKey();
            if (reals.get(key) > arr.length / K) {
                hasPrint = true;
                System.out.print(key + " ");
            }
        }

        System.out.println(hasPrint ? "" : "no such number.");
    }

    public static void allCandsMinusOne(HashMap<Integer, Integer> map) {

        // 要删除的key。为什么不直接删？因为这是迭代器，直接删会出问题的
        List<Integer> removeList = new LinkedList<>();


        for (Entry<Integer, Integer> set : map.entrySet()) {
            Integer key = set.getKey();
            Integer value = set.getValue();
            if (value == 1) {
                removeList.add(key);
            }
            map.put(key, value - 1);
        }

        for (Integer removeKey : removeList) {
            map.remove(removeKey);
        }
    }

    public static HashMap<Integer, Integer> getReals(int[] arr,
                                                     HashMap<Integer, Integer> cands) {
        HashMap<Integer, Integer> reals = new HashMap<>();
        for (int i = 0; i != arr.length; i++) {
            int curNum = arr[i];
            if (cands.containsKey(curNum)) {
                if (reals.containsKey(curNum)) {
                    reals.put(curNum, reals.get(curNum) + 1);
                } else {
                    reals.put(curNum, 1);
                }
            }
        }
        return reals;
    }

    public static void main(String[] args) {
        int[] arr = {1, 2, 3, 1, 1, 2, 1};
        printHalfMajor(arr);
        int K = 4;
        printKMajor(arr, K);
    }

}
