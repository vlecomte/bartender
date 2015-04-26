package be.uclouvain.lsinf1225.v.bartender.model;

import android.util.Pair;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Product {
    private String mName;
    private String mDisplayName;
    private double mPrice;
    private String mTypeIconFilename;
    private String mDescriptionFilename;
    private String mImageFilename;

    private Map<Ingredient, Double> mUsages;

    public Product(String name, String displayName, double price, String typeIconFilename,
                   String descriptionFilename, String imageFilename) {
        mName = name;
        mDisplayName = displayName;
        mPrice = price;
        mTypeIconFilename = typeIconFilename;
        mDescriptionFilename = descriptionFilename;
        mImageFilename = imageFilename;
        mUsages = new HashMap<>();
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

    public String getTypeIconFilename() {
        return mTypeIconFilename;
    }

    public String getDescriptionFilename() {
        return mDescriptionFilename;
    }

    public String getImageFilename() {
        return mImageFilename;
    }

    public void addUsage(Ingredient ingredient, double quantity) {
        mUsages.put(ingredient, quantity);
    }

    public int getNumAvailable() {
        int numAvailable = Integer.MAX_VALUE;
        for (Map.Entry<Ingredient, Double> usage : mUsages.entrySet()) {
            Ingredient ingredient = usage.getKey();
            double quantity = usage.getValue();
            numAvailable = Math.min(numAvailable,
                    (int) Math.floor((ingredient.getRemaining()) / quantity));
        }
        return numAvailable;
    }

    public void takeOffStock() {
        for (Map.Entry<Ingredient, Double> usage : mUsages.entrySet()) {
            Ingredient ingredient = usage.getKey();
            double quantity = usage.getValue();
            ingredient.addUsage(quantity);
        }
    }

    public void putBackStock() {
        for (Map.Entry<Ingredient, Double> usage : mUsages.entrySet()) {
            Ingredient ingredient = usage.getKey();
            double quantity = usage.getValue();
            ingredient.removeUsage(quantity);
        }
    }
}
