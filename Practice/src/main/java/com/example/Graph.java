package com.example;

import java.util.*;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-27 09:31
 **/
public class Graph {

    private Node startNode;

    public List<Line> buildAllPossibleLine(){

        List<Line> result = new LinkedList<>();

        Line line = new Line();
        line.addWalkedNode(startNode);
        nextMove(startNode, line, result);

        return result;
    }

    private void nextMove(Node startNode, Line line, List<Line> completedLine) {
        if (startNode.routeMap.isEmpty()){
            completedLine.add(line);
        }else {
            Set<Map.Entry<Integer, Set<Node>>> entrySet = startNode.routeMap.entrySet();
            entrySet.stream().forEach(entry -> {
                final int cost = entry.getKey();
                Set<Node> nodes = entry.getValue();

                nodes.stream().forEach(node -> {
                    if (!line.hasWalked(node.id)){
                        Line newLine = line.clone();
                        newLine.addWalkedNode(node);
                        newLine.addCost(cost);
                        nextMove(node, newLine, completedLine);
                    }
                });
            });
        }
    }

    public Line findBestLine(){
        List<Line> allLines = buildAllPossibleLine();
        Line bestLine = null;
        int bestLineCost = Integer.MAX_VALUE;
        for (Line line : allLines){
            if (bestLine == null){
                bestLineCost = line.cost;
                bestLine = line;
            }else {
                if (bestLineCost > line.cost){
                    bestLine = line;
                    bestLineCost = line.cost;
                }
            }
        }
        return bestLine;
    }


    private static final class Line{
        Queue<Node> walkedPath = new LinkedList<>();
        int cost = 0;
        private Set<String> rSet = new HashSet<>();

        public int getCost() {
            return cost;
        }

        public boolean hasWalked(String nodeId){
            return rSet.contains(nodeId);
        }

        public void addWalkedNode(Node node){
            walkedPath.add(node);
            rSet.add(node.id);
        }

        public void addCost(int newCost){
            cost = (cost + newCost);
        }

        public void printLine(){
            Iterator<Node> iterator = walkedPath.iterator();
            StringBuilder builder = new StringBuilder();
            builder.append("Line : ");
            while (iterator.hasNext()){
                builder.append("Node(");
                builder.append(iterator.next().id);
                builder.append(") -> ");
            }
            builder.append("Cost: " + cost);
            System.out.println(builder.toString());
        }

        protected Line clone(){

            Line newLine = new Line();
            Iterator<Node> iterator = walkedPath.iterator();
            while (iterator.hasNext()){
                newLine.addWalkedNode(iterator.next());
            }
            newLine.cost = cost;
            newLine.rSet.addAll(rSet);

            return newLine;
        }
    }


    private static final class Node{
        private String id;
        private Map<Integer, Set<Node>> routeMap = new HashMap<>();

        public Node(String id) {
            this.id = id;
        }

        public void addRoute(Integer cost, Node node){
            if (routeMap.containsKey(cost)){
                routeMap.get(cost).add(node);
            }else {
                Set<Node> nodes = new HashSet<>();
                nodes.add(node);
                routeMap.put(cost, nodes);
            }
        }
    }

    public static void main(String[] args){
        Graph grap = new Graph();

        Node node0 = new Node("0");
        Node node1 = new Node("1");
        Node node2 = new Node("2");
        Node node3 = new Node("3");
        Node node4 = new Node("4");
        Node node5 = new Node("5");
        Node node6 = new Node("6");
        Node node7 = new Node("7");
        Node node8 = new Node("8");

        grap.startNode = node0;

        // build route
        node0.addRoute(4, node1);
        node0.addRoute(8, node7);

        node1.addRoute(8, node2);
        node1.addRoute(11, node7);

        node2.addRoute(2, node8);
        node2.addRoute(4, node5);
        node2.addRoute(7, node3);

        node3.addRoute(14, node5);
        node3.addRoute(9, node4);

        node7.addRoute(11, node1);
        node7.addRoute(7, node8);
        node7.addRoute(1, node6);

        node8.addRoute(2, node2);
        node8.addRoute(6, node6);

        node6.addRoute(2, node5);
        node6.addRoute(6, node8);

        node5.addRoute(14, node3);
        node5.addRoute(10, node4);

        List<Line> lines = grap.buildAllPossibleLine();
        lines.forEach(Line::printLine);
    }
}
