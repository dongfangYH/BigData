package com.example.design_pattern.behavior.iterator;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-10 08:42
 **/
public class LinkedList<T> {

    private Node<T> head;
    private Node<T> tail;
    private int size = 0;

    public void add(T t){
        if (head == null){
            head = tail = new Node<>(t);
        }else {
            Node<T> node = new Node<>(t);
            tail.next = node;
            tail = node;
        }
        size++;
    }

    public int getSize() {
        return size;
    }

    public Iterator<T> iterator(){
        return new Iterator<T>() {

            Node<T> index = new Node<>(null, head);

            @Override
            public boolean hasNext() {
                return index.next != null;
            }

            @Override
            public T next() {
                index = index.next;
                return index.value;
            }


        };
    }


    private final static class Node<T> {
        private T value;
        private Node<T> next;

        public Node(T value) {
            this.value = value;
        }

        public Node(T value, Node<T> next) {
            this.value = value;
            this.next = next;
        }
    }
}
