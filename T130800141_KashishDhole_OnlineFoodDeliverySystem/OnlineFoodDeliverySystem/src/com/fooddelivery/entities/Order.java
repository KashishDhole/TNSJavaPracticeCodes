package com.fooddelivery.entities;

import java.util.LinkedHashMap;
import java.util.Map;

public class Order {
    private int orderId;
    private Customer customer;
    private Map<FoodItem, Integer> items;
    private String status;
    private DeliveryPerson deliveryPerson;
    private String deliveryAddress;

    public Order(int orderId, Customer customer) {
        this.orderId = orderId;
        this.customer = customer;
        this.items = new LinkedHashMap<>();
        this.status = "Pending";
    }

    public int getOrderId() { return orderId; }
    public Customer getCustomer() { return customer; }
    public Map<FoodItem, Integer> getItems() { return items; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public DeliveryPerson getDeliveryPerson() { return deliveryPerson; }
    public void setDeliveryPerson(DeliveryPerson deliveryPerson) { this.deliveryPerson = deliveryPerson; }
    public String getDeliveryAddress() { return deliveryAddress; }
    public void setDeliveryAddress(String deliveryAddress) { this.deliveryAddress = deliveryAddress; }

    public void addItem(FoodItem item, int qty) {
        if (item == null || qty <= 0) return;
        items.put(item, items.getOrDefault(item, 0) + qty);
    }

    @Override
    public String toString() {
        String dp = (deliveryPerson == null) ? "Not Assigned" : deliveryPerson.getName();
        return "Order{orderId=" + orderId +
                ", customer=" + customer.getUsername() +
                ", items=" + items +
                ", status='" + status + "'" +
                ", deliveryPerson=" + dp +
                ", deliveryAddress='" + (deliveryAddress == null ? "Not Provided" : deliveryAddress) + "'" +
                ", totalCost=Rs. " + calculateTotalCost() +
                "}";
    }


    public double calculateTotalCost() {
        double total = 0.0;
        for (Map.Entry<FoodItem, Integer> e : items.entrySet()) {
            total += e.getKey().getPrice() * e.getValue();
        }
        return total;
    }

}
