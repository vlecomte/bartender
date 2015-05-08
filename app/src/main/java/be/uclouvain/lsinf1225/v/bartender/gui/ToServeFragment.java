package be.uclouvain.lsinf1225.v.bartender.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.internal.widget.ContentFrameLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;
import java.util.Map;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoDetail;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;
import be.uclouvain.lsinf1225.v.bartender.model.Product;
import be.uclouvain.lsinf1225.v.bartender.util.CustomDetList;
import be.uclouvain.lsinf1225.v.bartender.util.CustomListTable;

public class ToServeFragment extends Fragment {
    ListView Tables;
    ListView Details;
    Button annule;
    Button Confirmer;
    Button refresh;
    TextView text;
    View v;
    boolean choice_table;

    List<Detail> currentList;

    String[] tabDestDescr;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_show_details, container, false);
        v=view;
        choice_table=true;
        UpdateView();
        updateLayout();
        return view;
    }

    private void generateTabFromList(List<Detail> list, String[] tab){
            for(int i=0;i<list.size();i++){
                tab[i] = list.get(i).getProduct().getDisplayName();
            }
    }

    private void updateLayout(){
        if(choice_table) {
            Details.setVisibility(View.INVISIBLE);
            annule.setVisibility(View.INVISIBLE);
            Confirmer.setVisibility(View.INVISIBLE);
            text.setVisibility(View.INVISIBLE);
            Tables.setVisibility(View.VISIBLE);
            choice_table = false;
        } else {
            Details.setVisibility(View.VISIBLE);
            annule.setVisibility(View.VISIBLE);
            text.setVisibility(View.VISIBLE);
            Confirmer.setVisibility(View.VISIBLE);
            Tables.setVisibility(View.INVISIBLE);
            choice_table = true;
        }
    }

    public void UpdateView(){
        Tables = (ListView) v.findViewById(R.id.tableList);
        Details = (ListView) v.findViewById(R.id.detailList);
        annule = (Button) v.findViewById(R.id.action_annule_button);
        annule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLayout();
            }
        });
        refresh = (Button) v.findViewById(R.id.refresh_button);
        text = (TextView) v.findViewById(R.id.txtDetails);
        refresh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateView();
            }
        });
        Map<Integer, List<Detail>> liste = DaoDetail.getOpenByTable();
        final String[] tabTables = new String[liste.size()];
        Integer[] tabNbrDetByTable = new Integer[liste.size()];
        final List[] tabList = new List[liste.size()];
        int i=0;
        for(Map.Entry<Integer, List<Detail>> entry : liste.entrySet()) {
            tabTables[i] = getString(R.string.table_number) + " " + entry.getKey();
            tabNbrDetByTable[i] = entry.getValue().size();
            tabList[i] = entry.getValue();
            i++;
        }
        Confirmer = (Button) v.findViewById(R.id.set_payed);
        Confirmer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoDetail.markDelivered(currentList);
                UpdateView();
                updateLayout();
            }
        });
        Tables.setAdapter(new CustomListTable(getActivity(), tabTables,tabNbrDetByTable));
        Tables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tabDestDescr = new String[tabList[position].size()];
                currentList = tabList[position];
                generateTabFromList(tabList[position],tabDestDescr);
                text.setText(tabTables[position]);
                Details.setAdapter(new CustomDetList(getActivity(), tabDestDescr));
                updateLayout();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        UpdateView();
    }
}
