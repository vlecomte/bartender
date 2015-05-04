package be.uclouvain.lsinf1225.v.bartender.util;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoUser;
import be.uclouvain.lsinf1225.v.bartender.gui.BillActivity;
import be.uclouvain.lsinf1225.v.bartender.gui.RankPickerDialogFragment;
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
        for (final Order order : orders) {
            LinearLayout row = (LinearLayout) mInflater.inflate(R.layout.row_order, mTable, false);

            TextView usernameOrTable = (TextView) row.findViewById(R.id.elem_username_or_table);
            TextView total = (TextView) row.findViewById(R.id.elem_total);
            ImageButton openBill = (ImageButton) row.findViewById(R.id.button_open_bill);

            if (order.hasCustomer()) {
                usernameOrTable.setText(order.getCustomerUsername());
            } else {
                usernameOrTable.setText(mContext.getString(R.string.elem_table_with_space)
                        + order.getTableNum());
            }
            total.setText(String.format(PRICE_FORMAT, order.getTotal()));
            openBill.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(mContext, BillActivity.class);
                    intent.putExtra("order_num", order.getOrderNum());
                    mContext.startActivity(intent);
                }
            });

            mTable.addView(row);
        }
    }

    private String getRankDisplayName(String rank) {
        switch (rank) {
            case RANK_CUSTOMER:
                return mContext.getString(R.string.rank_customer);
            case RANK_WAITER:
                return mContext.getString(R.string.rank_waiter);
            case RANK_ADMIN:
                return mContext.getString(R.string.rank_admin);
            default:
                throw new IllegalArgumentException("Invalid rank.");
        }
    }

    public void fillUsers(Set<Map.Entry<String, String>> users, final FragmentManager manager,
                          final Refreshable toRefresh) {
        for (final Map.Entry<String, String> user : users) {
            LinearLayout row = (LinearLayout) mInflater.inflate(R.layout.row_user, mTable, false);

            TextView username = (TextView) row.findViewById(R.id.elem_username);
            TextView rank = (TextView) row.findViewById(R.id.elem_rank);
            ImageButton editRank = (ImageButton) row.findViewById(R.id.button_edit_rank);

            username.setText(user.getKey());
            rank.setText(getRankDisplayName(user.getValue()));
            editRank.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (MyApp.getUser().getUsername().equals(user.getKey())) {
                        Toast.makeText(mContext,
                                mContext.getString(R.string.toast_cannot_modify_own_rank),
                                Toast.LENGTH_SHORT).show();
                    } else {
                        RankPickerDialogFragment rankPicker = new RankPickerDialogFragment();
                        rankPicker.setListener(new RankPickerDialogFragment.RankListener() {
                            @Override
                            public void onObtainRank(String rank) {
                                DaoUser.setRank(user.getKey(), rank);
                                toRefresh.refresh();
                            }
                        });
                        rankPicker.show(manager, "rank_picker");
                    }
                }
            });

            mTable.addView(row);
        }
    }
}