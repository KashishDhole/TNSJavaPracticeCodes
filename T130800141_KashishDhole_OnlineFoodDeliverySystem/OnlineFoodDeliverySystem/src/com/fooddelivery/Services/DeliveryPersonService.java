package com.fooddelivery.Services;
import com.fooddelivery.datastore.DataStore;
import com.fooddelivery.entities.Order;
public class DeliveryPersonService {
    private final DataStore db;

    public DeliveryPersonService(DataStore db) {
        this.db = db;
    }

    // View orders assigned to this delivery person
    public void viewAssignedOrders(int deliveryPersonId) {
        System.out.println("Assigned Orders:");
        boolean found = false;
        for (Order order : db.orders.values()) {
            if (order.getDeliveryPerson() != null &&
                    order.getDeliveryPerson().getDeliveryPersonId() == deliveryPersonId) {
                System.out.println(order);
                found = true;
            }
        }
        if (!found) {
            System.out.println("No orders assigned.");
        }
    }

    // Update the status of an order
    public void updateOrderStatus(int orderId, String status, int deliveryPersonId) {
        Order order = db.orders.get(orderId);
        if (order == null) {
            System.out.println("Order not found.");
            return;
        }
        if (order.getDeliveryPerson() == null ||
                order.getDeliveryPerson().getDeliveryPersonId() != deliveryPersonId) {
            System.out.println("This order is not assigned to you.");
            return;
        }
        order.setStatus(status);
        System.out.println("Order status updated to: " + status);
    }
}
