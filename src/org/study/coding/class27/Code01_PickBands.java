package org.study.coding.class27;

import java.util.Arrays;

/**
 * 1. 每一个项目都有三个数，[a,b,c]表示这个项目a和b乐队参演，花费为c
 *    每一个乐队可能在多个项目里都出现了，但是只能被挑一次
 *    nums是可以挑选的项目数量，所以一定会有nums*2只乐队被挑选出来
 *    返回一共挑nums轮(也就意味着一定请到所有的乐队)，最少花费是多少？
 *    如果怎么都无法在nums轮请到nums*2只乐队且每只乐队只能被挑一次，返回-1
 *    nums<9，programs长度小于500，每组测试乐队的全部数量一定是nums*2，且标号一定是0 ~ nums*2-1
 *
 * 题意：
 *    每一个项目可能是不同的乐队来表演，也可能是同样的两个乐队来表演，但是每一个项目都会有自己的报价
 *    所挑选的项目中，每个乐队只能出现一次
 *    每个项目只会是两个乐队参演
 * 	  也可能所给的数据挑不足num个项目。此时返回-1
 * 	  否则就挑num个项目，返回最小的花费
 *
 * 解法：
 * 1、先调整一下项目
 * 每个项目，都调整为乐队编号较小的在前面，乐队编号较大的在中间。报价放到最后【避免掉乐队调换位置的情况】
 *
 * 2、对项目排序：
 * 第一维数据从小到大。第一维一样的，按照第二维从小到大，第二维也一样的，按照第三维从小到大
 *
 * 3、数据清洗
 * 排序后，前两项一样的，只保留第一个，其他都删掉
 *
 *
 * 4、算法核心
 * 暴力解：
 * 从左往右的尝试模型
 * 来到i位置的时候，考虑该位置要不要
 * 需要记录还剩下多少个项目可以挑选
 * 需要记录哪些乐队挑了，哪些乐队没挑【可以考虑用int的二进制信息，来表示某个乐队是不是被挑选过了】
 *
 * 最优解：
 * 暴力情况下，采用分治法
 *
 * @since 2022-04-10 20:47:34
 */
public class Code01_PickBands {


	/**
	 * 每一个项目都有三个数，[a,b,c]表示这个项目a和b乐队参演，花费为c
	 * 每一个乐队可能在多个项目里都出现了，但是只能挑一次
	 * nums是可以挑选的项目数量，所以一定会有nums*2只乐队被挑选出来
	 * 乐队的全部数量一定是nums*2，且标号一定是0 ~ nums*2-1
	 * 返回一共挑nums轮(也就意味着一定请到所有的乐队)，最少花费是多少？
	 *
	 * 最优解
	 *
	 *
	 * @since 2022-04-10 20:48:25
	 */
	public static int minCost(int[][] programs, int nums) {
		if (nums == 0 || programs == null || programs.length == 0) {
			return 0;
		}

		// 清理数据。拿到剩下的有效长度 programs[0...size-1] 有效
		int size = clean(programs);

		// 初始化【初始化都是系统最大值】
		// map1[i]表示i对应的二进制有多少个位是1，就代表其对应1对应的位的乐队被选中，map1[i]就是选中这些乐队的最小代价
		int[] map1 = init(1 << (nums << 1));

		// map2[i]：是map1取反，也就是如果总共8只乐队，要挑4个项目，map1[15]=map[0000 1111]=x 。也就是0、1、2、3 号球队被挑选【对应两个项目】的最小代价是map1[15]
		// map2[i]:在num是偶数的情况下，和map1是一样的，也表示i（对应二进制位）只乐队被挑选的最小代价
		// 在num是奇数的情况下，map2要少一个项目。也就对应的少两只乐队
		int[] map2 = null;
		if ((nums & 1) == 0) {
			// 偶数个项目
			// nums = 8 , 4
			// fun 只是搞定挑一半的项目，得到挑选对应的乐队组合 及其该组合下的最小代价
			// num
			fun(programs, size, 0, 0, 0, nums >> 1, map1);
			map2 = map1;
		} else {
			// 奇数个项目
			// nums == 7
			// 4 -> map1 【8只乐队被挑选，最小的代缴是多少】【8只，可以是二进制上的不同的8个位是1】
			// 3 -> map2 【6只乐队被挑选，最小的代缴是多少】【6只，可以是二进制上的不同的6个位是1】
			fun(programs, size, 0, 0, 0, nums >> 1, map1);
			map2 = init(1 << (nums << 1));
			fun(programs, size, 0, 0, 0, nums - (nums >> 1), map2);
		}
		// 16 mask : 0..00 1111.1111(16个1)
		// 12 mask : 0..00 1111.1111(12个1)
		// mask就是乐队的所有编号。在有的位上全标1
		int mask = (1 << (nums << 1)) - 1;
		int ans = Integer.MAX_VALUE;
		for (int i = 0; i < map1.length; i++) {

			// 一半的队答案是有的。另外一半的队答案也是有的【那么，当前组合就是答案的一种可能性】
			// 比如前面选中的是0、1、2、3号乐队。  后面就要选中5、6、7、8号乐队才算了事
			// 所有的可能性中抓一个最小的
			if (map1[i] != Integer.MAX_VALUE && map2[mask & (~i)] != Integer.MAX_VALUE) {
				ans = Math.min(ans, map1[i] + map2[mask & (~i)]);
			}
		}
		return ans == Integer.MAX_VALUE ? -1 : ans;
	}


	/**
	 * [
	 * 	[9, 1, 100]
	 * 	[2, 9 , 50]
	 * 	...
	 * ]
	 *
	 * @since 2022-04-10 20:49:07
	 */
	public static int clean(int[][] programs) {
		int x = 0;
		int y = 0;

		// 先调整一下项目
		for (int[] p : programs) {
			x = Math.min(p[0], p[1]);
			y = Math.max(p[0], p[1]);
			p[0] = x;
			p[1] = y;
		}

		// 排序
		Arrays.sort(programs, (a, b) -> a[0] != b[0] ? (a[0] - b[0]) : (a[1] != b[1] ? (a[1] - b[1]) : (a[2] - b[2])));

		// 清洗数据
		x = programs[0][0];
		y = programs[0][1];
		int n = programs.length;
		// (0, 1, ? )

		// 从 1 号项目开始，如果第一维和第二维都一样，直接将这个项目丢掉
		for (int i = 1; i < n; i++) {
			if (programs[i][0] == x && programs[i][1] == y) {
				programs[i] = null;
			} else {
				// 更新作为比较的第一维个第二维
				x = programs[i][0];
				y = programs[i][1];
			}
		}

		// 将标空的数据丢掉
		int size = 1;
		for (int i = 1; i < n; i++) {
			if (programs[i] != null) {
				programs[size++] = programs[i];
			}
		}
		// programs[0...size-1]
		return size;
	}

	public static int[] init(int size) {
		int[] arr = new int[size];
		for (int i = 0; i < size; i++) {
			arr[i] = Integer.MAX_VALUE;
		}
		return arr;
	}

	public static void fun(int[][] programs, int size, int index, int status, int cost, int rest, int[] map) {
		if (rest == 0) {
			map[status] = Math.min(map[status], cost);
		} else {
			if (index != size) {
				fun(programs, size, index + 1, status, cost, rest, map);
				int pick = 0 | (1 << programs[index][0]) | (1 << programs[index][1]);
				if ((pick & status) == 0) {
					fun(programs, size, index + 1, status | pick, cost + programs[index][2], rest - 1, map);
				}
			}
		}
	}

//	// 如果nums，2 * nums 只乐队
//	// (1 << (nums << 1)) - 1  【这是挑num个项目，需要的乐队数。乐队数挑满才算挑完，不能多也不能少】
//	// programs 洗数据 size
//	// nums = 8   16只乐队
//	
//	// process(programs, size, (1 << (nums << 1)) - 1, 0, 4, 0, 0)
//
//	// 记录全局最小花费
//	public static int minCost = Integer.MAX_VALUE;
//
//	// map初始化全变成系统最大
//	// 这个map是可以表示下后16位所有可能出现的状态的
//	// map[i]：i只乐队被选出来，最小代价是map[i] 【这里的i表示的是二进制里面对应的位。比如 9=0000 1001 ，表示的是0号和3号乐队被选出来，最小代价是map[9]】
//	// 这里可能不止选两只乐队，肯定是偶数只乐队
//	public static int[] map = new int[1 << 16];
//	
//	
//  // 暴力解：
//	// 当前在考虑index位置的项目
//	// 剩余rest个项目可以挑
//	// pick是当前挑选的乐队数
//	// cost是之前的选择带来的花费
//	// map
//	public static void process(int[][] programs, int size, int index, int rest, int pick, int cost) {
//		if (rest == 0) {
//			
//			map[pick] = Math.min(map[pick], cost);
//			
//		} else {
// 			// 还有项目可挑
//
//			if (index != size) {
//				// 不考虑当前的项目！programs[index];
//				process(programs, size, index + 1, rest, pick, cost);
//				// 考虑当前的项目！programs[index];
//				int x = programs[index][0];
//				int y = programs[index][1];
//				int cur = (1 << x) | (1 << y);
//				if ((pick & cur) == 0) { // 终于可以考虑了！【&结算后等于0，说明之前都没选择个这两个项目】
//					process(programs, size, index + 1, rest - 1, pick | cur, cost + programs[index][2]);
//				}
//			}
//		}
//	}
//	
//	
//	public static void zuo(int[] arr, int index, int rest) {
//		if(rest == 0) {
//			停止
//		}
//		if(index != arr.length) {
//			zuo(arr, index + 1, rest);
//			zuo(arr, index + 1, rest - 1);
//		}
//	}

	// 为了测试
	public static int right(int[][] programs, int nums) {
		min = Integer.MAX_VALUE;
		r(programs, 0, nums, 0, 0);
		return min == Integer.MAX_VALUE ? -1 : min;
	}

	public static int min = Integer.MAX_VALUE;

	public static void r(int[][] programs, int index, int rest, int pick, int cost) {
		if (rest == 0) {
			min = Math.min(min, cost);
		} else {
			if (index < programs.length) {
				r(programs, index + 1, rest, pick, cost);
				int cur = (1 << programs[index][0]) | (1 << programs[index][1]);
				if ((pick & cur) == 0) {
					r(programs, index + 1, rest - 1, pick | cur, cost + programs[index][2]);
				}
			}
		}
	}

	// 为了测试
	public static int[][] randomPrograms(int N, int V) {
		int nums = N << 1;
		int n = nums * (nums - 1);
		int[][] programs = new int[n][3];
		for (int i = 0; i < n; i++) {
			int a = (int) (Math.random() * nums);
			int b = 0;
			do {
				b = (int) (Math.random() * nums);
			} while (b == a);
			programs[i][0] = a;
			programs[i][1] = b;
			programs[i][2] = (int) (Math.random() * V) + 1;
		}
		return programs;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 4;
		int V = 100;
		int T = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < T; i++) {
			int nums = (int) (Math.random() * N) + 1;
			int[][] programs = randomPrograms(nums, V);
			int ans1 = right(programs, nums);
			int ans2 = minCost(programs, nums);
			if (ans1 != ans2) {
				System.out.println("Oops!");
				break;
			}
		}
		System.out.println("测试结束");

		long start;
		long end;
		int[][] programs;

		programs = randomPrograms(7, V);
		start = System.currentTimeMillis();
		right(programs, 7);
		end = System.currentTimeMillis();
		System.out.println("right方法，在nums=7时候的运行时间(毫秒) : " + (end - start));

		programs = randomPrograms(10, V);
		start = System.currentTimeMillis();
		minCost(programs, 10);
		end = System.currentTimeMillis();
		System.out.println("minCost方法，在nums=10时候的运行时间(毫秒) : " + (end - start));

	}

}
