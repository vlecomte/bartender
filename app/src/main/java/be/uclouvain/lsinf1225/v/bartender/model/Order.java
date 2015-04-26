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

    public Order(int orderNum, String customerUsername, int tableNum, List<Detail> details) {
        mOrderNum = orderNum;
        mCustomerUsername = customerUsername;
        mTableNum = tableNum;
        mDetails = details;
        mTotal = computeTotal();
    }

    public Order(int orderNum, String customerUsername, int tableNum) {
        this(orderNum, customerUsername, tableNum, new ArrayList<Detail>(){});
    }

    private double computeTotal() {
        double total = 0.0;
        for (Detail detail : mDetails) {
            total += detail.getProduct().getPrice();
        }
        return total;
    }

    public int getOrderNum() {
        return mOrderNum;
    }

    public String getCustomerUsername() {
        return mCustomerUsername;
    }

    public int getTableNum() {
        return mTableNum;
    }

    public double getTotal() {
        return mTotal;
    }


    public void addBasket(Customer.Basket basket) {
        DaoDetail.create(mOrderNum, basket);
        mDetails = DaoDetail.getFromOrder(mOrderNum);
    }
}
