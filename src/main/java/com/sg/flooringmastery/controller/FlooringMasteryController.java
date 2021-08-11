package com.sg.flooringmastery.controller;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.ui.FlooringMasteryView;
import com.sg.flooringmastery.service.FlooringMasteryDataValidationException;
import com.sg.flooringmastery.service.FlooringMasteryDuplicateIdException;
import com.sg.flooringmastery.service.FlooringMasteryIllegalStateException;

import java.util.List;
import com.sg.flooringmastery.service.FlooringMasteryServiceLayer;

public class FlooringMasteryController {

    private final FlooringMasteryView view;
    private final FlooringMasteryServiceLayer service;

    public FlooringMasteryController(FlooringMasteryServiceLayer service, FlooringMasteryView view) {
        this.service = service;
        this.view = view;
    }

    /**
     * 
     * Main menu for the application.
     * 
     */
    public void run() {
        boolean keepGoing = true;
        int menuSelection;
        try {
            while (keepGoing) {

                menuSelection = getMenuSelection();

                switch (menuSelection) {
                    case 1:
                        displayOrders();
                        break;
                    case 2:
                        createOrder();
                        break;
                    case 3:
                        editOrder();
                        break;
                    case 4:
                        removeOrder();
                        break;
                    case 5:
                        keepGoing = false;
                        break;
                    default:
                        unknownCommand();
                }

            }
            exitMessage();
        } catch (FlooringMasteryPersistenceException | FlooringMasteryDataValidationException | FlooringMasteryDuplicateIdException | FlooringMasteryIllegalStateException e) {
            view.displayErrorMessage(e.getMessage());
        }
    }
    
    /**
     * 
     * Displays the orders for a specific date.
     * 
     */
    private void displayOrders() {
        String date = view.getDateEntry();
        List<Order> orderList;
        try {
            orderList = service.getAllOrders(date);
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
            return;
        }
             
        view.displayOrderList(orderList);
    }
    
    /**
     * 
     * Displays the orders for a specific date.
     * 
     */
    private void displayOrders(String date) {
        List<Order> orderList;
        try {
            orderList = service.getAllOrders(date);
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
            return;
        }
             
        view.displayOrderList(orderList);
    }
    
    /**
     * 
     * Creates an order for a specified date.
     * 
     * @throws FlooringMasteryDuplicateIdException 
     * @throws FlooringMasteryDataValidationException 
     * @throws FlooringMasteryPersistenceException 
     * @throws FlooringMasteryIllegalStateException 
     */
    private void createOrder() throws FlooringMasteryDuplicateIdException, 
            FlooringMasteryDataValidationException, 
            FlooringMasteryPersistenceException,
            FlooringMasteryIllegalStateException {
        
        view.displayCreateOrderBanner();
        
        String date = view.getNewDateEntry();
                
        Order currentOrder;
        
        boolean created;
        do {
            int orderId = 1;
            try {
                orderId = service.getNewOrderId(date);
            } catch (FlooringMasteryPersistenceException e) {
            }
            
            currentOrder = view.getNewOrderInfo(orderId, service.getProductData(), service.getStateData());
            
            try {
                service.createOrder(currentOrder, date);
            } catch (FlooringMasteryIllegalStateException e) {
                view.displayErrorMessage(e.getMessage());
            }
            created = true;
        } while (!created);
        
        view.displayOrder(currentOrder);
        
        view.displayCreateSuccessBanner();
    }
    
    /**
     * 
     * Edits an order property based on user integer input.
     * 
     * @throws FlooringMasteryPersistenceException
     * @throws FlooringMasteryDuplicateIdException
     * @throws FlooringMasteryDataValidationException
     * @throws FlooringMasteryIllegalStateException 
     */
    private void editOrder() throws FlooringMasteryPersistenceException, 
            FlooringMasteryDuplicateIdException, 
            FlooringMasteryDataValidationException, 
            FlooringMasteryIllegalStateException {
        
        view.displayEditOrderBanner();
        
        String date = view.getDateEntry();
        
        displayOrders(date);
        
        int orderId = view.getOrderIdChoice();
        Order order = service.getOrder(orderId, date);
        
        view.displayOrder(order);

        if (order == null)
            return;
        
        int editChoice = view.printEditMenuAndGetSelection();
        
        if (editChoice == 5)
            return;
        
        Order changedOrder = new Order(order);
        
        changedOrder = view.getNewOrderInfo(changedOrder, editChoice, service.getProductData(), service.getStateData());
        
        // State, product type, area
        if (editChoice == 2 || editChoice == 3 || editChoice == 4)
            service.computeFields(changedOrder);

        service.removeOrder(order.getOrderNumber(), date);

        service.createOrder(changedOrder, date);
        
        view.displayEditSuccessBanner();
        
        view.displayOrder(changedOrder);
    }
    
    /**
     * 
     * Removes an order for a specified date based on the order id.
     * 
     * @throws FlooringMasteryPersistenceException 
     */
    private void removeOrder() throws FlooringMasteryPersistenceException {
        view.displayRemoveOrderBanner();
        String date = view.getDateEntry();
        int orderId = view.getOrderIdChoice();
        
        Order removedOrder;
        
        try {
            removedOrder = service.getOrder(orderId, date);
        } catch (FlooringMasteryPersistenceException e) {
            view.displayErrorMessage(e.getMessage());
            return;
        }
        
        if (removedOrder == null) {
                view.displayOrder(removedOrder);
                return;
        }
        
        boolean removed = view.displayRemoveResult(removedOrder);
        
        if (removed)
            service.removeOrder(orderId, date);
    }

    private void unknownCommand() {
        view.displayUnknownCommandBanner();
    }

    private void exitMessage() {
        view.displayExitBanner();
    }

    private int getMenuSelection() {
        return view.printMenuAndGetSelection();
    }

}