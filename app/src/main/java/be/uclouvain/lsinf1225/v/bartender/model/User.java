package be.uclouvain.lsinf1225.v.bartender.model;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoUser;

/**
 * Any user of the application.
 */
public abstract class User {

    private String mUsername, mPassword, mEmail, mLanguage;

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

    public void setEmail(String email) {
        mEmail = email;
        DaoUser.setEmail(mUsername, mPassword, email);
    }

    public void setLanguage(String language) {
        mLanguage = language;
        DaoUser.setLanguage(mUsername, mPassword, language);
    }
}
