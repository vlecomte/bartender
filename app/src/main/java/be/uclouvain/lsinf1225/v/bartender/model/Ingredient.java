package be.uclouvain.lsinf1225.v.bartender.model;

public class Ingredient {
    private String mName, mDisplayName;
    private Double mCurrentStock, mCriticalStock, mMaxStock;
    private String mUnits;

    public Ingredient(String name, String displayName, Double currentStock, Double criticalStock,
                      Double maxStock, String units) {
        mName = name;
        mDisplayName = displayName;
        mCurrentStock = currentStock;
        mCriticalStock = criticalStock;
        mMaxStock = maxStock;
        mUnits = units;
    }
}
