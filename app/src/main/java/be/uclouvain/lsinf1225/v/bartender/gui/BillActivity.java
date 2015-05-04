package be.uclouvain.lsinf1225.v.bartender.gui;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoOrder;
import be.uclouvain.lsinf1225.v.bartender.model.Order;
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;

public class BillActivity extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bill);

        final int orderNum = getIntent().getIntExtra("order_num", 0);
        Order order = DaoOrder.getByNum(orderNum);

        LinearLayout billTable = (LinearLayout) findViewById(R.id.bill_content);
        TableFiller filler = new TableFiller(billTable, getLayoutInflater());
        filler.fillBill(order);

        Button markPaid = (Button) findViewById(R.id.button_mark_paid);
        markPaid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DaoOrder.setPaid(orderNum);
                finish();
            }
        });
    }
}
