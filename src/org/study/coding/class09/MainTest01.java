package org.study.coding.class09;

/**
 * @author phil
 * @date 2022-04-05 14:03:29
 */
public class MainTest01 {

    public static int noLoopMinStep1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] ^ 1;
        }

        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : arr[0] ^ 1;
        }

        // arr.length >= 3
        // 不按0位置
        int p1 = process(1, arr[0], arr[1], arr);
        // 按0位置
        int p2 = process(1, arr[0] ^ 1, arr[1] ^ 1, arr);
        p2 = p2 != Integer.MAX_VALUE ? p2 + 1 : p2;

        return Math.min(p1, p2);
    }

    // index位置做决定
    // preState : index-1位置的状态
    // curState : index 位置的状态
    public static int process(int index, int preState, int curState, int[] arr) {
        if (index == arr.length - 1) {
            return preState == curState ? curState ^ 1 : Integer.MAX_VALUE;
        }

        if (preState == 0) {
            // must change
            int next = process(index + 1, curState ^ 1, arr[index + 1] ^ 1, arr);
            return next == Integer.MAX_VALUE ? next : next + 1;
        } else {
            // must not change
            return process(index + 1, curState, arr[index + 1], arr);
        }
    }

    public static int noLoopMinStep2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] ^ 1;
        }

        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : arr[0] ^ 1;
        }

        // arr.length >= 3
        // 不按0位置
        int p1 = traceNoLoop(arr[0], arr[1], arr);
        // 按0位置
        int p2 = traceNoLoop(arr[0] ^ 1, arr[1] ^ 1, arr);
        p2 = p2 != Integer.MAX_VALUE ? p2 + 1 : p2;

        return Math.min(p1, p2);
    }


    public static int traceNoLoop(int preState, int curState, int[] arr) {
        int op = 0;
        int index = 1;

        // 最后一个位置放在后面去决定
        while (index != arr.length - 1) {
            if (preState == 0) {
                // must change
                op++;
                preState = curState ^ 1;
                curState = arr[index + 1] ^ 1;
                index++;
            } else {
                // must not change
                preState = curState;
                curState = arr[index + 1];
                index++;
            }
        }

        return preState != curState ? Integer.MAX_VALUE : (op + (curState ^ 1));
    }

    public static int loopMinStep1(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] ^ 1;
        }
        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        if (arr.length == 3) {
            return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }

        // 0 1两个位置都不按
        int p1 = process2(arr, 2, arr[1], arr[2], arr[0], arr[arr.length - 1]);

        // 0 按 1不按
        int p2 = process2(arr, 2, arr[1] ^ 1, arr[2], arr[0] ^ 1, arr[arr.length - 1] ^ 1);

        // 0不按 1按
        int p3 = process2(arr, 2, arr[1] ^ 1, arr[2] ^ 1, arr[0] ^ 1, arr[arr.length - 1]);

        // 0按 1也按
        int p4 = process2(arr, 2, arr[1], arr[2] ^ 1, arr[0], arr[arr.length - 1] ^ 1);

        p2 = p2 != Integer.MAX_VALUE ? (p2 + 1) : p2;
        p3 = p3 != Integer.MAX_VALUE ? (p3 + 1) : p3;
        p4 = p4 != Integer.MAX_VALUE ? (p4 + 2) : p4;
        return Math.min(Math.min(p1, p2), Math.min(p3, p4));
    }


    public static int process2(int[] arr, int index, int preState, int curState, int firstState, int endState) {
        if (index == arr.length - 1) {
            return preState != curState || curState != firstState ? Integer.MAX_VALUE : curState ^ 1;
        }

        int noNextPreState;
        int noNextCurState;
        int noNextEndState;

        int yesNextPreState;
        int yesNextCurState;
        int yesNextEndState;

        if (index == arr.length - 2) {
            noNextPreState = curState;
            noNextCurState = endState;
            noNextEndState = endState;

            yesNextPreState = curState ^ 1;
            yesNextCurState = endState ^ 1;
            yesNextEndState = endState ^ 1;
        } else {
            noNextPreState = curState;
            noNextCurState = arr[index + 1];
            noNextEndState = endState;

            yesNextPreState = curState ^ 1;
            yesNextCurState = arr[index + 1] ^ 1;
            yesNextEndState = endState;
        }

        if (preState == 0) {
            // must change
            int next = process2(arr, index + 1, yesNextPreState, yesNextCurState, firstState, yesNextEndState);
            return next == Integer.MAX_VALUE ? next : next + 1;
        } else {
            // must not change
            return process2(arr, index + 1, noNextPreState, noNextCurState, firstState, noNextEndState);
        }
    }

    /**
     * 有环改灯问题的迭代版本
     *
     * @since 2022-04-05 16:52:29
     */
    public static int loopMinStep2(int[] arr) {
        if (arr == null || arr.length == 0) {
            return 0;
        }
        if (arr.length == 1) {
            return arr[0] == 1 ? 0 : 1;
        }
        if (arr.length == 2) {
            return arr[0] != arr[1] ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        if (arr.length == 3) {
            return (arr[0] != arr[1] || arr[0] != arr[2]) ? Integer.MAX_VALUE : (arr[0] ^ 1);
        }
        // 0不变，1不变
        int p1 = traceLoop(arr, arr[1], arr[2], arr[arr.length - 1], arr[0]);
        // 0改变，1不变
        int p2 = traceLoop(arr, arr[1] ^ 1, arr[2], arr[arr.length - 1] ^ 1, arr[0] ^ 1);
        // 0不变，1改变
        int p3 = traceLoop(arr, arr[1] ^ 1, arr[2] ^ 1, arr[arr.length - 1], arr[0] ^ 1);
        // 0改变，1改变
        int p4 = traceLoop(arr, arr[1], arr[2] ^ 1, arr[arr.length - 1] ^ 1, arr[0]);
        p2 = p2 != Integer.MAX_VALUE ? (p2 + 1) : p2;
        p3 = p3 != Integer.MAX_VALUE ? (p3 + 1) : p3;
        p4 = p4 != Integer.MAX_VALUE ? (p4 + 2) : p4;
        return Math.min(Math.min(p1, p2), Math.min(p3, p4));
    }

    public static int traceLoop(int[] arr, int preStatus, int curStatus, int endStatus, int firstStatus) {
        int i = 3;
        int op = 0;
        while (i < arr.length - 1) {
            if (preStatus == 0) {
                op++;
                preStatus = curStatus ^ 1;
                curStatus = (arr[i++] ^ 1);
            } else {
                preStatus = curStatus;
                curStatus = arr[i++];
            }
        }
        if (preStatus == 0) {
            op++;
            preStatus = curStatus ^ 1;
            endStatus ^= 1;
            curStatus = endStatus;
        } else {
            preStatus = curStatus;
            curStatus = endStatus;
        }
        return (endStatus != firstStatus || endStatus != preStatus) ? Integer.MAX_VALUE : (op + (endStatus ^ 1));
    }

    public static void main(String[] args) {
        System.out.println("如果没有任何Oops打印，说明所有方法都正确");
        System.out.println("test begin");
        int testTime = 20000;
        int lenMax = 12;
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * lenMax);
            int[] arr = Code01_LightProblem.randomArray(len);
            int ans1 = Code01_LightProblem.noLoopRight(arr);
            int ans2 = noLoopMinStep1(arr);
            int ans3 = noLoopMinStep2(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("1 Oops!");
            }
        }
        for (int i = 0; i < testTime; i++) {
            int len = (int) (Math.random() * lenMax);
            int[] arr = Code01_LightProblem.randomArray(len);
            int ans1 = Code01_LightProblem.loopRight(arr);
            int ans2 = loopMinStep1(arr);
            int ans3 = loopMinStep2(arr);
            if (ans1 != ans2 || ans1 != ans3) {
                System.out.println("2 Oops!");
            }
        }
        System.out.println("test end");

        int len = 100000000;
        System.out.println("性能测试");
        System.out.println("数组大小：" + len);
        int[] arr = Code01_LightProblem.randomArray(len);
        long start;
        long end;
        start = System.currentTimeMillis();
        Code01_LightProblem.noLoopMinStep2(arr);
        end = System.currentTimeMillis();
        System.out.println("noLoopMinStep2 run time: " + (end - start) + "(ms)");

        start = System.currentTimeMillis();
        Code01_LightProblem.loopMinStep2(arr);
        end = System.currentTimeMillis();
        System.out.println("loopMinStep2 run time: " + (end - start) + "(ms)");
    }
}
