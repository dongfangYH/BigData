package com.example.design_pattern.behavior.backup;

import java.util.Objects;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-11-05 16:21
 **/
public class Input {

    private final String text;

    public Input(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public Snapshot createSnapshot(){
        return new Snapshot(getText());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Input input = (Input) o;
        return Objects.equals(text, input.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }
}
