package com.sg.flooringmastery.ui;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

import java.util.List;
import java.util.Map;


public class FlooringMasteryView {

    private final UserIO io;

    public FlooringMasteryView(UserIO io) {
        this.io = io;
    }

    public int printMenuAndGetSelection() {
        io.print("Main Menu");
        io.print("1. List Orders");
        io.print("2. Add an Order");
        io.print("3. Edit an Order");
        io.print("4. Remove a Order");
        io.print("5. Exit");

        return io.readInt("Please select from the above choices.", 1, 5);
    }
    
    /**
     * Displays the menu used to choose which property
     * is to be edited by the user.
     *
     * @return the user choice as an integer.
     */
    public int printEditMenuAndGetSelection() {

        io.print("Edit Menu");
        io.print("1. Customer Name");
        io.print("2. State");
        io.print("3. Product Type");
        io.print("4. Area");
        io.print("5. Exit Menu");

        return io.readInt("Please select from the above choices.", 1, 5);
    }

    public Order getNewOrderInfo(int orderId, Map<String, Product> products) {
        String customerName = "";
        
        do {
            customerName = io.readString("Please enter the customer name", 1);
        } while (!checkString(customerName));
        String state = io.readString("Please enter the state", 2);
        
        displayProducts(products);
        
        String productType = io.readString("Please enter the product type");
        BigDecimal area = io.readBigDecimal("Please enter the area (min 100)", new BigDecimal("100"));
        
        Order currentOrder = new Order(orderId);
        
        currentOrder.setCustomerName(customerName);
        currentOrder.setState(state);
        currentOrder.setProductType(productType);
        currentOrder.setArea(area);

        return currentOrder;
    }
    
    public Order getNewOrderInfo(Order order, int propChoice, Map<String, Product> products) {
        String propChange = "";

        switch (propChoice) {
            case 1:
                do {
                    propChange = io.readString("Please enter the customer name", 1);
                } while (!checkString(propChange));
                break;
            case 2:
                propChange = io.readString("Please enter the state", 2);
                break;
            case 3:
                displayProducts(products);
                propChange = io.readString("Please enter the product type");
                break;
            case 4:
                propChange = io.readBigDecimal("Please enter the area", new BigDecimal("100")).toString();
                break;
            default:
                return order;
        }

        switch (propChoice) {
            case 1:
                order.setCustomerName(propChange);
                break;
            case 2:
                order.setState(propChange);
                break;
            case 3:
                order.setProductType(propChange);
                break;
            case 4:
                order.setArea(new BigDecimal(propChange));
                break;
            default:
                break;
        }

        return order;
    }
    
    public void displayProducts(Map<String, Product> products) {
        products.values().forEach(prod -> {
            String productInfo = String.format("%25s : %-15s\n%25s : %-15s\n%25s : %-15s\n",
                    "Product",
                    prod.getProductType(),
                    "Cost per Sqr Ft",
                    "$" + prod.getCostPerSqrFt(),
                    "Labor Cost per Sqr Ft",
                    "$" + prod.getLaborCostPerSqrFt());
            
            io.print(productInfo);
        });
        io.readString("Please hit enter to continue.");
    }

    public void displayCreateOrderBanner() {
        io.print("=== Create Order ===");
    }

    public void displayCreateSuccessBanner() {
        io.readString("Order successfully created.  Please hit enter to continue");
    }

    public void displayOrderList(List<Order> orderList) {
        
        if (orderList.size() < 1) {
            io.print("No orders found\n");
            return;
        }
        orderList.forEach(currentOrder -> {
            String orderInfo = String.format("%21s : %s, %s\n",
                    "#" + currentOrder.getOrderNumber(),
                    currentOrder.getCustomerName(),
                    currentOrder.getState());
            
            String orderProductInfo = String.format("%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n",
                    "Product Type",
                    currentOrder.getProductType(),
                    "Area",
                    currentOrder.getArea(),
                    "Cost per Sqr Ft",
                    "$" + currentOrder.getCostPerSqrFt(),
                    "Labor Cost per Sqr Ft",
                    "$" + currentOrder.getLaborCostPerSqrFt(),
                    "Material Cost",
                    "$" + currentOrder.getMaterialCost(),
                    "Labor Cost",
                    "$" + currentOrder.getLaborCost(),
                    "Tax Rate",
                    currentOrder.getTaxRate() + " %",
                    "Tax",
                    "$" + currentOrder.getTax(),
                    "Total",
                    "$" + currentOrder.getTotal());
            
            io.print(orderInfo + orderProductInfo);
        });
        io.readString("Please hit enter to continue.");
    }

    public void displayRemoveOrderBanner () {
        io.print("=== Remove Order ===");
    }

    public boolean displayRemoveResult(Order orderRecord) {
        
        displayOrder(orderRecord);
                
        String input = io.readString("Enter 'y' to confirm deletion or nothing to cancel.");
        
        boolean remove = input.equals("y");
        
        if(orderRecord != null && remove){
            io.print("Order successfully removed.");
        } else if (!remove) {
            io.print("Order was not removed.");
        }else{
            io.print("No such order.");
        }
        io.readString("Please hit enter to continue.");
        
        return remove;
    }

    public void displayDisplayOrderBanner () {
        io.print("=== Display Order ===");
    }
    
    public void displayEditOrderBanner () {
        io.print("=== Edit Order ===");
    }

    public int getOrderIdChoice() {
        return io.readInt("Please enter the Order ID.");
    }
    
    public String getDateEntry() {
        
        int yearNum = 0;
        int monthNum;
        int dayNum;
        do {
            yearNum = io.readInt("Please enter the year");
        } while(Integer.toString(yearNum).length() < 4);

        monthNum = io.readInt("Please enter the month", 1, 12);

        int maxDays;
        // Set max days based on month entered
        switch (monthNum) {
            case 2:
                // Leap year
                maxDays = yearNum % 4 == 0 ? 29 : 28;
                break;
            case 4:
            case 6:
            case 9:
            case 11:
                maxDays = 30;
                break;
            default:
                maxDays = 31;
        }
        dayNum = io.readInt("Please enter the day", 1, maxDays);
                
        String year = Integer.toString(yearNum);
        String month = Integer.toString(monthNum);
        String day = Integer.toString(dayNum);
        
        if (monthNum < 10)
            month = "0" + month;
        if (dayNum < 10)
            day = "0" + day;
        
        String date = month + day + year;
                
        return date;
    }
    
    public String getNewDateEntry() {
        LocalDate today = LocalDate.now();
        DateTimeFormatter format = DateTimeFormatter.ofPattern("MMDDYYYY");
        today.format(format);
        
        LocalDate desiredDate;
        Period dateDiff = null;
        int yearNum = 0;
        int monthNum = 0;
        int dayNum = 0;
        do {
            if (dateDiff != null && dateDiff.isNegative())
                displayErrorMessage("Invalid date. Date must be today or later.\n");
            
            do {
                yearNum = io.readInt("Please enter the year");
            } while(Integer.toString(yearNum).length() < 4);

            monthNum = io.readInt("Please enter the month", 1, 12);

            int maxDays;
            // TODO:  Check for leap year.
            switch (monthNum) {
                case 2:
                    // Leap year
                    maxDays = yearNum % 4 == 0 ? 29 : 28;
                    break;
                case 4:
                case 6:
                case 9:
                case 11:
                    maxDays = 30;
                    break;
                default:
                    maxDays = 31;
            }
            dayNum = io.readInt("Please enter the day", 1, maxDays);
            
            desiredDate = LocalDate.of(yearNum, monthNum, dayNum);
            dateDiff = Period.between(today, desiredDate);
        } while(dateDiff.isNegative());
        
                
        String year = Integer.toString(yearNum);
        String month = Integer.toString(monthNum);
        String day = Integer.toString(dayNum);
        
        // Format the month and days to be XX
        if (monthNum < 10)
            month = "0" + month;
        if (dayNum < 10)
            day = "0" + day;
        
        String date = month + day + year;
                
        return date;
    }

    public void displayOrder(Order order) {
        if (order != null) {
            String orderInfo = String.format("%21s : %s, %s\n",
                    "#" + order.getOrderNumber(),
                    order.getCustomerName(),
                    order.getState());
            
            String orderProductInfo = String.format("%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n"
                    + "%21s : %-20s\n",
                    "Product Type",
                    order.getProductType(),
                    "Area",
                    order.getArea(),
                    "Cost per Sqr Ft",
                    "$" + order.getCostPerSqrFt(),
                    "Labor Cost per Sqr Ft",
                    "$" + order.getLaborCostPerSqrFt(),
                    "Material Cost",
                    "$" + order.getMaterialCost(),
                    "Labor Cost",
                    "$" + order.getLaborCost(),
                    "Tax Rate",
                    order.getTaxRate() + " %",
                    "Tax",
                    "$" + order.getTax(),
                    "Total",
                    "$" + order.getTotal());
            
            io.print(orderInfo + orderProductInfo);
            
        } else {
            io.print("No such order.");
        }
        io.readString("Please hit enter to continue.");
    }
    
    public void displayEditSuccessBanner() {
        io.print("\nSuccessfully changed and saved order.");
    }

    public void displayErrorMessage(String errorMsg) {
        io.print("=== ERROR ===");
        io.print(errorMsg);
    }

    public void displayExitBanner() {
        io.print("Exiting.");
    }

    public void displayUnknownCommandBanner() {
        io.print("Unknown Command!!!");
    }

    public void displayDisplayAllBanner() {
        io.print("=== Display All Orders ===");
    }
    
    /**
     * Checks that a string contains at least one letter or number.
     *
     * @param s The string to be checked
     * @return true if a letter or number was found.
     */
    private boolean checkString(String s) {
        if (s == null)
            return false;

        boolean charFound = false;

        int index = 0;

        while (index < s.length() && !charFound) {
            if (Character.isLetterOrDigit(s.charAt(index)) || s.charAt(index) != ',')
                charFound = true;

            index++;
        }

        return charFound;
    }
}
