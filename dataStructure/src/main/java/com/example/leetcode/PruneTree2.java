package com.example.leetcode;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-03 16:29
 **/
public class PruneTree2 {

    /**
     * @param root
     * @return
     */
    public TreeNode pruneTree(TreeNode root) {
        return containsOne(root) ? root : null;
    }

    private boolean containsOne(TreeNode node) {
        if (node == null) return false;

        boolean lo = containsOne(node.left);
        boolean ro = containsOne(node.right);

        if (!lo) node.left = null;
        if (!ro) node.right = null;

        return node.val == 1 || lo || ro;
    }


    public static final class TreeNode {
        int val;
        TreeNode left;
        TreeNode right;
        TreeNode() {}
        TreeNode(int val) { this.val = val; }
        TreeNode(int val, TreeNode left, TreeNode right) {
            this.val = val;
            this.left = left;
            this.right = right;
        }
    }
}
