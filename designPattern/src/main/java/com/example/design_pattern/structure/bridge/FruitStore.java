package com.example.design_pattern.structure.bridge;

public class FruitStore extends Store<Fruit>{

    public FruitStore(Fruit product) {
        super(product);
    }

    @Override
    public void sell() {
        Fruit fruit = getProduct();
        // 水果价格得看质量
        System.out.println("sell fruit : " + fruit.getName() + " , " + fruit.getTotalPrice());
    }
}
