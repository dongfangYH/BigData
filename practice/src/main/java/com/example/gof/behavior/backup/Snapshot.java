package com.example.gof.behavior.backup;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 16:24
 **/
public class Snapshot {

    private final String text;

    public Snapshot(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "Snapshot{" +
                "text='" + text + '\'' +
                '}';
    }
}
