package org.study.coding.class32;

/**
 * 给你一个字符串columnTitle ，表示 Excel 表格中的列名称。返回 该列名称对应的列序号。
 *
 * 例如：
 *
 * A -> 1
 * B -> 2
 * C -> 3
 * ...
 * Z -> 26
 * AA -> 27
 * AB -> 28 
 * ...
 * 
 *
 * 示例 1:
 *
 * 输入: columnTitle = "A"
 * 输出: 1
 * 示例2:
 *
 * 输入: columnTitle = "AB"
 * 输出: 28
 * 示例3:
 *
 * 输入: columnTitle = "ZY"
 * 输出: 701
 *
 * @since 2022-04-18 23:39:00
 */
public class Problem_0171_ExcelSheetColumnNumber {

	/**
	 * 这道题反过来也要会写
	 *
	 * 字母的每一个代表26的某次方
	 * 从右到左
	 * 右边倒数第一位代表有几个26的0次方
	 * 右边倒数第二位代表有几个26的1次方
	 * 右边倒数第三位代表有几个26的3次方
	 * 。。。
	 *
	 * 每一位不能有0状态
	 * ABCDE... 分别代表12345...  一直到26次方
	 *
	 * 比如
	 * BCA = 2*26^2 + 3*26 + 1*26^0
	 *
	 * @since 2022-04-18 23:38:06
	 */
	public static int titleToNumber(String s) {
		char[] str = s.toCharArray();
		int ans = 0;

		// 这是从高位往低位算
		// 高位每增高一位。就成个26，再加上当前的数
		for (char c : str) {
			ans = ans * 26 + (c - 'A') + 1;
		}
		return ans;
	}

	public static void main(String[] args) {
		System.out.println(titleToNumber("ABC"));
	}
}
