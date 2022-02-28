package org.study.system.class15;

/**
 * 朋友圈数量问题
 *
 * @author phil
 * @date 2021/7/9 12:52
 */
public class MainTest02 {

    public static int findCircleNum(int[][] arr){
        UnionFind unionFind = new UnionFind(arr.length);
        // arr数组的长和宽是一样的。只遍历举证的右上三角，且不包含对角线
        for (int i = 0; i < arr.length ; i++) {
            for (int j = i+1; j < arr.length; j++) {
                if(arr[i][j] == 1){

                    // i和j表示两个人的下标
                    unionFind.union(i,j);
                }
            }
        }

        return unionFind.getSets();
    }


    public static class UnionFind{
        // parents[i] = l  ====> 表示下标为i的元素的父节点的下标是l
        // 相当于 parents[i].parent=parents[l]
        // parents存放的都是下标
        private final int[] parents;

        // size[i] = l======> 表示下标为i的元素为头的聊表的集合的长度
        private final int[] size;

        // help是辅助数组。用于实现栈的功能
        private final int[] help;

        // 当前的集合数
        private int sets;

        public UnionFind(int length){
            this.parents = new int[length];
            this.size = new int[length];
            this.help = new int[length];
            sets = length;

            for (int i = 0; i < length; i++) {
                parents[i] = i;
                size[i] = 1;
            }
        }

        // a是某个元素在数组中的下标
        private int find(int a){
            int index = 0;
            while (a != parents[a]){
                help[index++] = a;
                a = parents[a];
            }

            for (index--; index >=0 ; index--) {
                parents[help[index]] = a;
            }

            return a;
        }

        // a1和a2是两个节点的下标
        private void union(int a1,int a2){
            // a1节点所在的链的头的下标
            int find1 = find(a1);

            // a2节点所在的链的头的下标
            int find2 = find(a2);

            if(find1 != find2){
                if (size[find1] >= size[find2]) {
                    parents[find2] = find1;
                    size[find1] += size[find2];
                }else{
                    parents[find1] = find2;
                    size[find2] += size[find1];
                }


                // 两个集合合并。总集合数 1
                sets--;
            }
        }


        private int getSets(){
            return sets;
        }
    }
}
