package com.example.design_pattern.structure.proxy;

public class House {

    private long no;
    private String address;
    private double size;
    private double pricePerSize;

    public House(long no, String address, double size, double pricePerSize) {
        this.no = no;
        this.address = address;
        this.size = size;
        this.pricePerSize = pricePerSize;
    }

    public long getNo() {
        return no;
    }

    public String getAddress() {
        return address;
    }

    public double getSize() {
        return size;
    }

    public double getPricePerSize() {
        return pricePerSize;
    }

    public double getTotalPrice(){
        return getSize() * getPricePerSize();
    }

    @Override
    public String toString() {
        return "House{" +
                "no=" + no +
                ", address='" + address + '\'' +
                ", size=" + size +
                ", pricePerSize=" + pricePerSize +
                ", totalPrice=" + getTotalPrice() +
                '}';
    }
}
