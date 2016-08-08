package com.mason.brent.demo;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by brent on 2016/5/19,019.
 */
public class MyDialogFragment extends DialogFragment{
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
//        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
//        builder.setTitle("用户申明")
//                .setMessage("Hello world!")
//                .setPositiveButton(R.string.agree, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dismiss();
//                    }
//                })
//                .setNegativeButton(R.string.disagree, new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        dismiss();
//                    }
//                })
//                .setCancelable(false);
//        //.show(); // show cann't be use here
//
//        return builder.create();

        super.onCreateDialog(savedInstanceState);
        Dialog mDialog = new Dialog(getActivity(), R.style.spinner_dialog);

        mDialog.setContentView(R.layout.spinner_dialog);
        WindowManager.LayoutParams lp = mDialog.getWindow().getAttributes();
        lp.alpha = 1f;
        lp.dimAmount = 0.5f;

        mDialog.getWindow().setAttributes(lp);
        mDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        ImageButton mBack = (ImageButton) mDialog.findViewById(R.id.imgbutton_back);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        mDialog.setCanceledOnTouchOutside(true);
        return mDialog;
    }
}
