package com.zhou.algorithmproblem;

/**
 * @author zhouyuanke
 * @date 2023/6/13
 */
public class ReverseList_1 {

    public class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }

        public class Solution {
            public ListNode ReverseList(ListNode head) {
                if (head == null || head.next == null) {
                    return head;
                }
                ListNode cur = head, next, pre = null;
                while (cur != null) {
                    next = cur.next;
                    cur.next = pre;
                    pre = cur;
                    cur = next;
                }
                return pre;
            }
        }

    }
}
