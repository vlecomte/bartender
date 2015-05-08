package be.uclouvain.lsinf1225.v.bartender.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;
import java.util.Map;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoDetail;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;
import be.uclouvain.lsinf1225.v.bartender.util.Refreshable;
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;

public class ToServeFragment extends Fragment implements Refreshable {
    public static boolean sRefreshRequested = false;

    LinearLayout mToServeTable;
    TextView mRowNoDrinks;
    TableFiller mFiller;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_to_serve, container, false);

        mToServeTable = (LinearLayout) view.findViewById(R.id.to_serve_content);
        mRowNoDrinks = (TextView) view.findViewById(R.id.row_no_drinks);
        mFiller = new TableFiller(mToServeTable, inflater);

        refresh();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (sRefreshRequested) {
            refresh();
            sRefreshRequested = false;
        }
    }

    @Override
    public void refresh() {
        mToServeTable.removeAllViews();
        Map<Integer, List<Detail>> openByTable = DaoDetail.getOpenByTable();

        if (!openByTable.isEmpty()) {
            mRowNoDrinks.setVisibility(View.GONE);
            mFiller.fillToServe(openByTable);
        } else {
            mRowNoDrinks.setVisibility(View.VISIBLE);
        }
    }
}
