package org.study.coding.class01;

public class Code03_Near2Power {

	// 已知n是正数
	// 返回大于等于，且最接近n的，2的某次方的值
	public static int tableSizeFor(int n) {

		// 减去1  是为了防止所给的数正好是2的某次方
		// 如果n-1是一个负数的话【最高位(符号位)是1】。经过下面的流程后，会变成一个所有位置全是1的数字。而实际上应该返回1【特殊处理】
		n--;

		// 跑完之后，从最高位的1开始，右边全是1
		n |= n >>> 1;
		n |= n >>> 2;
		n |= n >>> 4;
		n |= n >>> 8;

		// int 类型就32位。如果是long的话，再多 或等于 一个32
		n |= n >>> 16;

		// 从最高位开始，右边全是1.再加一个1就是答案。加1可以把所有的1怼到最高位的上一位
		return (n < 0) ? 1 : n + 1;
	}

	public static void main(String[] args) {
		int cap = 120;
		System.out.println(tableSizeFor(cap));
	}

}
