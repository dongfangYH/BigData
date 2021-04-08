package com.example.algo.structcure;

/**
 * @author yuanhang.liu@tcl.com
 * @description java bitmap
 * @date 2020-11-10 13:51
 **/
public class BitMap {

    private final int size;
    private final int[] map;

    public BitMap(int size) {

        if (size <= 0){
            throw new IllegalArgumentException("size can not less than 1");
        }

        this.size = size;
        map = new int[size/32 + 1];
    }

    public void set(int index){
        int mapIdx = index / 32;
        int offset = index % 32;
        map[mapIdx] |= (1 << offset);
    }

    public void unset(int index){
        int mapIdx = index / 32;
        int offset = index % 32;
        map[mapIdx] &= ~(1 << offset);
    }

    public boolean exists(int index){
        int mapIdx = index / 32;
        int offset = index % 32;
        int r = map[mapIdx] &= (1 << offset);
        return r == (1 << offset);
    }

    public int getSize() {
        return size;
    }

    public static void main(String[] args){
        BitMap bitMap = new BitMap(64);
        bitMap.set(32);
        System.out.println(bitMap.exists(31));
        bitMap.unset(32);
        System.out.println("done");
    }

}
