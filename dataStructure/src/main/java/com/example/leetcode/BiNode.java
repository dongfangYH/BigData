package com.example.leetcode;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-03 17:21
 **/
public class BiNode {

    public static final class TreeNode {
      int val;
      TreeNode left;
      TreeNode right;
      TreeNode(int x) { val = x; }
    }

    public TreeNode convertBiNode(TreeNode root) {
        TreeNode head = new TreeNode(0);// 单链表的头指针哨兵
        inorder(root,head);
        return head.right;
    }

    private TreeNode inorder(TreeNode root,TreeNode prev){
        if (root != null){
            prev = inorder(root.left,prev);
            root.left = null;
            prev.right = root;
            prev = root;
            prev = inorder(root.right,prev);
        }
        return prev;
    }

    public static void main(String[] args){
        TreeNode root = new TreeNode(5);
        TreeNode node3 = new TreeNode(3);
        TreeNode node1 = new TreeNode(1);
        TreeNode node4 = new TreeNode(4);
        TreeNode node7 = new TreeNode(7);
        TreeNode node6 = new TreeNode(6);
        TreeNode node8 = new TreeNode(8);

        root.left = node3;
        root.right=node7;

        node3.left = node1;
        node3.right = node4;

        node7.left = node6;
        node7.right = node8;

        BiNode biNode = new BiNode();
        biNode.convertBiNode(root);
    }

}
