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
    ListView tableList;
    ListView detailsList;
    Button annuleButton;
    boolean hide = true;

    String[] table;
    Integer[] nbr_details;
    String[] descDetails;
    List<Detail>[] detList;

    @Override
    public View onCreateView(LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.activity_show_details, container, false);
        loadTabs(DaoDetail.getOpenByTable());
        tableList = (ListView) view.findViewById(R.id.tableList);
        detailsList = (ListView) view.findViewById(R.id.detailList);
        CustomListTable adapter = new CustomListTable(getActivity(), table, nbr_details);
        tableList.setAdapter(adapter);
        tableList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UpdateView();
                generateTabFromList(detList[position]);
                detailsList.setAdapter(new CustomDetList(getActivity(),descDetails));
            }
        });
        annuleButton = (Button) view.findViewById(R.id.action_annule_button);
        annuleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UpdateView();
            }
        });
        annuleButton.setVisibility(View.INVISIBLE);
        return view;
    }

    private void loadTabs(Map<Integer, List<Detail>> map){
        int i=0;
        table = new String[map.size()];
        detList = new List[map.size()];
        nbr_details = new Integer[map.size()];
        for (Map.Entry<Integer, List<Detail>> entry : map.entrySet()){
            table[i]= "table n°"+entry.getKey();
            List<Detail> liste = entry.getValue();
            nbr_details[i] = liste.size();
            detList[i]=liste;
            i++;
        }
    }

    private void generateTabFromList(List<Detail> list){
        descDetails = new String[list.size()];
        for (int i =0;i<list.size();i++){
            System.out.println("VALEUR="+list.get(i).getProduct().getDisplayName());
            descDetails[i] = list.get(i).getProduct().getDisplayName();
        }
    }

    private void UpdateView(){
        if(hide){
            tableList.setVisibility(View.INVISIBLE);
            annuleButton.setVisibility(View.VISIBLE);
            detailsList.setVisibility(View.VISIBLE);
            hide = false;
        } else {
            tableList.setVisibility(View.VISIBLE);
            annuleButton.setVisibility(View.INVISIBLE);
            detailsList.setVisibility(View.INVISIBLE);
            hide = true;
        }
    }
}
