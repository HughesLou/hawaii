package com.hughes.code.lintcode;

import java.util.Stack;

/**
 * Created by Hughes on 2016/8/14.
 */
public class Queue {

    private Stack<Integer> stack1;
    private Stack<Integer> stack2;

    public Queue() {
        stack1 = new Stack<>();
        stack2 = new Stack<>();
    }

    public void push(int element) {
        while (!stack2.empty()) {
            stack1.push(stack2.pop());
        }
        stack1.push(element);
        while (!stack1.empty()) {
            stack2.push(stack1.pop());
        }
    }

    public int pop() {
        return stack2.pop();
    }

    public int top() {
        return stack2.peek();
    }
}
