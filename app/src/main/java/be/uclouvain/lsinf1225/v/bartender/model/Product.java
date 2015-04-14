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
}
