/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

import com.sg.flooringmastery.dao.FlooringMasteryPersistenceException;
import com.sg.flooringmastery.dto.Order;
import java.math.BigDecimal;
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
public class FlooringMasteryServiceLayerImplTest {
    
    private FlooringMasteryServiceLayer service;
    
    public FlooringMasteryServiceLayerImplTest() {
        ApplicationContext ctx = 
                new ClassPathXmlApplicationContext("applicationContext.xml");
        service = ctx.getBean("serviceLayer", FlooringMasteryServiceLayer.class);
    }
    
    @BeforeAll
    public static void setUpClass() {
    }
    
    @AfterAll
    public static void tearDownClass() {
    }
    
    @BeforeEach
    public void setUp() {
    }
    
    @AfterEach
    public void tearDown() {
    }

    /**
     * Test of createOrder method, of class FlooringMasteryServiceLayerImpl.
     */
    @Test
    public void testCreateValidOrder() {
        // ARRANGE
        Order order = new Order(99);
        order.setCustomerName("Charles");
        order.setState("KY");
        order.setTaxRate(new BigDecimal("6.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("200"));order.setArea(new BigDecimal("200"));
        order.setCostPerSqrFt(new BigDecimal("2000"));
        order.setLaborCostPerSqrFt(new BigDecimal("2000"));
        order.setMaterialCost(new BigDecimal("2000"));
        order.setLaborCost(new BigDecimal("2000"));
        order.setTax(new BigDecimal("2000"));
        order.setTotal(new BigDecimal("2000"));
        
        String date = "04092032";
        // ACT
        try {
            service.createOrder(order, date);
        } catch (FlooringMasteryDuplicateIdException
                | FlooringMasteryDataValidationException
                | FlooringMasteryPersistenceException
                | FlooringMasteryIllegalStateException e) {
        // ASSERT
            System.out.println(e.getMessage());
            fail("Order was valid. No exception should have been thrown.");
        }
    }

    @Test
    public void testCreateOrderInvalidData() throws Exception {
        // ARRANGE
        Order order = new Order(5);
        order.setCustomerName("");
        order.setState("Country");
        order.setTaxRate(new BigDecimal("6.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("200"));order.setArea(new BigDecimal("5"));
        order.setCostPerSqrFt(new BigDecimal("2000"));
        order.setLaborCostPerSqrFt(new BigDecimal("2000"));
        order.setMaterialCost(new BigDecimal("2000"));
        order.setLaborCost(new BigDecimal("2000"));
        order.setTax(new BigDecimal("2000"));
        order.setTotal(new BigDecimal("2000"));
        
        String date = "02091992";
        
        // ACT
        try {
            service.createOrder(order, date);
            fail("Expected ValidationException was not thrown.");
        } catch (FlooringMasteryDuplicateIdException
                | FlooringMasteryPersistenceException e) {
        // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryDataValidationException | FlooringMasteryIllegalStateException e){
        }  
    }
    
    @Test
    public void testCreateDuplicateIdOrder() throws FlooringMasteryIllegalStateException {
        // ARRANGE
        Order order = new Order(1);
        order.setCustomerName("Charles");
        order.setState("KY");
        order.setTaxRate(new BigDecimal("6.00"));
        order.setProductType("Tile");
        order.setArea(new BigDecimal("200"));order.setArea(new BigDecimal("200"));
        order.setCostPerSqrFt(new BigDecimal("2000"));
        order.setLaborCostPerSqrFt(new BigDecimal("2000"));
        order.setMaterialCost(new BigDecimal("2000"));
        order.setLaborCost(new BigDecimal("2000"));
        order.setTax(new BigDecimal("2000"));
        order.setTotal(new BigDecimal("2000"));
        
        String date = "04092032";
        
        // ACT
        try {
            service.createOrder(order, date);
            fail("Expected DupeId Exception was not thrown.");
        } catch (FlooringMasteryDataValidationException
                | FlooringMasteryPersistenceException
                | FlooringMasteryIllegalStateException e) {
        // ASSERT
            fail("Incorrect exception was thrown.");
        } catch (FlooringMasteryDuplicateIdException e){
        }
    }
    
    /**
     * Test of getAllOrders method, of class FlooringMasteryServiceLayerImpl.
     */
    @Test
    public void testGetAllOrders() throws Exception {
        // ARRANGE
        Order testClone = new Order(88);
        testClone.setCustomerName("Ada");
        testClone.setState("KY");
        testClone.setTaxRate(new BigDecimal("6.00"));
        testClone.setProductType("Tile");
        testClone.setArea(new BigDecimal("200"));
        testClone.setCostPerSqrFt(new BigDecimal("2000"));
        testClone.setLaborCostPerSqrFt(new BigDecimal("2000"));
        testClone.setMaterialCost(new BigDecimal("2000"));
        testClone.setLaborCost(new BigDecimal("2000"));
        testClone.setTax(new BigDecimal("2000"));
        testClone.setTotal(new BigDecimal("2000"));
        
        String date = "04092032";
        
        service.createOrder(testClone, date);
        
        // ACT & ASSERT
        assertEquals( 1, service.getAllOrders(date).size(), 
                                       "Should only have one order.");
    }

    /**
     * Test of getOrder method, of class FlooringMasteryServiceLayerImpl.
     */
    @Test
    public void testGetOrder() throws Exception {
        // ARRANGE
        Order testClone = new Order(1);
        testClone.setCustomerName("Ada");
        testClone.setState("KY");
        testClone.setTaxRate(new BigDecimal("6.00"));
        testClone.setProductType("Tile");
        testClone.setArea(new BigDecimal("200"));testClone.setArea(new BigDecimal("200"));
        testClone.setCostPerSqrFt(new BigDecimal("2000"));
        testClone.setLaborCostPerSqrFt(new BigDecimal("2000"));
        testClone.setMaterialCost(new BigDecimal("2000"));
        testClone.setLaborCost(new BigDecimal("2000"));
        testClone.setTax(new BigDecimal("2000"));
        testClone.setTotal(new BigDecimal("2000"));

        String date = "04092032";

        // ACT & ASSERT
        Order shouldBeAda = service.getOrder(1, date);
        assertNotNull(shouldBeAda, "Getting 2 should be not null.");
        assertEquals( testClone, shouldBeAda,
                                       "Order stored under 2 should be Ada.");

        Order shouldBeNull = service.getOrder(52, date);    
        assertNull( shouldBeNull, "Getting 52 should be null.");

    }
    /**
     * Test of removeOrder method, of class FlooringMasteryServiceLayerImpl.
     */
    @Test
    public void testRemoveOrder() throws Exception {
        // ARRANGE
        Order testClone = new Order(1);
        testClone.setCustomerName("Ada");
        testClone.setState("KY");
        testClone.setTaxRate(new BigDecimal("6.00"));
        testClone.setProductType("Tile");
        testClone.setArea(new BigDecimal("200"));
        testClone.setCostPerSqrFt(new BigDecimal("2000"));
        testClone.setLaborCostPerSqrFt(new BigDecimal("2000"));
        testClone.setMaterialCost(new BigDecimal("2000"));
        testClone.setLaborCost(new BigDecimal("2000"));
        testClone.setTax(new BigDecimal("2000"));
        testClone.setTotal(new BigDecimal("2000"));
        
        String date = "04092032";
        
        // ACT & ASSERT
        Order shouldBeAda = service.removeOrder(1, date);
        assertNotNull( shouldBeAda, "Removing 1 should be not null.");
        assertEquals( testClone, shouldBeAda, "Order removed from 2 should be Ada.");

        Order shouldBeNull = service.removeOrder(50, date);    
        assertNull( shouldBeNull, "Removing 50 should be null.");

    }
    
}
