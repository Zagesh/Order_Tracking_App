package com.example.projectactivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import androidx.appcompat.app.AppCompatDialogFragment;



public class Custom_Dialog2 extends AppCompatDialogFragment {

    EditText Cust;
    RadioButton rb1;
    RadioButton rb2;
    RadioButton rb3;
    String u;

    Custom_DialogInterface dialogInterface;


    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {


        AlertDialog.Builder builder2 = new AlertDialog.Builder(getContext());
        LayoutInflater inflator2 = getActivity().getLayoutInflater() ;
        View view1 = inflator2.inflate(R.layout.layout_dialog2,null);
        builder2.setView(view1)
                .setTitle("Update the status of order")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String number = (Cust.getText().toString());
                        String status ="";
                        if (rb1.isChecked()){
                            status="Pending";

                        }else if (rb2.isChecked()){
                            status = "Ready";
                        }else{
                            status="Collected";
                        }

                        dialogInterface.applyupdate(number, status);

                    }
                });

        Cust = view1.findViewById(R.id.edtCust2);
        rb1 = view1.findViewById(R.id.radioButton);
        rb2 = view1.findViewById(R.id.radioButton2);
        rb3 = view1.findViewById(R.id.radioButton3);


        return builder2.create();
    }
    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        dialogInterface = (Custom_DialogInterface) context;
    }


    public interface Custom_DialogInterface {
        void applyupdate(String username, String status);

    }
}