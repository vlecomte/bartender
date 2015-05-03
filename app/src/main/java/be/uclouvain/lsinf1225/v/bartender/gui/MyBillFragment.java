package be.uclouvain.lsinf1225.v.bartender.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;
import be.uclouvain.lsinf1225.v.bartender.model.Order;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;

public class MyBillFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_bill, container, false);

        LinearLayout billContent = (LinearLayout) view.findViewById(R.id.bill_content);
        if (MyApp.getCustomer().hasCurrentOrder()) {
            Order order = MyApp.getCustomer().getCurrentOrder();
            List<Detail> details = order.getDetails();

            SimpleDateFormat sdf = new SimpleDateFormat("HH:mm", Locale.getDefault());

            for (Detail detail : details) {
                TableRow row = (TableRow) inflater.inflate(R.layout.row_bill, billContent, false);

                TextView productName = (TextView) row.findViewById(R.id.elem_product_name);
                TextView time = (TextView) row.findViewById(R.id.elem_time);
                TextView price = (TextView) row.findViewById(R.id.elem_price);

                productName.setText(detail.getProduct().getDisplayName());
                time.setText(sdf.format(detail.getDateAdded()));
                price.setText(String.format("%.2f€", detail.getProduct().getPrice()));

                billContent.addView(row);
            }

            TableRow rowTotal = (TableRow) inflater.inflate(R.layout.row_bill_total, billContent, false);
            TextView totalPrice = (TextView) rowTotal.findViewById(R.id.elem_total_price);
            totalPrice.setText(String.format("%.2f€", order.getTotal()));
            billContent.addView(rowTotal);
        } else {
            billContent.findViewById(R.id.row_total).setVisibility(View.GONE);
        }

        return view;
    }
}
