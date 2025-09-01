package com.fooddelivery.datastore;

import com.fooddelivery.entities.Customer;
import com.fooddelivery.entities.DeliveryPerson;
import com.fooddelivery.entities.Order;
import com.fooddelivery.entities.Restaurant;

import java.util.*;

public class DataStore {
    public final Map<Integer, Restaurant> restaurants = new LinkedHashMap<>();
    public final Map<Integer, Customer> customers = new LinkedHashMap<>();
    public final Map<Integer, DeliveryPerson> deliveryPeople = new LinkedHashMap<>();
    public final Map<Integer, Order> orders = new LinkedHashMap<>();

    private int nextOrderId = 1;

    public int getNextOrderId() {
        return nextOrderId++;
    }
}
