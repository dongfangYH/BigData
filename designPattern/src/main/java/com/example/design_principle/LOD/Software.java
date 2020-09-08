package com.example.design_principle.LOD;

public class Software {

    private boolean installStep1(){
        if (System.currentTimeMillis() % 1 == 0){
            return true;
        }
        return false;
    }

    private boolean installStep2(){
        if (System.currentTimeMillis() % 2 == 0){
            return true;
        }
        return false;
    }

    public boolean install(){
        return installStep1() & installStep2();
    }
}
