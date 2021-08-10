/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.flooringmastery.service;

/**
 *
 * @author Cosmos
 */
public class FlooringMasteryIllegalStateException extends Exception {
    public FlooringMasteryIllegalStateException(String message) {
        super(message);
    }

    public FlooringMasteryIllegalStateException(String message, Throwable cause) {
        super(message, cause);
    }
}
