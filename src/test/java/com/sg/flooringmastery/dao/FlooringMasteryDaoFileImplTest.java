/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 *
 * @author Cosmos
 */
public class FlooringMasteryDaoFileImplTest {
    
    FlooringMasteryDao testDao;
    
    public FlooringMasteryDaoFileImplTest() {
        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
        testDao = ctx.getBean("testDao", FlooringMasteryDaoFileImpl.class);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() throws Exception{
        String date = "04092032";
        String currDir = System.getProperty("user.dir");
        String ORDER_HEADERS = "OrderNumber::CustomerName::State::TaxRate::ProductType::Area::CostPerSquareFoot::LaborCostPerSquareFoot::MaterialCost::LaborCost::Tax::Total";
        String testFile = currDir + "\\Orders\\Orders_" + date + ".txt";
        // Use the FileWriter to quickly blank the file
        PrintWriter out;
        out = new PrintWriter(new FileWriter(testFile));
        
        // Write header
        out.println(ORDER_HEADERS);
        out.flush();
        out.close();

        testDao = new FlooringMasteryDaoFileImpl();
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of addOrder method, of class FlooringMasteryDaoFileImpl.
     */
    @Test
    public void testAddGetOrder() throws Exception {
        //this.setUp();
        // Create our method test inputs
        int orderId = 1;
        Order order = new Order(orderId);
        order.setCustomerName("Ada");
        order.setState("KY");
        order.setTaxRate(new BigDecimal("6.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("200"));
        order.setCostPerSqrFt(new BigDecimal("2000"));
        order.setLaborCostPerSqrFt(new BigDecimal("2000"));
        order.setMaterialCost(new BigDecimal("2000"));
        order.setLaborCost(new BigDecimal("2000"));
        order.setTax(new BigDecimal("2000"));
        order.setTotal(new BigDecimal("2000"));

        String date = "04092032";
        
        //  Add the order to the DAO
        testDao.addOrder(orderId, order, date);
        // Get the order from the DAO
        Order retrievedOrder = testDao.getOrder(orderId, date);

        // Check the data is equal
        assertEquals(order.getOrderNumber(),
                    retrievedOrder.getOrderNumber(),
                    "Checking order id.");
        assertEquals(order.getCustomerName(),
                    retrievedOrder.getCustomerName(),
                    "Checking order customer name.");
        assertEquals(order.getState(), 
                    retrievedOrder.getState(),
                    "Checking order state.");
        assertEquals(order.getArea(), 
                    retrievedOrder.getArea(),
                    "Checking order area.");
    }

    /**
     * Test of getAllOrders method, of class FlooringMasteryDaoFileImpl.
     */
    @Test
    public void testAddGetAllOrders() throws Exception {
        //this.setUp();
        // Create our first order
        Order firstOrder = new Order(1);
        firstOrder.setCustomerName("Ada");
        firstOrder.setState("KY");
        firstOrder.setTaxRate(new BigDecimal("6.00"));
        firstOrder.setProductType("Tile");
        firstOrder.setArea(new BigDecimal("200"));
        firstOrder.setCostPerSqrFt(new BigDecimal("2000"));
        firstOrder.setLaborCostPerSqrFt(new BigDecimal("2000"));
        firstOrder.setMaterialCost(new BigDecimal("2000"));
        firstOrder.setLaborCost(new BigDecimal("2000"));
        firstOrder.setTax(new BigDecimal("2000"));
        firstOrder.setTotal(new BigDecimal("2000"));
        
        // Create our second order
        Order secondOrder = new Order(2);
        secondOrder.setCustomerName("Charles");
        secondOrder.setState("KY");
        secondOrder.setTaxRate(new BigDecimal("6.00"));
        secondOrder.setProductType("Tile");
        secondOrder.setArea(new BigDecimal("300"));
        secondOrder.setCostPerSqrFt(new BigDecimal("2000"));
        secondOrder.setLaborCostPerSqrFt(new BigDecimal("2000"));
        secondOrder.setMaterialCost(new BigDecimal("2000"));
        secondOrder.setLaborCost(new BigDecimal("2000"));
        secondOrder.setTax(new BigDecimal("2000"));
        secondOrder.setTotal(new BigDecimal("2000"));

        String date = "04092032";
        
        // Add both our orders to the DAO
        testDao.addOrder(firstOrder.getOrderNumber(), firstOrder, date);
        testDao.addOrder(secondOrder.getOrderNumber(), secondOrder, date);

        // Retrieve the list of all orders within the DAO
        List<Order> allOrders = testDao.getAllOrders(date);

        // First check the general contents of the list
        assertNotNull(allOrders, "The list of orders must not null");
        assertEquals(2, allOrders.size(),"List of orders should have 2 orders.");

        // Then the specifics
        boolean foundAda = false;
        boolean foundCharles = false;
        for (Order currOrder : testDao.getAllOrders(date)) {
            if (currOrder.getOrderNumber() == 1)
                foundAda = true;
            else if (currOrder.getOrderNumber() == 2)
                foundCharles = true;
            
            if (foundAda && foundCharles)
                break;
        }
        assertTrue(foundAda,
                    "The list of orders should include Ada.");
        assertTrue(foundCharles,
                "The list of orders should include Charles.");
    }

    /**
     * Test of getOrder method, of class FlooringMasteryDaoFileImpl.
     */
    @Test
    public void testGetOrder() throws Exception {
        //this.setUp();
        System.out.println("getOrder");
        int orderId = 0;
        Order expResult = null;
        
        String date = "04092032";
        
        Order result = testDao.getOrder(orderId, date);
        assertEquals(expResult, result);
    }

    /**
     * Test of removeOrder method, of class FlooringMasteryDaoFileImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        
        //this.setUp();
        
        // Create two new orders
        Order firstOrder = new Order(1);
        firstOrder.setCustomerName("Ada");
        firstOrder.setState("KY");
        firstOrder.setTaxRate(new BigDecimal("6.00"));
        firstOrder.setProductType("Tile");
        firstOrder.setArea(new BigDecimal("200"));
        firstOrder.setCostPerSqrFt(new BigDecimal("2000"));
        firstOrder.setLaborCostPerSqrFt(new BigDecimal("2000"));
        firstOrder.setMaterialCost(new BigDecimal("2000"));
        firstOrder.setLaborCost(new BigDecimal("2000"));
        firstOrder.setTax(new BigDecimal("2000"));
        firstOrder.setTotal(new BigDecimal("2000"));

        Order secondOrder = new Order(2);
        secondOrder.setCustomerName("Charles");
        secondOrder.setState("KY");
        secondOrder.setTaxRate(new BigDecimal("6.00"));
        secondOrder.setProductType("Tile");
        secondOrder.setArea(new BigDecimal("200"));
        secondOrder.setCostPerSqrFt(new BigDecimal("2000"));
        secondOrder.setLaborCostPerSqrFt(new BigDecimal("2000"));
        secondOrder.setMaterialCost(new BigDecimal("2000"));
        secondOrder.setLaborCost(new BigDecimal("2000"));
        secondOrder.setTax(new BigDecimal("2000"));
        secondOrder.setTotal(new BigDecimal("2000"));

        String date = "04092032";
        
        // Add both to the DAO
        testDao.addOrder(firstOrder.getOrderNumber(), firstOrder, date);
        testDao.addOrder(secondOrder.getOrderNumber(), secondOrder, date);

        // remove the first order - Ada
        Order removedOrder = testDao.removeOrder(firstOrder.getOrderNumber(), date);

        // Check that the correct object was removed.
        assertEquals(removedOrder.getOrderNumber(), firstOrder.getOrderNumber(), "The removed order should be Ada.");

        // Get all the orders
        List<Order> allOrders = testDao.getAllOrders(date);

        // First check the general contents of the list
        assertNotNull( allOrders, "All orders list should be not null.");
        assertEquals( 1, allOrders.size(), "All orders should only have 1 order.");

        // Then the specifics

        // Then the specifics
        boolean foundAda = false;
        boolean foundCharles = false;
        for (Order currOrder : testDao.getAllOrders(date)) {
            if (currOrder.getOrderNumber() == 1)
                foundAda = true;
            else if (currOrder.getOrderNumber() == 2)
                foundCharles = true;
            if (foundAda && foundCharles)
                break;
        }
        
        assertFalse( foundAda, "All orders should NOT include Ada.");
        assertTrue( foundCharles, "All orders should include Charles.");    
        
        // Remove the second order
        removedOrder = testDao.removeOrder(secondOrder.getOrderNumber(), date);
        // Check that the correct object was removed.
        assertEquals( removedOrder.getOrderNumber(), secondOrder.getOrderNumber(), "The removed order should be Charles.");

        // retrieve all of the orders again, and check the list.
        allOrders = testDao.getAllOrders(date);

        // Check the contents of the list - it should be empty
        assertTrue( allOrders.isEmpty(), "The retrieved list of orders should be empty.");

        // Try to 'get' both orders by their old id - they should be null!
        Order retrievedOrder = testDao.getOrder(firstOrder.getOrderNumber(), date);
        assertNull(retrievedOrder, "Ada was removed, should be null.");

        retrievedOrder = testDao.getOrder(secondOrder.getOrderNumber(), date);
        assertNull(retrievedOrder, "Charles was removed, should be null.");

    }
    
}
