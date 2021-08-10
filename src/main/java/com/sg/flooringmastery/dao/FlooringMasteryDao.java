package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.StateTax;

import java.util.List;
import java.util.Map;

public interface FlooringMasteryDao {
    /**
     * Adds the given Order to the database and associates it with the given
     * order id. If there is already a order associated with the given
     * order id it will return that order object, otherwise it will
     * return null.
     *
     * @param orderId id with which order is to be associated
     * @param order order to be added to the database
     * @param date the creation date of the order
     * @return the Order object previously associated with the given
     * order id if it exists, null otherwise
     * @throws FlooringMasteryPersistenceException
     */
    Order addOrder(Integer orderId, Order order, String date)
            throws FlooringMasteryPersistenceException;

    /**
     * Returns a List of all Orders on the database.
     *
     * @param date the date the orders were created on.
     * @return Order List containing all orders on the database.
     * @throws FlooringMasteryPersistenceException
     */
    List<Order> getAllOrders(String date)
            throws FlooringMasteryPersistenceException;

    /**
     * Returns the order object associated with the given order id.
     * Returns null if no such order exists
     *
     * @param orderId ID of the order to retrieve
     * @param date the date the order was created on
     * @return the Order object associated with the given order id,
     * null if no such order exists
     * @throws FlooringMasteryPersistenceException
     */
    Order getOrder(Integer orderId, String date)
            throws FlooringMasteryPersistenceException;

    /**
     * Removes from the database the order associated with the given id.
     * Returns the order object that is being removed or null if
     * there is no order associated with the given id
     *
     * @param orderId id of order to be removed
     * @param date the date the order was created on
     * @return Order object that was removed or null if no order
     * was associated with the given order id
     * @throws FlooringMasteryPersistenceException
     */
    Order removeOrder(Integer orderId, String date)
            throws FlooringMasteryPersistenceException;
    
    Map<String, Product> getProductData() 
            throws FlooringMasteryPersistenceException;
    
    Map<String, StateTax> getStateTaxData() 
            throws FlooringMasteryPersistenceException;
    
}