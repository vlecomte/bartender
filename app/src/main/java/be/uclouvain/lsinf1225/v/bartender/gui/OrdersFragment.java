package be.uclouvain.lsinf1225.v.bartender.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoOrder;
import be.uclouvain.lsinf1225.v.bartender.model.Order;
import be.uclouvain.lsinf1225.v.bartender.util.Refreshable;
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;

public class OrdersFragment extends Fragment implements Refreshable {
    LinearLayout mOrdersTable;
    TextView mRowNoOrder;
    TableFiller mFiller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_orders, container, false);

        mOrdersTable = (LinearLayout) view.findViewById(R.id.orders_content);
        mRowNoOrder = (TextView) view.findViewById(R.id.row_no_order);
        mFiller = new TableFiller(mOrdersTable, inflater);

        refresh();

        return view;
    }

    public void refresh() {
        mOrdersTable.removeAllViews();
        List<Order> orders = DaoOrder.getAllOpen();

        if (!orders.isEmpty()) {
            mRowNoOrder.setVisibility(View.GONE);
            mFiller.fillOrders(orders);
        } else {
            mRowNoOrder.setVisibility(View.VISIBLE);
        }
    }
}
