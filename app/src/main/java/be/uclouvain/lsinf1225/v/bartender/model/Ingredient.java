package be.uclouvain.lsinf1225.v.bartender.model;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoIngredient;

public class Ingredient {
    private String mName, mDisplayName;
    private double mCurrentStock;
    // Those are Double because they can be null
    private Double mCriticalStock, mMaxStock;
    private String mUnits;

    public Ingredient(String name, String displayName, double currentStock, Double criticalStock,
                      Double maxStock, String units) {
        mName = name;
        mDisplayName = displayName;
        mCurrentStock = currentStock;
        mCriticalStock = criticalStock;
        mMaxStock = maxStock;
        mUnits = units;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public double getCurrent() {
        return mCurrentStock;
    }

    public Double getCritical() {
        return mCriticalStock;
    }

    public Double getMax() {
        return mMaxStock;
    }

    public void add(double quantity) {
        mCurrentStock += quantity;
    }

    public void remove(double quantity) {
        mCurrentStock -= quantity;
    }

    public void setCurrent(double stock) {
        DaoIngredient.setCurrent(mName, stock);
        mCurrentStock = stock;
    }

    public void setCritical(Double stock) {
        DaoIngredient.setCritical(mName, stock);
        mCriticalStock = stock;
    }

    public void setMax(Double stock) {
        DaoIngredient.setMax(mName, stock);
        mMaxStock = stock;
    }
}
