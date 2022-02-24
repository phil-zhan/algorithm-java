package org.study.class35;

/**
 * @author phil
 * @date 2021/10/9 16:19
 */
public class MainTest02 {


    public static class AvlTree<K extends Comparable<K>, V> {
        public AvlNode<K, V> root;
        public int size;

        public AvlTree() {
            this.root = null;
            this.size = 0;
        }

        private AvlNode<K, V> leftRotate(AvlNode<K, V> head) {

            int same = head.all - (head.left != null ? head.left.all : 0) - head.right.all;

            AvlNode<K, V> right = head.right;
            head.right = right.left;
            right.left = head;

            // adjust height
            // right head
            head.height = Math.max(head.left != null ? head.left.height : 0, head.right != null ? head.right.height : 0) + 1;
            right.height = Math.max(right.left.height, right.right != null ? right.right.height : 0) + 1;

            // adjust all size
            right.all = head.all;
            head.all = (head.left != null ? head.left.all : 0) + (head.right != null ? head.right.all : 0) + same;

            return right;
        }

        private AvlNode<K, V> rightRotate(AvlNode<K, V> head) {

            int same = head.all - head.left.all - (head.right != null ? head.right.all : 0);

            AvlNode<K, V> left = head.left;
            head.left = left.right;
            left.right = head;

            // adjust height
            head.height = Math.max(head.left != null ? head.left.height : 0, head.right != null ? head.right.height : 0) + 1;
            left.height = Math.max(left.left != null ? left.left.height : 0, left.right.height) + 1;

            left.all = head.all;
            head.all = (head.left != null ? head.left.all : 0) + (head.right != null ? head.right.all : 0) + same;


            return left;
        }

        private AvlNode<K, V> maintain(AvlNode<K, V> head) {
            if (head == null) {
                return null;
            }

            int leftHeight = head.left != null ? head.left.height : 0;
            int rightHeight = head.right != null ? head.right.height : 0;
            if (Math.abs(leftHeight - rightHeight) > 1) {

                // LR
                // LL
                if (leftHeight > rightHeight) {
                    int leftLeftHeight = head.left != null && head.left.left != null ? head.left.left.height : 0;
                    int leftRightHeight = head.left != null && head.left.right != null ? head.left.right.height : 0;
                    if (leftRightHeight > leftLeftHeight) {
                        // LR
                        head.left = leftRotate(head.left);
                    }
                    head = rightRotate(head);

                } else {
                    // RL
                    // RR
                    int rightRightHeight = head.right != null && head.right.right != null ? head.right.right.height : 0;
                    int rightLeftHeight = head.right != null && head.right.left != null ? head.right.left.height : 0;

                    if (rightLeftHeight > rightRightHeight) {
                        // RL
                        head.right = rightRotate(head.right);
                    }
                    head = leftRotate(head);

                }
            }

            return head;
        }

        public AvlNode<K, V> add(AvlNode<K, V> head, K key, V value) {
            if (null == head) {
                return new AvlNode<>(key, value);
            }

            head.all++;

            if (key == head.key){
                // key exist
                return head;
            }

            if (key.compareTo(head.key) < 0) {
                head.left = add(head.left, key, value);
            } else {
                head.right = add(head.right, key, value);
            }

            // adjust height
            head.height = Math.max(head.left != null ? head.left.height : 0, head.right != null ? head.right.height : 0) + 1;

            return maintain(head);
        }

        public void add(K sum,V val) {
            root = add(root, sum,val);
        }

        public AvlNode<K, V> delete(AvlNode<K, V> head, K key) {
            if (head == null) {
                return null;
            }


            if (key.compareTo(head.key) > 0) {
                head.all--;
                head.right = delete(head.right, key);
            } else if (key.compareTo(head.key) < 0) {
                head.all--;
                head.left = delete(head.left, key);
            } else {
                // delete current node
                if (head.left != null && head.right != null) {
                    if (head.all == 1){
                        AvlNode<K, V> des = head.right;
                        while (des.left != null) {
                            des = des.left;
                        }

                        head.right = delete(head.right, des.key);

                        des.left = head.left;
                        des.right = head.right;
                        head = des;
                    }else{
                        head.all--;
                    }

                } else if (head.left != null) {
                    if (head.all == 1){
                        head = head.left;
                    }else{
                        head.all--;
                    }

                } else if (head.right != null) {
                    if (head.all == 1){
                        head = head.right;
                    }else{
                        head.all--;
                    }

                } else {
                    if (head.all == 1){
                        head = null;
                    }else{
                        head.all--;
                    }
                }
            }
            if (head != null) {
                head.height = Math.max(head.left != null ? head.left.height : 0, head.right != null ? head.right.height : 0) + 1;
            }

            return maintain(head);
        }

        public int getLessKey(K key){
            int ans = 0;
            AvlNode<K,V> head = this.root;
            while (head != null){
                if (key.compareTo(head.key) < 0){
                    // left
                    head = head.left;
                }else if (key.compareTo(head.key) > 0){
                    // right
                    ans += head.all - (head.right != null ? head.right.all:0);
                    head = head.right;
                }else{
                    ans += (head.left != null ? head.left.all:0);
                    break;
                }
            }

            return ans;
        }

        /**
         * avl node
         *
         * @since 2022-02-23 11:19:37
         */
        public static class AvlNode<K extends Comparable<K>, V> {
            public K key;
            public V value;
            public int height;
            public int all;
            public AvlNode<K, V> left;
            public AvlNode<K, V> right;

            public AvlNode(K key, V value) {
                this.key = key;
                this.value = value;
                this.height = 1;
                this.all = 1;
            }
        }
    }

    public static void main(String[] args) {
        // -2,5,-1
        AvlTree<Integer, Integer> avlTree = new AvlTree<>();
        int[] nums = new int[]{-2,5,-1};
        int sum = 0;
        int lower =-2;
        int upper = 2;
        
        avlTree.add(0,0);

        int ans = 0;
        for (int num : nums) {
            sum += num;

            int a = avlTree.getLessKey(sum - lower + 1);
            int b = avlTree.getLessKey(sum - upper);
            ans += a - b;
            avlTree.add(sum, sum);
        }
        System.out.println(ans);
    }
}
