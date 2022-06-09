package org.study.coding.class39;

// 来自百度
// 给定一个字符串str，和一个正数k
// str子序列的字符种数必须是k种，返回有多少子序列满足这个条件
// 已知str中都是小写字母
// 原始是取mod
// 本节在尝试上，最难的
// 搞出桶来，组合公式

/**
 * 子序列的个数只与字符的种数以及每个字符的个数有关。与字符的顺序无关
 * aabbcc 和 abbacc   所表示出来的满足条件的子序列个数是一样的
 *
 * 在桶数组bu[index....] 一定要凑出rest种字符来！请问几种方法！
 * 【bu[index]=x  表示的是index种字符的词频。同时index也表示字符。用0、1、2、3... 和a、b、c... 对应】
 *
 * 对于每个字符。考虑要或者不要
 *
 * @since 2022-06-08 20:39:33
 */
public class Code03_SequenceKDifferentKinds {

	// bu -> {6,7,0,0,6,3}
	// 0 1 2 3 4 5
	// a b c d e f
	// 在桶数组bu[index....] 一定要凑出rest种来！请问几种方法！
	public static int f(int[] bu, int index, int rest) {
		if (index == bu.length) {
			return rest == 0 ? 1 : 0;
		}
		// 最后形成的子序列，一个index代表的字符也没有!
		int p1 = f(bu, index + 1, rest);
		// 最后形成的子序列，一定要包含index代表的字符，几个呢？(所有可能性都要算上！)

		int p2 = 0;
		// 剩余的种数，没耗尽，可以包含当前桶的字符
		if (rest > 0) {
			// index 号字符有 bu[index] 种。可以考虑该种字符的第一个、第二个、第三个... 要或是不要 。总共有 2^x  。最后再乘以剩下的 rest-1种字符搞出来的子序列数
			p2 = (1 << bu[index] - 1) * f(bu, index + 1, rest - 1);
		}
		return p1 + p2;
	}

	public static int nums(String s, int k) {
		char[] str = s.toCharArray();
		int[] counts = new int[26];
		for (char c : str) {
			counts[c - 97]++;
		}
		return ways(counts, 0, k);
	}

	public static int ways(int[] c, int i, int r) {
		if (r == 0) {
			return 1;
		}
		if (i == c.length) {
			return 0;
		}
		// math(n) -> 2 ^ n -1
		// c[i] 这种字符要或者不要。最后加起来
		return math(c[i]) * ways(c, i + 1, r - 1) + ways(c, i + 1, r);
	}

	// n个不同的球
	// 挑出1个的方法数 + 挑出2个的方法数 + ... + 挑出n个的方法数为:
	// C(n,1) + C(n,2) + ... + C(n,n) == (2 ^ n) -1
	public static int math(int n) {
		return (1 << n) - 1;
	}

	public static void main(String[] args) {
		String str = "acbbca";
		int k = 3;
		System.out.println(nums(str, k));
	}

}
