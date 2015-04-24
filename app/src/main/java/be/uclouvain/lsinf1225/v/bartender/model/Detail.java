package be.uclouvain.lsinf1225.v.bartender.model;


public class Detail {
    private Product mProduct;
    private int mDetailId;

    public Detail(int detailId, Product product) {
        mProduct = product;
        mDetailId = detailId;
    }

    public Product getProduct() {
        return mProduct;
    }

    public int getDetailId() {
        return mDetailId;
    }
}
