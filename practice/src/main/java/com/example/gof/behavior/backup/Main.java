package com.example.gof.behavior.backup;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 16:22
 **/
public class Main {

    public static void main(String[] args){
        Map<Input, Command> commandMap = new HashMap<>();
        commandMap.put(new Input(":list"), new ListCommand());
        commandMap.put(new Input(":undo"), new UndoCommand());

        Scanner scanner = new Scanner(System.in);
        SnapshotHolder snapshotHolder = new SnapshotHolder();
        while (scanner.hasNext()){
            String text = scanner.next();
            Input input = new Input(text);

            Command command = commandMap.get(input);

            if (command != null){
                command.execute(snapshotHolder);
            }else {
                snapshotHolder.addSnapshot(input.createSnapshot());
            }

        }
    }
}
