package be.uclouvain.lsinf1225.v.bartender.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;
import be.uclouvain.lsinf1225.v.bartender.model.Order;

public class TableFiller {

    private static final String TIME_FORMAT = "HH:mm", PRICE_FORMAT = "%.2f€";

    private LinearLayout mTable;
    private LayoutInflater mInflater;
    private Context mContext;

    public TableFiller(LinearLayout table, LayoutInflater inflater) {
        mTable = table;
        mInflater = inflater;
        mContext = mInflater.getContext();
    }

    public void fillBill(Order order) {
        List<Detail> details = order.getDetails();

        SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT, Locale.getDefault());

        for (Detail detail : details) {
            TableRow row = (TableRow) mInflater.inflate(R.layout.row_bill, mTable, false);

            TextView productName = (TextView) row.findViewById(R.id.elem_product_name);
            TextView time = (TextView) row.findViewById(R.id.elem_time);
            TextView price = (TextView) row.findViewById(R.id.elem_price);

            productName.setText(detail.getProduct().getDisplayName());
            time.setText(sdf.format(detail.getDateAdded()));
            price.setText(String.format(PRICE_FORMAT, detail.getProduct().getPrice()));

            mTable.addView(row);
        }

        TableRow rowTotal = (TableRow) mInflater.inflate(R.layout.row_bill_total, mTable, false);
        TextView totalPrice = (TextView) rowTotal.findViewById(R.id.elem_total_price);
        totalPrice.setText(String.format(PRICE_FORMAT, order.getTotal()));
        mTable.addView(rowTotal);
    }

    public void fillOrders(List<Order> orders) {
        for (Order order : orders) {
            TableRow row = (TableRow) mInflater.inflate(R.layout.row_order, mTable, false);

            TextView usernameOrTable = (TextView) row.findViewById(R.id.elem_username_or_table);
            TextView total = (TextView) row.findViewById(R.id.elem_total);

            if (order.hasCustomer()) {
                usernameOrTable.setText(order.getCustomerUsername());
            } else {
                usernameOrTable.setText(mContext.getString(R.string.elem_table_with_space)
                        + order.getTableNum());
            }
            total.setText(String.format(PRICE_FORMAT, order.getTotal()));

            mTable.addView(row);
        }
    }
}
