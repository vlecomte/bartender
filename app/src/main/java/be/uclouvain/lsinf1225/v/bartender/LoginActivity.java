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
 * A login screen that offers login via username/password.
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
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
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

    private void userLogin(String email, String password) {

        boolean exists = false, success = false;
        for (String credential : DUMMY_CREDENTIALS) {
            String[] pieces = credential.split(":");
            if (pieces[0].equals(email)) {
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

    private void userRegister(String username, String password, String email) {
        // TODO: Register user
        Toast.makeText(this, "Registering ("+username+", "+password+", "+email+").",
                Toast.LENGTH_SHORT).show();
        // TODO: Switch to main menu
    }

    private boolean isUsernameValid(String username) {
        return username.length() >= 3 && username.length() <= 50;
    }

    private boolean isPasswordValid(String password) {
        return password.length() >= 5 && password.length() <= 50;
    }

    private boolean isEmailValid(String email) {
        // TODO: Find out an easy way to check an email
        return email.contains("@");
    }

    private void reportError(EditText field, String message) {
        field.setError(message);
        field.requestFocus();
    }

    /**
     * Resets errors on all fields.
     */
    private void resetErrors() {
        mUsernameView.setError(null);
        mPasswordView.setError(null);
        mConfirmPasswordView.setError(null);
        mEmailView.setError(null);
    }
}



