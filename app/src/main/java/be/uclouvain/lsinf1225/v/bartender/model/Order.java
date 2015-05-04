package be.uclouvain.lsinf1225.v.bartender.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoDetail;

public class Order {
    private int mOrderNum;
    private String mCustomerUsername;
    private int mTableNum;
    private List<Detail> mDetails;
    private double mTotal;

    public Order(int orderNum, String customerUsername, int tableNum) {
        mOrderNum = orderNum;
        mCustomerUsername = customerUsername;
        mTableNum = tableNum;
        mDetails = new ArrayList<>();
        refreshTotal();
    }

    private void refreshTotal() {
        mTotal = 0.0;
        for (Detail detail : mDetails) {
            mTotal += detail.getProduct().getPrice();
        }
    }

    public int getOrderNum() {
        return mOrderNum;
    }

    public boolean hasCustomer() {
        return mCustomerUsername != null;
    }

    public String getCustomerUsername() {
        return mCustomerUsername;
    }

    public int getTableNum() {
        return mTableNum;
    }

    public List<Detail> getDetails() {
        return mDetails;
    }

    public void setDetails(List<Detail> details) {
        mDetails = details;
        refreshTotal();
    }

    public double getTotal() {
        return mTotal;
    }


    public void addBasket(Customer.Basket basket) {
        DaoDetail.create(mOrderNum, basket);
        mDetails = DaoDetail.getFromOrder(mOrderNum);
    }
}
