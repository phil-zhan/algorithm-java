package org.study.system.class48;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.Objects;
import java.util.Queue;

/**
 * @author phil
 * @since 2022-04-17 17:20:11
 */
public class Code32_297_Serialize_And_Deserialize_Binary_Tree {

    public static class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;

        TreeNode(int x) {
            val = x;
        }

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    // Encodes a tree to a single string.
    public static String serialize(TreeNode root) {
        Queue<String> ans = new LinkedList<>();
        pres(root, ans);
        String res = "";
        while (!ans.isEmpty()) {
            res += ans.poll() + " ";
        }
        // for(int i=0;i<ans.size()-1;i++){
        //     res+=ans.poll()+" ";
        // }
        res += ans.poll();


        return res;
    }

    // Decodes your encoded data to tree.
    public static TreeNode deserialize(String data) {
        String[] ins = data.split(" ");
        Queue<String> queue = new LinkedList<>(Arrays.asList(ins));

        if (queue.size() == 0) {
            return null;
        }
        return preb(queue);
    }

    public static void pres(TreeNode head, Queue<String> ans) {
        if (head == null) {
            ans.add(null);
        } else {
            ans.add(String.valueOf(head.val));
            pres(head.left, ans);
            pres(head.right, ans);
        }
    }

    public static TreeNode preb(Queue<String> prelist) {
        String val = prelist.poll();
        if (Objects.equals(val, "null") || val == null) {
            return null;
        }
        System.out.println(val);
        TreeNode head = new TreeNode(Integer.parseInt(val));
        head.left = preb(prelist);
        head.right = preb(prelist);
        return head;
    }

    public static void main(String[] args) {

        TreeNode head = new TreeNode(1);
        head.left = new TreeNode(2);
        head.right = new TreeNode(3);
        head.right.left = new TreeNode(4);
        head.right.right = new TreeNode(5);

        String serialize = serialize(head);
        System.out.println(serialize);

        TreeNode root = deserialize(serialize);
        System.out.println(root);
    }
}
