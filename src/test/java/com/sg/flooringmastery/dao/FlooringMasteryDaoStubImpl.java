/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dao;

import com.sg.flooringmastery.dto.Order;
import com.sg.flooringmastery.dto.Product;
import com.sg.flooringmastery.dto.StateTax;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Cosmos
 */
public class FlooringMasteryDaoStubImpl implements FlooringMasteryDao {

    public Order onlyOrder;
    public StateTax onlyState;
    public Product onlyProduct;
    
    public FlooringMasteryDaoStubImpl() {
                
        onlyOrder = new Order(1);
        onlyOrder.setCustomerName("Ada");
        onlyOrder.setState("KY");
        onlyOrder.setTaxRate(new BigDecimal("6.00"));
        onlyOrder.setProductType("Tile");
        onlyOrder.setArea(new BigDecimal("200"));
        onlyOrder.setCostPerSqrFt(new BigDecimal("2000"));
        onlyOrder.setLaborCostPerSqrFt(new BigDecimal("2000"));
        onlyOrder.setMaterialCost(new BigDecimal("2000"));
        onlyOrder.setLaborCost(new BigDecimal("2000"));
        onlyOrder.setTax(new BigDecimal("2000"));
        onlyOrder.setTotal(new BigDecimal("2000"));
        
        onlyState = new StateTax("KY");
        onlyState.setStateName("Kentucky");
        onlyState.setTaxRate(new BigDecimal("6.00"));
        
        onlyProduct = new Product("Tile");
        onlyProduct.setCostPerSqrFt(new BigDecimal("2.00"));
        onlyProduct.setLaborCostPerSqrFt(new BigDecimal("2.15"));
    }

    public FlooringMasteryDaoStubImpl(Order testOrder){
         this.onlyOrder = testOrder;
     }

    @Override
    public Order addOrder(Integer orderId, Order student, String date)
                  throws FlooringMasteryPersistenceException {
        if (orderId.equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllOrders(String date)
                 throws FlooringMasteryPersistenceException {
        List<Order> orderList = new ArrayList<>();
        orderList.add(onlyOrder);
        return orderList;
    }

    @Override
    public Order getOrder(Integer orderId, String date)
                throws FlooringMasteryPersistenceException {
        if (orderId.equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }       
    }

    @Override
    public Order removeOrder(Integer orderId, String date)
                throws FlooringMasteryPersistenceException {
        if (orderId.equals(onlyOrder.getOrderNumber())) {
            return onlyOrder;
        } else {
            return null;
        }
    }
    
    @Override
    public Map<String, StateTax> getStateTaxData() {
        Map<String, StateTax> stateList = new HashMap<>();
        stateList.put(onlyState.getStateAbrv(), onlyState);
        
        return stateList;
    }
    
    @Override
    public Map<String, Product> getProductData() {
        Map<String, Product> prodList = new HashMap<>();
        prodList.put(onlyProduct.getProductType(), onlyProduct);
        
        return prodList;
    }
}
