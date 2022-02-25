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

        public SizeNode(K key) {
            this.key = key;
            this.size = 1;
        }
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

            if (leftLeftSize > rightSize) {
                head = rightRotate(head);
                head.right = maintain(head.right);
                head = maintain(head);
            }
            if (leftRightSize > rightSize) {
                head.left = leftRotate(head.left);
                head = rightRotate(head);
                head.left = maintain(head.left);
                head.right = maintain(head.right);
                head = maintain(head);
            }
            if (rightLeftSize > leftSize) {
                head.right = rightRotate(head.right);
                head = leftRotate(head);
                head.left = maintain(head.left);
                head.right = maintain(head.right);
                head = maintain(head);
            }
            if (rightRightSize > leftSize) {
                head = leftRotate(head);
                head.left = maintain(head.left);
                head = maintain(head);

            }

            return head;
        }

        public SizeNode<K> add(SizeNode<K> head, K key) {

            if (head == null) {
                return new SizeNode<>(key);
            }

            head.size++;
            if (key.compareTo(head.key) < 0) {
                head.left = add(head.left, key);
            } else {
                head.right = add(head.right, key);
            }

            return maintain(head);
        }

        public void add(K key){
            root = add(root,key);
        }

        public SizeNode<K> delete(SizeNode<K> head, K key) {
            if (key.compareTo(head.key) > 0) {
                head.right = delete(head.right, key);
            } else if (key.compareTo(head.key) < 0) {
                head.left = delete(head.left, key);
            } else {
                if (head.left == null && head.right == null) {
                    head = null;
                } else if (head.left == null) {
                    head = head.right;
                } else if (head.right == null) {
                    head = head.left;
                } else {
                    SizeNode<K> pre = null;
                    SizeNode<K> des = head.right;
                    while (des.left != null) {
                        pre = des;
                        des = des.left;
                    }
                    if (pre != null) {
                        pre.left = des.right;
                        des.right = head.right;
                    }

                    des.left = head.left;
                    des.size = des.left.size + (des.right != null ? des.right.size : 0) + 1;

                    head = des;

                }
            }

            // return maintain(head);
            return head;
        }

        public SizeNode<K> getIndex(SizeNode<K> head, int kth) {
            if (kth == (head.left != null ? head.left.size : 0) + 1) {
                return head;
            }
            if (kth <= (head.left != null ? head.left.size : 0)) {

                return head.left != null ? getIndex(head.left, kth) : null;
            }
            return getIndex(head.right, kth - (head.left != null ? head.left.size : 0) - 1);
        }

        public K getIndexKey(int index) {
            if (index < 0 || index >= this.size()) {
                throw new RuntimeException("invalid parameter.");
            }
            return getIndex(root, index + 1).key;
        }
        public int size() {
            return root == null ? 0 : root.size;
        }

        private SizeNode<K> findLastIndex(K key) {
            SizeNode<K> pre = root;
            SizeNode<K> cur = root;
            while (cur != null) {
                pre = cur;
                if (key.compareTo(cur.key) == 0) {
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return pre;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            SizeNode<K> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.key) == 0;
        }

        public void remove(K key) {
            if (key == null) {
                throw new RuntimeException("invalid parameter.");
            }
            if (containsKey(key)) {
                root = delete(root, key);
            }
        }

    }

    public static class Node implements Comparable<Node> {
        public int index;
        public int value;

        public Node(int i, int v) {
            index = i;
            value = v;
        }

        @Override
        public int compareTo(Node o) {
            return value != o.value ? Integer.compare(value, o.value)
                    : Integer.compare(index, o.index);
        }
    }

    public static double[] medianSlidingWindow(int[] nums, int k) {
        SizeTree<Node> tree = new SizeTree<>();
        for (int i = 0; i < k - 1; i++) {
            tree.add(new Node(i, nums[i]));
        }
        double[] ans = new double[nums.length - k + 1];
        int index = 0;
        for (int i = k - 1; i < nums.length; i++) {
            tree.add(new Node(i, nums[i]));
            if (tree.size() % 2 == 0) {
                Node upMid = tree.getIndexKey(tree.size() / 2 - 1);
                Node downMid = tree.getIndexKey(tree.size() / 2);
                ans[index++] = ((double) upMid.value + (double) downMid.value) / 2;
            } else {
                Node mid = tree.getIndexKey(tree.size() / 2);
                ans[index++] = mid.value;
            }
            tree.remove(new Node(i - k + 1, nums[i - k + 1]));
        }
        return ans;
    }
}
