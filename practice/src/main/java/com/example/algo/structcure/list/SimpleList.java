package com.example.algo.structcure.list;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-04-24 09:42
 **/
public class SimpleList<V> implements List<V>{

    /**
     * head index
     */
    private Node<V> head;

    /**
     * tail index
     */
    private Node<V> tail;

    /**
     * size of the list
     */
    private long size = 0;

    /**
     * add a value to the list
     * @param v
     */
    public void add(V v) {
        if (head == null){
            head = new Node<V>();
        }
        Node<V> node = new Node<V>(v);

        if (tail == null){
            tail = node;
            head.next = node;
            tail.pre = head;
            size++;
            return;
        }

        Node<V> prevTail = tail;
        node.pre = prevTail;
        prevTail.next = node;
        tail = node;
        size++;
    }

    /**
     * delete a node
     * @param index
     */
    public void delete(Integer index) {
        //check if the index is valid
        if (index < 0 || size == 0 || index >= size){
            throw new IndexOutOfBoundsException();
        }

        int currentIndex = 0;
        Node<V> currentNode = head.next;

        while (true){
            if (currentIndex == index){

                //1. if it's the tail
                if (currentNode == tail){
                    tail = currentNode.pre;
                    currentNode.pre.next = null;
                    //help gc
                    currentNode.pre = null;
                    size--;
                    break;
                }

                currentNode.pre.next = currentNode.next;
                currentNode.next.pre = currentNode.pre;

                //help gc
                currentNode.pre = null;
                currentNode.next = null;

                size--;
                break;

            }
            currentIndex++;
        }

    }

    /**
     * remove a node by value
     * @param v
     * @return
     */
    public boolean remove(V v) {

        if (size == 0 || v == null){
            return false;
        }

        Integer index = 0;
        Node<V> currentNode = head.next;
        while (index < size){
            if (currentNode.value.equals(v)){
                delete(index);
                return true;
            }
            currentNode = currentNode.next;
            index++;
        }

        return false;

    }

    /**
     * get value by index
     * @param index
     * @return
     */
    public V get(Integer index) {

        if (index < 0 || index > (size - 1)){
            return null;
        }

        Integer currentIndex = 0;
        Node<V> currentNode = head.next;

        while (!(currentIndex == index)){
            currentIndex++;
            currentNode = currentNode.next;
        }

        return currentNode.value;
    }

    public Long length(){
        return size;
    }

    /**
     * a single node to store the indexes of previous and next nodes and its value
     * @param <V>
     */
    private static class Node<V> {
        private Node<V> pre;
        private Node<V> next;
        private V value;

        public Node() {
        }

        public Node(V value) {
            this.value = value;
        }
    }

    public static void main(String[] args){
        SimpleList<String> list = new SimpleList<String>();
        list.add("a");
        list.add("b");
        list.add("c");
        list.delete(0);
        list.delete(0);
        list.delete(0);
        list.add("d");
        System.out.println(list.length());
    }
}
