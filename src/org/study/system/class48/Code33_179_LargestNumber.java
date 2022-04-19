package org.study.system.class48;

import java.util.Arrays;
import java.util.Comparator;

/**
 * @author phil
 * @since 2022-04-18 00:09:17
 */
public class Code33_179_LargestNumber {
    public static void main(String[] args) {
        System.out.println(new Code33_179_LargestNumber().largestNumber(new int[]{0, 0}));
    }

    public String largestNumber(int[] nums) {
        if (nums == null || nums.length == 0) {
            return "";
        }
        String[] strs = new String[nums.length];
        for(int i=0;i<nums.length;i++){
            strs[i] = String.valueOf(nums[i]);
        }


        Arrays.sort(strs, new MyComparator());
        String res = "";
        for (int i = 0; i < strs.length; i++) {
            res += strs[i];
        }

        char[] ch = res.toCharArray();
        int nonZeroIndex = -1;
        for(int i = 0;i<ch.length;i++){
            if(ch[i] == '0'){
                nonZeroIndex = i;
            }else{
                break;
            }

        }



        return nonZeroIndex==-1?res:res.substring(nonZeroIndex);
    }

    public static class MyComparator implements Comparator<String> {
        @Override
        public int compare(String a, String b) {
            return (b + a).compareTo(a + b);
        }
    }
}
