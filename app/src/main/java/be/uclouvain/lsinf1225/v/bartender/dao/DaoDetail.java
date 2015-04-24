package be.uclouvain.lsinf1225.v.bartender.dao;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import be.uclouvain.lsinf1225.v.bartender.MyApp;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;

public class DaoDetail {
    private static Map<Integer, Detail> sDetailById;

    public static Detail[] getInOrder(int orderNum) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_DETAIL,
                new String[]{COL_ID, COL_PRODUCT_NAME},
                COL_ORDER_NUM+" = ?", new String[]{""+orderNum},
                null, null, null);
        c.moveToFirst();

        int numDetails = c.getCount();
        Detail[] details = new Detail[numDetails];

        for (int i = 0; i < numDetails; i++) {
            int detailId = c.getInt(c.getColumnIndex(COL_ID));
            String productName = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
            if (sDetailById.containsKey(detailId)) {
                details[i] = sDetailById.get(detailId);
            } else {
                details[i] = new Detail(detailId, DaoProduct.getByName(productName));
                sDetailById.put(detailId, details[i]);
            }
        }

        return details;
    }

    public static Map<Integer, List<Detail>> getOpenByTable() {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.rawQuery("SELECT d."+COL_ID+", d."+COL_ID+", o."+COL_TABLE_NUM
                +" FROM "+TABLE_DETAIL+" d, "+TABLE_ORDER+" o"
                +" WHERE d."+COL_DATE_DELIVERED+" = NULL",
                new String[]{});
        c.moveToFirst();

        int numDetails = c.getCount();
        Map<Integer, List<Detail>> openDetailsByTable = new HashMap<>();

        for (int i = 0; i < numDetails; i++) {
            int detailId = c.getInt(c.getColumnIndex(COL_ID));
            String productName = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
            Detail detail;
            if (sDetailById.containsKey(detailId)) {
                detail = sDetailById.get(detailId);
            } else {
                detail = new Detail(detailId, DaoProduct.getByName(COL_PRODUCT_NAME));
                sDetailById.put(detailId, detail);
            }
            int tableNum = c.getInt(c.getColumnIndex(COL_TABLE_NUM));

            if (!openDetailsByTable.containsKey(tableNum)) {
                openDetailsByTable.put(tableNum, new ArrayList<Detail>());
            }
            openDetailsByTable.get(tableNum).add(detail);
        }
        c.close();

        return openDetailsByTable;
    }
}
