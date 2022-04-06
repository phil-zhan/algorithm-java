package org.study.coding.class25;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;



/**
 * 本题测试链接 : https://leetcode.com/problems/ip-to-cidr/
 * 1. An IP address is a formatted 32-bit unsigned integer where each group of 8 bits is printed as a decimal number and the dot character '.' splits the groups.
 *<p>
 *    For example, the binary number 00001111 10001000 11111111 01101011 (spaces added for clarity) formatted as an IP address would be "15.136.255.107".
 *<p>
 *    A CIDR block is a format used to denote a specific set of IP addresses. It is a string consisting of a base IP address, followed by a slash, followed by a prefix length k.
 *    The addresses it covers are all the IPs whose first k bits are the same as the base IP address.
 *<p>
 *    For example, "123.45.67.89/20" is a CIDR block with a prefix length of 20. Any IP address whose binary representation matches
 *    01111011 00101101 0100xxxx xxxxxxxx, where x can be either 0 or 1, is in the set covered by the CIDR block.
 *<p>
 *    You are given a start IP address ip and the number of IP addresses we need to cover n.
 *    Your goal is to use as few CIDR blocks as possible to cover all the IP addresses in the inclusive range [ip, ip + n - 1] exactly
 *    No other IP addresses outside of the range should be covered.
 *<p>
 *    Return the shortest list of CIDR blocks that covers the range of IP addresses. If there are multiple answers, return any of them.
 *<p>
 *<p>
 * 题意：
 * 给定一个初始IP。需要在该IP的基础上，分配出 n 个IP出来
 * IP的最后一段是一个字节。也就是8位。
 * 考虑掩码。掩码从32、31、30... 一直往前降。【除最后一段。前面的三段都是一样的】
 * 最后一段 0000 0010 。掩码是32，能搞定1个IP
 * 最后一段 0000 0100 。掩码是31，能搞定2个IP
 * 最后一段 0000 1000 。掩码是30，能搞定4个IP
 * 最后一段 0001 0000 。掩码是29，能搞定8个IP
 * 最后一段 0010 0000 。掩码是28，能搞定16个IP
 * ...
 * 以此类推
 * 搞定这n个IP的最佳组合方式。返回的IP字符串【带掩码】尽可能的少。
 *<p>
 * 当然，需要注意的是，初始IP的最后段，如果是100.那么0...99 是不能包含在分配出去的IP中的
 *<p>
 *
 * 解法：
 * 第一步：
 * 将IP转化称为4个8位。【用32bit位的int来表示一个完整的IP】
 * 每8位表示一段IP
 *
 * 第二步：分配IP
 * 1）初始IP的最后一段的数，转化成二进制。看看后面有几个0.用对应的掩码，是否够用
 * a) 够用【也就是不用升位】
 * 	  此时考虑的是从最后一个1开始往后推，看看到那一位刚刚好。
 * b) 不够用【需要升位】
 * 	  此时考虑的是从最后一个1开始往前推，看看到哪一位够用。然后去走够用的逻辑
 *
 *
 *
 * @since 2022-04-05 22:11:19
 */
public class Code01_IPToCIDR {

	/**
	 * 最优解
	 * @since 2022-04-05 23:39:31
	 */
	public static List<String> ipToCIDR(String ip, int n) {
		// ip -> 32位整数
		int cur = status(ip);
		// cur这个状态，最右侧的1，能表示下2的几次方
		int maxPower = 0;
		// 已经解决了多少ip了
		int solved = 0;
		// 临时变量
		int power = 0;
		List<String> ans = new ArrayList<>();
		while (n > 0) {
			// cur最右侧的1，能搞定2的几次方个ip
			// cur : 000...000 01001000
			// 3
			maxPower = mostRightPower(cur);
			// cur : 0000....0000 00001000 -> 2的3次方的问题
			// solved : 0000....0000 00000001 -> 1 2的0次方
			// solved : 0000....0000 00000010 -> 2 2的1次方
			// solved : 0000....0000 00000100 -> 4 2的2次方
			// solved : 0000....0000 00001000 -> 8 2的3次方

			// 32 位都有效，就能解决1个
			solved = 1;
			power = 0;
			// 怕溢出. power不能超过cur。也就是不能超过最后一段的最后一个1对应的十进制。也就是不能超过当前够用的IP数
			// solved
			// 尝试将1从最右侧开始左移
			while ((solved << 1) <= n && (power + 1) <= maxPower) {
				solved <<= 1;
				power++;
			}

			// 生成要返回的字符串
			ans.add(content(cur, power));

			// 说明当前的最后一个1对应的十进制位搞不定【也就是不够用】【需要升位】
			n -= solved;
			cur += solved;
		}

		// 要的是尽可能少的浪费IP。不一定要100%的等于n个，可以稍稍大于n个
		return ans;
	}

	/**
	 * ip -> int(32位状态)
	 * @since 2022-04-05 22:54:29
	 */
	public static int status(String ip) {
		int ans = 0;
		int move = 24;
		// 这两个反斜线必须加。不然切分不出来。这是语言特性
		for (String str : ip.split("\\.")) {
			// 17.23.16.5  "17" "23" "16" "5"
			// "17" -> 17 << 24
			// "23" -> 23 << 16
			// "16" -> 16 << 8
			// "5" -> 5 << 0
			ans |= Integer.parseInt(str) << move;
			move -= 8;
		}
		return ans;
	}

	/**
	 * key：当前数最右侧的1对应的十进制
	 * value：从这个最右侧的1往后，能搞定多少个IP
	 *
	 * 000000....000000 -> 2的32次方
	 * 00...0000 00000001 2的0次方
	 * 00...0000 00000010 2的1次方
	 * 00...0000 00000100 2的2次方
	 * 00...0000 00001000 2的3次方
	 * ..........
	 *
	 * @since 2022-04-05 23:45:41
	 */
	public static HashMap<Integer, Integer> map = new HashMap<>();

	/**
	 * num最右侧的1，能搞定2的几次方个ip
	 *
	 * @since 2022-04-05 23:45:10
	 */
	public static int mostRightPower(int num) {
		// map只会生成1次，以后直接用
		if (map.isEmpty()) {

			// 最右侧没有1.能搞定2的32次方
			map.put(0, 32);
			for (int i = 0; i < 32; i++) {
				// 00...0000 00000001 2的0次方
				// 00...0000 00000010 2的1次方
				// 00...0000 00000100 2的2次方
				// 00...0000 00001000 2的3次方

				// 1 右移i位，能搞定2的i次方个IP
				map.put(1 << i, i);
			}
		}
		// num & (-num) -> num & (~num+1) -> 提取出最右侧的1
		return map.get(num & (-num));
	}

	public static String content(int status, int power) {
		StringBuilder builder = new StringBuilder();
		for (int move = 24; move >= 0; move -= 8) {
			builder.append(((status & (255 << move)) >>> move) + ".");
		}
		builder.setCharAt(builder.length() - 1, '/');
		builder.append(32 - power);
		return builder.toString();
	}

}
