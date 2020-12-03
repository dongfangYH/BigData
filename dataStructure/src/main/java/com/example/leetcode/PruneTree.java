package com.example.leetcode;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-03 15:54
 **/
public class PruneTree {

    public TreeNode pruneTree(TreeNode root) {
        if (root == null) return null;
        while (doPruneLeafTree(null, root));
        if (root.left == null && root.right == null && root.val == 0) return null;
        return root;
    }

    private boolean doPruneLeafTree(TreeNode p, TreeNode node) {

        if (node == null){
            return false;
        }

        boolean flag = false;

        if (p != null && node.left == null && node.right == null && node.val == 0){
            if (p.left == node){
                p.left = null;
                flag = true;
            }

            if (p.right == node){
                p.right = null;
                flag = true;
            }
        }

        return flag || doPruneLeafTree(node, node.left) || doPruneLeafTree(node, node.right);

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
