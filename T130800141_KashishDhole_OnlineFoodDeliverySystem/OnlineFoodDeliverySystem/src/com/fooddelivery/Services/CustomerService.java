package com.fooddelivery.Services;

import com.fooddelivery.datastore.DataStore;
import com.fooddelivery.entities.Customer;
import com.fooddelivery.entities.FoodItem;
import com.fooddelivery.entities.Order;
import com.fooddelivery.entities.Restaurant;

import java.util.Map;

public class CustomerService {
    private final DataStore db;

    public CustomerService(DataStore db) {
        this.db = db;
    }

    public void addCustomer(int userId, String username, long contactNo) {
        db.customers.put(userId, new Customer(userId, username, contactNo));
        System.out.println("Customer created successfully!");
    }

    public void viewFoodItems() {
        System.out.println("Restaurants and Menus:");
        for (Restaurant r : db.restaurants.values()) {
            System.out.println(r);
        }
    }

    public void addFoodToCart(int customerId, int restaurantId, int foodItemId, int quantity) {
        Customer c = db.customers.get(customerId);
        if (c == null) { System.out.println("Customer not found."); return; }
        Restaurant r = db.restaurants.get(restaurantId);
        if (r == null) { System.out.println("Restaurant not found."); return; }
        FoodItem target = null;
        for (FoodItem fi : r.getMenu()) {
            if (fi.getId() == foodItemId) { target = fi; break; }
        }
        if (target == null) { System.out.println("Food item not found."); return; }
        c.getCart().addItem(target, quantity);
        System.out.println("Food item added to cart!");
    }

    public void viewCart(int customerId) {
        Customer c = db.customers.get(customerId);
        if (c == null) { System.out.println("Customer not found."); return; }
        System.out.println("Cart:");
        System.out.println(c.getCart().toString());
    }

    public void placeOrder(int customerId, String address) {
        Customer c = db.customers.get(customerId);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }
        int orderId = db.getNextOrderId();
        Order order = new Order(orderId, c);
        for (Map.Entry<FoodItem, Integer> e : c.getCart().getItems().entrySet()) {
            order.addItem(e.getKey(), e.getValue());
        }
        order.setDeliveryAddress(address);
        db.orders.put(orderId, order);

        // Clear cart after placing order
        c.getCart().getItems().clear();

        System.out.println("Order placed successfully! Your order ID is: " + orderId);
    }


    public void viewOrders(int customerId) {
        System.out.println("Orders:");
        for (Order o : db.orders.values()) {
            if (o.getCustomer().getUserId() == customerId) {
                System.out.println(o);
            }
        }
    }

    public void removeFoodFromCart(int customerId, int foodItemId) {
        Customer c = db.customers.get(customerId);
        if (c == null) {
            System.out.println("Customer not found.");
            return;
        }

        FoodItem target = null;
        for (FoodItem fi : c.getCart().getItems().keySet()) {
            if (fi.getId() == foodItemId) {
                target = fi;
                break;
            }
        }

        if (target == null) {
            System.out.println("Food item not found in cart.");
            return;
        }

        c.getCart().removeItem(target);
        System.out.println("Food item removed from cart!");
    }

}
