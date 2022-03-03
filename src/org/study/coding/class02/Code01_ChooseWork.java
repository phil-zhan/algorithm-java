package org.study.coding.class02;

import java.util.Arrays;
import java.util.Comparator;
import java.util.TreeMap;

public class Code01_ChooseWork {

	public static class Job {
		public int money;
		public int hard;

		public Job(int m, int h) {
			money = m;
			hard = h;
		}
	}

	/**
	 * 先根据难度排序
	 * 难度一样的情况下，根据报酬排序
	 * 【难度升序。报酬降序】
	 * 在每一组里面，第一份工作肯定是最好的，难度最低，钱最多
	 * @since 2022-03-01 09:50:14
	 */
	public static class JobComparator implements Comparator<Job> {
		@Override
		public int compare(Job o1, Job o2) {
			return o1.hard != o2.hard ? (o1.hard - o2.hard) : (o2.money - o1.money);
		}
	}

	/**
	 * 相同难度的，只保留报酬高的
	 * 难度提高，报酬反而降低的，也去掉
	 *
	 * 留下的就是随着难度提高，收入也提高的工作
	 * 每个人来的时候，直接采用二分法，找出自己能胜任的，待遇最高的工作
	 * 也就是找到难度小于等于自己能力，离自己能力最近的工作
	 *
	 * 也可以用有序表来做【Java里就是TreeMap】
	 *
	 * @since 2022-03-01 10:06:03
	 */
	public static int[] getMoneys(Job[] job, int[] ability) {
		Arrays.sort(job, new JobComparator());
		// key : 难度   value：报酬
		// 删掉的工作不进这个表【该表中，难度上升，报酬必定上升】
		TreeMap<Integer, Integer> map = new TreeMap<>();
		map.put(job[0].hard, job[0].money);
		// pre : 上一份进入map的工作
		Job pre = job[0];
		for (int i = 1; i < job.length; i++) {

			// 难度不一样且报酬比前一个高
			// 难度一样的情况下，是按照报酬降序排序的，前面的已经加入有序表了，当前这个直接舍弃
			// 是先按照难度升序排序的
			// 当前的难度一定比之前的难度高。如果当前的报酬还没之前的多，也不需要加入有序表
			if (job[i].hard != pre.hard && job[i].money > pre.money) {
				pre = job[i];
				map.put(pre.hard, pre.money);
			}
		}
		int[] ans = new int[ability.length];
		for (int i = 0; i < ability.length; i++) {
			// ability[i] 当前人的能力 <= ability[i]  且离它最近的
			Integer key = map.floorKey(ability[i]);
			ans[i] = key != null ? map.get(key) : 0;
		}
		return ans;
	}

}
