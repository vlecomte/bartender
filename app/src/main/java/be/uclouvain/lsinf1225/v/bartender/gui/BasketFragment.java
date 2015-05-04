package be.uclouvain.lsinf1225.v.bartender.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Map;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.model.Customer;
import be.uclouvain.lsinf1225.v.bartender.model.Product;
import be.uclouvain.lsinf1225.v.bartender.model.Waiter;
import be.uclouvain.lsinf1225.v.bartender.util.CustomBasket;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;

public class BasketFragment extends Fragment implements TablePickerDialogFragment.TableNumListener {
    private ListView mList;
    private TextView mTotal;
    boolean tablePicked;
    Customer custom;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        mList = (ListView) view.findViewById(R.id.listView_basket);
        mTotal = (TextView) view.findViewById(R.id.total_basket);

        Button confirmButton = (Button) view.findViewById(R.id.confirm_basket_button);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApp.getCustomer().hasCurrentOrder()) {
                    confirmAndClean();
                } else {
                    TablePickerDialogFragment tablePicker = new TablePickerDialogFragment();
                    tablePicker.setListener(BasketFragment.this);
                    tablePicker.show(getFragmentManager(), "table_picker");
                }
            }
        });
        tablePicked = false;
        Button confirmForClientButton = (Button) view.findViewById(R.id.confirm_client_button);
        confirmForClientButton.setVisibility(View.INVISIBLE);
        if(MyApp.isWaiter()) {
            confirmForClientButton.setVisibility(View.VISIBLE);
        }
        confirmForClientButton.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (tablePicked) {
                    Waiter currentWait = (Waiter) MyApp.getCustomer();
                    currentWait.confirmBasketFor(2);
                    updateBasketView();
                    Toast.makeText(getActivity(), R.string.confirmed_basket_client, Toast.LENGTH_LONG).show();
                } else {
                    //tablePicked = true;
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateBasketView();
    }

    public void confirmAndClean() {
        MyApp.getCustomer().confirmBasket();
        updateBasketView();
        Toast.makeText(getActivity(), R.string.confirmed_basket, Toast.LENGTH_LONG).show();
    }

    public void updateBasketView() {
        double count_total = 0.0;
        Map<Product, Integer> basket_elem = MyApp.getCustomer().getBasket();
        String[] tab_elem = new String[basket_elem.size()];
        Double[] tab_prix_elem = new Double[basket_elem.size()];
        int i =0;
        for(Map.Entry<Product, Integer> entry : basket_elem.entrySet()){
            tab_elem[i] = entry.getKey().getDisplayName() + "["+ entry.getKey().getPrice()+"€]" + "  *  " + entry.getValue();
            tab_prix_elem[i] = entry.getValue() * entry.getKey().getPrice();
            count_total = count_total + tab_prix_elem[i];
            i++;
        }
        CustomBasket adapter = new CustomBasket(getActivity(), tab_elem, tab_prix_elem);
        mList.setAdapter(adapter);
        mTotal.setText("TOTAL: " + count_total + "€");
    }

    public void onObtainTableNum(int tableNum) {
        MyApp.getCustomer().openOrder(tableNum);
        confirmAndClean();
    }
}
