package org.study.system.class15;

import java.util.*;

/**
 * 岛屿问题
 *
 * @author phil
 * @date 2021/7/12 9:13
 */
public class MainTest03 {

    private static int numIslands1(char[][] board){
        int numLands=0;
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if (board[i][j] == '1') {
                    numLands++;
                    infect(board,i,j);
                }
            }
        }


        return numLands;
    }

    private static void infect(char[][] board, int i, int j) {
        if (i < 0 || i >= board.length || j < 0 || j >= board[0].length || board[i][j] != '1') {
            return;
        }

        board[i][j] = 0;

        infect(board,i+1,j);
        infect(board,i-1,j);
        infect(board,i,j+1);
        infect(board,i,j-1);
    }



    private static class Dot{}

    private static int numIslands2(char[][] board){

        List<Dot> list = new ArrayList<>();
        Dot[][] dots = new Dot[board.length][board[0].length];
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {
                if(board[i][j] == '1'){
                    dots[i][j] = new Dot();
                    list.add(dots[i][j]);
                }
            }
        }

        UnionFind<Dot> unionFind = new UnionFind<>(list);

        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[0].length; j++) {

                boolean curIsOne = board[i][j] == '1';
                boolean upIsOne = i-1>=0 && board[i-1][j]=='1';
                boolean downIsOne = i+1 < board.length && board[i+1][j]=='1';
                boolean leftIsOne = j-1>=0 && board[i][j-1]=='1';
                boolean rightIsOne = j+1 < board[0].length && board[i][j+1]=='1';
                if( curIsOne && upIsOne){
                    unionFind.union(dots[i][j],dots[i-1][j]);
                }

                if(curIsOne && downIsOne){
                    unionFind.union(dots[i][j],dots[i+1][j]);
                }
                if(curIsOne && leftIsOne){
                    unionFind.union(dots[i][j],dots[i][j-1]);
                }
                if(curIsOne && rightIsOne){
                    unionFind.union(dots[i][j],dots[i][j+1]);
                }
            }
        }

        return unionFind.sets();
    }

    

    private static class UnionFind<V>{
        
        public UnionFind(List<V> list){
            nodeMap = new HashMap<>();
            parents = new HashMap<>();
            sizeMap = new HashMap<>();
            for (V v:list) {
                Node<V> node = new Node<>(v);
                nodeMap.put(v,node);
                parents.put(node,node);
                sizeMap.put(node,1);
            }
        }
        
        
        private static class Node<V>{
            public V value;
            public Node(V value){
                this.value = value;
            }

            @Override
            public boolean equals(Object o) {
                if (this == o) {
                    return true;
                }
                if (o == null || getClass() != o.getClass()) {
                    return false;
                }
                Node<?> node = (Node<?>) o;
                return Objects.equals(value, node.value);
            }

            @Override
            public int hashCode() {
                return Objects.hash(value);
            }
        }
        private final Map<V,Node<V>> nodeMap;

        private final Map<Node<V>,Node<V>> parents;
        
        private final Map<Node<V>,Integer> sizeMap;

        public void union(V a1, V a2){
            Node<V> node1 = findNode(nodeMap.get(a1));
            Node<V> node2 = findNode(nodeMap.get(a2));

            if(node1 != node2){
                int node1Size = sizeMap.get(node1);
                int node2Size = sizeMap.get(node2);

                Node<V> big = node1Size > node2Size?node1:node2;
                Node<V> small = node1Size > node2Size?node2:node1;


                parents.put(small,big);
                sizeMap.put(big,node1Size+node2Size);

                sizeMap.remove(small);
            }
        }

        private Node<V> findNode(Node<V> node) {

            Stack<Node<V>> stack = new Stack<>();

            while (node != parents.get(node)) {
                stack.push(node);
                node = parents.get(node);
            }

            while (!stack.isEmpty()){
                parents.put(stack.pop(),node);

            }

            return node;
        }

        public int sets() {
            return sizeMap.size();
        }
    }


    private static int numIslands3(char[][] boards){


        UnionFind2 unionFind2 = new UnionFind2(boards);

        for (int i = 0; i < boards.length; i++) {
            for (int j = 0; j < boards[0].length; j++) {

                boolean curIsOne = boards[i][j] == '1';
                boolean upIsOne = i-1>=0 && boards[i-1][j]=='1';
                boolean downIsOne = i+1 < boards.length && boards[i+1][j]=='1';
                boolean leftIsOne = j-1>=0 && boards[i][j-1]=='1';
                boolean rightIsOne = j+1 < boards[0].length && boards[i][j+1]=='1';
                if( curIsOne && upIsOne){
                    unionFind2.union(i,j,i-1,j);
                }

                if(curIsOne && downIsOne){
                    unionFind2.union(i,j,i+1,j);
                }
                if(curIsOne && leftIsOne){
                    unionFind2.union(i,j,i,j-1);
                }
                if(curIsOne && rightIsOne){
                    unionFind2.union(i,j,i,j+1);
                }
            }
        }

        return unionFind2.getSets();
    }

    private static class UnionFind2{
        private final int[] parents;
        private final int[] size;
        private final int[] help;
        int col;

        private int sets;

        public UnionFind2(char[][] boards){
            col = boards[0].length;
            sets = 0;
            int row = boards.length;
            int len = row * col;

            parents = new int[len];
            size = new int[len];
            help = new int[len];

            for (int r = 0; r < row; r++) {
                for (int c = 0; c < col; c++) {
                    if (boards[r][c] == '1') {
                        int index = index(r,c);
                        parents[index] = index;
                        size[index] = 1;
                        sets++;
                    }
                }
            }
        }

        private int index(int row1,int col1){
            return row1 * col + col1;
        }

        private void union(int row1,int col1,int row2,int col2){
            int index1 = index(row1,col1);
            int index2 = index(row2,col2);


            int find1 = findParentIndex(index1);
            int find2 = findParentIndex(index2);

            if(find1 != find2){
                if(size[find1] > size[find2]){
                    parents[find2]=find1;
                    size[find1]=size[find1]+size[find2];
                }else{
                    parents[find1]=find2;
                    size[find2]=size[find1]+size[find2];
                }

                sets--;
            }
        }


        private int findParentIndex(int index){

            int helpIndex = 0;
            while (index != parents[index]) {

                help[helpIndex++] = index;
                index = parents[index];
            }

            for (helpIndex--;helpIndex>=0;helpIndex--){
                parents[help[helpIndex]]=index;
            }

            return index;
        }

        public int getSets(){
            return sets;
        }
    }




    // 为了测试
    public static char[][] generateRandomMatrix(int row, int col) {
        char[][] board = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                board[i][j] = Math.random() < 0.5 ? '1' : '0';
            }
        }
        return board;
    }

    // 为了测试
    public static char[][] copy(char[][] board) {
        int row = board.length;
        int col = board[0].length;
        char[][] ans = new char[row][col];
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < col; j++) {
                ans[i][j] = board[i][j];
            }
        }
        return ans;
    }

    // 为了测试
    public static void main(String[] args) {
        int row = 0;
        int col = 0;
        char[][] board1 = null;
        char[][] board2 = null;
        char[][] board3 = null;
        long start = 0;
        long end = 0;

        row = 1000;
        col = 1000;
        board1 = generateRandomMatrix(row, col);
        board2 = copy(board1);
        board3 = copy(board1);

        System.out.println("感染方法、并查集(map实现)、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands1(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行结果: " + numIslands2(board2));
        end = System.currentTimeMillis();
        System.out.println("并查集(map实现)的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands3(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

        System.out.println();

        row = 10000;
        col = 10000;
        board1 = generateRandomMatrix(row, col);
        board3 = copy(board1);
        System.out.println("感染方法、并查集(数组实现)的运行结果和运行时间");
        System.out.println("随机生成的二维矩阵规模 : " + row + " * " + col);

        start = System.currentTimeMillis();
        System.out.println("感染方法的运行结果: " + numIslands1(board1));
        end = System.currentTimeMillis();
        System.out.println("感染方法的运行时间: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行结果: " + numIslands3(board3));
        end = System.currentTimeMillis();
        System.out.println("并查集(数组实现)的运行时间: " + (end - start) + " ms");

    }
}
