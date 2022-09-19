package com.zhou.algorithmproblem;

/**
 * 使用链表表示两个数，计算两数相加的和
 * 需要注意，添加标志判断两个数之和是否大于10，是否需要进1
 * 用于移动的链表节点可以定义为tail
 * 当两个链表都遍历完时，需要判断是否最后还需要进1
 *
 * @author zhouyuanke
 * @date 2022/9/17 18:57
 */
public class AddTwoNumbers {
    /**
     * Definition for singly-linked list
     */
    public class ListNode {
        int val;
        ListNode next;

        ListNode() {
        }

        ListNode(int val) {
            this.val = val;
        }

        ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public class Solution {
        public ListNode addTwoNumbers(ListNode l1, ListNode l2) {
            int symbol = 0;
            ListNode head = new ListNode(), tail = head;
            while (l1 != null || l2 != null) {
                ListNode node = new ListNode();
                int total = symbol;
                if (l1 != null) {
                    total += l1.val;
                    l1 = l1.next;
                }
                if (l2 != null) {
                    total += l2.val;
                    l2 = l2.next;
                }
                if (total >= 10) {
                    total -= 10;
                    symbol = 1;
                } else {
                    symbol = 0;
                }
                node.val = total;
                tail.next = node;
                tail = node;
            }
            if (symbol == 1) {
                ListNode node = new ListNode(1, null);
                tail.next = node;
            }
            return head.next;
        }
    }
}
