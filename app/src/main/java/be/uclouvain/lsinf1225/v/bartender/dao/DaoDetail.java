package be.uclouvain.lsinf1225.v.bartender.dao;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import be.uclouvain.lsinf1225.v.bartender.MyApp;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;

public class DaoDetail {
    public static Detail[] getInOrder(int orderNum) {
        SQLiteDatabase db = MyApp.getReadableDb();
        Cursor c = db.query(TABLE_DETAIL,
                new String[]{COL_PRODUCT_NAME},
                COL_ORDER_NUM+" = ?", new String[]{""+orderNum},
                null, null, null);
        c.moveToFirst();

        int numDetails = c.getCount();
        Detail[] details = new Detail[numDetails];

        for (int i = 0; i < numDetails; i++) {
            String productName = c.getString(c.getColumnIndex(COL_PRODUCT_NAME));
            details[i] = new Detail(DaoProduct.getByName(productName));
        }

        return details;
    }
}
