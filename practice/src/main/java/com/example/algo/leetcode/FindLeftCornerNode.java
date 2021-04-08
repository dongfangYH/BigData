package com.example.algo.leetcode;

import java.util.LinkedList;
import java.util.List;

/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class FindLeftCornerNode {

    public static int findBottomLeftValue(TreeNode root) {

        if (root == null) return Integer.MIN_VALUE;

        List<TreeNode> nextLevelNodes = findNextLevelNodes(root);

        while (!nextLevelNodes.isEmpty()){
            List<TreeNode> subLevelNodes = new LinkedList<>();
            for (TreeNode treeNode : nextLevelNodes){
                subLevelNodes.addAll(findNextLevelNodes(treeNode));
            }

            if (!subLevelNodes.isEmpty()){
                nextLevelNodes = subLevelNodes;
            }else {
                break;
            }
        }

        return nextLevelNodes.get(0).val;
    }

    private static List<TreeNode> findNextLevelNodes(TreeNode node) {

        List<TreeNode> treeNodes = new LinkedList<>();
        if (node.left != null){
            treeNodes.add(node.left);
        }
        if (node.right != null){
            treeNodes.add(node.right);
        }

        return treeNodes;
    }

    public static void main(String[] args){

    }

    private static final class TreeNode {
       int val;
       TreeNode left;
       TreeNode right;
       TreeNode(int x) { val = x; }
    }
}
