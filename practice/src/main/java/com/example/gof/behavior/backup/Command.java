package com.example.gof.behavior.backup;

public interface Command {

    void execute(SnapshotHolder holder);
}
