package org.study.class11;

/**
 *
 * 所有的节点组成了一颗二叉树
 * 树的深度表示折叠的次数
 *
 * // 头节点是凹的
 * // 所有左字树的头都是凹的【也就是所有左节点都是凹的】
 * // 所有右子树的头节点都是凸的【也就是所有右节点都是凸的】
 * @author phil
 * @date 2021/7/1 9:12
 */
public class Code07_PaperFolding {
    public static void printAllFolds(int N) {
        process(1, N, true);
        System.out.println();
    }

    // 当前你来了一个节点，脑海中想象的！
    // 这个节点在第i层，一共有N层，N固定不变的
    // 这个节点如果是凹的话，down = T
    // 这个节点如果是凸的话，down = F
    // 函数的功能：中序打印以你想象的节点为头的整棵树！
    public static void process(int i, int N, boolean down) {
        if (i > N) {
            return;
        }

        // 中序遍历。i表示当前层。down=true表示去左边。false去右边
        process(i + 1, N, true);
        System.out.print(down ? "凹 " : "凸 ");
        process(i + 1, N, false);
    }

    public static void main(String[] args) {
        int N = 4;
        printAllFolds(N);
    }
}
