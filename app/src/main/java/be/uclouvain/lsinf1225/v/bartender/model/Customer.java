package be.uclouvain.lsinf1225.v.bartender.model;

import java.util.HashMap;
import java.util.Map;

/**
 * A customer who can order drinks.
 */
public class Customer extends User {
    private Order mCurrentOrder;
    protected Map<Product, Integer> mBasket;

    public Customer(String username, String password, String email) {
        super(username, password, email);
        mBasket = new HashMap<>();
    }

    public void setCurrentOrder(Order order) {
        mCurrentOrder = order;
    }

    public Map<Product, Integer> getBasket() {
        return mBasket;
    }

    public int getNumInBasket(Product product) {
        if (mBasket.containsKey(product)) {
            return mBasket.get(product);
        } else {
            return 0;
        }
    }

    public void addToBasket(Product product) {
        if (mBasket.containsKey(product)) {
            mBasket.put(product, mBasket.get(product)+1);
        } else {
            mBasket.put(product, 1);
        }
        product.takeOffStock();
    }

    public void removeFromBasket(Product product) {
        if (!mBasket.containsKey(product))
            throw new IllegalArgumentException("The product to be removed is not in the basket.");
        int currentNumber = mBasket.get(product);
        if (currentNumber == 1) {
            mBasket.remove(product);
        } else {
            mBasket.put(product, currentNumber-1);
        }
        product.putBackStock();
    }

    public void clearBasket() {
        mBasket.clear();
    }
}
