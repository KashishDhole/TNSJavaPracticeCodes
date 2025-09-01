package com.fooddelivery.application;

import com.fooddelivery.Services.AdminService;
import com.fooddelivery.Services.CustomerService;
import com.fooddelivery.Services.DeliveryPersonService;
import com.fooddelivery.datastore.DataStore;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        DataStore db = new DataStore();
        AdminService adminService = new AdminService(db);
        CustomerService customerService = new CustomerService(db);
        DeliveryPersonService dpService = new DeliveryPersonService(db);
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("1. Admin Menu");
            System.out.println("2. Customer Menu");
            System.out.println("3. Delivery Person Menu");
            System.out.println("4. Exit");
            System.out.print("Choose an option: ");
            int mainChoice = readInt(sc);

            if (mainChoice == 1) {
                boolean adminRunning = true;
                while (adminRunning) {
                    System.out.println("Admin Menu:");
                    System.out.println("1. Add Restaurant");
                    System.out.println("2. Add Food Item to Restaurant");
                    System.out.println("3. Remove Food Item from Restaurant");
                    System.out.println("4. View Restaurants and Menus");
                    System.out.println("5. View Orders");
                    System.out.println("6. Add Delivery Person");
                    System.out.println("7. Assign Delivery Person to Order");
                    System.out.println("8. Exit");
                    System.out.println("9. Update Order Status");
                    System.out.println("10. View Delivery Persons");
                    System.out.print("Choose an option: ");
                    int choice = readInt(sc);

                    switch (choice) {
                        case 1:
                            System.out.print("Enter Restaurant ID: ");
                            int rid = readInt(sc);
                            System.out.print("Enter Restaurant Name: ");
                            String rname = sc.nextLine().trim();
                            adminService.addRestaurant(rid, rname);
                            break;
                        case 2:
                            System.out.print("Enter Restaurant ID: ");
                            int rid2 = readInt(sc);
                            System.out.print("Enter Food Item ID: ");
                            int fid = readInt(sc);
                            System.out.print("Enter Food Item Name: ");
                            String fname = sc.nextLine().trim();
                            System.out.print("Enter Food Item Price: ");
                            double price = readDouble(sc);
                            adminService.addFoodItemToRestaurant(rid2, fid, fname, price);
                            break;
                        case 3:
                            System.out.print("Enter Restaurant ID: ");
                            int rid3 = readInt(sc);
                            System.out.print("Enter Food Item ID: ");
                            int fid2 = readInt(sc);
                            adminService.removeFoodItemFromRestaurant(rid3, fid2);
                            break;
                        case 4:
                            adminService.viewRestaurantsAndMenus();
                            break;
                        case 5:
                            adminService.viewOrders();
                            break;
                        case 6:
                            System.out.print("Enter Delivery Person ID: ");
                            int dpid = readInt(sc);
                            System.out.print("Enter Delivery Person Name: ");
                            String dpname = sc.nextLine().trim();
                            System.out.print("Enter Contact No.: ");
                            long dpc = readLong(sc);
                            adminService.addDeliveryPerson(dpid, dpname, dpc);
                            break;
                        case 7:
                            System.out.print("Enter Order ID: ");
                            int oid = readInt(sc);
                            System.out.print("Enter Delivery Person ID: ");
                            int dpid2 = readInt(sc);
                            adminService.assignDeliveryPersonToOrder(oid, dpid2);
                            break;
                        case 8:
                            System.out.println("Exiting Admin Module");
                            adminRunning = false;
                            break;
                        case 9:
                            System.out.print("Enter Order ID: ");
                            int uoid = readInt(sc);
                            System.out.print("Enter New Status (Pending / Out for Delivery / Delivered): ");
                            String status = sc.nextLine().trim();
                            adminService.updateOrderStatus(uoid, status);
                            break;
                        case 10:
                            adminService.viewDeliveryPersons();
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }
                }
            } else if (mainChoice == 2) {
                boolean customerRunning = true;
                while (customerRunning) {
                    System.out.println("Customer Menu:");
                    System.out.println("1. Add Customer");
                    System.out.println("2. View Food Items");
                    System.out.println("3. Add Food to Cart");
                    System.out.println("4. View Cart");
                    System.out.println("5. Place Order");
                    System.out.println("6. View Orders");
                    System.out.println("7. Remove Food from Cart");
                    System.out.println("8. Exit");
                    System.out.print("Choose an option: ");
                    int cchoice = readInt(sc);

                    switch (cchoice) {
                        case 1:
                            System.out.print("Enter User ID: ");
                            int uid = readInt(sc);
                            System.out.print("Enter Username: ");
                            String uname = sc.nextLine().trim();
                            System.out.print("Enter Contact No.: ");
                            long cno = readLong(sc);
                            customerService.addCustomer(uid, uname, cno);
                            break;
                        case 2:
                            customerService.viewFoodItems();
                            break;
                        case 3:
                            System.out.print("Enter Customer ID: ");
                            int cuid = readInt(sc);
                            System.out.print("Enter Restaurant ID: ");
                            int crid = readInt(sc);
                            System.out.print("Enter Food Item ID: ");
                            int cfid = readInt(sc);
                            System.out.print("Enter Quantity: ");
                            int qty = readInt(sc);
                            customerService.addFoodToCart(cuid, crid, cfid, qty);
                            break;
                        case 4:
                            System.out.print("Enter Customer ID: ");
                            int vcid = readInt(sc);
                            System.out.println("Cart:");
                            customerService.viewCart(vcid);
                            break;
                        case 5:
                            System.out.print("Enter Customer ID: ");
                            int pcid = readInt(sc);
                            System.out.print("Enter Delivery Address: ");
                            String address = sc.nextLine().trim();
                            customerService.placeOrder(pcid, address);
                            break;
                        case 6:
                            System.out.print("Enter Customer ID: ");
                            int ocid = readInt(sc);
                            customerService.viewOrders(ocid);
                            break;
                        case 7:
                            System.out.print("Enter Customer ID: ");
                            int rcid = readInt(sc);
                            System.out.print("Enter Food Item ID to remove: ");
                            int rfid = readInt(sc);
                            customerService.removeFoodFromCart(rcid, rfid);
                            break;
                        case 8:
                            System.out.println("Exiting Customer Module");
                            customerRunning = false;
                            break;

                        default:
                            System.out.println("Invalid option.");
                    }
                }
            } else if (mainChoice == 3) {
                boolean dpRunning = true;
                while (dpRunning) {
                    System.out.println("Delivery Person Menu:");
                    System.out.println("1. View Assigned Orders");
                    System.out.println("2. Update Order Status");
                    System.out.println("3. Exit");
                    System.out.print("Choose an option: ");
                    int dpChoice = readInt(sc);

                    switch (dpChoice) {
                        case 1:
                            System.out.print("Enter Delivery Person ID: ");
                            int dpId = readInt(sc);
                            dpService.viewAssignedOrders(dpId);
                            break;
                        case 2:
                            System.out.print("Enter Delivery Person ID: ");
                            int dpid = readInt(sc);
                            System.out.print("Enter Order ID: ");
                            int oid = readInt(sc);
                            System.out.print("Enter New Status (Pending / Out for Delivery / Delivered): ");
                            String status = sc.nextLine().trim();
                            dpService.updateOrderStatus(oid, status, dpid);
                            break;
                        case 3:
                            System.out.println("Exiting Delivery Person Module");
                            dpRunning = false;
                            break;
                        default:
                            System.out.println("Invalid option.");
                    }
                }

            } else if (mainChoice == 4) {
                System.out.println("Exiting application. Goodbye!");
                break;
            }
                else {
                System.out.println("Invalid option.");
            }
        }
    }

    private static int readInt(Scanner sc) {
        while (true) {
            try {
                int val = Integer.parseInt(sc.nextLine().trim());
                return val;
            } catch (Exception e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static long readLong(Scanner sc) {
        while (true) {
            try {
                long val = Long.parseLong(sc.nextLine().trim());
                return val;
            } catch (Exception e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }

    private static double readDouble(Scanner sc) {
        while (true) {
            try {
                double val = Double.parseDouble(sc.nextLine().trim());
                return val;
            } catch (Exception e) {
                System.out.print("Please enter a valid number: ");
            }
        }
    }
}
