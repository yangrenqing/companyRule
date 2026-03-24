package com.companyrule.performance.application;

public class CycleNotFoundException extends RuntimeException {

    public CycleNotFoundException(String cycleId) {
        super("Performance cycle not found: " + cycleId);
    }
}
