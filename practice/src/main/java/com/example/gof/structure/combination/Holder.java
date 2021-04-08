package com.example.gof.structure.combination;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class Holder extends Node{

    private Map<String, Node> subNode = new HashMap<>();

    protected Holder(String name) {
        super(name);
    }

    public Map<String, Node> getSubNode() {
        return Collections.unmodifiableMap(subNode);
    }

    public void addSubNode(Node node){
        if (node == null){
            throw new NullPointerException("empty node");
        }

        String newNodeName = node.getName();

        if (null == newNodeName || newNodeName.isEmpty()){
            throw new IllegalArgumentException("illegal node name : " + newNodeName);
        }

        if (subNode.containsKey(newNodeName)){
            throw new IllegalArgumentException("node name {" + newNodeName+ "} exists.");
        }
        subNode.put(newNodeName, node);
    }

    public void list(){
        list(this, 1);
    }

    public void list(Holder holder, int level){
        String name = holder.getName();
        String indent = getIndent(level);
        System.out.println(indent + ">holder : " + name);

        Collection<Node> nodes = holder.getSubNode().values();
        for (Node node : nodes){
            if (node instanceof Holder) {
                list((Holder) node, ++level);
            }else {
                System.out.println(indent + "->node : " + node.getName());
            }
        }
    }

    private String getIndent(int level) {
        StringBuilder builder = new StringBuilder();
        while (level-- > 0){
            builder.append("-");
        }
        return builder.toString();
    }
}
