package be.uclouvain.lsinf1225.v.bartender.model;

import be.uclouvain.lsinf1225.v.bartender.dao.DaoUser;

/**
 * Any user of the application.
 */
public class User {

    private String mUsername, mEmail, mLanguage;

    public User(String username, String email, String language) {
        mUsername = username;
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
        DaoUser.setEmail(mUsername, email);
    }

    public void setLanguage(String language) {
        mLanguage = language;
        DaoUser.setLanguage(mLanguage, language);
    }
}
