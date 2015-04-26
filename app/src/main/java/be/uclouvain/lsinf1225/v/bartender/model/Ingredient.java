package be.uclouvain.lsinf1225.v.bartender.model;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoIngredient;

public class Ingredient {

    private String mName, mDisplayName;
    private double mCurrentStock, mCurrentUsage;
    // Those are Double because they can be null
    private Double mCriticalStock, mMaxStock;
    private String mUnits;


    public Ingredient(String name, String displayName, double currentStock, Double criticalStock,
                      Double maxStock, String units) {
        mName = name;
        mDisplayName = displayName;
        mCurrentStock = currentStock;
        mCurrentUsage = 0.0;
        mCriticalStock = criticalStock;
        mMaxStock = maxStock;
        mUnits = units;
    }

    public String getName() {
        return mName;
    }

    public String getDisplayName() {
        return mDisplayName;
    }

    public double getCurrent() {
        return mCurrentStock;
    }

    public double getCurrentUsage() {
        return mCurrentUsage;
    }

    public double getRemaining() {
        return mCurrentStock - mCurrentUsage;
    }

    public Double getCritical() {
        return mCriticalStock;
    }

    public Double getMax() {
        return mMaxStock;
    }

    public String getUnits() {
        return mUnits;
    }


    public void addUsage(double quantity) {
        mCurrentUsage += quantity;
    }

    public void removeUsage(double quantity) {
        mCurrentUsage -= quantity;
    }

    public void applyUsage() {
        mCurrentStock -= mCurrentUsage;
        mCurrentUsage = 0;
    }

    public void refreshCurrent(double stock) {
        mCurrentStock = stock;
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
