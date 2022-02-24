package org.study.class35;

/**
 * @author phil
 * @date 2021/10/9 16:19
 */
public class MainTest01 {

    private static class AvlNode<K extends Comparable<K>, V> {
        public K key;
        public V value;
        public AvlNode<K, V> left;
        public AvlNode<K, V> right;
        public int height;

        public AvlNode(K key, V value) {
            this.key = key;
            this.value = value;
            this.height = 1;
        }
    }


    private class AvlTree<K extends Comparable<K>, V> {
        public AvlNode<K, V> root;
        public int size;

        public AvlTree() {
            this.root = null;
            this.size = 0;
        }

        public int size() {
            return size;
        }

        public boolean containsKey(K key) {
            if (key == null) {
                return false;
            }
            AvlNode<K, V> lastNode = findLastIndex(key);
            return lastNode != null && key.compareTo(lastNode.key) == 0;
        }


        public void put(K key, V value) {
            if (key == null) {
                return;
            }
            AvlNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                lastNode.value = value;
            } else {
                size++;
                root = add(root, key, value);
            }
        }

        public void remove(K key) {
            if (key == null) {
                return;
            }
            if (containsKey(key)) {
                size--;
                root = delete(root, key);
            }
        }

        public V get(K key) {
            if (key == null) {
                return null;
            }
            AvlNode<K, V> lastNode = findLastIndex(key);
            if (lastNode != null && key.compareTo(lastNode.key) == 0) {
                return lastNode.value;
            }
            return null;
        }

        public K firstKey() {
            if (root == null) {
                return null;
            }
            AvlNode<K, V> cur = root;
            while (cur.left != null) {
                cur = cur.left;
            }
            return cur.key;
        }

        public K lastKey() {
            if (root == null) {
                return null;
            }
            AvlNode<K, V> cur = root;
            while (cur.right != null) {
                cur = cur.right;
            }
            return cur.key;
        }

        public K floorKey(K key) {
            if (key == null) {
                return null;
            }
            AvlNode<K, V> lastNoBigNode = findLastNoBigIndex(key);
            return lastNoBigNode == null ? null : lastNoBigNode.key;
        }

        public K ceilingKey(K key) {
            if (key == null) {
                return null;
            }
            AvlNode<K, V> lastNoSmallNode = findLastNoSmallIndex(key);
            return lastNoSmallNode == null ? null : lastNoSmallNode.key;
        }



        // 找到小于等于 K 最左的
        private AvlNode<K, V> findLastIndex(K key) {
            AvlNode<K, V> pre = root;
            AvlNode<K, V> cur = root;
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

        // 找打大于等于K，离K最近的
        private AvlNode<K, V> findLastNoSmallIndex(K key) {
            AvlNode<K, V> ans = null;
            AvlNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    ans = cur;
                    cur = cur.left;
                } else {
                    cur = cur.right;
                }
            }
            return ans;
        }


        //
        private AvlNode<K, V> findLastNoBigIndex(K key) {
            AvlNode<K, V> ans = null;
            AvlNode<K, V> cur = root;
            while (cur != null) {
                if (key.compareTo(cur.key) == 0) {
                    ans = cur;
                    break;
                } else if (key.compareTo(cur.key) < 0) {
                    cur = cur.left;
                } else {
                    ans = cur;
                    cur = cur.right;
                }
            }
            return ans;
        }


        private AvlNode<K, V> add(AvlNode<K, V> root, K key, V value) {
            if (null == root) {
                return new AvlNode<>(key, value);
            } else {
                if (key.compareTo(root.key) < 0) {
                    root.left = add(root.left, key, value);
                } else {
                    root.right = add(root.right, key, value);
                }
                // 调整高度
                root.height = Math.max(root.left != null ? root.left.height : 0, root.right != null ? root.right.height : 0) + 1;

            }

            return maintain(root);
        }

        private AvlNode<K, V> delete(AvlNode<K, V> root, K key) {
            if (key.compareTo(root.key) > 0) {
                root.right = delete(root.right, key);
            } else if (key.compareTo(root.key) < 0) {
                root.left = delete(root.left, key);
            } else {
                if (root.left == null && root.right == null) {
                    root = null;
                } else if (root.left == null) {
                    root = root.right;
                } else if (root.right == null) {
                    root = root.left;
                } else {
                    AvlNode<K, V> des = root.right;
                    while (des.left != null) {
                        des = des.left;
                    }

                    root.right = delete(root.right, des.key);
                    des.left = root.left;
                    des.right = root.right;
                    root = des;
                }
            }

            if (root != null) {
                root.height = Math.max(root.left != null ? root.left.height : 0, root.right != null ? root.right.height : 0) + 1;
            }
            return maintain(root);
        }

        private AvlNode<K, V> maintain(AvlNode<K, V> root) {

            if (null == root) {
                return null;
            }
            int leftHeight = root.left != null ? root.left.height : 0;
            int rightHeight = root.right != null ? root.right.height : 0;

            if (Math.abs(leftHeight - rightHeight) > 1) {
                if (leftHeight > rightHeight) {
                    int leftLeftHeight = root.left != null && root.left.left != null ? root.left.left.height : 0;
                    int leftRightHeight = root.left != null && root.left.right != null ? root.left.right.height : 0;
                    if (leftLeftHeight >= leftRightHeight) {
                        root = rightRotate(root);
                    } else {
                        root.left = leftRotate(root.left);
                        root = rightRotate(root);
                    }
                } else {
                    int rightLeftHeight = root.right != null && root.right.left != null ? root.right.left.height : 0;
                    int rightRightHeight = root.right != null && root.right.right != null ? root.right.right.height : 0;
                    if (rightRightHeight >= rightLeftHeight) {
                        root = leftRotate(root);
                    } else {
                        root.right = rightRotate(root.right);
                        root = leftRotate(root);
                    }
                }
            }

            return root;
        }

        private AvlNode<K, V> leftRotate(AvlNode<K, V> root) {

            AvlNode<K, V> right = root.right;
            root.right = right.left;
            right.left = root;

            // 调整高度
            root.height = Math.max(root.left != null ? root.left.height : 0, root.right != null ? root.right.height : 0) + 1;
            right.height = Math.max(right.left.height, right.right != null ? right.right.height : 0) + 1;

            return right;
        }

        private AvlNode<K, V> rightRotate(AvlNode<K, V> root) {
            AvlNode<K, V> left = root.left;
            root.left = left.right;
            left.right = root;

            // 调整高度
            root.height = Math.max(root.left != null ? root.left.height : 0, root.right != null ? root.right.height : 0) + 1;
            left.height = Math.max(left.left != null ? left.left.height : 0, left.right.height) + 1;


            return left;
        }

    }
}
