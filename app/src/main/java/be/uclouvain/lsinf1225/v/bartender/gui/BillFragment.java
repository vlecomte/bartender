package be.uclouvain.lsinf1225.v.bartender.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.util.CustomListTable;

public class BillFragment extends Fragment {
    ListView tableList;
    ListView detailsList;
    String[] table = {"table 1 " , "table 2 " ,"table 3 "};
    Integer[] nbr_details = {5, 1000, 9};

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_show_details, container, false);

        tableList = (ListView) view.findViewById(R.id.detailsList);
        CustomListTable adapter = new CustomListTable(getActivity(), table, nbr_details);
        tableList.setAdapter(adapter);
        tableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        return view;
    }
}
