package org.study.class48;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author phil
 * @date 2022/2/14 16:04
 */
public class Code07_637_AverageOfLevels {


    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(3);

        root.left = node1;
        root.right = node2;

        List<Double> list = new Code07_637_AverageOfLevels().averageOfLevels(null);
        System.out.println(list);
    }
    public List<Double> averageOfLevels(TreeNode root) {
        List<Double> ansList = new ArrayList<>();
        // 判空
        if(root == null){
            return ansList;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode curNode;
        TreeNode curEnd=root;
        TreeNode nextEnd=null;
        int levelNum=0;
        double levelSum=0;

        while (!queue.isEmpty()){
            curNode = queue.poll();
            levelNum++;
            levelSum+=(curNode.val);

            if(curNode.left!=null){
                queue.add(curNode.left);
                nextEnd = curNode.left;
            }
            if(curNode.right!=null){
                queue.add(curNode.right);
                nextEnd = curNode.right;
            }

            if(curNode == curEnd){
                curEnd = nextEnd;

                ansList.add((double) (levelSum/levelNum));
                levelNum=0;
                levelSum=0;
            }
        }


        return ansList;
    }

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode() {
        }

        TreeNode(int val) {
            this.val = val;
        }

        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
