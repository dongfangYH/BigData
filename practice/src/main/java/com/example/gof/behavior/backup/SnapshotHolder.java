package com.example.gof.behavior.backup;

import java.util.Stack;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 16:25
 **/
public class SnapshotHolder {

    Stack<Snapshot> snapshots = new Stack<>();

    public void addSnapshot(Snapshot snapshot){
        snapshots.push(snapshot);
    }

    public void removeLast(){
        Snapshot snapshot = snapshots.pop();
        System.out.println("remove snapshot : " + snapshot.getText());
    }

    public void printHistoryInput(){
        if (!snapshots.isEmpty()){
            snapshots.stream().forEach(a -> System.out.println(a.getText()));
        }
    }
}
