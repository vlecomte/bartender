package be.uclouvain.lsinf1225.v.bartender;

import android.app.Activity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


/**
 * A login screen that switches to a register screen if the username does not exist yet.
 */
public class LoginActivity extends Activity {

    /**
     * A dummy authentication store containing known user names and passwords.
     * TODO: Remove after connecting to a real authentication system.
     */
    private static final String[] DUMMY_CREDENTIALS = new String[]{
            "vlecomte:hello", "slardinois:world"
    };

    // Login fields.
    private EditText mUsernameView, mPasswordView;
    // Register fields.
    private EditText mConfirmPasswordView, mEmailView;
    // Buttons.
    private Button mSignInButton, mRegisterButton;
    // Whether the registering part is shown.
    private boolean registerShown = false;

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
        registerShown = true;

        // Hide login button
        mSignInButton.setVisibility(View.GONE);

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
        registerShown = false;

        // Hide additional fields and register button.
        mRegisterButton.setVisibility(View.GONE);
        mEmailView.setVisibility(View.GONE);
        mConfirmPasswordView.setVisibility(View.GONE);

        // Display login button.
        mSignInButton.setVisibility(View.VISIBLE);

        // Change focus to the username field.
        mUsernameView.requestFocus();
    }

    @Override
    public void finish() {
        if (registerShown) {
            hideRegister();
        } else {
            super.finish();
        }
    }


    /**
     * Attempts to sign in or register the account specified by the login form.
     * If some of the fields are invalid, an error is reported and no actual attempt is made.
     */
    public void attemptLogin() {

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
        if (!isUsernameValid(username)) {
            reportError(mUsernameView, getString(R.string.error_invalid_username));
            return;
        }
        if (!isPasswordValid(password)) {
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
        if (!isUsernameValid(username)) {
            reportError(mUsernameView, getString(R.string.error_invalid_username));
            return;
        }
        if (!isPasswordValid(password)) {
            reportError(mPasswordView, getString(R.string.error_invalid_password));
            return;
        }
        if (!isEmailValid(email)) {
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
     * @param username The username of the account to be accessed.
     * @param password The password to be checked.
     */
    private void userLogin(String username, String password) {

        boolean exists = false, success = false;
        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(username)) {
                exists = true;
                success = pieces[1].equals(password);
            }
        }

        if (exists) {
            // TODO: Switch to main menu
            if (success) Toast.makeText(this, "Logging in.", Toast.LENGTH_SHORT).show();
            else reportError(mPasswordView, getString(R.string.error_incorrect_password));
        } else {
            showRegister();
        }
    }

    /**
     * Attempts to register a new user with the specified credentials.
     * If the username already exists, an error is reported.
     * Otherwise, we switch to the main menu.
     * @param username The username of the account to be created.
     * @param password The password to be associated with the account.
     * @param email The email address to be associated with the account.
     */
    private void userRegister(String username, String password, String email) {
        // TODO: Register user
        Toast.makeText(this, "Registering ("+username+", "+password+", "+email+").",
                Toast.LENGTH_SHORT).show();
        // TODO: Switch to main menu
    }

    /**
     * Checks that a username has a valid format.
     * @param username The username to be checked.
     * @return true if the username is valid, false otherwise.
     */
    private boolean isUsernameValid(String username) {
        return username.length() >= 3 && username.length() <= 50;
    }

    /**
     * Checks that a password has a valid format.
     * @param password The password to be checked.
     * @return true if the password is valid, false otherwise.
     */
    private boolean isPasswordValid(String password) {
        return password.length() >= 5 && password.length() <= 50;
    }

    /**
     * Checks that an email address has a valid format
     * @param email The email address to be checked.
     * @return true if the email address is valid, false otherwise.
     */
    private boolean isEmailValid(String email) {
        // TODO: Find out an easy way to check an email
        return email.contains("@");
    }

    /**
     * Reports an error on a field and moves the focus to it.
     * @param field The field that has caused an error.
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



