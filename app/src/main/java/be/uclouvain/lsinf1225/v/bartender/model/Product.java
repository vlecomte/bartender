package be.uclouvain.lsinf1225.v.bartender.model;

import android.util.Pair;

import java.util.ArrayList;

public class Product {
    private String mName;
    private String mDisplayName;
    private double mPrice;

    private ArrayList<Pair<Ingredient, Double>> mUsages;

    public Product(String name, String displayName, double price) {
        mName = name;
        mDisplayName = displayName;
        mPrice = price;
        mUsages = new ArrayList<>();
    }

    public String getName() {
        return mName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public double getPrice() {
        return mPrice;
    }

    public void addUsage(Ingredient ingredient, double quantity) {
        mUsages.add(new Pair<>(ingredient, quantity));
    }

    public int getNumAvailable() {
        int numAvailable = Integer.MAX_VALUE;
        for (Pair<Ingredient, Double> usage : mUsages) {
            Ingredient ingredient = usage.first;
            double quantity = usage.second;
            numAvailable = Math.min(numAvailable,
                    (int) Math.floor(ingredient.getCurrent() / quantity));
        }
        return numAvailable;
    }

    public void takeOffStock() {
        for (Pair<Ingredient, Double> usage : mUsages) {
            Ingredient ingredient = usage.first;
            double quantity = usage.second;
            ingredient.remove(quantity);
        }
    }

    public void putBackStock() {
        for (Pair<Ingredient, Double> usage : mUsages) {
            Ingredient ingredient = usage.first;
            double quantity = usage.second;
            ingredient.add(quantity);
        }
    }
}
