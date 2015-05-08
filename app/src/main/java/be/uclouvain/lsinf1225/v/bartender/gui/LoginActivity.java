package be.uclouvain.lsinf1225.v.bartender.gui;

import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoUser;
import be.uclouvain.lsinf1225.v.bartender.model.User;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Pattern;


/**
 * A login screen that switches to a register screen if the username does not exist yet.
 */
public class LoginActivity extends Activity {

    /** Valid character for a username: not an invisible or control character. */
    private static final String VALID_USERNAME_CHAR = "[^\\p{Z}\\p{C}]";
    /** Valid character for a password: not a control character. */
    private static final String VALID_PASSWORD_CHAR = "[^\\p{C}]";

    // Login fields.
    private EditText mUsernameView, mPasswordView;
    // Register fields.
    private EditText mConfirmPasswordView, mEmailView;
    // Buttons.
    private Button mSignInButton, mRegisterButton;
    // Whether the registering part is shown.
    private boolean mRegisterShown = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Set up the login form.
        mUsernameView = (EditText) findViewById(R.id.username);
        mPasswordView = (EditText) findViewById(R.id.password);
        mSignInButton = (Button) findViewById(R.id.sign_in_button);
        mSignInButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptLogin();
            }
        });

        // Set up the register form.
        mConfirmPasswordView = (EditText) findViewById(R.id.confirm_password);
        mEmailView = (EditText) findViewById(R.id.email);
        mRegisterButton = (Button) findViewById(R.id.register_button);
        mRegisterButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                attemptRegister();
            }
        });
    }

    /**
     * Shows the registering part of the form.
     */
    private void showRegister() {
        mRegisterShown = true;

        // Hide login button
        mSignInButton.setVisibility(View.GONE);

        // Change action bar title
        setTitle(getString(R.string.title_activity_register));

        // Display additional fields and register button.
        mConfirmPasswordView.setVisibility(View.VISIBLE);
        mEmailView.setVisibility(View.VISIBLE);
        mRegisterButton.setVisibility(View.VISIBLE);

        // Change focus to the next field to fill.
        mConfirmPasswordView.requestFocus();
    }

    /**
     * Hides the registering part of the form.
     */
    private void hideRegister() {
        mRegisterShown = false;

        // Hide additional fields and register button.
        mRegisterButton.setVisibility(View.GONE);
        mEmailView.setVisibility(View.GONE);
        mConfirmPasswordView.setVisibility(View.GONE);

        // Change action bar title
        setTitle(getString(R.string.title_activity_login));

        // Display login button.
        mSignInButton.setVisibility(View.VISIBLE);

        // Change focus to the username field.
        mUsernameView.requestFocus();
    }

    @Override
    public void onBackPressed() {
        if (mRegisterShown) {
            hideRegister();
        } else {
            finish();
        }
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If some of the fields are invalid, an error is reported and no actual attempt is made.
     */
    private void attemptLogin() {

        resetErrors();

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();

        // Both fields are required.
        if (TextUtils.isEmpty(username)) {
            reportError(mUsernameView, getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            reportError(mPasswordView, getString(R.string.error_field_required));
            return;
        }

        // Both fields must be valid.
        if (isUsernameInvalid(username)) {
            reportError(mUsernameView, getString(R.string.error_invalid_username));
            return;
        }
        if (isPasswordInvalid(password)) {
            reportError(mPasswordView, getString(R.string.error_invalid_password));
            return;
        }

        userLogin(username, password);
    }

    /**
     * Attempts to register the account specified by the login form.
     * If some of the fields are invalid, an error is reported and no actual attempt is made.
     */
    private void attemptRegister() {

        resetErrors();

        // Store values at the time of the login attempt.
        String username = mUsernameView.getText().toString();
        String password = mPasswordView.getText().toString();
        String confirmPassword = mConfirmPasswordView.getText().toString();
        String email = mEmailView.getText().toString();

        // All fields are required.
        if (TextUtils.isEmpty(username)) {
            reportError(mUsernameView, getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(password)) {
            reportError(mPasswordView, getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(confirmPassword)) {
            reportError(mConfirmPasswordView, getString(R.string.error_field_required));
            return;
        }
        if (TextUtils.isEmpty(email)) {
            reportError(mEmailView, getString(R.string.error_field_required));
            return;
        }

        // All fields must be valid.
        if (isUsernameInvalid(username)) {
            reportError(mUsernameView, getString(R.string.error_invalid_username));
            return;
        }
        if (isPasswordInvalid(password)) {
            reportError(mPasswordView, getString(R.string.error_invalid_password));
            return;
        }
        if (isEmailInvalid(email)) {
            reportError(mEmailView, getString(R.string.error_invalid_email));
            return;
        }

        // The passwords must match.
        if (!password.equals(confirmPassword)) {
            reportError(mConfirmPasswordView, getString(R.string.error_password_mismatch));
            return;
        }

        userRegister(username, password, email);
    }

    /**
     * Attempts to sign in with the specified credentials.
     * If the username does not exist, the form switches to register mode.
     * If the password is incorrect, an error is reported.
     * Otherwise, we switch to the main menu.
     *
     * @param username The username of the account to be accessed.
     * @param password The password to be checked.
     */
    private void userLogin(String username, String password) {

        if (DaoUser.isUsernameTaken(username)) {

            User user = DaoUser.attemptLogin(username, password);

            if (user == null) {
                reportError(mPasswordView, getString(R.string.error_incorrect_password));
            } else {
                setUserAndSwitch(user);
            }
        } else {
            showRegister();
        }
    }

    /**
     * Attempts to register a new user with the specified credentials.
     * If the username already isUsernameTaken, an error is reported.
     * Otherwise, we switch to the main menu.
     *
     * @param username The username of the account to be created.
     * @param password The password to be associated with the account.
     * @param email    The email address to be associated with the account.
     */
    private void userRegister(String username, String password, String email) {

        if (DaoUser.isUsernameTaken(username)) {
            reportError(mUsernameView, getString(R.string.error_username_taken));
            return;
        }
        if (DaoUser.isEmailTaken(email)) {
            reportError(mEmailView, getString(R.string.error_email_taken));
            return;
        }
        setUserAndSwitch(DaoUser.create(username, password, email));
    }

    private void setUserAndSwitch(User user) {
        MyApp.setUser(user);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    /**
     * Checks whether a username has an invalid format.
     *
     * @param username The username to be checked.
     * @return true if the username is invalid, false otherwise.
     */
    private boolean isUsernameInvalid(String username) {
        return !Pattern.compile("^"+VALID_USERNAME_CHAR+"{3,20}$").matcher(username).matches();
    }

    /**
     * Checks whether a password has an invalid format.
     *
     * @param password The password to be checked.
     * @return true if the password is valid, false otherwise.
     */
    private boolean isPasswordInvalid(String password) {
        return !Pattern.compile("^"+VALID_PASSWORD_CHAR+"{5,50}$").matcher(password).matches();
    }

    /**
     * Checks whether an email address has an invalid format.
     *
     * @param email The email address to be checked.
     * @return true if the email address is valid, false otherwise.
     */
    private boolean isEmailInvalid(String email) {
        return !Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    /**
     * Reports an error on a field and moves the focus to it.
     *
     * @param field   The field that has caused an error.
     * @param message The error message to be displayed.
     */
    private void reportError(EditText field, String message) {
        field.setError(message);
        field.requestFocus();
    }

    /**
     * Removes the error messages on all fields.
     */
    private void resetErrors() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);
        mEmailView.setError(null);
    }
}



