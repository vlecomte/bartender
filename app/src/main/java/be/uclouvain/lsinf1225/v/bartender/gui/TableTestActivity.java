package be.uclouvain.lsinf1225.v.bartender.gui;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import be.uclouvain.lsinf1225.v.bartender.R;

public class TableTestActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.table_test);

        ScrollView table = (ScrollView) findViewById(R.id.test_table_layout);
        for (int i=0; i<5; i++)
        {
            TableRow tr = new TableRow(this);
            for (int j=0; j<3; j++)
            {
                TextView tv = new TextView(this);
                tv.setText(i+","+j);
                tr.addView(tv);
            }
            table.addView(tr);
        }
    }
}
