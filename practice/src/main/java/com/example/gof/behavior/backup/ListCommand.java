package com.example.gof.behavior.backup;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 16:28
 **/
public class ListCommand implements Command{
    @Override
    public void execute(SnapshotHolder holder) {
        holder.printHistoryInput();
    }
}
