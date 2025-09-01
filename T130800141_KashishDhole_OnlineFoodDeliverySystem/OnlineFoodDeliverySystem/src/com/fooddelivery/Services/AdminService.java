package com.fooddelivery.Services;

import com.fooddelivery.datastore.DataStore;
import com.fooddelivery.entities.DeliveryPerson;
import com.fooddelivery.entities.FoodItem;
import com.fooddelivery.entities.Order;
import com.fooddelivery.entities.Restaurant;

import java.util.Map;

public class AdminService {
    private final DataStore db;

    public AdminService(DataStore db) {
        this.db = db;
    }

    public void addRestaurant(int id, String name) {
        db.restaurants.put(id, new Restaurant(id, name));
        System.out.println("Restaurant added successfully!");
    }

    public void addFoodItemToRestaurant(int restaurantId, int foodId, String name, double price) {
        Restaurant r = db.restaurants.get(restaurantId);
        if (r == null) {
            System.out.println("Restaurant not found.");
            return;
        }
        r.addFoodItem(new FoodItem(foodId, name, price));
        System.out.println("Food item added successfully!");
    }

    public void removeFoodItemFromRestaurant(int restaurantId, int foodItemId) {
        Restaurant r = db.restaurants.get(restaurantId);
        if (r == null) {
            System.out.println("Restaurant not found.");
            return;
        }
        r.removeFoodItem(foodItemId);
        System.out.println("Food item removed successfully!");
    }

    public void viewRestaurantsAndMenus() {
        System.out.println("Restaurants and Menus:");
        if (db.restaurants.isEmpty()) {
            System.out.println("No restaurants available.");
        }
        for (Map.Entry<Integer, Restaurant> e : db.restaurants.entrySet()) {
            System.out.println(e.getValue());
        }
    }

    public void viewOrders() {
        System.out.println("Orders:");
        if (db.orders.isEmpty()) {
            System.out.println("No orders found.");
        }
        for (Order o : db.orders.values()) {
            System.out.println(o);
        }
    }

    public void addDeliveryPerson(int id, String name, long contactNo) {
        db.deliveryPeople.put(id, new DeliveryPerson(id, name, contactNo));
        System.out.println("Delivery person added successfully!");
    }

    public void assignDeliveryPersonToOrder(int orderId, int deliveryPersonId) {
        Order order = db.orders.get(orderId);
        DeliveryPerson dp = db.deliveryPeople.get(deliveryPersonId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        if (dp == null) {
            System.out.println("Delivery person not found.");
            return;
        }
        order.setDeliveryPerson(dp);
        System.out.println("Delivery person assigned to order successfully!");
    }

    public void updateOrderStatus(int orderId, String status) {
        Order order = db.orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        order.setStatus(status);
        System.out.println("Order status updated successfully!");
    }

    public void viewDeliveryPersons() {
        System.out.println("Delivery Persons:");
        if (db.deliveryPeople.isEmpty()) {
            System.out.println("No delivery persons available.");
        }
        for (DeliveryPerson dp : db.deliveryPeople.values()) {
            System.out.println("ID: " + dp.getDeliveryPersonId() +
                    ", Name: " + dp.getName() +
                    ", Contact: " + dp.getContactNo());
        }
    }


}
