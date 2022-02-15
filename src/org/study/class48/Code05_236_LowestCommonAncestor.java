package org.study.class48;

/**
 * @author phil
 * @date 2022/2/14 14:06
 */
public class Code05_236_LowestCommonAncestor {

    public static void main(String[] args) {
        TreeNode root = new TreeNode(1);
        TreeNode node1 = new TreeNode(2);
        TreeNode node2 = new TreeNode(3);
        TreeNode node3 = new TreeNode(3);

        root.left = node1;
        root.right = node2;
        //node2.left = node3;
        TreeNode treeNode = new Code05_236_LowestCommonAncestor().lowestCommonAncestor(root, node2, node1);
        System.out.println(treeNode.val);
    }
    public TreeNode lowestCommonAncestor(TreeNode root, TreeNode p, TreeNode q) {
        if(root == null){
            return null;
        }

        return process(root,p,q).ancestor;
    }

    public Info process(TreeNode root, TreeNode p, TreeNode q){
        if(root ==null){
            return new Info();
        }

        Info leftInfo = process(root.left,p,q);
        Info rightInfo = process(root.right,p,q);
        Info curInfo = new Info();
        curInfo.findP = root == p || leftInfo.findP || rightInfo.findP;
        curInfo.findQ = root == q || leftInfo.findQ || rightInfo.findQ;

        if(leftInfo.ancestor!=null){
            curInfo.ancestor = leftInfo.ancestor;
        }else if(rightInfo.ancestor != null){
            curInfo.ancestor = rightInfo.ancestor;
        }else{
            boolean curAsAncestor = (root == p && (leftInfo.findQ || rightInfo.findQ))
                    || (root == q && (leftInfo.findP || rightInfo.findP))
                    || ((leftInfo.findP && rightInfo.findQ) || leftInfo.findQ && rightInfo.findP);
            if(curAsAncestor){
                curInfo.ancestor = root;
            }
        }
        return curInfo;
    }


    public class Info{
        public TreeNode ancestor;
        public boolean findP;
        public boolean findQ;
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
