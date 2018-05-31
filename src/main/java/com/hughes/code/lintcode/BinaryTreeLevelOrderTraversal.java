package com.hughes.code.lintcode;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.LinkedList;

import com.hughes.model.TreeNode;

/**
 * Created by Hughes on 2016/8/15.
 */
public class BinaryTreeLevelOrderTraversal {

    /**
     * @param root: The root of binary tree.
     * @return: buttom-up level order a list of lists of integer
     */
    public ArrayList<ArrayList<Integer>> levelOrderBottom(TreeNode root) {
        LinkedList<ArrayList<Integer>> result = new LinkedList<>();
        if (root == null) {
            return new ArrayList<>();
        }
        java.util.Queue<TreeNode> queue = new ArrayDeque<>();
        queue.add(root);
        TreeNode last = new TreeNode(0);
        queue.add(last);
        ArrayList<Integer> currentLevel = new ArrayList<>();
        while (queue.size() > 1) {
            TreeNode node = queue.poll();
            if (node != last) {
                currentLevel.add(node.val);
                if (node.left != null) queue.add(node.left);
                if (node.right != null) queue.add(node.right);
            } else {
                result.addFirst(new ArrayList<>(currentLevel));
                currentLevel.clear();
                queue.add(last);
            }
        }
        result.addFirst(currentLevel);
        return new ArrayList<>(result);
    }
}
