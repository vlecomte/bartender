package be.uclouvain.lsinf1225.v.bartender.gui;

import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoProduct;
import be.uclouvain.lsinf1225.v.bartender.model.Product;
import be.uclouvain.lsinf1225.v.bartender.util.CustomList;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;

public class MenuFragment extends Fragment {
    ListView list;
    Product[] menu;
    String[] fileName;
    String[] productName;
    @Override
    public View onCreateView(LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_new_command, container, false);

        menu = DaoProduct.getMenu();
        fileName = new String[menu.length];
        productName = new String[menu.length];

        for(int i=0;i<menu.length;i++){
            String nim=menu[i].getTypeIconFilename();
            fileName[i]= nim.substring(0, (nim.length())-4);
            productName[i] = menu[i].getDisplayName();
        }

        Integer[] ids = new Integer[menu.length];
        for(int i =0;i<menu.length;i++) {
            ids[i] = getResources().getIdentifier(fileName[i], "drawable", getActivity().getPackageName()); //test avec "coca" à la place de filenName[i], ça fonctionne
        }
        list = (ListView) view.findViewById(R.id.list_consommation);
        CustomList adapter = new CustomList(getActivity(), productName, ids);
        list.setAdapter(adapter);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MyApp.setDisplayedProduct(menu[position]);
                Intent intent = new Intent(getActivity(), DescriptionActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
