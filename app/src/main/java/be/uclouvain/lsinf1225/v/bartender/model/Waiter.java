package be.uclouvain.lsinf1225.v.bartender.model;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoOrder;

/**
 * A waiter who can serve drinks.
 */
public class Waiter extends Customer {

    public Waiter(String username, String password, String email) {
        super(username, password, email);
    }

    public void confirmBasketFor(int tableNum) {
        DaoOrder.getOpenOrCreateFor(tableNum).addBasket(mBasket);
        mBasket.clear();
    }
}
