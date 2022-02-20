package org.study.class32;

public class Code01_IndexTree {

	// 下标从1开始！
	public static class IndexTree {

		// 前缀和数组
		private int[] tree;

		// 下标从 1 开始
		private int N;

		// 0位置弃而不用！
		public IndexTree(int size) {
			N = size;
			tree = new int[N + 1];
		}

		// 1~index 累加和是多少？
		public int sum(int index) {
			int ret = 0;
			while (index > 0) {
				ret += tree[index];

				// 前一个管不到的位置
				index -= index & -index;
			}
			return ret;
		}

		// index & -index : 提取出index最右侧的1出来
		// index :           0011001000
		// index & -index :  0000001000

		// index :    			0011001000
		// ~index :   			1100110111  【取反之后。0变1,1变0】
		// ~index+1 : 			1100111000  【取反之后加一，也就是其相反数】
		// (~index+1) & index : 0000001000  【相反数与原数进行与计算】

		public void add(int index, int d) {
			while (index <= N) {
				tree[index] += d;

				// 从 index 开始。前缀和数组的受到牵连的数组都要调整
				// 原本的数，再将其最右侧的 1 再加一遍，就是受到牵连的数
				// 【原来的前缀和数组所管的范围的开始位置是 抹掉最后一个 1 的下一个位置。也就是一个前开后闭的区间】
				// 后一个会管到自己的位置
				index += index & -index;
			}
		}
	}

	public static class Right {
		private int[] nums;
		private int N;

		public Right(int size) {
			N = size + 1;
			nums = new int[N + 1];
		}

		public int sum(int index) {
			int ret = 0;
			for (int i = 1; i <= index; i++) {
				ret += nums[i];
			}
			return ret;
		}

		public void add(int index, int d) {
			nums[index] += d;
		}

	}

	public static void main(String[] args) {
		int N = 100;
		int V = 100;
		int testTime = 2000000;
		IndexTree tree = new IndexTree(N);
		Right test = new Right(N);
		System.out.println("test begin");
		for (int i = 0; i < testTime; i++) {
			int index = (int) (Math.random() * N) + 1;
			if (Math.random() <= 0.5) {
				int add = (int) (Math.random() * V);
				tree.add(index, add);
				test.add(index, add);
			} else {
				if (tree.sum(index) != test.sum(index)) {
					System.out.println("Oops!");
				}
			}
		}
		System.out.println("test finish");
	}

}
