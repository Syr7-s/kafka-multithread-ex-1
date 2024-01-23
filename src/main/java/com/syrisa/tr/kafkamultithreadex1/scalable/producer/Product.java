package com.syrisa.tr.kafkamultithreadex1.scalable.producer;

import java.util.HashMap;
import java.util.Map;

public class Product {
    private static final Map<Integer, String> productList = new HashMap<>();

    static {
        //https://fakestoreapi.com/products
        productList.put(1, "Laptop");
        productList.put(2, "Mouse");
        productList.put(3, "Keyboard");
        productList.put(4, "Headphones");
        productList.put(5, "Monitor");
        productList.put(6, "Camera");
        productList.put(7, "Printer");
        productList.put(8, "Phone");
        productList.put(9, "Watch");
        productList.put(10, "Tablet");
        productList.put(11, "TV");
        productList.put(12, "Gamepad");
        productList.put(13, "Speaker");
        productList.put(14, "Microphone");
        productList.put(15, "Drone");
        productList.put(16, "Router");
        productList.put(17, "USB");
        productList.put(18, "HDMI");
        productList.put(19, "Charger");
        productList.put(20, "Cable");
        productList.put(21, "Powerbank");
        productList.put(22, "Battery");
        productList.put(23, "Memory Card");
        productList.put(24, "Hard Disk");
        productList.put(25, "SSD");
        productList.put(26, "CPU");
        productList.put(27, "GPU");
        productList.put(28, "Motherboard");
        productList.put(29, "RAM");
        productList.put(30, "Cooler");
        productList.put(31, "Fan");

        productList.put(32, "T-Shirt");
        productList.put(33, "Pants");
        productList.put(34, "Shoes");
        productList.put(35, "Socks");
        productList.put(36, "Underwear");
        productList.put(37, "Jacket");
        productList.put(38, "Coat");
        productList.put(39, "Hat");
        productList.put(40, "Scarf");
        productList.put(41, "Gloves");
        productList.put(42, "Belt");
        productList.put(43, "Sunglasses");
        productList.put(44, "Watch");
        productList.put(45, "Bracelet");
        productList.put(46, "Necklace");
        productList.put(47, "Earrings");
        productList.put(48, "Ring");
        productList.put(49, "Bag");
        productList.put(50, "Wallet");
        productList.put(51, "Backpack");
        productList.put(52, "Suitcase");
        productList.put(53, "Umbrella");
        productList.put(54, "Tie");
        productList.put(55, "Scarf");
        productList.put(56, "Belt");

        productList.put(57, "Book");
        productList.put(58, "Notebook");
        productList.put(59, "Pen");
        productList.put(60, "Pencil");
        productList.put(61, "Eraser");
        productList.put(62, "Ruler");
        productList.put(63, "Stapler");
        productList.put(64, "Staples");
        productList.put(65, "Scissors");
        productList.put(66, "Glue");
        productList.put(67, "Tape");
        productList.put(68, "Paper");
        productList.put(69, "Folder");
        productList.put(70, "Binder");

    }

    public static String getProduct(int id) {
        return productList.get(id);
    }

    public static int getProductListSize() {
        return productList.size();
    }
}
