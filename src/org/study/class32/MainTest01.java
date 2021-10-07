package org.study.class32;

/**
 * @author phil
 * @date 2021/10/5 17:36
 */
public class MainTest01 {

    public static class IndexTree{

        private final int[] tree;

        private final int length;

        public IndexTree(int size){
            this.length = size + 1;
            this.tree = new int[length];
        }


        public void insert(int index , int value){
            while (index <= length){
                tree[index] += value;
                index += index & -index;
            }
        }

        public int sum(int index){
            int answer = 0;

            while (index > 0){
                answer += tree[index];
                index -= index & (~index+1);
            }

            return answer;
        }

    }


    public static void main(String[] args) {
        IndexTree indexTree = new IndexTree(10);
        for (int i = 1; i <= 10; i++) {
            indexTree.insert(i,i);
        }

        System.out.println(indexTree.sum(3)-indexTree.sum(2));
    }
}
