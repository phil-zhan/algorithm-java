package org.study.system.class48;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 * @author phil
 * @date 2022/2/14 14:35
 */
public class Code06_107_LevelOrderBottom {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(2);
        TreeNode node1 = new TreeNode(1);
        TreeNode node2 = new TreeNode(3);

        root.left = node1;
        root.right = node2;

        List<List<Integer>> list = new Code06_107_LevelOrderBottom().levelOrder(root);
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
        int len = resList.size();
        for (int i = 0; i <len ; i++) {
            if(i < (len-i-1)){
                List<Integer> left = resList.get(i);
                resList.set(i,resList.get(len-i-1));
                resList.set(len-i-1,left);
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
