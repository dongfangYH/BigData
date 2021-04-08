package com.example.algo.structcure;

import java.util.LinkedList;

/**
 * 二叉树
 * @param <T>
 */
public class BinaryTree<T> {

    private TreeNode<T> root;

    public BinaryTree(T data) {
        this.root = new TreeNode<>(data);
    }

    /**
     * 前序遍历
     * @param treeNode
     */
    public void preOrder(TreeNode<T> treeNode){
        if (null != treeNode){
            System.out.println(treeNode.data);
            preOrder(treeNode.left);
            preOrder(treeNode.right);
        }
    }

    /**
     *
     * @param treeNode
     */
    public void inOrder(TreeNode<T> treeNode){
        if (null != treeNode){
            inOrder(treeNode.left);
            System.out.println(treeNode.data);
            inOrder(treeNode.right);
        }
    }

    /**
     *
     * @param treeNode
     */
    public void postOrder(TreeNode<T> treeNode){
        if (null != treeNode){
            postOrder(treeNode.left);
            postOrder(treeNode.right);
            System.out.println(treeNode.data);
        }
    }

    /**
     *
     * @param treeNode
     */
    public void levelOrder(TreeNode<T> treeNode){
        LinkedList<TreeNode<T>> queue = new LinkedList<>();
        queue.add(treeNode);
        while (!queue.isEmpty()){
            TreeNode<T> node = queue.poll();
            System.out.println(node.data);
            if (node.left != null){
                queue.add(node.left);
            }
            if (node.right != null){
                queue.add(node.right);
            }
        }
    }

    public static void main(String[] args){
        BinaryTree<Integer> tree = new BinaryTree<>(10);
        tree.root.left = new TreeNode<>(5);
        tree.root.right = new TreeNode<>(15);

        tree.root.left.left = new TreeNode<>(3);
        tree.root.left.right = new TreeNode<>(6);

        tree.root.right.left = new TreeNode<>(13);
        tree.root.right.right = new TreeNode<>(16);

        tree.levelOrder(tree.root);
    }

    private static final class TreeNode<T>{

        private T data;
        private TreeNode<T> left;
        private TreeNode<T> right;

        public TreeNode(T data) {
            this.data = data;
        }
    }
}
