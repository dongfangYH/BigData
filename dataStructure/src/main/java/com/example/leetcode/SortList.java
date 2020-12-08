package com.example.leetcode;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-12-04 10:47
 **/
public class SortList {

    public static final class ListNode {
      int val;
      ListNode next;
      ListNode() {}
      ListNode(int val) { this.val = val; }
      ListNode(int val, ListNode next) { this.val = val; this.next = next; }
  }

    public ListNode sortList(ListNode head) {
        if (head == null) {
            return null;
        }

        ListNode lastNode = findLastNode(head);
        if (head == lastNode){
            return head;
        }

        doSortList(head, lastNode, true);

        return head;
    }

    private void doSortList(ListNode ln, ListNode rn, boolean includeLast) {
        if (ln == null || ln == rn){
            return;
        }

        ListNode piv = ln;
        ListNode crr = ln.next;

        while (includeLast ? crr != null : crr != rn){
            if (crr.val < piv.val){
                int tmpCrrVal = crr.val;
                crr.val = piv.val;
                piv.val = tmpCrrVal;
                piv = crr;
            }
            crr = crr.next;
        }

        doSortList(ln, piv, false);
        doSortList(piv.next, rn, true);
    }

    private ListNode findLastNode(ListNode head) {
        ListNode current = head;
        while (current.next != null){
            current = current.next;
        }
        return current;
    }

    public static void main(String[] args){
        //[-1,5,3,4,0]
        int[] s = {12, 1, -1, 3, 2, 11, 7, 9, 4, 6, 5, 32, 14};
        ListNode node = buildListFromArray(s);

        SortList sortList = new SortList();
        sortList.sortList(node);
        System.out.println("");
    }

    private static ListNode buildListFromArray(int[] s) {
        ListNode head = null;
        ListNode last = null;
        for (int i = 0; i < s.length; i++){
            if (head == null){
                head = new ListNode(s[i]);
                last = head;
            }else {
                last.next = new ListNode(s[i]);
                last = last.next;
            }
        }
        return head;
    }
}
