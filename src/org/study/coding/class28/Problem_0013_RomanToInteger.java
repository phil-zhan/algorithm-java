package org.study.coding.class28;

/**
 * // 举一反三：将罗马数字转完正常数字
 * // 如：CMXIX
 * // C的含义比M小  -100
 * // M的含义比X大  +1000
 * // X比I的含义大  +10
 * // I比X的含义小  -1
 * // 最后一个X     +10
 * // 最后累加起来
 * <p>
 * <p>
 * i位置比i+1位置代表的含义大，就是正的【找到其代表的数】
 * i位置比i+1位置代表的含义小，就是负的【找到其代表的数】
 * 最后累加起来
 *
 * @since 2022-04-11 23:01:47
 */
public class Problem_0013_RomanToInteger {

    public static int romanToInt(String s) {
        // C     M     X   C     I   X
        // 100  1000  10   100   1   10
        int[] nums = new int[s.length()];
        for (int i = 0; i < s.length(); i++) {
            switch (s.charAt(i)) {
                case 'M':
                    nums[i] = 1000;
                    break;
                case 'D':
                    nums[i] = 500;
                    break;
                case 'C':
                    nums[i] = 100;
                    break;
                case 'L':
                    nums[i] = 50;
                    break;
                case 'X':
                    nums[i] = 10;
                    break;
                case 'V':
                    nums[i] = 5;
                    break;
                case 'I':
                    nums[i] = 1;
                    break;
                default:
            }
        }
        int sum = 0;
        for (int i = 0; i < nums.length - 1; i++) {
            if (nums[i] < nums[i + 1]) {
                sum -= nums[i];
            } else {
                sum += nums[i];
            }
        }
        return sum + nums[nums.length - 1];
    }

}
