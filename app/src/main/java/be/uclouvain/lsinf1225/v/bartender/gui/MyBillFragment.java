package be.uclouvain.lsinf1225.v.bartender.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.model.Order;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.util.Refreshable;
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;

public class MyBillFragment extends Fragment implements Refreshable {
    private LinearLayout mBillTable;
    private TextView mRowNoOwnOrder;
    private TableFiller mFiller;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        mBillTable = (LinearLayout) view.findViewById(R.id.bill_content);
        mRowNoOwnOrder = (TextView) view.findViewById(R.id.row_no_own_order);
        mFiller = new TableFiller(mBillTable, inflater);

        refresh();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh() {
        mBillTable.removeAllViews();

        if (MyApp.getCustomer().hasCurrentOrder()) {
            mRowNoOwnOrder.setVisibility(View.GONE);
            Order order = MyApp.getCustomer().getCurrentOrder();
            mFiller.fillBill(order);
        } else {
            mRowNoOwnOrder.setVisibility(View.VISIBLE);
        }
    }
}
