package com.fooddelivery.entities;

import java.util.ArrayList;
import java.util.List;

public class Restaurant {
    private int id;
    private String name;
    private List<FoodItem> menu;

    public Restaurant(int id, String name) {
        this.id = id;
        this.name = name;
        this.menu = new ArrayList<>();
    }

    public int getId() { return id; }
    public String getName() { return name; }
    public List<FoodItem> getMenu() { return menu; }

    public void addFoodItem(FoodItem item) {
        if (item != null) {
            this.menu.add(item);
        }
    }

    public void removeFoodItem(int foodItemId) {
        this.menu.removeIf(fi -> fi.getId() == foodItemId);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Restaurant ID: ").append(id).append(", Name: ").append(name);
        if (menu.isEmpty()) return sb.toString();
        sb.append(" - ");
        for (int i = 0; i < menu.size(); i++) {
            FoodItem fi = menu.get(i);
            sb.append("Food Item ID: ").append(fi.getId())
              .append(", Name: ").append(fi.getName())
              .append(", Price: Rs. ").append(fi.getPrice());
            if (i < menu.size() - 1) sb.append(" - ");
        }
        return sb.toString();
    }
}
