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
        node.next = node.next.next;
        node.val = node.next.val;
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
            first = first.next;
        }
        if (first == null) {
            if (i == n) {
                return head.next;
            } else {
                return null;
            }
        }
        while (first.next != null) {
            first = first.next;
            second = second.next;
        }
        second.next = second.next.next;
        return head;
    }
}
