package org.study.coding.class07;

/**
 * @author phil
 * @date 2022-03-24 16:23:29
 */
public class MainTest02 {
    private static class TreeNode {
        public int value;
        public TreeNode left;
        public TreeNode right;
    }


    public static int minCameraCover1(TreeNode head) {

        Info info = process(head);
        return (int) Math.min(Math.min(info.coveredYesCamera, info.coveredNoCamera), info.uncovered + 1);
    }

    private static Info process(TreeNode head) {

        if (head == null) {
            return new Info(Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
        }

        Info leftInfo = process(head.left);
        Info rightInfo = process(head.right);

        long uncovered = leftInfo.coveredNoCamera + rightInfo.coveredNoCamera;

        long coveredNoCamera = Math.min(
                // 1)
                leftInfo.coveredYesCamera + rightInfo.coveredYesCamera,

                Math.min(
                        // 2)
                        leftInfo.coveredYesCamera + rightInfo.coveredNoCamera,

                        // 3)
                        leftInfo.coveredNoCamera + rightInfo.coveredYesCamera));

        long coveredYesCamera =
                Math.min(
                        leftInfo.uncovered,
                        Math.min(leftInfo.coveredNoCamera, leftInfo.coveredYesCamera))


                        + Math.min(
                        rightInfo.uncovered,
                        Math.min(rightInfo.coveredNoCamera, rightInfo.coveredYesCamera))

                        + 1;

        return new Info(uncovered, coveredNoCamera, coveredYesCamera);
    }


    private static class Info {
        public long uncovered;
        public long coveredNoCamera;
        public long coveredYesCamera;

        public Info(long uncovered, long coveredNoCamera, long coveredYesCamera) {
            this.uncovered = uncovered;
            this.coveredNoCamera = coveredNoCamera;
            this.coveredYesCamera = coveredYesCamera;
        }
    }
}
