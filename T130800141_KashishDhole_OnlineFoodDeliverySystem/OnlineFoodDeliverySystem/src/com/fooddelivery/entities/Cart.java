package com.fooddelivery.entities;

import java.util.LinkedHashMap;
import java.util.Map;

public class Cart {
    private Map<FoodItem, Integer> items;

    public Cart() {
        this.items = new LinkedHashMap<>();
    }

    public void addItem(FoodItem foodItem, int quantity) {
        if (foodItem == null || quantity <= 0) return;
        this.items.put(foodItem, this.items.getOrDefault(foodItem, 0) + quantity);
    }

    public void removeItem(FoodItem foodItem) {
        if (foodItem == null) return;
        this.items.remove(foodItem);
    }

    public Map<FoodItem, Integer> getItems() {
        return items;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        double total = 0.0;
        for (Map.Entry<FoodItem, Integer> e : items.entrySet()) {
            FoodItem fi = e.getKey();
            int qty = e.getValue();
            double cost = fi.getPrice() * qty;
            total += cost;
            sb.append("Food Item: ").append(fi.getName())
              .append(", Quantity: ").append(qty)
              .append(", Cost: Rs. ").append(cost).append("\n");
        }
        sb.append("Total Cost: Rs. ").append(total);
        return sb.toString();
    }
}
