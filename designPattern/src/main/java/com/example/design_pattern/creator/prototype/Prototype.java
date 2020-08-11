package com.example.design_pattern.creator.prototype;

import java.util.ArrayList;
import java.util.List;

/**
 * @author yuanhang.liu@tcl.com
 * @description
 * @date 2020-07-01 15:45
 **/
public class Prototype implements Cloneable{

    private String name;
    private int id;
    private Integer id2;
    private ArrayList<String> carries = new ArrayList<>();
    private Class<?> clazz;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Integer getId2() {
        return id2;
    }

    public void setId2(Integer id2) {
        this.id2 = id2;
    }

    public List<String> getCarries() {
        return carries;
    }

    public void addCarries(List<String> carries) {
        this.carries.addAll(carries);
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public void setClazz(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected Prototype clone() throws CloneNotSupportedException{
        Prototype clazz = (Prototype)super.clone();
        clazz.carries = (ArrayList)this.carries.clone();
        return clazz;
    }
}
