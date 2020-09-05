package leetcode;

import java.util.LinkedList;

/**
 * 链表中倒数第k个节点
 *
 * 给定一个链表: 1->2->3->4->5, 和 k = 2.
 *
 * 返回链表 4->5.
 *
 *
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class LastKNode {

    /**
     * 使用队列存储k个值，空间复杂度为k
     * @param head
     * @param k
     * @return
     */
    public static ListNode getKthFromEnd1(ListNode head, int k) {
        LinkedList<ListNode> queue = new LinkedList<ListNode>();
        ListNode curr = head;
        while (curr != null){
            queue.add(curr);
            curr = curr.next;
            if (queue.size() > k){
                queue.pollFirst();
            }
        }
        return queue.pollFirst();
    }

    /**
     * 使用双指针查询
     * @param head
     * @param k
     * @return
     */
    public static ListNode getKthFromEnd2(ListNode head, int k) {
        ListNode p = head;
        ListNode q = head;
        for (int i =1; i< k; i++){
            q = q.next;
        }

        while (q.next != null){
            p = p.next;
            q = q.next;
        }

        return p;
    }

    public static void main(String[] args){
        ListNode head = new ListNode(1);
        ListNode curr = head;
        for (int i=2; i<= 5; i++){
            ListNode node = new ListNode(i);
            curr.next = node;
            curr = curr.next;
        }
        System.out.println(getKthFromEnd1(head, 2));
    }



    private static class ListNode {
      int val;
      ListNode next;
      ListNode(int x) { val = x; }
  }
}
