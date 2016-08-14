package com.hughes.code.lintcode;

import com.hughes.model.ListNode;

/**
 * Created by Hughes on 2016/8/14.
 */
public class ReverseLinkedList {
    public ListNode reverse(ListNode head) {
        if (null == head || null == head.getNext()) {
            return head;
        }

        ListNode reversedHead = reverse(head.getNext());
        head.getNext().setNext(head);
        head.setNext(null);
        return reversedHead;
    }

    public ListNode reverse2(ListNode head) {
        if (null == head) {
            return null;
        }
        ListNode pre = head;
        ListNode cur = head.getNext();
        ListNode next;
        while (null != cur) {
            next = cur.getNext();
            cur.setNext(pre);
            pre = cur;
            cur = next;
        }
        head.setNext(null);
        head = pre;
        return head;
    }
}
