package be.uclouvain.lsinf1225.v.bartender.gui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.text.method.PasswordTransformationMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;


public class SettingsFragment extends Fragment{
    Button chgemail;
    Button showPass;
    boolean isHidden;
    TextView logintxt;
    TextView emailtxt;
    TextView pass;
    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_settings_fragment, container, false);
        isHidden = true;
        logintxt = (TextView) view.findViewById(R.id.user_name);
        emailtxt = (TextView) view.findViewById(R.id.email_txt);
        pass = (TextView) view.findViewById(R.id.pass_txt);
        pass.setTransformationMethod(new PasswordTransformationMethod());
        showPass = (Button) view.findViewById(R.id.show_pass);
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
        return view;
    }

    public void updateLayout(){
        logintxt.setText(getText(R.string.login_txt) +" "+ MyApp.getUser().getUsername());
        emailtxt.setText(getText(R.string.email_txt)+" "+MyApp.getUser().getEmail());
        pass.setText(MyApp.getUser().getPassword());
    }

}
