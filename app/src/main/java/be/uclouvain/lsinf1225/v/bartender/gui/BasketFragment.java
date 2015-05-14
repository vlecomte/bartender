package be.uclouvain.lsinf1225.v.bartender.gui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import be.uclouvain.lsinf1225.v.bartender.R;
import be.uclouvain.lsinf1225.v.bartender.dao.DaoIngredient;
import be.uclouvain.lsinf1225.v.bartender.util.MyApp;
import be.uclouvain.lsinf1225.v.bartender.util.Refreshable;
import be.uclouvain.lsinf1225.v.bartender.util.TableFiller;

public class BasketFragment extends Fragment implements Refreshable {
    private LinearLayout mBasketTable;
    private TextView mRowBasketEmpty;
    private TableFiller mFiller;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_basket, container, false);

        mBasketTable = (LinearLayout) view.findViewById(R.id.basket_content);
        mRowBasketEmpty = (TextView) view.findViewById(R.id.row_basket_empty);
        mFiller = new TableFiller(mBasketTable, inflater);

        Button buttonConfirmBasket = (Button) view.findViewById(R.id.button_confirm_basket);
        buttonConfirmBasket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApp.getCustomer().getBasket().isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.error_no_basket_to_confirm),
                            Toast.LENGTH_LONG).show();
                } else if (!DaoIngredient.getInsufficient().isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.error_ingredient_insufficient),
                            Toast.LENGTH_LONG).show();
                } else {
                    if (MyApp.getCustomer().hasCurrentOrder()) {
                        confirmBasketAndRefresh();
                    } else {
                        TablePickerDialogFragment tablePicker = new TablePickerDialogFragment();
                        tablePicker.setListener(new TablePickerDialogFragment.TableNumListener() {
                            @Override
                            public void onObtainTableNum(int tableNum) {
                                MyApp.getCustomer().openOrder(tableNum);
                                confirmBasketAndRefresh();
                            }
                        });
                        tablePicker.show(getFragmentManager(), TablePickerDialogFragment.TAG);
                    }
                }
            }
        });

        Button buttonConfirmForTable = (Button) view.findViewById(R.id.button_confirm_for_table);
        if(MyApp.isWaiter()) {
            buttonConfirmForTable.setVisibility(View.VISIBLE);
            buttonConfirmBasket.setText(getString(R.string.button_confirm_for_me));
        }
        buttonConfirmForTable.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (MyApp.getCustomer().getBasket().isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.error_no_basket_to_confirm),
                            Toast.LENGTH_SHORT).show();
                } else if (!DaoIngredient.getInsufficient().isEmpty()) {
                    Toast.makeText(getActivity(), getString(R.string.error_ingredient_insufficient),
                            Toast.LENGTH_SHORT).show();
                } else {
                    TablePickerDialogFragment tablePicker = new TablePickerDialogFragment();
                    tablePicker.setListener(new TablePickerDialogFragment.TableNumListener() {
                        @Override
                        public void onObtainTableNum(int tableNum) {
                            MyApp.getWaiter().confirmBasketFor(tableNum);
                            refresh();
                            Toast.makeText(getActivity(), getString(R.string.confirmed_for_table)
                                    + tableNum, Toast.LENGTH_SHORT).show();
                        }
                    });
                    tablePicker.show(getFragmentManager(), TablePickerDialogFragment.TAG);
                }
            }
        });
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        refresh();
    }

    public void confirmBasketAndRefresh() {
        MyApp.getCustomer().confirmBasket();
        refresh();

        Toast.makeText(getActivity(), R.string.confirmed_basket, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void refresh() {
        mBasketTable.removeAllViews();
        if (!MyApp.getCustomer().getBasket().isEmpty()) {
            mRowBasketEmpty.setVisibility(View.GONE);
            mFiller.fillBasket(MyApp.getCustomer());
        } else {
            mRowBasketEmpty.setVisibility(View.VISIBLE);
        }
    }
}
