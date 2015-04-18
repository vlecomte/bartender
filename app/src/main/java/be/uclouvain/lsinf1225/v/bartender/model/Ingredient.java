package be.uclouvain.lsinf1225.v.bartender.model;

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
        // TODO: Call DAO
        mCurrentStock = stock;
    }

    public void setCriticalStock(double stock) {
        // TODO
    }

    // TODO: setMaxStock
}
