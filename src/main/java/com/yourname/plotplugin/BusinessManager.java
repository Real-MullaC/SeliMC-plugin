package com.yourname.plotplugin;

import java.util.HashMap;

public class BusinessManager {
    private final HashMap<String, Double> businessBalances = new HashMap<>();

    public void payBusiness(String businessName, double amount) {
        businessBalances.put(businessName, businessBalances.getOrDefault(businessName, 0.0) + amount);
    }

    public boolean businessExists(String businessName) {
        return businessBalances.containsKey(businessName);
    }

    public void createBusiness(String businessName, double initialBalance) {
        businessBalances.put(businessName, initialBalance);
    }
}