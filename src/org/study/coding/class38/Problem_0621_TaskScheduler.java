package org.study.coding.class38;

/**
 * https://leetcode.cn/problems/task-scheduler/
 *
 *
 * @since 2022-06-02 21:10:41
 */
public class Problem_0621_TaskScheduler {

	/**
	 *
	 * 情况1：词频最多的那个的次数是唯一的
	 * 按照词频从高到低的去排列
	 * 最后将空出来的位 用时间片补上
	 *
	 * 也就是按照间隔时间要求。先安排词频最多的
	 * 见图
	 *
	 * 以上是中间空位没耗尽的情况
	 * ================================
	 * 情况会耗尽的情况如下
	 * 直接在每一组的后面插入就好了
	 *
	 *
	 *
	 * 情况2：词频最多的那个的次数是不唯一
	 * 那么就把词频最多的那几个字符捆绑起来作为一堆，优先去把这一堆排好。中间记得留出规定的时间间隔
	 *
	 *
	 * @since 2022-06-02 21:25:57
	 */
	// ['A', 'B', 'A']
	public static int leastInterval(char[] tasks, int free) {
		int[] count = new int[256];
		// 出现最多次的任务，到底是出现了几次
		int maxCount = 0;
		for (char task : tasks) {
			count[task]++;
			maxCount = Math.max(maxCount, count[task]);
		}
		// 有多少种任务，都出现最多次
		int maxKinds = 0;
		for (int task = 0; task < 256; task++) {
			if (count[task] == maxCount) {
				maxKinds++;
			}
		}
		// maxKinds : 有多少种任务，都出现最多次
		// maxCount : 最多次，是几次？
		// 砍掉最后一组剩余的任务数
		int tasksExceptFinalTeam = tasks.length - maxKinds;
		int spaces = (free + 1) * (maxCount - 1);
		// 到底几个空格最终会留下！
		int restSpaces = Math.max(0, spaces - tasksExceptFinalTeam);
		return tasks.length + restSpaces;
		// return Math.max(tasks.length, ((n + 1) * (maxCount - 1) + maxKinds));
	}
	

}
