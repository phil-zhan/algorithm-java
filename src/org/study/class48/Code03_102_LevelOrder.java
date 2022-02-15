package org.study.class48;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * https://leetcode-cn.com/problems/binary-tree-level-order-traversal/
 * @author phil
 * @date 2022/2/14 13:25
 */
public class Code03_102_LevelOrder {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(3);

        root.left = node1;
        root.right = node2;

        List<List<Integer>> list = new Code03_102_LevelOrder().levelOrder(root);
        System.out.println(list);
    }
    public List<List<Integer>> levelOrder(TreeNode root) {
        List<List<Integer>> resList = new ArrayList<>();

        // 判空
        if(root == null){
            return resList;
        }

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);
        TreeNode curNode;
        TreeNode curEnd=root;
        TreeNode nextEnd=null;

        List<Integer> levelList = new ArrayList<>();
        while (!queue.isEmpty()){
            curNode = queue.poll();
            levelList.add(curNode.val);
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
                resList.add(levelList);
                levelList = new ArrayList<>();
            }
        }

        return resList;
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
