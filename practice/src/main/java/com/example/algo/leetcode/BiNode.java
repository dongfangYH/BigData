package com.example.algo.leetcode;

import java.util.Stack;

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

    public TreeNode convertBiNode2(TreeNode root) {
        if (root == null) {
            return null;
        }
        TreeNode subRoot = convertBiNode(root.left);
        if (subRoot == null) {
            subRoot = root;
        } else {
            TreeNode subRootTmp = subRoot;
            while (subRoot.right != null) {
                subRoot = subRoot.right;
            }
            subRoot.right = root;
            subRoot = subRootTmp;
        }
        root.left = null;
        root.right = convertBiNode(root.right);
        return subRoot;
    }

    public TreeNode convertBiNode4(TreeNode root) {
        //跳出递归的条件
        if (root == null)
            return null;

        //遍历到最左节点
        TreeNode RootNode = convertBiNode(root.left);

        if (RootNode == null)
            //细节，如果返回值为null,证明已经到最左节点了，将最左节点的值赋给RootNode
            RootNode = root;
        else {
            TreeNode RNode = RootNode;//备份一个头指针，用于迭代

            //迭代到RootNode的最右节点
            while (RNode.right != null)
                RNode = RNode.right;

            //最右节点指向root
            RNode.right = root;

            //left置空
            root.left = null;
            //RNode.right.left =null;意思一样，因为RNode.right就是指向root
        }

        //root.right重新指向root.right的最左节点
        root.right = convertBiNode(root.right);
        return RootNode;//返回最左节点
    }

    public TreeNode convertBiNode3(TreeNode root) {
        Stack<TreeNode> stack=new Stack<>();
        //定义头节点
        TreeNode RootNode=new TreeNode(0);
        //备份头节点
        TreeNode rNode=RootNode;
        while(root!=null||!stack.isEmpty()){
            if(root!=null){
                stack.push(root);//入栈
                root=root.left;
            }else{
                TreeNode lNode=stack.pop();//弹栈
                rNode.right=lNode;
                lNode.left=null;
                rNode=lNode;
                root=lNode.right;//遍历右子树
            }
        }
        return RootNode.right;
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
