package be.uclouvain.lsinf1225.v.bartender.model;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Order {
    private int mOrderNum;
    private String mCustomerUsername;
    private int mTableNum;
    private List<Detail> mDetails;
    private double mTotal;

    public Order(int orderNum, String customerUsername, int tableNum, Detail[] details) {
        mOrderNum = orderNum;
        mCustomerUsername = customerUsername;
        mTableNum = tableNum;
        mDetails = new ArrayList<>(Arrays.asList(details));
        mTotal = computeTotal();
    }

    public Order(int orderNum, String customerUsername, int tableNum) {
        this(orderNum, customerUsername, tableNum, new Detail[]{});
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

    public Detail[] getDetails() {
        return (Detail[]) mDetails.toArray();
    }

    public double getTotal() {
        return mTotal;
    }
}
