package com.example.design_pattern.behavior.backup;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 16:29
 **/
public class UndoCommand implements Command {
    @Override
    public void execute(SnapshotHolder holder) {
        holder.removeLast();
    }
}
