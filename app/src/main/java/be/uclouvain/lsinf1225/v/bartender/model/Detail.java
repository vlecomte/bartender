package be.uclouvain.lsinf1225.v.bartender.model;


import java.util.Date;

public class Detail {
    private int mDetailId;
    private Product mProduct;
    private Date mDateAdded;

    public Detail(int detailId, Product product, Date dateAdded) {
        mDetailId = detailId;
        mProduct = product;
        mDateAdded = dateAdded;
    }

    public int getDetailId() {
        return mDetailId;
    }

    public Product getProduct() {
        return mProduct;
    }

    public Date getDateAdded() {
        return mDateAdded;
    }
}
