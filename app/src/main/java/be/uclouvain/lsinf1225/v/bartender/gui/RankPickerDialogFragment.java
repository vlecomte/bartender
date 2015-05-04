package be.uclouvain.lsinf1225.v.bartender.gui;

import static be.uclouvain.lsinf1225.v.bartender.dao.Contract.*;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.DialogFragment;

import be.uclouvain.lsinf1225.v.bartender.R;

public class RankPickerDialogFragment extends DialogFragment {
    RankListener mListener;

    @Override
    @NonNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());

        builder.setTitle(getString(R.string.dialog_title_pick_rank))
                .setItems(new String[]{
                                getString(R.string.rank_customer),
                                getString(R.string.rank_waiter),
                                getString(R.string.rank_admin)
                        },
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                switch (which) {
                                    case 0:
                                        mListener.onObtainRank(RANK_CUSTOMER);
                                        break;
                                    case 1:
                                        mListener.onObtainRank(RANK_WAITER);
                                        break;
                                    case 2:
                                        mListener.onObtainRank(RANK_ADMIN);
                                        break;
                                }
                            }
                        });

        return builder.create();
    }

    public interface RankListener {
        void onObtainRank(String rank);
    }

    public void setListener(RankListener listener) {
        mListener = listener;
    }
}