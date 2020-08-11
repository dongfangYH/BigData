package com.example.sortalgo;

class ListNode {
    int val;
    ListNode next;

    ListNode(int x) {
        val = x;
    }
}


public class Main {
    public static ListNode reverseList(ListNode head) {
        if(head == null || head.next == null){
            return head;
        }

        ListNode P = null, H = head, N = head.next;
        while(H.next != null){
            ListNode OH = H;
            H.next = P;
            P = OH;
            H = N;
            N = N.next;
        }

        H.next = P;

        return H;
    }

    public static void main(String[] args){
        ListNode head = new ListNode(1);
        ListNode last = head;
        for (int i=2; i<=5;i++){
            ListNode CN = new ListNode(i);
            last.next = CN;
            last = CN;
        }

        ListNode reverseHead = reverseList(head);
        System.out.println(reverseHead);
    }
}