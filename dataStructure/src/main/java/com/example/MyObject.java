package com.example;

public class MyObject {

    private String name;
    private String id;
    private long time;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "MyObject{" +
                "name='" + name + '\'' +
                ", id='" + id + '\'' +
                ", time=" + time +
                '}';
    }
}
