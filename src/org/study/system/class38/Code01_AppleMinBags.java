package org.study.system.class38;

public class Code01_AppleMinBags {

	public static int minBags(int apple) {
		if (apple < 0) {
			return -1;
		}
		int bag8 = (apple >> 3);

		// (bag8 << 3)   bag8 * 8
		int rest = apple - (bag8 << 3);
		while(bag8 >= 0) {
			// rest 个
			if(rest % 6 ==0) {
				return bag8 + (rest / 6);
			} else {
				bag8--;
				rest += 8;
			}
		}
		return -1;
	}

	/**
	 * 根据暴力解找到规律
	 * 从18开始。每八个数一组。
	 * 在一组内。奇数返回 -1.  偶数就返回  (apple - 18) / 8 + 3;
	 *
	 * @date 2021-10-13 16:21:43
	 */
	public static int minBagAwesome(int apple) {
		if ((apple & 1) != 0) { // 如果是奇数，返回-1
			return -1;
		}
		if (apple < 18) {
			return apple == 0 ? 0 : (apple == 6 || apple == 8) ? 1
					: (apple == 12 || apple == 14 || apple == 16) ? 2 : -1;
		}
		return (apple - 18) / 8 + 3;
	}

	public static void main(String[] args) {
		for(int apple = 1; apple < 2000;apple++) {
			System.out.println(apple + " : "+ minBags(apple));
		}

	}

}
