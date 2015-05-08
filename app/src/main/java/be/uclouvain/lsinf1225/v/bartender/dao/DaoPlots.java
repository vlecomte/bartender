package be.uclouvain.lsinf1225.v.bartender.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Pair;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import be.uclouvain.lsinf1225.v.bartender.model.Product;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

public class DaoPlots {
    public static Map<String, Double> getTurnoverByDate() {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.rawQuery("SELECT SUM(p."+COL_PRICE+") AS turnover"
                        +", date(o."+COL_DATE_PAID+",'unixepoch','localtime') AS the_date"
                +" FROM "+TABLE_ORDER+" o, "+TABLE_DETAIL+" d, "+TABLE_PRODUCT+" p"
                +" WHERE o."+COL_DATE_PAID+" IS NOT NULL"
                        +" AND d."+COL_ORDER_NUM+" = o."+COL_ORDER_NUM
                        +" AND d."+COL_PRODUCT_NAME+" = p."+COL_PRODUCT_NAME
                +" GROUP BY the_date",
                new String[]{});

        Map<String, Double> turnoverByDate = new HashMap<>();
        while (c.moveToNext()) {
            String date = c.getString(c.getColumnIndex("the_date"));
            double turnover = c.getDouble(c.getColumnIndex("turnover"));
            turnoverByDate.put(date, turnover);
        }
        c.close();
        return turnoverByDate;
    }

    public static List<Double> getTurnoverInRange(Calendar dateBegin, Calendar dateEnd) {
        Map<String, Double> turnoverByDate = getTurnoverByDate();
        ArrayList<Double> turnovers = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd", Locale.US);

        for (; !dateBegin.after(dateEnd); dateBegin.add(Calendar.DATE, 1)) {
            Double turnover = turnoverByDate.get(sdf.format(dateBegin.getTime()));
            //Log.w("here", sdf.format(dateBegin.getTime()));
            if (turnover == null) turnover = 0.0;
            turnovers.add(turnover);
        }
        return turnovers;
    }

    public static List<Pair<Product, Integer>> getProductsByPopularity(Calendar dateBegin,
                                                                       Calendar dateEnd) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.rawQuery("SELECT p."+COL_PRODUCT_NAME+", COUNT(d."+COL_ID+") AS the_count"
                +" FROM "+TABLE_PRODUCT+" p, "+TABLE_DETAIL+" d"
                +" WHERE d."+COL_PRODUCT_NAME+" = p."+COL_PRODUCT_NAME
                        +" AND ? <= d."+COL_DATE_ADDED+" AND d."+COL_DATE_ADDED+" <= ?"
                +" GROUP BY p."+COL_PRODUCT_NAME
                +" ORDER BY the_count DESC",
                new String[]{""+(dateBegin.getTimeInMillis() / 1000),
                        ""+(dateEnd.getTimeInMillis() / 1000)});

        List<Pair<Product, Integer>> productsByPopularity = new ArrayList<>();
        while (c.moveToNext()) {
            String productName = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
            Product product = DaoProduct.getByName(productName);
            int popularity = c.getInt(c.getColumnIndex("the_count"));

            productsByPopularity.add(new Pair<>(product, popularity));
        }
        c.close();
        return productsByPopularity;
    }

    public static List<Pair<String, Double>> getCustomersByTurnover(Calendar dateBegin,
                                                                    Calendar dateEnd) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.rawQuery("SELECT u." + COL_USERNAME + ", SUM(p." + COL_PRICE + ") AS turnover"
                        + " FROM " + TABLE_USER + " u, " + TABLE_ORDER + " o"
                        + ", " + TABLE_DETAIL + " d, " + TABLE_PRODUCT + " p"
                        + " WHERE o." + COL_DATE_PAID + " IS NOT NULL"
                        + " AND o." + COL_CUSTOMER_USERNAME + " = u." + COL_USERNAME
                        + " AND d." + COL_ORDER_NUM + " = o." + COL_ORDER_NUM
                        + " AND d." + COL_PRODUCT_NAME + " = p." + COL_PRODUCT_NAME
                        + " AND ? <= o." + COL_DATE_PAID + " AND o." + COL_DATE_PAID + " <= ?"
                        + " GROUP BY u." + COL_USERNAME
                        + " ORDER BY turnover DESC",
                new String[]{"" + (dateBegin.getTimeInMillis() / 1000),
                        "" + (dateEnd.getTimeInMillis() / 1000)});

        List<Pair<String, Double>> clientsByTurnover = new ArrayList<>();
        while (c.moveToNext()) {
            String username = c.getString(c.getColumnIndex(COL_USERNAME));
            double turnover = c.getDouble(c.getColumnIndex("turnover"));
            clientsByTurnover.add(new Pair<>(username, turnover));
        }
        c.close();
        return clientsByTurnover;
    }

    public static List<Pair<String, Integer>> getWaiterByServices(Calendar dateBegin,
                                                                  Calendar dateEnd) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.rawQuery("SELECT u."+COL_USERNAME+", COUNT(d."+COL_ID+") AS the_count"
                        +" FROM "+TABLE_USER+" u, "+TABLE_DETAIL+" d"
                        +" WHERE d."+COL_WAITER_USERNAME+" = u."+COL_USERNAME
                        +" AND ? <= d."+COL_DATE_ADDED+" AND d."+COL_DATE_ADDED+" <= ?"
                        +" GROUP BY u."+COL_USERNAME
                        +" ORDER BY the_count DESC",
                new String[]{""+(dateBegin.getTimeInMillis() / 1000),
                        ""+(dateEnd.getTimeInMillis() / 1000)});

        List<Pair<String, Integer>> waiterByServices = new ArrayList<>();
        while (c.moveToNext()) {
            String username = c.getString(c.getColumnIndex(COL_USERNAME));
            int services = c.getInt(c.getColumnIndex("the_count"));

            waiterByServices.add(new Pair<>(username, services));
        }
        c.close();
        return waiterByServices;
    }
}
