package be.uclouvain.lsinf1225.v.bartender.model;

import java.util.HashMap;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoOrder;

/**
 * A customer who can order drinks.
 */
public class Customer extends User {
    private Order mCurrentOrder;
    protected Basket mBasket;

    public static class Basket extends HashMap<Product, Integer> {

        public int getOccur(Product product) {
            if (containsKey(product)) {
                return get(product);
            } else {
                return 0;
            }
        }

        public void addOne(Product product) {
            if (containsKey(product)) {
                put(product, get(product) + 1);
            } else {
                put(product, 1);
            }
            product.takeOffStock();
        }

        public void removeOne(Product product) {
            int currentNumber = get(product);
            if (currentNumber == 1) {
                remove(product);
            } else {
                put(product, currentNumber - 1);
            }
            product.putBackStock();
        }
    }

    public Customer(String username, String password, String email) {
        super(username, password, email);
        mCurrentOrder = null;
        mBasket = new Basket();
    }

    public Order getCurrentOrder() {
        if (mCurrentOrder == null) {
            mCurrentOrder = DaoOrder.getOpen(getUsername());
        } else if (DaoOrder.isPaid(mCurrentOrder.getOrderNum())) {
            mCurrentOrder = null;
        }
        return mCurrentOrder;
    }

    public boolean hasCurrentOrder() {
        return getCurrentOrder() != null;
    }

    public void openOrder(int tableNum) {
        mCurrentOrder = DaoOrder.create(getUsername(), tableNum);
    }

    public Basket getBasket() {
        return mBasket;
    }

    public int getNumInBasket(Product product) {
        return mBasket.getOccur(product);
    }

    public void addToBasket(Product product) {
        mBasket.addOne(product);
    }

    public void removeFromBasket(Product product) {
        mBasket.removeOne(product);
    }

    // Assumes a current order exists!!
    public void confirmBasket() {
        mCurrentOrder.addBasket(mBasket);
        mBasket.clear();
    }
}
