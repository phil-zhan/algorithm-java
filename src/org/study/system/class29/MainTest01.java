package org.study.system.class29;

/**
 * @author phil
 * @date 2021/10/3 9:50
 */
public class MainTest01 {

    public static int minKth2(int[] arr, int k) {
        // 排完序之后，处于第 k-1 位置的数就是第 k 小的数
        return process2(arr, 0, arr.length - 1, k - 1);
    }

    public static int minKth3(int[] arr, int k) {
        // 排完序之后，处于第 k-1 位置的数就是第 k 小的数
        return bfptr(arr, 0, arr.length - 1, k - 1);
    }

    public static int bfptr(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }

        int p = medianOfMedians(arr, L, R);
        int[] range = partition(arr, L, R, p);

        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return bfptr(arr, L, range[0] - 1, index);
        } else {
            return bfptr(arr, range[1] + 1, R, index);
        }
    }

    public static int medianOfMedians(int[] arr, int L, int R) {
        int size = R - L + 1;
        int offset = size % 5 == 0 ? 0 : 1;

        int[] midArr = new int[size / 5 + offset];
        for (int team = 0; team < midArr.length; team++) {
            int teamFirst = L + team * 5;


            midArr[team] = getMedian(arr, teamFirst, Math.min(R, teamFirst + 4));
        }


        return bfptr(midArr, 0, midArr.length - 1, midArr.length / 2);
    }

    public static int getMedian(int[] arr, int L, int R) {

        insertSort(arr, L, R);

        return arr[(L + R) / 2];
    }

    public static void insertSort(int[] arr, int L, int R) {
        for (int i = L + 1; i <= R; i++) {
            for (int j = i - 1; j >= L && arr[j] > arr[j + 1]; j--) {
                swap(arr, j, j + 1);
            }
        }
    }

    public static int process2(int[] arr, int L, int R, int index) {
        if (L == R) {
            return arr[L];
        }


        int p = arr[L + (int) (Math.random() * (R - L + 1))];
        int[] range = partition(arr, L, R, p);

        if (index >= range[0] && index <= range[1]) {
            return arr[index];
        } else if (index < range[0]) {
            return process2(arr, L, range[0] - 1, index);
        } else {
            return process2(arr, range[1] + 1, R, index);
        }

    }


    public static int[] partition(int[] arr, int L, int R, int p) {

        int less = L - 1;
        int more = R + 1;

        int cur = L;
        while (cur < more) {
            if (arr[cur] < p) {
                swap(arr, ++less, cur++);
            } else if (arr[cur] > p) {
                swap(arr, --more, cur);
            } else {
                cur++;
            }
        }

        return new int[]{less + 1, more - 1};
    }


    public static void swap(int[] arr, int i1, int i2) {

        int temp = arr[i1];
        arr[i1] = arr[i2];
        arr[i2] = temp;
    }




    public static void main(String[] args) {
        int testTime = 1000000;
        int maxSize = 100;
        int maxValue = 100;
        System.out.println("test begin");
        for (int i = 0; i < testTime; i++) {
            int[] arr = Code01_FindMinKth.generateRandomArray(maxSize, maxValue);
            int k = (int) (Math.random() * arr.length) + 1;
            int ans1 = Code01_FindMinKth.minKth1(arr, k);
            int ans2 = minKth2(arr, k);
            int ans3 = minKth3(arr, k);
            if (ans1 != ans2 || ans2 != ans3) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test finish");
    }
}
