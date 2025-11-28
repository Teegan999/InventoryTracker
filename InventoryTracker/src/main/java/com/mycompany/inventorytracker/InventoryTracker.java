package com.mycompany.inventorytracker;

import javax.swing.*;
import java.util.HashMap;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * InventoryTracker ---------------- A simple Java inventory management app
 * using JOptionPane dialog. Allows the user to: - Add new items with quantity -
 * Update existing items - Remove items - View current inventory - See low stock
 * alerts - Search for specific items - Export inventory to a text file
 *
 * Data is stored in a HashMap (key: item name, value: quantity).
 */
public class InventoryTracker {

    // Stores all items using item name as KEY and quantity as VALUE
    static HashMap<String, Integer> inventory = new HashMap<>();

    public static void main(String[] args) {

        // Main menu loop - keeps running until user exits
        while (true) {

            // Menu text (multiline string)
            String menu = """
                       INVENTORY TRACKER
                    -----------------------
                    1. Add Item
                    2. Update Quantity
                    3. Remove Item
                    4. View Inventory
                    5. Low Stock Alert
                    6. Search for an item
                    7. Export Inventory
                    8. Exit
                    """;

            // Ask user for menu option
            String choice = JOptionPane.showInputDialog(menu);

            // If user presses CANCEL, exit loop
            if (choice == null) {
                break;
            }

            // Handle menu selection
            switch (choice) {
                case "1" ->
                    addItem();       // Add new item
                case "2" ->
                    updateItem();    // Update quantity of existing item
                case "3" ->
                    removeItem();    // Remove item
                case "4" ->
                    viewInventory(); // Show all items
                case "5" ->
                    lowStockAlert(); // Show items with low stock (<5)
                case "6" ->
                    searchItem();    // Search for a specific item
                case "7" ->
                    saveInventory(); // Export inventory to text file
                case "8" ->
                    System.exit(0);  // Exit the application
                default ->
                    JOptionPane.showMessageDialog(null, "Invalid option.");
            }
        }
    }

    /**
     * Adds a new item to the inventory. Prompts for item name and quantity.
     */
    private static void addItem() {

        String name = JOptionPane.showInputDialog("Item name:");
        if (name == null) {
            return; // user canceled
        }
        String qtyStr = JOptionPane.showInputDialog("Quantity:");
        if (qtyStr == null) {
            return;
        }

        int qty = Integer.parseInt(qtyStr);
        inventory.put(name, qty);

        JOptionPane.showMessageDialog(null, "Item added!");
    }

    /**
     * Updates quantity of an existing item. Checks if the item exists before
     * updating.
     */
    private static void updateItem() {

        String name = JOptionPane.showInputDialog("Item to update:");
        if (name == null) {
            return;
        }

        if (!inventory.containsKey(name)) {
            JOptionPane.showMessageDialog(null, "Item not found.");
            return;
        }

        String qtyStr = JOptionPane.showInputDialog("New quantity:");
        if (qtyStr == null) {
            return;
        }

        int qty = Integer.parseInt(qtyStr);
        inventory.put(name, qty);

        JOptionPane.showMessageDialog(null, "Quantity updated!");
    }

    /**
     * Removes an item from the inventory. Shows a message if item does not
     * exist.
     */
    private static void removeItem() {

        String name = JOptionPane.showInputDialog("Item to remove:");
        if (name == null) {
            return;
        }

        if (inventory.remove(name) != null) {
            JOptionPane.showMessageDialog(null, "Item removed!");
        } else {
            JOptionPane.showMessageDialog(null, "Item not found.");
        }
    }

    /**
     * Displays all items and their quantities.
     */
    private static void viewInventory() {

        if (inventory.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Inventory empty.");
            return;
        }

        StringBuilder sb = new StringBuilder(" CURRENT INVENTORY:\n\n");
        for (String key : inventory.keySet()) {
            sb.append(key).append(": ").append(inventory.get(key)).append("\n");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    /**
     * Shows items with low stock (<5 items) If all items have enough stock,
     * display the respective message
     */
    private static void lowStockAlert() {

        StringBuilder sb = new StringBuilder("️ LOW STOCK ITEMS (< 5):\n\n");
        boolean found = false;

        for (var entry : inventory.entrySet()) {
            if (entry.getValue() < 5) {
                sb.append(entry.getKey()).append(": ").append(entry.getValue()).append("\n");
                found = true;
            }
        }

        if (!found) {
            sb.append("All items are sufficiently stocked!");
        }

        JOptionPane.showMessageDialog(null, sb.toString());
    }

    /**
     * Search for an item by name and display its quantity.
     */
    private static void searchItem() {
        String name = JOptionPane.showInputDialog("Enter item name to search:");
        if (name == null) {
            return;
        }

        if (inventory.containsKey(name)) {
            int qty = inventory.get(name);
            JOptionPane.showMessageDialog(null, "Item found!\n" + name + ": " + qty);
        } else {
            JOptionPane.showMessageDialog(null, "Item not found in inventory.");
        }
    }

    /**
     * Export current inventory to a text file named 'inventory.txt'.
     */
    private static void saveInventory() {
        if (inventory.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Inventory empty. Nothing to export.");
            return;
        }

        try (PrintWriter pw = new PrintWriter(new FileWriter("inventory.txt"))) {

            pw.println(" CURRENT INVENTORY:\n");
            for (var entry : inventory.entrySet()) {
                pw.println(entry.getKey() + ": " + entry.getValue());
            }

            JOptionPane.showMessageDialog(null, "Inventory exported to inventory.txt!");
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error exporting inventory: " + e.getMessage());
        }
    }
}
