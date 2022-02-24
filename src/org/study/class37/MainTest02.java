package org.study.class37;

/**
 * @author phil
 * @date 2021/10/13 14:32
 */
public class MainTest02 {

    public static class SizeNode<K extends Comparable<K>> {
        public K key;
        public SizeNode<K> left;
        public SizeNode<K> right;
        public int size;
    }

    public static class SizeTree<K extends Comparable<K>> {
        public SizeNode<K> root;

        private SizeNode<K> leftRotate(SizeNode<K> head) {
            SizeNode<K> right = head.right;

            // adjust pointer
            head.right = right.left;
            right.left = head;

            // adjust size
            right.size = head.size;
            head.size = (head.left != null ? head.left.size : 0) + (head.right != null ? head.right.size : 0) + 1;

            return right;
        }

        private SizeNode<K> rightRotate(SizeNode<K> head) {
            SizeNode<K> left = head.left;

            // adjust pointer
            head.left = left.right;
            left.right = head;

            // adjust size
            left.size = head.size;
            head.size = (head.left != null ? head.left.size : 0) + (head.right != null ? head.right.size : 0) + 1;

            return left;
        }

        private SizeNode<K> maintain(SizeNode<K> head) {
            if (head == null) {
                return null;
            }

            int leftSize = head.left != null ? head.left.size : 0;
            int leftLeftSize = head.left != null && head.left.left != null ? head.left.left.size : 0;
            int leftRightSize = head.left != null && head.left.right != null ? head.left.right.size : 0;

            int rightSize = head.right != null ? head.right.size : 0;
            int rightLeftSize = head.right != null && head.right.left != null ? head.right.left.size : 0;
            int rightRightSize = head.right != null && head.right.right != null ? head.right.right.size : 0;

            if(leftLeftSize > rightSize){

            }
            if(leftRightSize > rightSize){

            }
            if(rightLeftSize > leftSize){

            }
            if(rightRightSize > leftSize){

            }

            return head;
        }

    }
}
