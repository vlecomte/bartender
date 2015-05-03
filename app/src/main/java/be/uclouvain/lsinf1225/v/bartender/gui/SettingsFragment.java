package be.uclouvain.lsinf1225.v.bartender.gui;

import android.app.Activity;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;


public class SettingsFragment extends Activity{
    Button chgemail;
    Button chgemdp;
    Button showPass;
    TextView logintxt;
    TextView emailtxt;
    TextView pass;
    TextView old;
    TextView newc;
    TextView confirm;
    Button valide;
    EditText olde;
    EditText newe;
    EditText confirme;
    int modetype;
    boolean isHidden;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings_fragment);
        isHidden = true;
        logintxt = (TextView) findViewById(R.id.user_name);
        emailtxt = (TextView) findViewById(R.id.email_txt);
        old = (TextView) findViewById(R.id.old_mdp);
        newc = (TextView) findViewById(R.id.new_mdp);
        confirm = (TextView) findViewById(R.id.confirm_mdp);
        valide = (Button) findViewById(R.id.valider_change_pass);
        olde = (EditText) findViewById(R.id.edit_old);
        newe = (EditText) findViewById(R.id.edit_new);
        confirme = (EditText) findViewById(R.id.edit_confirm);
        old.setVisibility(View.INVISIBLE);
        newc.setVisibility(View.INVISIBLE);
        confirm.setVisibility(View.INVISIBLE);
        valide.setVisibility(View.INVISIBLE);
        olde.setVisibility(View.INVISIBLE);
        newe.setVisibility(View.INVISIBLE);
        confirme.setVisibility(View.INVISIBLE);
        pass = (TextView) findViewById(R.id.pass_txt);
        pass.setTransformationMethod(new PasswordTransformationMethod());
        showPass = (Button) findViewById(R.id.show_pass);
        updateLayout();
        showPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isHidden) {
                    pass.setTransformationMethod(null);
                    showPass.setText(getText(R.string.hide_pass));
                    isHidden = false;
                } else {
                    pass.setTransformationMethod(new PasswordTransformationMethod());
                    showPass.setText(getText(R.string.show_pass));
                    isHidden = true;
                }
            }
        });
        chgemail = (Button) findViewById(R.id.changer_email_button);
        chgemdp = (Button) findViewById(R.id.change_pass);
        chgemdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassChange(1);
            }
        });
        chgemail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassChange(2);
            }
        });

        chgemdp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showPassChange(1);
            }
        });
        valide.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modetype==1){
                    if(MyApp.getUser().getPassword().equals(olde.getText().toString()) && newe.getText().toString().equals(confirme.getText().toString())){
                        MyApp.getUser().setPassword(newe.getText().toString());
                        hide();
                        updateLayout();
                    } else Toast.makeText(SettingsFragment.this, getText(R.string.error_change_passe), Toast.LENGTH_LONG).show();
                } else {
                    if(MyApp.getUser().getEmail().equals(olde.getText().toString()) && newe.getText().toString().equals(confirme.getText().toString())){
                        MyApp.getUser().setEmail(newe.getText().toString());
                        hide();
                        updateLayout();
                    } else Toast.makeText(SettingsFragment.this, getText(R.string.error_change_mail), Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void updateLayout(){
        logintxt.setText(getText(R.string.login_txt) +" "+ MyApp.getUser().getUsername());
        emailtxt.setText(getText(R.string.email_txt)+" "+MyApp.getUser().getEmail());
        pass.setText(MyApp.getUser().getPassword());
    }

    public void hide(){
        old.setVisibility(View.INVISIBLE);
        newc.setVisibility(View.INVISIBLE);
        confirm.setVisibility(View.INVISIBLE);
        olde.setVisibility(View.INVISIBLE);
        newe.setVisibility(View.INVISIBLE);
        confirme.setVisibility(View.INVISIBLE);
        valide.setVisibility(View.INVISIBLE);
    }

    public void showPassChange(int mode){
           if(mode==1){
               old.setText(getText(R.string.old_mdp));
               olde.setTransformationMethod(new PasswordTransformationMethod());
               newc.setText(getText(R.string.new_mdp));
               newe.setTransformationMethod(new PasswordTransformationMethod());
               confirm.setText(getText(R.string.confirmation_mdp));
               confirme.setTransformationMethod(new PasswordTransformationMethod());
               modetype=1;
           } else {
               old.setText(getText(R.string.old_email));
               olde.setTransformationMethod(null);
               newc.setText(getText(R.string.new_email));
               newe.setTransformationMethod(null);
               confirm.setText(getText(R.string.confirmation_email));
               confirme.setTransformationMethod(null);
               modetype=2;
           }
        old.setVisibility(View.VISIBLE);
        newc.setVisibility(View.VISIBLE);
        confirm.setVisibility(View.VISIBLE);
        olde.setVisibility(View.VISIBLE);
        olde.setText("");
        newe.setVisibility(View.VISIBLE);
        newe.setText("");
        confirme.setVisibility(View.VISIBLE);
        confirme.setText("");
        valide.setVisibility(View.VISIBLE);
    }

}
