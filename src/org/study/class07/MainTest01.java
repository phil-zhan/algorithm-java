package org.study.class07;

import java.util.Arrays;
import java.util.Comparator;
import java.util.PriorityQueue;

/**
 * @author phil
 * @date 2022/1/14 15:06
 */
public class MainTest01 {
    public static class Line {
        public int start;
        public int end;

        public Line(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static int maxCover(int[][] m) {
        Line[] lines = new Line[m.length];
        for (int i = 0; i < m.length; i++) {
            Line line = new Line(m[i][0], m[i][1]);
            lines[i] = line;
        }

        Arrays.sort(lines, new StartComparator());
        PriorityQueue<Integer> endQueue = new PriorityQueue<>();
        int maxNum = Integer.MIN_VALUE;
        for (Line line : lines) {
            while (!endQueue.isEmpty() && endQueue.peek() <= line.start) {
                endQueue.poll();
            }
            endQueue.add(line.end);
            maxNum = Math.max(maxNum,endQueue.size());
        }


        return maxNum;
    }

    public static class StartComparator implements Comparator<Line> {
        @Override
        public int compare(Line o1, Line o2) {
            return o1.start - o2.start;
        }
    }

    public static void main(String[] args) {
        System.out.println("test begin");
        int N = 100;
        int L = 0;
        int R = 200;
        int testTimes = 200000;
        for (int i = 0; i < testTimes; i++) {
            int[][] lines = Code01_CoverMax.generateLines(N, L, R);
            int ans1 = Code01_CoverMax.maxCover1(lines);
            int ans2 = maxCover(lines);
            if (ans1 != ans2) {
                System.out.println("Oops!");
            }
        }
        System.out.println("test end");
    }


}
