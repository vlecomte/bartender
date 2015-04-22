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
