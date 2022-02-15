package org.study.class48;

/**
 * @author phil
 * @date 2022/2/15 13:48
 */
public class Code09_547_FindCircleNum {

    public static void main(String[] args) {
        int circleNum = new Code09_547_FindCircleNum().findCircleNum(new int[][]{

        });

        char[][] grid = new char[1][1];
        boolean flag = grid[0][0] == '1';
        System.out.println(circleNum);
    }
    /**
     * isConnected[i][j] : 第 i 号城市和第 j 号城市是否相连
     *
     * @since 2022-02-15 13:48:45
     */
    public int findCircleNum(int[][] isConnected) {
        int length =  isConnected.length;
        UnionFind unionFind = new UnionFind(length);
        for (int i = 0; i < isConnected.length; i++) {
            for (int j = i+1; j <  isConnected.length; j++) {
                if(isConnected[i][j] == 1){
                    unionFind.union(i,j);
                }
            }
        }

        return unionFind.sets;
    }

    class UnionFind {
        public int[] parent;
        public int[] size;
        public int[] help;
        public int sets;

        public UnionFind(int length) {
            parent = new int[length];
            size = new int[length];
            help = new int[length];
            sets = length;

            for (int i = 0; i < length; i++) {
                parent[i] = i;
                size[i] = 1;
            }
        }

        public int find(int i) {
            int hi = 0;
            while (i != parent[i]) {
                help[hi++] = i;
                i = parent[i];
            }

            for (hi--; hi >= 0; hi--) {
                parent[help[hi]] = i;
            }

            return i;
        }

        public void union(int i, int j) {
            int f1 = find(i);
            int f2 = find(j);
            if (f1 != f2) {
                if (size[f1] >= size[f2]) {
                    parent[f2] = f1;
                    size[f1] += size[f2];
                } else {
                    parent[f1] = f2;
                    size[f2] += size[f1];
                }
                sets--;
            }
        }
    }
}
