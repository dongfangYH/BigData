package com.example.design_pattern.structure.bridge;

public abstract class Store<T extends Product> {

    private T product;

    public abstract void sell();

    public Store(T product) {
        this.product = product;
    }

    public T getProduct() {
        return product;
    }
}
