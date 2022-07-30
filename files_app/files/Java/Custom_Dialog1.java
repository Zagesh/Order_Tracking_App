package com.example.projectactivity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Custom_Dialog1 extends AppCompatDialogFragment {

    EditText t1;
    EditText t2;
    EditText t3;
    TextView v1;
    TextView v2;

    Custom_DialogInterface dialogInterface;
    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        LayoutInflater inflator = getActivity().getLayoutInflater() ;
        View view = inflator.inflate(R.layout.layout_dialog1,null);
        builder.setView(view)
                .setTitle("Add a new order")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String tc = t1.getText().toString();
                        String ts = t2.getText().toString();
                        String tr = t3.getText().toString() ;
                        dialogInterface.applyOrder(tc,ts,tr);

                    }
                });



        t1 = view.findViewById(R.id.edtCust);
        t2 = view.findViewById(R.id.edtStaff);
        t3 = view.findViewById(R.id.edtRest);
        v1 = view.findViewById(R.id.textView);
        v2 = view.findViewById(R.id.textView2);
        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialogInterface = (Custom_DialogInterface) context;
    }

    public interface  Custom_DialogInterface{
        void applyOrder(String text1,String text2,String text3);



    }
}