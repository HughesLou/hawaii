package com.hughes.model;

/**
 * Created by Hughes on 2016/8/14.
 */
public class ListNode {
    int val;

    ListNode next;

    public ListNode(int val) {
        this.val = val;
        this.next = null;
    }

    public int getVal() {
        return val;
    }

    public void setVal(int val) {
        this.val = val;
    }

    public ListNode getNext() {
        return next;
    }

    public void setNext(ListNode next) {
        this.next = next;
    }
}
