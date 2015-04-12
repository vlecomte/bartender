package be.uclouvain.lsinf1225.v.bartender.model;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoUser;

/**
 * Any user of the application.
 */
public abstract class User {

    private final String mUsername;
    private String mPassword;
    private String mEmail;
    private String mLanguage;

    protected User(String username, String password, String email, String language) {
        mUsername = username;
        mPassword = password;
        mEmail = email;
        mLanguage = language;
    }

    public String getUsername() {
        return mUsername;
    }

    public String getEmail() {
        return mEmail;
    }

    public String getLanguage() {
        return mLanguage;
    }

    public void setPassword(String password) {
        DaoUser.setPassword(mUsername, mPassword, password);
        mPassword = password;
    }

    public void setEmail(String email) {
        DaoUser.setEmail(mUsername, mPassword, email);
        mEmail = email;
    }

    public void setLanguage(String language) {
        DaoUser.setLanguage(mUsername, mPassword, language);
        mLanguage = language;
    }
}
