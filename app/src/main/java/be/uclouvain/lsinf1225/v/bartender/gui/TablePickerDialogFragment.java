package be.uclouvain.lsinf1225.v.bartender.gui;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.NumberPicker;

import be.uclouvain.lsinf1225.v.bartender.R;

public class TablePickerDialogFragment extends DialogFragment {
    private static final int TABLE_NUM_MIN = 1, TABLE_NUM_MAX = 10;

    private NumberPicker mTablePicker;
    TableNumListener mListener;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.dialog_table_picker, null);
        mTablePicker = (NumberPicker) view.findViewById(R.id.picker_table);
        mTablePicker.setMinValue(TABLE_NUM_MIN);
        mTablePicker.setMaxValue(TABLE_NUM_MAX);
        mTablePicker.setWrapSelectorWheel(false);

        builder.setTitle(getString(R.string.dialog_title_pick_table))
                .setView(view)
                .setPositiveButton(R.string.button_ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int id) {
                        mListener.onObtainTableNum(getResult());
                    }
                })
                .setNegativeButton(R.string.button_cancel, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        TablePickerDialogFragment.this.getDialog().cancel();
                    }
                });

        return builder.create();
    }

    public int getResult() {
        return mTablePicker.getValue();
    }

    public static interface TableNumListener {
        public void onObtainTableNum(int tableNum);
    }

    public void setListener(BasketFragment basketFragment) {
        mListener = basketFragment;
    }
}
