package be.uclouvain.lsinf1225.v.bartender.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import java.util.List;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoDetail;
import be.uclouvain.lsinf1225.v.bartender.model.Detail;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.util.Refreshable;
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;

public class AtTableActivity extends Activity {
    public static final String ARGUMENT_TABLE_NUM = "table_num";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_at_table);

        int tableNum = getIntent().getIntExtra(ARGUMENT_TABLE_NUM, 0);
        final List<Detail> toServe = DaoDetail.getOpenAtTable(tableNum);

        setTitle(getString(R.string.to_serve_at_table_then_space) + tableNum);

        LinearLayout atTableTable = (LinearLayout) findViewById(R.id.at_table_content);
        TableFiller filler = new TableFiller(atTableTable, getLayoutInflater());
        filler.fillAtTable(toServe);

        Button markDelivered = (Button) findViewById(R.id.button_mark_delivered);
        markDelivered.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyApp.getWaiter().serve(toServe);
                ToServeFragment.sRefreshRequested = true;
                finish();
            }
        });
    }
}
