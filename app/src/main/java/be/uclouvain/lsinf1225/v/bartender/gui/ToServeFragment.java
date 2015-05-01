package be.uclouvain.lsinf1225.v.bartender.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
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
    TextView text;
    View v;
    boolean choice_table;

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
            Tables.setVisibility(View.VISIBLE);
            choice_table = false;
        } else {
            Details.setVisibility(View.VISIBLE);
            annule.setVisibility(View.VISIBLE);
            Tables.setVisibility(View.INVISIBLE);
            choice_table = true;
        }
    }

    private void UpdateView(){
        Tables = (ListView) v.findViewById(R.id.tableList);
        Details = (ListView) v.findViewById(R.id.detailList);
        annule = (Button) v.findViewById(R.id.action_annule_button);
        annule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateLayout();
            }
        });
        Map<Integer, List<Detail>> liste = DaoDetail.getOpenByTable();
        String[] tabTables = new String[liste.size()];
        Integer[] tabNbrDetByTable = new Integer[liste.size()];
        final List[] tabList = new List[liste.size()];
        int i=0;
        for(Map.Entry<Integer, List<Detail>> entry : liste.entrySet()) {
            tabTables[i] = "Table n°" + entry.getKey();
            tabNbrDetByTable[i] = entry.getValue().size();
            tabList[i] = entry.getValue();
            i++;
        }
        Tables.setAdapter(new CustomListTable(getActivity(), tabTables,tabNbrDetByTable));
        Tables.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                tabDestDescr = new String[tabList[position].size()];
                generateTabFromList(tabList[position],tabDestDescr);
                Details.setAdapter(new CustomDetList(getActivity(), tabDestDescr));
                updateLayout();
            }
        });
    }
}
