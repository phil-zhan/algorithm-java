package org.study.coding.class33;

/**
 * 这是应该付费题
 * 给定一个参数n。表示有0...n-1 这n个人。0...n-1 是人的编号
 * 它会提供一个函数。
 * knows(int x, int i) ： 表示 i 是否认识 j。 认识就会返回true。不认识就会返回false
 * i 认识 j 。不代表 j 也认识 i
 * 任何人都会认识自己
 *
 * 定义一个明星：
 * 1）除自己之外的所有人都认识自己
 * 2）自己只认识自己，其他人一个都不认识
 *
 * 请你确定 0...n-1 是否存在明星。存在就返回明星的序号。不存在就返回 -1
 *
 * 思考：
 * 有明星的话，最多之后有一个。不可能有多个。也可能没明星
 * 这道题。要的是尽可能少的调用 knows 方法
 *
 *
 *
 *
 * @since 2022-04-21 00:01:04
 */
public class Problem_0277_FindTheCelebrity {

	/**
	 * 提交时不要提交这个函数，因为默认系统会给你这个函数
	 * knows方法，自己不认识自己
	 * 这里只是一个模拟方法。系统提供的可能不是这样
	 *
	 * @since 2022-04-21 00:00:33
	 */
	public static boolean knows(int x, int i) {
		return true;
	}

	/**
	 * 只提交下面的方法 0 ~ n-1
	 *
	 * @since 2022-04-21 00:00:48
	 */
	public int findCelebrity(int n) {
		// 谁可能成为明星，谁就是cand
		// 默认0是可能成为候选
		int cand = 0;
		for (int i = 0; i < n; ++i) {

			// 如果候选认识i。i就变成候选【所有人都要认识明星。但是明星不认识其他人】
			// 如果前面的候选认识i。那么前面的候选就不再是候选了。【明星不认识别人】
			// 如果前面的数不认识i。那么i就不会是明星了【所有人都要认识明星】【有人不认识自己。自己就不再是明星了】
			if (knows(cand, i)) {
				cand = i;
			}
		}
		// 到这里。cand就是唯一可能是明星的人！【因为其他的全被否了（之前的和之后的都否了）】



		// 下一步就是验证，它到底是不是明星
		// 1) cand是不是不认识所有的人 cand...（右侧cand都不认识）
		// 所以，只用验证 ....cand的左侧即可
		for (int i = 0; i < cand; ++i) {
			if (knows(cand, i)) {
				return -1;
			}
		}


		// 2) 是不是所有的人都认识cand
		for (int i = 0; i < n; ++i) {
			if (!knows(i, cand)) {
				return -1;
			}
		}
		return cand;
	}

}
