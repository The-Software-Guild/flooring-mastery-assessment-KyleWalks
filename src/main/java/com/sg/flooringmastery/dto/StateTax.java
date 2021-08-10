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
public class StateTax {
    private String stateAbrv;
    private String stateName;
    private BigDecimal taxRate;

    public StateTax(String stateAbrv, String stateName, BigDecimal taxRate) {
        this.stateAbrv = stateAbrv;
        this.stateName = stateName;
        this.taxRate = taxRate;
    }
    
    public StateTax(String stateAbrv) {
        this.stateAbrv = stateAbrv;
    }

    public String getStateAbrv() {
        return stateAbrv;
    }

    public void setStateAbrv(String stateAbrv) {
        this.stateAbrv = stateAbrv;
    }

    public String getStateName() {
        return stateName;
    }

    public void setStateName(String stateName) {
        this.stateName = stateName;
    }

    public BigDecimal getTaxRate() {
        return taxRate;
    }

    public void setTaxRate(BigDecimal taxRate) {
        this.taxRate = taxRate;
    }
    
}
