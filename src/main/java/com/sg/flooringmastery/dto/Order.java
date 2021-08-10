/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;
import java.util.Objects;

/**
 *
 * @author Cosmos
 */
public class Order {
    
    private int orderNumber;
    private String customerName;
    private String state;
    private BigDecimal taxRate;
    private String productType;
    private BigDecimal area;
    private BigDecimal costPerSqrFt;
    private BigDecimal laborCostPerSqrFt;
    private BigDecimal materialCost;
    private BigDecimal laborCost;
    private BigDecimal tax;
    private BigDecimal total;

    public Order(int orderNumber, String customerName, String state, BigDecimal taxRate, String productType, BigDecimal area, BigDecimal costPerSqrFt, BigDecimal laborCostPerSqrFt, BigDecimal materialCost, BigDecimal laborCost, BigDecimal tax, BigDecimal total) {
        this.orderNumber = orderNumber;
        this.customerName = customerName;
        this.state = state;
        this.taxRate = taxRate;
        this.productType = productType;
        this.area = area;
        this.costPerSqrFt = costPerSqrFt;
        this.laborCostPerSqrFt = laborCostPerSqrFt;
        this.materialCost = materialCost;
        this.laborCost = laborCost;
        this.tax = tax;
        this.total = total;
    }

    public Order(int orderNumber) {
        this.orderNumber = orderNumber;
    }
    
    public Order(Order otherOrder) {
        this.orderNumber = otherOrder.getOrderNumber();
        this.customerName = otherOrder.getCustomerName();
        this.state = otherOrder.getState();
        this.taxRate = otherOrder.getTaxRate();
        this.productType = otherOrder.getProductType();
        this.area = otherOrder.getArea();
        this.costPerSqrFt = otherOrder.getCostPerSqrFt();
        this.laborCostPerSqrFt = otherOrder.getLaborCostPerSqrFt();
        this.materialCost = otherOrder.getMaterialCost();
        this.laborCost = otherOrder.getLaborCost();
        this.tax = otherOrder.getTax();
        this.total = otherOrder.getTotal();
    }

    public int getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(int orderNumber) {
        this.orderNumber = orderNumber;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public BigDecimal getArea() {
        return area;
    }

    public void setArea(BigDecimal area) {
        this.area = area;
    }

    public BigDecimal getCostPerSqrFt() {
        return costPerSqrFt;
    }

    public void setCostPerSqrFt(BigDecimal costPerSqrFt) {
        this.costPerSqrFt = costPerSqrFt;
    }

    public BigDecimal getLaborCostPerSqrFt() {
        return laborCostPerSqrFt;
    }

    public void setLaborCostPerSqrFt(BigDecimal laborCostPerSqrFt) {
        this.laborCostPerSqrFt = laborCostPerSqrFt;
    }

    public BigDecimal getMaterialCost() {
        return materialCost;
    }

    public void setMaterialCost(BigDecimal materialCost) {
        this.materialCost = materialCost;
    }

    public BigDecimal getLaborCost() {
        return laborCost;
    }

    public void setLaborCost(BigDecimal laborCost) {
        this.laborCost = laborCost;
    }

    public BigDecimal getTax() {
        return tax;
    }

    public void setTax(BigDecimal tax) {
        this.tax = tax;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public void setTotal(BigDecimal total) {
        this.total = total;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 47 * hash + this.orderNumber;
        hash = 47 * hash + Objects.hashCode(this.customerName);
        hash = 47 * hash + Objects.hashCode(this.state);
        hash = 47 * hash + Objects.hashCode(this.taxRate);
        hash = 47 * hash + Objects.hashCode(this.productType);
        hash = 47 * hash + Objects.hashCode(this.area);
        hash = 47 * hash + Objects.hashCode(this.costPerSqrFt);
        hash = 47 * hash + Objects.hashCode(this.laborCostPerSqrFt);
        hash = 47 * hash + Objects.hashCode(this.materialCost);
        hash = 47 * hash + Objects.hashCode(this.laborCost);
        hash = 47 * hash + Objects.hashCode(this.tax);
        hash = 47 * hash + Objects.hashCode(this.total);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Order other = (Order) obj;
        if (this.orderNumber != other.orderNumber) {
            return false;
        }
        if (!Objects.equals(this.customerName, other.customerName)) {
            return false;
        }
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.productType, other.productType)) {
            return false;
        }
        if (!Objects.equals(this.taxRate, other.taxRate)) {
            return false;
        }
        if (!Objects.equals(this.area, other.area)) {
            return false;
        }
        if (!Objects.equals(this.costPerSqrFt, other.costPerSqrFt)) {
            return false;
        }
        if (!Objects.equals(this.laborCostPerSqrFt, other.laborCostPerSqrFt)) {
            return false;
        }
        if (!Objects.equals(this.materialCost, other.materialCost)) {
            return false;
        }
        if (!Objects.equals(this.laborCost, other.laborCost)) {
            return false;
        }
        if (!Objects.equals(this.tax, other.tax)) {
            return false;
        }
        if (!Objects.equals(this.total, other.total)) {
            return false;
        }
        return true;
    }
    
}
