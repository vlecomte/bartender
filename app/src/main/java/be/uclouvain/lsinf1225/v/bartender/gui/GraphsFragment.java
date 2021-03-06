package be.uclouvain.lsinf1225.v.bartender.gui;

/**
 * Created by haroldsomers on 7/05/15.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.util.PieGraphActivity;


public class GraphsFragment extends Fragment {

    Button graph1, graph2, graph3, graph4;

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_graphs, container, false);
        graph1 = (Button) view.findViewById(R.id.efficacite_serveurs);
        graph2 = (Button) view.findViewById(R.id.chiffre_affaire);
        graph3 = (Button) view.findViewById(R.id.meilleurs_clients);
        graph4 = (Button) view.findViewById(R.id.meilleurs_boissons);

        graph1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), WaiterPlotActivity.class);
                startActivity(intent);
            }
        });

        graph2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ChiffreAffaireActivity.class);
                startActivity(intent);
            }
        });

        graph3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), ClientPlotActivity.class);
                startActivity(intent);
            }
        });

        graph4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), PieGraphActivity.class);
                startActivity(intent);
            }
        });
        return view;
    }
}
