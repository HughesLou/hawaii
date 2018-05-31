package com.hughes.code.lintcode;

import com.hughes.model.TreeNode;

/**
 * Created by Hughes on 2016/8/14.
 */
public class ConstructBinaryTree {

    /**
     * @param preorder : A list of integers that preorder traversal of a tree
     * @param inorder  : A list of integers that inorder traversal of a tree
     * @return : Root of a tree
     */
    public TreeNode buildTree(int[] preorder, int[] inorder) {
        return build(preorder, inorder, 0, preorder.length - 1, 0, inorder.length - 1);

    }

    public TreeNode build(int[] preorder, int[] inorder, int pStart, int pEnd, int iStart,
            int iEnd) {
        if (pStart > pEnd) return null;
        int head = preorder[pStart];
        int i = iStart;
        for (; i < iEnd; i++) {
            if (inorder[i] == head) break;
        }
        TreeNode node = new TreeNode(head);
        int lenLeft = i - iStart;
        node.left = build(preorder, inorder, pStart + 1, pStart + lenLeft, iStart, i - 1);
        node.right = build(preorder, inorder, pStart + lenLeft + 1, pEnd, i + 1, iEnd);
        return node;
    }

    public static void main(String[] args) {
        int[] preoder = { 1, 2, 3 };
        int[] inorder = { 2, 1, 3 };
        TreeNode treeNode = new ConstructBinaryTree().buildTree(preoder, inorder);
        System.out.println(treeNode);
    }
}
