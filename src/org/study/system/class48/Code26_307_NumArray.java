package org.study.system.class48;

/**
 * @author phil
 * @since 2022-0222 00:16:34
 */
public class Code26_307_NumArray {


    public static void main(String[] args) {
        Code26_307_NumArray obj = new Code26_307_NumArray(new int[]{7,2,7,2,0});
        System.out.println(obj.sumRange(0,2));
        obj.update(4,6);
        obj.update(0,2);
        obj.update(0,9);
        System.out.println(obj.sumRange(4,4));
        obj.update(3,8);
        System.out.println(obj.sumRange(0,4));
    }

    int[] sum;
    int[] nums;
    public Code26_307_NumArray(int[] nums) {
        this.nums = nums;

        this.sum = new int[nums.length+1];
        for(int i=0;i<nums.length;i++){

            for(int j=i+1;j<=nums.length; j += j & -j){
                this.sum[j]+=nums[i];
            }
        }
    }

    public void update(int index, int val) {
        int addVal = val - nums[index];
        index = index + 1;
        while(index <= this.nums.length){
            sum[index]+=addVal;
            index += index & -index;
        }
    }

    public int sumRange(int left, int right) {

        // sum[right+1] - sum[left]
        right = right + 1;
        int sumRight = 0;
        while(right > 0){
            sumRight+= sum[right];
            right -= right & -right;
        }

        int leftSum = 0;
        while(left > 0){
            leftSum+= sum[left];
            left -= left & -left;
        }

        return sumRight - leftSum;
    }
}
