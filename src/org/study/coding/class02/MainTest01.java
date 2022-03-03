package org.study.coding.class02;

import java.util.Arrays;
import java.util.TreeMap;

/**
 * @author phil
 * @since 2022-0303 07:54:23
 */
public class MainTest01 {

    public static class Job {
        public int hard;
        public int money;

        public Job(int hard, int money) {
            this.hard = hard;
            this.money = money;
        }
    }

    public int[] getMoneys(int[] hard, int[] money, int[] ability) {
        if (hard == null || hard.length == 0 ||
                money == null || money.length != hard.length ||
                ability == null || ability.length == 0) {

            return null;
        }

        // package
        Job[] jobs = new Job[hard.length];
        for (int i = 0; i < hard.length; i++) {
            jobs[i] = new Job(hard[i], money[i]);
        }

        // sort
        Arrays.sort(jobs, ((o1, o2) -> o1.hard != o2.hard ? o1.hard - o2.hard : o2.money - o1.money));


        // selectable
        TreeMap<Integer,Integer> treeMap = new TreeMap<>();
        treeMap.put(jobs[0].hard,jobs[0].money);

        Job pre = jobs[0];
        for (int i = 1; i < jobs.length; i++) {
            if (jobs[i].hard != pre.hard && jobs[i].money > pre.money){
                treeMap.put(jobs[i].hard,jobs[i].money);
                pre = jobs[i];
            }
        }

        // ans
        int[] ans = new int[ability.length];
        for (int i = 0; i < ability.length; i++) {
            Integer key = treeMap.floorKey(ability[i]);
            ans[i] = key != null ? treeMap.get(key) : 0;
        }

        return ans;
    }
}
