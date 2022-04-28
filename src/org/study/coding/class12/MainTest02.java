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
        System.out.println(new MainTest02().findKthNum2(nums1, nums2, 8));

        // 1 2 3 4 5 6 7 8
        // 1 2 3 4 5 6
        System.out.println(new MainTest02().getUpMedian2(new int[]{1, 3, 5, 7}, 0, 3, new int[]{2, 4, 6, 8}, 0, 3));
        System.out.println(new MainTest02().getUpMedian3(new int[]{1, 3, 5, 7}, 0, 3, new int[]{2, 4, 6, 8}, 0, 3));
    }

    /**
     * 进阶问题 : 在两个都有序的数组中，找整体第K小的数,也就是找到合并后，下标是k-1的数
     * 可以做到O(log(Min(M,N)))
     *
     * @since 2022-03-14 12:28:11
     */
    public int findKthNum(int[] nums1, int[] nums2, int kth) {
        int[] longs = nums1.length >= nums2.length ? nums1 : nums2;
        int[] shorts = longs == nums1 ? nums2 : nums1;

        int longLen = longs.length;
        int shortLen = shorts.length;

        if (kth <= shortLen) {
            // kth <= shortLen

            return getUpMedian(nums1, 0, kth - 1, nums2, 0, kth - 1);
        } else if (kth <= longLen) {
            // shortLen < kth <= longLen

            // 考虑就算短数组全部插在前面。长数组中，哪些是不可能的数
            // 考虑就算短数组全部不参与。长数组中，哪些是不可能的数
            // 见图【img_Code02_3.png】
            // 短数组中的数全可能

            // 此时的长短数组的，剩下可能的数中。不等长。无法用中位数的算法原型。
            // 单独考虑一下长数组的可能的数中 ，其最左边的数。【此时只能考虑长数组的最左边的数。因为长数组多一位】
            // 不能考虑淘汰长数组的右边，因为凑不够


            if (longs[kth - shortLen - 1] >= shorts[shortLen - 1]) {
                return longs[kth - shortLen - 1];
            }
            return getUpMedian(shorts, 0, shortLen - 1, longs, kth - shortLen, kth - 1);
        } else {

            // (kth > longLen)
            // 就算长数组全在前面，考虑短数组的哪些不可能
            // 就算短数组全在前面，考虑长数组的哪些不可能

            // 此时要注意。不能用剩下的数之间去求上中位数。以就算求出了中位数，也只能到 kth-1。【见图 img_Code02_1.png】
            // 手动淘汰，此时的长、短数组的可能数据的最左边的数 【见图 img_Code02_2.png】


            if (shorts[kth - longLen - 1] >= longs[longLen - 1]) {
                return shorts[kth - longLen - 1];
            }

            if (longs[kth - shortLen - 1] >= shorts[shortLen - 1]) {
                return longs[kth - shortLen - 1];
            }

            return getUpMedian(shorts, kth - longLen, shortLen - 1, longs, kth - shortLen, longLen - 1);
        }


    }

    public int findKthNum2(int[] nums1, int[] nums2, int kth) {
        int[] longs = nums1.length > nums2.length ? nums1 : nums2;
        int[] shorts = longs == nums1 ? nums2 : nums1;

        int longLen = longs.length;
        int shortLen = shorts.length;
        // 1 2 3 4 5   6 7 8 9 10 11 12 13 14 15   16 17
        // 1 2 3 4 5 6 7 8 9 10


        if (kth <= shortLen) {
            return getUpMedian3(longs, 0, kth - 1, shorts, 0, kth - 1);
        } else if (kth <= longLen) {
            // kth = 15
            if (longs[kth - shortLen - 1] >= shorts[shortLen - 1]) {
                return longs[kth - shortLen - 1];
            }

            return getUpMedian3(longs, kth - shortLen, kth - 1, shorts, 0, shortLen - 1);
        } else {
            // kth == 23

            if (longs[kth - shortLen - 1] >= shorts[shortLen - 1]) {
                return longs[kth - shortLen - 1];
            }
            if (shorts[kth - longLen - 1] >= longs[longLen - 1]) {
                return shorts[kth - longLen - 1];
            }
            return getUpMedian3(longs, kth - shortLen, longLen - 1, shorts, kth - longLen, shortLen - 1);

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

    public int getUpMedian2(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2) {
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
                    if (nums2[mid2] > nums1[mid1 - 1]) {
                        return nums2[mid2];
                    }
                    end1 = mid1;
                    start2 = mid2;
                } else {
                    if (nums1[mid1] > nums2[mid2 - 1]) {
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

    public int getUpMedian3(int[] nums1, int start1, int end1, int[] nums2, int start2, int end2) {

        int mid1 = 0;
        int mid2 = 0;
        while (start1 < end1) {
            mid1 = (start1 + end1) / 2;
            mid2 = (start2 + end2) / 2;
            if (nums1[mid1] == nums2[mid2]) {
                return nums1[mid1];
            }

            if (((end1 - start1 + 1) & 1) == 1) {
                // 奇数长度

                if (nums1[mid1] > nums2[mid2]) {
                    if (nums2[mid2] >= nums1[mid1 - 1]) {
                        return nums2[mid2];
                    }
                    end1 = mid1 - 1;
                    start2 = mid2 + 1;
                } else {
                    if (nums1[mid1] >= nums2[mid2 - 1]) {
                        return nums1[mid1];
                    }
                    start1 = mid1 + 1;
                    end2 = mid2 - 1;
                }
            } else {
                // 偶数长度

                if (nums1[mid1] > nums2[mid2]) {
                    end1 = mid1;
                    start2 = mid2 + 1;

                } else {
                    end2 = mid2;
                    start1 = mid1 + 1;
                }
            }
        }


        return Math.min(nums1[start1], nums2[start2]);
    }


    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int size = nums1.length + nums2.length;
        boolean isEvent = (size & 1) == 0;

        if (nums1.length != 0 && nums2.length != 0) {
            if (isEvent) {
                return (findKthNum2(nums1, nums2, size / 2) + findKthNum2(nums1, nums2, size / 2 + 1)) / 2D;
            } else {
                return findKthNum2(nums1, nums2, size / 2 + 1);
            }
        } else if (nums1.length != 0) {
            if (isEvent) {
                return (nums1[(size - 1) / 2] + nums1[size / 2]) / 2D;
            } else {
                return nums1[size / 2];
            }

        } else if (nums2.length != 0) {
            if (isEvent) {
                return (nums2[(size - 1) / 2] + nums2[size / 2]) / 2D;
            } else {
                return nums2[size / 2];
            }
        } else {
            return 0;
        }
    }


    public double findMedianSortedArrays2(int[] nums1, int[] nums2) {
        int size = nums1.length + nums2.length;
        boolean isEvent = (size & 1) == 0;

        if (nums1.length != 0 && nums2.length != 0) {
            if (isEvent) {
                return (findKthNum2(nums1, nums2, size / 2) + findKthNum2(nums1, nums2, size / 2 + 1)) / 2D;
            }
            return findKthNum2(nums1, nums2, size / 2 + 1);
        } else if (nums1.length != 0) {
            if (isEvent) {
                return (nums1[(size - 1) / 2] + nums1[size / 2]) / 2D;
            }
            return nums1[size / 2];
        } else if (nums2.length != 0) {
            if (isEvent) {
                return (nums2[(size - 1) / 2] + nums2[size / 2]) / 2D;
            }
            return nums2[size / 2];
        }
        return 0;
    }

}
