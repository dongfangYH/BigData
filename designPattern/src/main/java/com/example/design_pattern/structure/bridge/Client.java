package com.example.design_pattern.structure.bridge;

public class Client {

    public static void main(String[] args){

        Computer macBookAir = new MacBookAir(5000, "Air13", "laptop");
        ComputerStore computerStore = new ComputerStore(macBookAir);

        WaterMelon waterMelon = new WaterMelon(10, "大西瓜", 0.8);
        FruitStore fruitStore = new FruitStore(waterMelon);

        computerStore.sell();
        fruitStore.sell();
    }
}
