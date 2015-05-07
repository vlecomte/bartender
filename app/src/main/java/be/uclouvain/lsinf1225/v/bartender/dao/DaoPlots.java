package be.uclouvain.lsinf1225.v.bartender.dao;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.HashMap;
import java.util.Map;

import be.uclouvain.lsinf1225.v.bartender.util.MyApp;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

public class DaoPlots {
    public static Map<String, Double> getTurnoverByDate() {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.rawQuery("SELECT SUM(p."+COL_PRICE+") AS turnover"
                        +", date(o."+COL_DATE_PAID+",'unixepoch') AS the_date"
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
}
