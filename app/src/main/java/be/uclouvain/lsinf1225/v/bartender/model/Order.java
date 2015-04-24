package be.uclouvain.lsinf1225.v.bartender.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order {
    private int mOrderNum;
    private Customer mCustomer;
    private int mTableNum;
    private List<Detail> mDetails;
    private double mTotal;

    public Order(int orderNum, Customer customer, int tableNum, Detail[] details) {
        mOrderNum = orderNum;
        mCustomer = customer;
        mTableNum = tableNum;
        mDetails = new ArrayList<>(Arrays.asList(details));
        mTotal = computeTotal();
    }

    public Order(int orderNum, Customer customer, int tableNum) {
        this(orderNum, customer, tableNum, new Detail[]{});
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

    public Customer getCustomer() {
        return mCustomer;
    }

    public int getTableNum() {
        return mTableNum;
    }

    public Detail[] getDetails() {
        return (Detail[]) mDetails.toArray();
    }

    public double getTotal() {
        return mTotal;
    }
}
