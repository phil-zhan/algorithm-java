package org.study.system.class11;

import java.util.ArrayList;
import java.util.List;

/**
 * @author phil
 * @date 2021/7/2 9:06
 */
public class MainTest3 {

    private static class Node{
        public int value;

        public List<Node> childrens;

        public Node(){}
        public Node(int value){
            this.value = value;
        }

        public Node(int value,List<Node> childrens){
            this.value = value;
            this.childrens = childrens;
        }
    }

    private static class TreeNode{
        public int value;
        public TreeNode left;
        public TreeNode right;

        public TreeNode(int value){
            this.value = value;
        }
    }

    private static class Codec{



        private static TreeNode encode(Node root){
            if (null == root) {
                return null;
            }
            TreeNode head = new TreeNode(root.value);

            //
            head.left = en(root.childrens);

            return head;
        }

        private static TreeNode en(List<Node> childrens) {
            TreeNode head = null;
            TreeNode cur = null;
            for (Node child : childrens) {
                TreeNode treeNode = new TreeNode(child.value);

                if (null == head) {

                    // 第一次只会接入if，不会走到else
                    head = treeNode;
                }else {
                    cur.right = treeNode;
                }

                // 第一次将head和cur都置位当前节点。第二次及之后【当前节点变成当前节点的右节点】
                cur = treeNode;
                // 生成当前节点的左数
                cur.left = en(child.childrens);
            }

            return head;
        }


        private static Node decode(TreeNode head){
            if (null == head) {
                return null;
            }
            Node root = new Node(head.value);

            root.childrens = de(head.left);

            return root;
        }

        private static List<Node> de(TreeNode head) {

            List<Node> childrens = new ArrayList<>();
            while (null != head) {
                Node cur = new Node(head.value,de(head.left));

                childrens.add(cur);
                head = head.right;
            }

            return childrens;
        }
    }

}
