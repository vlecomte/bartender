package be.uclouvain.lsinf1225.v.bartender.gui;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoIngredient;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoProduct;
import be.uclouvain.lsinf1225.v.bartender.util.Refreshable;
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;

public class MenuFragment extends Fragment implements Refreshable {
    LinearLayout mMenuTable;
    TableFiller mFiller;

    @Override
    public View onCreateView(LayoutInflater inflater,
                                ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_menu, container, false);

        mMenuTable = (LinearLayout) view.findViewById(R.id.menu_content);
        mFiller = new TableFiller(mMenuTable, inflater);

        refresh();

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refreshLocal();
    }

    private void refreshLocal() {
        mMenuTable.removeAllViews();
        mFiller.fillMenu(DaoProduct.getMenu());
    }

    public void refresh() {
        DaoIngredient.refreshStock();
        refreshLocal();
    }
}
