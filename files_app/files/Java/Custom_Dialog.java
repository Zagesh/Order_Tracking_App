package com.example.projectactivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatDialogFragment;

public class Custom_Dialog extends AppCompatDialogFragment {

    Custom_DialogInterface dialogInterface;

    EditText edit1;
    RadioButton rUp;
    RadioButton rDown;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());

        LayoutInflater inflater = getActivity().getLayoutInflater();

        View view = inflater.inflate(R.layout.layout_rate_dialog, null);

        builder.setView(view)
                .setTitle("Rate Order")
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                })
                .setPositiveButton("Proceed", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String t1 = edit1.getText().toString();
                        String rate = "";

                        if (rUp.isChecked()){
                            rate = "1";
                        }

                        else if (rDown.isChecked()) {
                            rate = "0";
                        }
                        dialogInterface.applyTexts(t1, rate);
                    }
                });

        edit1 = view.findViewById(R.id.editTextOrd);
        rUp = view.findViewById(R.id.radioButtonUP);
        rDown = view.findViewById(R.id.radioButtonDOWN);

        return builder.create();
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);

        dialogInterface = (Custom_DialogInterface) context;

    }

    public interface Custom_DialogInterface {
        void applyTexts(String t1, String rate);
    }
}
