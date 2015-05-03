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
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;

public class MyBillFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        if (MyApp.getCustomer().hasCurrentOrder()) {
            LinearLayout billTable = (LinearLayout) view.findViewById(R.id.bill_content);
            TableFiller filler = new TableFiller(billTable, inflater);

            Order order = MyApp.getCustomer().getCurrentOrder();
            filler.fillBill(order);
        } else {
            TextView rowNoOwnOrder = (TextView) view.findViewById(R.id.row_no_own_order);
            rowNoOwnOrder.setVisibility(View.VISIBLE);
        }

        return view;
    }
}
