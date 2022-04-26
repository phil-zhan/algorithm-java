package org.study.coding.class12;

/**
 * @author phil
 * @since 2022-0313 23:27:31
 */
public class MainTest02 {


    public static void main(String[] args) {
        int[] nums1 = new int[]{1, 3, 4, 6, 7, 8};
        int[] nums2 = new int[]{2, 4, 4, 5};
        System.out.println(new MainTest02().findKthNum(nums1, nums2, 8));

        // 1 2 3 4 5 6 7 8
        // 1 2 3 4 5 6
        System.out.println(getUpMedian2(new int[]{1, 3, 5, 7}, 0, 3, new int[]{2, 4, 6, 8}, 0, 3));
    }

    /**
     * @since 2022-03-14 12:28:11
     */
    public int findKthNum(int[] nums1, int[] nums2, int kth) {
        int[] longs = nums1.length >= nums2.length ? nums1 : nums2;
        int[] shorts = longs == nums1 ? nums2 : nums1;

        int longLen = longs.length;
        int shortLen = shorts.length;

        if (kth <= shortLen) {
            return getUpMedian(nums1, 0, kth - 1, nums2, 0, kth - 1);
        } else if (kth <= longLen) {
            if (longs[kth - shortLen - 1] >= shorts[shortLen - 1]) {
                return longs[kth - shortLen - 1];
            }
            return getUpMedian(shorts, 0, shortLen - 1, longs, kth - shortLen, kth - 1);
        } else {
            // (kth > longLen)
            if (shorts[kth - longLen - 1] >= longs[longLen - 1]) {
                return shorts[kth - longLen - 1];
            }
            if (longs[kth - shortLen - 1] >= shorts[shortLen - 1]) {
                return longs[kth - shortLen - 1];
            }
            return getUpMedian(shorts, kth - longLen, shortLen - 1, longs, kth - shortLen, longLen - 1);
        }


    }

    public int getUpMedian(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2) {

        int mid1;
        int mid2;
        while (start1 < end1) {
            mid1 = start1 + ((end1 - start1) >> 1);
            mid2 = start2 + ((end2 - start2) >> 1);
            if (nums1[mid1] == nums2[mid2]) {
                return nums2[mid1];
            }

            if (((start1 - end1 + 1) & 1) != 0) {
                // 奇数
                // 2 4 6 8 10
                // 1 3 5 7 9
                if (nums1[mid1] > nums2[mid2]) {
                    if (nums2[mid2] > nums1[mid1 - 1]) {
                        return nums2[mid2];
                    }
                    end1 = mid1 - 1;
                    start2 = mid2 + 1;
                } else {
                    if (nums1[mid1] > nums2[mid2 - 1]) {
                        return nums1[mid1];
                    }
                    start1 = mid1 + 1;
                    end2 = mid2 - 1;
                }
            } else {
                // 偶数
                // 2 4 6 8 10 12
                // 1 3 5 7 9 11
                if (nums1[mid1] > nums2[mid2]) {
                    end1 = mid1;
                    start2 = mid2 + 1;
                } else {
                    start1 = mid1 + 1;
                    end2 = mid2;
                }
            }

        }

        return Math.min(nums1[start1], nums2[start2]);
    }

    public static int getUpMedian2(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2) {
        int mid1;
        int mid2;
        while (start1 < end1) {
            mid1 = start1 + ((end1 - start1) >> 1);
            mid2 = start2 + ((end2 - start2) >> 1);
            if (nums1[mid1] == nums2[mid2]) {
                return nums1[mid1];
            }
            if (((end1 - start1 + 1) & 1) == 0) {
                // 偶数个
                if (nums1[mid1] > nums2[mid2]) {
                    end1 = mid1;
                    start2 = mid2 + 1;
                } else {
                    start1 = mid1 + 1;
                    end2 = mid2;
                }
            } else {
                // 奇数个
                if (nums1[mid1] > nums2[mid2]) {
                    if (nums2[mid2] > nums1[mid1-1]){
                        return nums2[mid2];
                    }
                    end1 = mid1;
                    start2 = mid2;
                } else {
                    if (nums1[mid1] > nums2[mid2-1]){
                        return nums1[mid1];
                    }
                    end2 = mid2;
                    start1 = mid1;
                }
            }

            // 偶数个，返回第四小，也就是下标为3.
            // 2 4 6 8
            // 1 3 5 7

            // 奇数个。返回第五小，也就是下标为4
            // 2 4 6 8 10
            // 1 3 5 7 9


        }

        // start1 = end1
        // start2 = end2

        return Math.min(nums1[start1], nums2[start2]);
    }

}
