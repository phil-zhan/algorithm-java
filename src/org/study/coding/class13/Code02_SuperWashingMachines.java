package org.study.coding.class13;

/**
 * 本题测试链接 : <a href="https://leetcode.com/problems/super-washing-machines/">https://leetcode.com/problems/super-washing-machines/</a>
 *
 * 假设有 n台超级洗衣机放在同一排上。开始的时候，每台洗衣机内可能有一定量的衣服，也可能是空的
 * 在每一步操作中，你可以选择任意 m（1 ≤ m ≤ n）台洗衣机，与此同时将每台洗衣机的一件衣服送到相邻的一台洗衣机
 * 给定一个非负整数数组代表从左至右每台洗衣机中的衣物数量，请给出能让所有洗衣机中剩下的衣物的数量相等的最少的操作步数
 * 如果不能使每台洗衣机中衣物的数量相等，则返回-1
 * machines[i] : 表示第 i 台洗衣机中的衣服数量
 *
 * 超级洗衣机问题
 *
 * 解法：
 * 如果不能搞平均。直接返回 -1 。搞不定
 * 本题属于取巧系列
 *
 * 考虑总答案和单点拼劲的关系
 * 假设来到 index 号洗衣机。该号洗衣机上有 num 件衣服。 假设其左边有 left 台洗衣机。总共拥有 leftNum 件衣服。
 * 其右边有 right 台洗衣机。总共拥有 rightNum 件衣服。  假设最终调整后，每台洗衣机的衣服数量为 x
 * left * x 就应该是最终左边的总件数
 *
 * 1）如果 index 的左侧欠 a 件 。index的右侧富裕 b件。
 * 那么 index 位置多少轮能安静。  Math.max(a,b)
 *
 * 2）反过来也一样。左边是富裕的时候，右边是欠的时候。
 * 那么 index 位置多少轮能安静。   Math.max(a,b)
 *
 * 3）如果左侧富裕a件，右侧也富裕 b 件。
 * 那么 index 位置需要 Math.max(a,b) 轮之后才会安静。左右都要给index位置衣服。而每个位置每轮可以接收多件衣服
 *
 * 4）如果左侧欠 a件，右侧也欠 b件
 * 那么 index 位置需要 a+b 轮之后才会安静。左右都要index位置给他们衣服。而每个位置每轮只能给出去一件衣服
 *
 *
 * 先算出总共的衣服数量。
 * 再做一个累加和，累加左侧衣服的数量
 * 这样来到 index 位置的时候，就知道index位置需要多少轮才能安静下来
 * 最后抓一个最大的轮数
 *
 *
 *
 *
 *
 * @since 2022-03-14 10:46:40
 */
public class Code02_SuperWashingMachines {

	public static int findMinMoves(int[] machines) {
		if (machines == null || machines.length == 0) {
			return 0;
		}
		int size = machines.length;

		// 整体累加和
		int sum = 0;
		for (int i = 0; i < size; i++) {
			sum += machines[i];
		}

		// 要求要平均，搞不定
		if (sum % size != 0) {
			return -1;
		}
		int avg = sum / size;

		// 左侧部分的累加和
		int leftSum = 0;
		int ans = 0;
		for (int i = 0; i < machines.length; i++) {

			// 这是左右的富裕。欠的话就是负值。最后去绝对值去比较
			int leftRest = leftSum - i * avg;
			int rightRest = (sum - leftSum - machines[i]) - (size - i - 1) * avg;

			// 左右都欠
			if (leftRest < 0 && rightRest < 0) {
				ans = Math.max(ans, Math.abs(leftRest) + Math.abs(rightRest));
			} else {
				// 左欠右富裕
				// 左富裕右欠
				// 左右都富裕

				ans = Math.max(ans, Math.max(Math.abs(leftRest), Math.abs(rightRest)));
			}

			leftSum += machines[i];
		}
		return ans;
	}

}
