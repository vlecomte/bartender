package be.uclouvain.lsinf1225.v.bartender.model;

import java.util.HashMap;
import java.util.Map;

import be.uclouvain.lsinf1225.v.bartender.MyApp;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoUser;

/**
 * Any user of the application.
 */
public abstract class User {

    private final String mUsername;
    private String mPassword;
    private String mEmail;

    private Map<Product, Integer> mBasket = new HashMap<>();

    protected User(String username, String password, String email) {
        mUsername = username;
        mPassword = password;
        mEmail = email;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getPassword() {
        return mPassword;
    }

    public String getEmail() {
        return mEmail;
    }

    public Map<Product, Integer> getBasket() {
        return mBasket;
    }

    public void addToBasket(Product product) {
        if (mBasket.containsKey(product)) {
            mBasket.put(product, mBasket.get(product)+1);
        } else {
            mBasket.put(product, 1);
        }
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
    }

    public void clearBasket() {
        mBasket.clear();
    }

    public void setPassword(String password) {
        DaoUser.setPassword(mUsername, mPassword, password);
        MyApp.writeUserInPreferences();
        mPassword = password;
    }

    public void setEmail(String email) {
        DaoUser.setEmail(mUsername, mPassword, email);
        mEmail = email;
    }
}
