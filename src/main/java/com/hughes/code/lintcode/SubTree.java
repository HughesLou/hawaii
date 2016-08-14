package com.hughes.code.lintcode;

import com.hughes.model.TreeNode;

import java.util.ArrayList;

/**
 * Created by Hughes on 2016/8/15.
 */
public class SubTree {
    /**
     * @param T1, T2: The roots of binary tree.
     * @return: True if T2 is a subtree of T1, or false.
     */
    public boolean isSubtree(TreeNode T1, TreeNode T2) {
        if (null == T1 && null == T2) {
            return true;
        }
        if (null == T1) {
            return false;
        }
        if (null == T2) {
            return true;
        }
        if (T1.val == T2.val) {
            if (isEqual(T1, T2)) {
                return true;
            }
        }
        boolean l = isSubtree(T1.left, T2);
        boolean r = isSubtree(T1.right, T2);
        if (l || r) {
            return true;
        }
        return false;
    }

    public boolean isEqual(TreeNode T1, TreeNode T2) {
        if (T1 == null && T2 == null)
            return true;
        if (T1 == null)
            return false;
        if (T2 == null)
            return false;
        if (T1.val == T2.val) {
            boolean lft = isEqual(T1.left, T2.left);
            boolean rit = isEqual(T1.right, T2.right);
            if (lft && rit)
                return true;
        }
        return false;
    }

    public ArrayList<Integer> preorderTraversal(TreeNode root) {
        if (null == root) {
            return null;
        }
        ArrayList<Integer> results = new ArrayList<>();
        results.add(new Integer(root.val));
        results.addAll(preorderTraversal(root.left));
        results.addAll(preorderTraversal(root.right));
        return results;
    }
}
