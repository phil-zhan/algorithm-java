package org.study.system.class29;

public class Code03_ReservoirSampling {

	public static class RandomBox {
		private int[] bag;
		private int N;
		private int count;

		public RandomBox(int capacity) {
			bag = new int[capacity];
			N = capacity;
			count = 0;
		}

		private int rand(int max) {
			return (int) (Math.random() * max) + 1;
		}

		public void add(int num) {
			count++;
			if (count <= N) {
				bag[count - 1] = num;
			} else {
				if (rand(count) <= N) {
					bag[rand(N) - 1] = num;
				}
			}
		}

		public int[] choices() {
			int[] ans = new int[N];
			for (int i = 0; i < N; i++) {
				ans[i] = bag[i];
			}
			return ans;
		}

	}

	// 请等概率返回1~i中的一个数字
	public static int random(int i) {
		return (int) (Math.random() * i) + 1;
	}

	public static void main(String[] args) {
		System.out.println("hello");
		int test = 100000;
		int ballNum = 17;
		int[] count = new int[ballNum + 1];
		for (int i = 0; i < test; i++) {
			int[] bag = new int[10];
			int bagi = 0;

			// 考虑 1-17个球
			for (int num = 1; num <= ballNum; num++) {
				if (num <= 10) {
					bag[bagi++] = num;
				} else { // num > 10
					// 对应 num 号球，对其在 [1,num] 上给r他随机选一个座位，
					// 如果座位号小于 10 ，安排座位，大于10.直接放弃
					// 也就是每个球来的时候，都有 10/num 的概率被选中
					if (random(num) <= 10) { // 一定要把num球入袋子
						bagi = (int) (Math.random() * 10);
						bag[bagi] = num;
					}
				}

			}

			// 统计每次测试的每个球被选中的次数
			for (int num : bag) {
				count[num]++;
			}
		}


		for (int i = 0; i <= ballNum; i++) {
			System.out.println(count[i]);
		}

		System.out.println("hello");
		int all = 100;
		int choose = 10;
		int testTimes = 5000000;
		int[] counts = new int[all + 1];
		for (int i = 0; i < testTimes; i++) {
			RandomBox box = new RandomBox(choose);
			for (int num = 1; num <= all; num++) {
				box.add(num);
			}
			int[] ans = box.choices();
			for (int j = 0; j < ans.length; j++) {
				counts[ans[j]]++;
			}
		}

		for (int i = 0; i < counts.length; i++) {
			System.out.println(i + " times : " + counts[i]);
		}

	}
}
