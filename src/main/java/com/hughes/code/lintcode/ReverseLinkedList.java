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

    /**
     * @param head is the head of the linked list
     * @oaram m and n
     * @return: The head of the reversed ListNode
     */
    public ListNode reverseBetween(ListNode head, int m, int n) {
        if (m == n) {
            return head;
        }
        ListNode q = null;
        ListNode p = head;
        for (int i = 1; i < m; i++) {
            q = p;
            p = p.getNext();
        }
        ListNode end = p;
        ListNode pPre = p;
        p = p.getNext();
        for (int i = m + 1; i <= n; i++) {
            ListNode pNext = p.getNext();

            p.setNext(pPre);
            pPre = p;
            p = pNext;
        }
        end.setNext(p);
        if (q != null) {
            q.setNext(pPre);
        } else {
            head = pPre;
        }
        return head;
    }
}
