package be.uclouvain.lsinf1225.v.bartender.gui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.model.Order;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.util.Refreshable;
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;

public class StockFragment extends Fragment implements Refreshable {
     Button insufStock;
     Button showStock;
     TextView title;


    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.stock_fragment_layout, container, false);
        insufStock = (Button) view.findViewById(R.id.show_insuf_button);
        showStock = (Button) view.findViewById(R.id.show_stock_button);
        title = (TextView) view.findViewById(R.id.stock_main_title);
        refresh();
        insufStock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), InsufStockActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void refresh(){
        insufStock.setVisibility(View.VISIBLE);
        showStock.setVisibility(View.VISIBLE);
        title.setVisibility(View.VISIBLE);
    }
}
