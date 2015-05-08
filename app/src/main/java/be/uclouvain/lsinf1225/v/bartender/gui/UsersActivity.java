package be.uclouvain.lsinf1225.v.bartender.gui;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.LinearLayout;

import java.util.Map;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoUser;
import be.uclouvain.lsinf1225.v.bartender.util.Refreshable;
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;

public class UsersActivity extends FragmentActivity implements Refreshable {
    private LinearLayout mUsersTable;
    private TableFiller mFiller;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);

        mUsersTable = (LinearLayout) findViewById(R.id.users_content);
        mFiller = new TableFiller(mUsersTable, getLayoutInflater());

        refresh();
    }

    @Override
    public void refresh() {
        mUsersTable.removeAllViews();
        Map<String, String> rankByUsername = DaoUser.getAllRanks();
        mFiller.fillUsers(rankByUsername.entrySet(), getSupportFragmentManager(), this);
    }
}
