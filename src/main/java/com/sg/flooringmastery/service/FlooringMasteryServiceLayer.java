package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import java.util.List;
import java.util.Map;

public interface FlooringMasteryServiceLayer {
 
    void createOrder(Order order, String date) throws
            FlooringMasteryDuplicateIdException,
            FlooringMasteryDataValidationException,
            FlooringMasteryPersistenceException,
            FlooringMasteryIllegalStateException;
 
    List<Order> getAllOrders(String date) throws
            FlooringMasteryPersistenceException;
 
    Order getOrder(int orderId, String date) throws
            FlooringMasteryPersistenceException;
 
    Order removeOrder(int orderId, String date) throws
            FlooringMasteryPersistenceException;
    
    int getNewOrderId(String date) throws 
            FlooringMasteryPersistenceException;
 
    Map<String, Product> getProductData() throws 
            FlooringMasteryPersistenceException;
    
    void computeFields(Order order) throws 
            FlooringMasteryPersistenceException, 
            FlooringMasteryIllegalStateException;
}