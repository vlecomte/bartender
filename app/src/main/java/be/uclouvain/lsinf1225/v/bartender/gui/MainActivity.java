package be.uclouvain.lsinf1225.v.bartender.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.Pair;
import android.view.Menu;
import android.view.MenuItem;

import java.util.GregorianCalendar;
import java.util.List;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoPlots;
import be.uclouvain.lsinf1225.v.bartender.model.Product;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.util.Refreshable;

public class MainActivity extends FragmentActivity {
    private static final int NUM_ITEMS_CUSTOMER = 3, NUM_ITEMS_WAITER = 4, NUM_ITEMS_ADMIN = 6; //IL FAUT METTRE 6!!!!!!
    private static final int POS_MENU = 0, POS_BASKET = 1, POS_BILL = 2, POS_TO_SERVE = 3,
            POS_STOCK = 4, POS_GRAPHS = 5;
    private ViewPager mPager;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager());
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setAdapter(adapter);

        // TODO: Test to see if it crashes, to remove
        List<Double> turnoverInRange = DaoPlots.getTurnoverInRange(
                new GregorianCalendar(2015, 4, 1),
                new GregorianCalendar(2015, 4, 10)
        );
        for (double turnover : turnoverInRange) {
            Log.d("hey", ""+turnover);
        }
        List<Pair<Product, Integer>> productsByPopularity = DaoPlots.getProductsByPopularity(
                new GregorianCalendar(2015, 4, 1),
                new GregorianCalendar(2015, 4, 10)
        );
        for (Pair<Product, Integer> entry : productsByPopularity) {
            Log.d("hey", entry.first.getDisplayName() + ": " + entry.second);
        }
        List<Pair<String, Double>> clientsByTurnover = DaoPlots.getCustomersByTurnover(
                new GregorianCalendar(2015, 4, 1),
                new GregorianCalendar(2015, 4, 10)
        );
        for (Pair<String, Double> entry : clientsByTurnover) {
            Log.d("hey", entry.first + ": " + entry.second);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu items for use in the action bar.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        if (MyApp.isAdmin()) {
            menu.findItem(R.id.action_manage_users).setVisible(true);
        }
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

            case R.id.action_manage_users:
                intent = new Intent(this, UsersActivity.class);
                startActivity(intent);
                return true;

            case R.id.action_refresh:
                Fragment fragment = getCurrentFragment();
                if (fragment instanceof Refreshable) {
                    ((Refreshable) fragment).refresh();
                }
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
            if (MyApp.isAdmin())return NUM_ITEMS_ADMIN;
            else if(MyApp.isWaiter()) return NUM_ITEMS_WAITER;
             else return NUM_ITEMS_CUSTOMER; //NUM_ITEMS_CUSTOMER
        }

        @Override
        public Fragment getItem(int position) {
            switch(position) {
                case POS_MENU:
                    return new MenuFragment();
                case POS_BASKET:
                    return new BasketFragment();
                case POS_BILL:
                    if (MyApp.isWaiter()) return new OrdersFragment();
                    else return new MyBillFragment();
                case POS_TO_SERVE:
                    return new ToServeFragment();
                case POS_STOCK:
                    return new StockFragment();
                case POS_GRAPHS:
                    return new GraphsFragment();
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
                case POS_STOCK:
                    return getString(R.string.title_fragment_stock);
                case POS_GRAPHS:
                    return "Graphs";
                default:
                    throw new IllegalArgumentException("Position out of bounds");
            }
        }
    }

    private Fragment getCurrentFragment() {
        return getSupportFragmentManager().findFragmentByTag("android:switcher:" + R.id.pager + ":"
                + mPager.getCurrentItem());
    }
}
