package leetcode;

/**
 * 给定两个用链表表示的整数，每个节点包含一个数位。
 *
 * 这些数位是反向存放的，也就是个位排在链表首部。
 *
 * 编写函数对这两个整数求和，并用链表形式返回结果。
 *
 *
 *
 * 示例：
 *
 * 输入：(7 -> 1 -> 6) + (5 -> 9 -> 2)，即617 + 295
 * 输出：2 -> 1 -> 9，即912
 *
 * 进阶：假设这些数位是正向存放的，请再做一遍。
 *
 * 示例：
 *
 * 输入：(6 -> 1 -> 7) + (2 -> 9 -> 5)，即617 + 295
 * 输出：9 -> 1 -> 2，即912
 *
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class SumNodes {

    public static ListNode addTwoNumbers(ListNode l1, ListNode l2) {

        ListNode head = new ListNode(0);
        ListNode curr = head;

        ListNode l1c = l1;
        ListNode l2c = l2;
        int last = 0;
        while (true){

            int v1 = l1c != null ? l1c.val : 0;
            int v2 = l2c != null ? l2c.val : 0;

            int sum = v1 + v2 + last;

            if (sum >= 10){
                curr.next = new ListNode(sum - 10);
                last = 1;
            }else {
                curr.next = new ListNode(sum);
                last = 0;
            }

            curr = curr.next;

            if (l1c != null)
                l1c = l1c.next;
            if (l2c != null)
                l2c = l2c.next;

            if (l1c == null && l2c == null && last == 0) break;

        }

        return head.next;
    }

    public static void main(String[] args){
        ListNode l1 = new ListNode(9);
        int[] b = {9,9,9,9,9,9,9,9,9};

        ListNode l2 = new ListNode(1);
        ListNode curr = l2;
        for (int i : b){
            curr.next = new ListNode(i);
            curr = curr.next;
        }

        ListNode l = addTwoNumbers(l1, l2);
        System.out.println(l);
    }

    private static class ListNode {
        int val;
        ListNode next;
        ListNode(int x) { val = x; }
    }
}
