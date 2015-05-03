package be.uclouvain.lsinf1225.v.bartender.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;

public class MainActivity extends FragmentActivity {
    private static final int NUM_ITEMS_CUSTOMER = 3, NUM_ITEMS_WAITER = 4, NUM_ITEMS_ADMIN = 6;
    private static final int POS_MENU = 0, POS_BASKET = 1, POS_BILL = 2, POS_TO_SERVE = 3,
            POS_STOCK = 4, POS_GRAPHS = 5;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabbed);

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        ViewPager pager = (ViewPager) findViewById(R.id.pager);
        pager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle presses on the action bar items.
        Intent intent;
        switch (item.getItemId()) {
            case R.id.action_logout:
                MyApp.logoutUser();
                intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
                return true;

            case R.id.action_settings:
                intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public class MyAdapter extends FragmentPagerAdapter {
        public MyAdapter(FragmentManager fm) {
            super(fm);
        }
        @Override
        public int getCount() {
            if (MyApp.isWaiter()) return NUM_ITEMS_WAITER;
            else return NUM_ITEMS_CUSTOMER;
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case POS_MENU:
                    return new MenuFragment();
                case POS_BASKET:
                    return new BasketFragment();
                case POS_BILL:
                    // TODO: if user is a waiter, show all bills instead
                    return new MyBillFragment();
                case POS_TO_SERVE:
                    return new ToServeFragment();
                default:
                    throw new IllegalArgumentException("Position out of bounds");
            }
        }

        @Override
        public String getPageTitle(int position) {
            switch (position) {
                case POS_MENU:
                    return getString(R.string.tab_menu);
                case POS_BASKET:
                    return getString(R.string.tab_basket);
                case POS_BILL:
                    if (MyApp.isWaiter()) return getString(R.string.tab_bills);
                    else return getString(R.string.tab_my_bill);
                case POS_TO_SERVE:
                    return getString(R.string.tab_to_serve);
                default:
                    throw new IllegalArgumentException("Position out of bounds");
            }
        }
    }
}
