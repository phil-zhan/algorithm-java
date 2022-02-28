package org.study.system.class11;

/**
 * @author phil
 * @date 2021/7/2 12:36
 */
public class MainTest6 {

    private static class Node{
        public int value;
        public Node left;
        public Node right;
        public Node parent;

        public Node(int value){
            this.value = value;
        }
    }


    private static Node getSuccessorNode(Node node){
        if (null == node) {
            return null;
        }

        if (null != node.right) {
            return getRightMostLeft(node.right);
        }

        // 也就相当于上面的 else

        Node parent = node.parent;
        // 无右子树
        while (node.parent != null && parent.right == node) {
            // 当前节点是其父亲节点右孩子
            node = parent;
            parent = node.parent;
        }

        return parent;
    }

    private static Node getRightMostLeft(Node node) {
        if (node == null) {
            return null;
        }

        while (null != node.left){
            node = node.left;
        }
        return node;
    }
}
