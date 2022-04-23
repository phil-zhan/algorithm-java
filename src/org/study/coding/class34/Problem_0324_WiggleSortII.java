package org.study.coding.class34;

/**
 * 324. 摆动排序 II
 * 给你一个整数数组 nums，将它重新排列成 nums[0] < nums[1] > nums[2] < nums[3]... 的顺序。
 *
 * 你可以假设所有输入数组都可以得到满足题目要求的结果。
 *
 *
 * 示例 1：
 *
 * 输入：nums = [1,5,1,1,6,4]
 * 输出：[1,6,1,5,1,4]
 * 解释：[1,4,1,5,1,6] 同样是符合题目要求的结果，可以被判题程序接受。
 * 示例 2：
 *
 * 输入：nums = [1,3,2,2,3,1]
 * 输出：[2,3,1,3,1,2]
 *
 *
 * 本题超级难。但是又高频。要搞明白
 * 本题能做到时间复杂度 O(N) 。空间复杂度 O(1)
 *
 *
 * 解法：
 * 1）將N/2 作为目标数，去走一步随机快跑。【小于目标数的放在左边。等于目标数的放在中间，大于目标数的放在右边】
 * 利用完美洗牌，将第一步的结果，跑完完美洗牌[L1 ,L2 ,L3 ,L4 ,R1 ,R2 ,R3 ,R4]=>[R1 ,L1 ,R2 ,L2 ,L3 ,L3 ,R4 ,L4]
 * 【完美洗牌在coding23】
 * 2-1）如果是偶数个的话，再将洗牌结果逆序过来【要的是小大交替。洗牌实现的是R在前，会是大小交替】
 * 如：[3,1,4,4,  4,6,8,7] ==> [4, 3, 6, 1, 8, 4, 7, 4] ==> [4, 7, 4, 8, 1, 6, 3, 4]
 *
 * 2-2）如果是奇数个最左边的那个（假设是L0）先不动，将右边的偶数个实现完美洗牌即可
 * 如:[0,3,1,4,4  ,4,6,8,7] ==> [0, 4, 3, 6, 1,   8, 4, 7, 4]
 *
 * 3）如果这个方法都做不到 小大小大 交替的话。其他方法也不可能。【该怎么证明呢？】【可以考虑用暴力解的对数器来做】
 * 【题目给定的数组是一定可以实现的。如果可能出现实现不了的，就再遍历一次，卡看结果】
 *
 *
 * @since 2022-04-21 22:51:13
 */
public class Problem_0324_WiggleSortII {

	// 时间复杂度O(N)，额外空间复杂度O(1)
	public static void wiggleSort(int[] nums) {
		if (nums == null || nums.length < 2) {
			return;
		}
		int N = nums.length;
		// 小 中 右  【跑一次随机快排】
		findIndexNum(nums, 0, nums.length - 1, N / 2);

		// 偶数长度
		if ((N & 1) == 0) {
			// R L -> L R
			// 先洗牌【洗牌的结果是先R再L 。 而我们要的是先L 再R】
			shuffle(nums, 0, nums.length - 1);
			// R1 L1 R2 L2 R3 L3 R4 L4
			// L4 R4 L3 R3 L2 R2 L1 R1 -> 代码中的方式，可以的！
			// L1 R1 L2 R2 L3 R3 L4 R4 -> 课上的分析，是不行的！不能两两交换！
			reverse(nums, 0, nums.length - 1);
			// 做个实验，如果把上一行的code注释掉(reverse过程)，然后跑下面注释掉的for循环代码
			// for循环的代码就是两两交换，会发现对数器报错，说明两两交换是不行的, 必须整体逆序
//			for (int i = 0; i < nums.length; i += 2) {
//				swap(nums, i, i + 1);
//			}
		} else {
			// 奇数长度。1...n-1 范围上洗牌就行
			shuffle(nums, 1, nums.length - 1);
		}
	}

	public static int findIndexNum(int[] arr, int L, int R, int index) {
		int pivot = 0;
		int[] range = null;
		while (L < R) {
			pivot = arr[L + (int) (Math.random() * (R - L + 1))];
			range = partition(arr, L, R, pivot);
			if (index >= range[0] && index <= range[1]) {
				return arr[index];
			} else if (index < range[0]) {
				R = range[0] - 1;
			} else {
				L = range[1] + 1;
			}
		}
		return arr[L];
	}

	public static int[] partition(int[] arr, int L, int R, int pivot) {
		int less = L - 1;
		int more = R + 1;
		int cur = L;
		while (cur < more) {
			if (arr[cur] < pivot) {
				swap(arr, ++less, cur++);
			} else if (arr[cur] > pivot) {
				swap(arr, cur, --more);
			} else {
				cur++;
			}
		}
		return new int[] { less + 1, more - 1 };
	}

	public static void shuffle(int[] nums, int l, int r) {
		while (r - l + 1 > 0) {
			int lenAndOne = r - l + 2;
			int bloom = 3;
			int k = 1;
			while (bloom <= lenAndOne / 3) {
				bloom *= 3;
				k++;
			}
			int m = (bloom - 1) / 2;
			int mid = (l + r) / 2;
			rotate(nums, l + m, mid, mid + m);
			cycles(nums, l - 1, bloom, k);
			l = l + bloom - 1;
		}
	}

	public static void cycles(int[] nums, int base, int bloom, int k) {
		for (int i = 0, trigger = 1; i < k; i++, trigger *= 3) {
			int next = (2 * trigger) % bloom;
			int cur = next;
			int record = nums[next + base];
			int tmp = 0;
			nums[next + base] = nums[trigger + base];
			while (cur != trigger) {
				next = (2 * cur) % bloom;
				tmp = nums[next + base];
				nums[next + base] = record;
				cur = next;
				record = tmp;
			}
		}
	}

	public static void rotate(int[] arr, int l, int m, int r) {
		reverse(arr, l, m);
		reverse(arr, m + 1, r);
		reverse(arr, l, r);
	}

	public static void reverse(int[] arr, int l, int r) {
		while (l < r) {
			swap(arr, l++, r--);
		}
	}

	public static void swap(int[] nums, int i, int j) {
		int tmp = nums[i];
		nums[i] = nums[j];
		nums[j] = tmp;
	}

	// 为了测试，暴力方法
	// 把arr全排列尝试一遍，
	// 其中任何一个排列能做到 < > < > ... 返回true;
	// 如果没有任何一个排列能做到，返回false;
	public static boolean test(int[] arr) {
		return process(arr, 0);
	}

	// 为了测试
	public static boolean process(int[] arr, int index) {
		if (index == arr.length) {
			return valid(arr);
		}
		for (int i = index; i < arr.length; i++) {
			swap(arr, index, i);
			if (process(arr, index + 1)) {
				return true;
			}
			swap(arr, index, i);
		}
		return false;
	}

	// 为了测试
	public static boolean valid(int[] arr) {
		boolean more = true;
		for (int i = 1; i < arr.length; i++) {
			if ((more && arr[i - 1] >= arr[i]) || (!more && arr[i - 1] <= arr[i])) {
				return false;
			}
			more = !more;
		}
		return true;
	}

	// 为了测试
	public static int[] randomArray(int n, int v) {
		int[] ans = new int[n];
		for (int i = 0; i < n; i++) {
			ans[i] = (int) (Math.random() * v);
		}
		return ans;
	}

	// 为了测试
	public static int[] copyArray(int[] arr) {
		int[] ans = new int[arr.length];
		for (int i = 0; i < arr.length; i++) {
			ans[i] = arr[i];
		}
		return ans;
	}

	// 为了测试
	public static void main(String[] args) {
		int N = 10;
		int V = 10;
		int testTime = 10000;
		System.out.println("测试开始");
		for (int i = 0; i < testTime; i++) {
			int n = (int) (Math.random() * N) + 1;
			int[] arr1 = randomArray(n, V);
			int[] arr2 = copyArray(arr1);
			wiggleSort(arr1);
			if (valid(arr1) != test(arr2)) {
				System.out.println("出错了!");
			}
		}
		System.out.println("测试结束");
	}

}
