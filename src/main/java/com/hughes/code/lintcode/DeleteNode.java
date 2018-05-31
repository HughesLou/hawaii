package com.hughes.code.lintcode;

import com.hughes.model.ListNode;

/**
 * Created by Hughes on 2016/8/14.
 */
public class DeleteNode {

    /**
     * @param node: the node in the list should be deleted
     * @return: nothing
     */
    public void deleteNode(ListNode node) {
        node.setNext(node.getNext().getNext());
        node.setVal(node.getNext().getVal());
    }

    /**
     * @param head: The first node of linked list.
     * @param n:    An integer.
     * @return: The head of linked list.
     */
    ListNode removeNthFromEnd(ListNode head, int n) {
        if (head == null) {
            return null;
        }
        ListNode first = head;
        ListNode second = head;
        int i = 0;
        for (; first != null && i < n; i++) {
            first = first.getNext();
        }
        if (first == null) {
            if (i == n) {
                return head.getNext();
            } else {
                return null;
            }
        }
        while (first.getNext() != null) {
            first = first.getNext();
            second = second.getNext();
        }
        second.setNext(second.getNext().getNext());
        return head;
    }
}
