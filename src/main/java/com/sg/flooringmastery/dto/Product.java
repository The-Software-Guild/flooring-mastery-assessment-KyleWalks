/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.dto;

import java.math.BigDecimal;

/**
 *
 * @author Cosmos
 */
public class Product {
    private String productType;
    private BigDecimal costPerSqrFt;
    private BigDecimal laborCostPerSqrFt;

    public Product(String productType, BigDecimal costPerSqrFt, BigDecimal laborCostPerSqrFt) {
        this.productType = productType;
        this.costPerSqrFt = costPerSqrFt;
        this.laborCostPerSqrFt = laborCostPerSqrFt;
    }
    
    public Product(String productType) {
        this.productType = productType;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
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
    
}
