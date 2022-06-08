package org.study.coding.class38;

// 360笔试题
// 长城守卫军
// 题目描述：
// 长城上有连成一排的n个烽火台，每个烽火台都有士兵驻守。
// 第i个烽火台驻守着ai个士兵，相邻峰火台的距离为1。另外，有m位将军，
// 每位将军可以驻守一个峰火台，每个烽火台可以有多个将军驻守，
// 将军可以影响所有距离他驻守的峰火台小于等于x的烽火台。
// 每个烽火台的基础战斗力为士兵数，另外，每个能影响此烽火台的将军都能使这个烽火台的战斗力提升k。
// 长城的战斗力为所有烽火台的战斗力的最小值。
// 请问长城的最大战斗力可以是多少？
// 输入描述
// 第一行四个正整数n,m,x,k(1<=x<=n<=10^5,0<=m<=10^5,1<=k<=10^5)
// 第二行n个整数ai(0<=ai<=10^5)
// 输出描述 仅一行，一个整数，表示长城的最大战斗力
// 样例输入
// 5 2 1 2
// 4 4 2 4 4
// 样例输出
// 6
public class Code02_GreatWall {

	/**
	 * 解法：
	 * 1）二分答案
	 * 2）类似AOE魔法师问题
	 * 3）线段树
	 *
	 *
	 * 考虑，如果要让数组中的每一个位置都大于等于规定的limit。至少需要几位将军
	 * 这是有单调性的【limit和将军数量】
	 *
	 * limit 的最大值：  最高战斗力+所有将军的加成【也就是左右将军都给最高点加成】
	 * limit 的最小值：  也就是数组中的最小值。【所有将军都不给最低点加成】
	 *
	 * 然后再在这个范围内二分。
	 * 看看将战斗力提升到 limit/2 能不能m个将军以内拿下。
	 * 能：就往右边二分。
	 * 不能：就往左边二分
	 *
	 * 最后找到能拿下的最右侧，也就是找到能拿下的最高战力。
	 *
	 *
	 * @since 2022-06-04 11:26:38
	 */
	public static int maxForce(int[] wall, int m, int x, int k) {
		long L = 0;
		long R = 0;
		for (int num : wall) {
			R = Math.max(R, num);
		}
		R += m * k;
		long ans = 0;
		while (L <= R) {
			long M = (L + R) / 2;
			if (can(wall, m, x, k, M)) {
				ans = M;
				L = M + 1;
			} else {
				R = M - 1;
			}
		}
		return (int) ans;
	}

	/**
	 * 长城上每个点的战斗力 wall
	 * 一共有 m 个将军
	 * 每个将军影响的直径 x
	 * 每个将军的战斗力加成 k
	 *
	 * 返回，在将军数量不超过 m 的情况下。能不能让长城上每个点的战斗力都大于等于 limit
	 *
	 *
	 * 遍历数组。来到 i 位置。看看 i 位置是否需要加成 wall[i] 是否大于等于 limit
	 *
	 * 如果 i 位置不需要加成，就直接跳过
	 * 如果 i 位置需要加成，就看看至少需要几个将军加成 wall[i]/k 【向上取整】
	 *
	 * 那么怎么加成才能最经济呢。也就是让将军的作用最大化。
	 * 可以让将军的左边界刚好覆盖到 i 位置即可【因为 i 的左边是不需要加成的。i 的右边可能需要加成】。
	 *
	 * 这里涉及到区间上的修改【范围上的数修改】  【线段树】
	 *
	 * 如果将军的数量用完了。但是还有需要加成的点。就直接返回 false。
	 *
	 *
	 * @since 2022-06-04 11:32:40
	 */
	public static boolean can(int[] wall, int m, int x, int k, long limit) {
		int N = wall.length;
		// 注意：下标从1开始
		SegmentTree st = new SegmentTree(wall);
		st.build(1, N, 1);
		int need = 0;
		for (int i = 0; i < N; i++) {
			// 注意：下标从1开始
			long cur = st.query(i + 1, i + 1, 1, N, 1);
			if (cur < limit) {
				int add = (int) ((limit - cur + k - 1) / k);
				need += add;
				if (need > m) {
					return false;
				}
				st.add(i + 1, Math.min(i + x, N), add * k, 1, N, 1);
			}
		}
		return true;
	}

	public static class SegmentTree {
		private int MAXN;
		private int[] arr;
		private int[] sum;
		private int[] lazy;
		private int[] change;
		private boolean[] update;

		public SegmentTree(int[] origin) {
			MAXN = origin.length + 1;
			arr = new int[MAXN];
			for (int i = 1; i < MAXN; i++) {
				arr[i] = origin[i - 1];
			}
			sum = new int[MAXN << 2];
			lazy = new int[MAXN << 2];
			change = new int[MAXN << 2];
			update = new boolean[MAXN << 2];
		}

		private void pushUp(int rt) {
			sum[rt] = sum[rt << 1] + sum[rt << 1 | 1];
		}

		private void pushDown(int rt, int ln, int rn) {
			if (update[rt]) {
				update[rt << 1] = true;
				update[rt << 1 | 1] = true;
				change[rt << 1] = change[rt];
				change[rt << 1 | 1] = change[rt];
				lazy[rt << 1] = 0;
				lazy[rt << 1 | 1] = 0;
				sum[rt << 1] = change[rt] * ln;
				sum[rt << 1 | 1] = change[rt] * rn;
				update[rt] = false;
			}
			if (lazy[rt] != 0) {
				lazy[rt << 1] += lazy[rt];
				sum[rt << 1] += lazy[rt] * ln;
				lazy[rt << 1 | 1] += lazy[rt];
				sum[rt << 1 | 1] += lazy[rt] * rn;
				lazy[rt] = 0;
			}
		}

		public void build(int l, int r, int rt) {
			if (l == r) {
				sum[rt] = arr[l];
				return;
			}
			int mid = (l + r) >> 1;
			build(l, mid, rt << 1);
			build(mid + 1, r, rt << 1 | 1);
			pushUp(rt);
		}

		public void update(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				update[rt] = true;
				change[rt] = C;
				sum[rt] = C * (r - l + 1);
				lazy[rt] = 0;
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) {
				update(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				update(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		public void add(int L, int R, int C, int l, int r, int rt) {
			if (L <= l && r <= R) {
				sum[rt] += C * (r - l + 1);
				lazy[rt] += C;
				return;
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			if (L <= mid) {
				add(L, R, C, l, mid, rt << 1);
			}
			if (R > mid) {
				add(L, R, C, mid + 1, r, rt << 1 | 1);
			}
			pushUp(rt);
		}

		public long query(int L, int R, int l, int r, int rt) {
			if (L <= l && r <= R) {
				return sum[rt];
			}
			int mid = (l + r) >> 1;
			pushDown(rt, mid - l + 1, r - mid);
			long ans = 0;
			if (L <= mid) {
				ans += query(L, R, l, mid, rt << 1);
			}
			if (R > mid) {
				ans += query(L, R, mid + 1, r, rt << 1 | 1);
			}
			return ans;
		}

	}

	public static void main(String[] args) {
		int[] wall = { 3, 1, 1, 1, 3 };
		int m = 2;
		int x = 3;
		int k = 1;
		System.out.println(maxForce(wall, m, x, k));
	}

}
