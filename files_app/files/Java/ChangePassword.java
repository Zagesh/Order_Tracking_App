package com.example.projectactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class ChangePassword extends AppCompatActivity {

    EditText u;
    EditText p;
    EditText cp;
    RadioButton c;
    RadioButton s;
    Button change;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password);

        u = findViewById(R.id.editTextUsernamecp);
        p = findViewById(R.id.editTextPasswordcp);
        cp = findViewById(R.id.editTextConfirmcp);
        c = findViewById(R.id.radioButtonCustomercp);
        s = findViewById(R.id.radioButtonStaffcp);
        change = findViewById(R.id.buttoncp);

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String username = u.getText().toString();
                final String password = p.getText().toString();
                final String confirm = cp.getText().toString();

                if (c.isChecked()){
                    ChangeCust(username, password, confirm);
                }
                else if (s.isChecked()) {
                    ChangeStaff(username, password, confirm);
                }
                else {
                    Toast.makeText(ChangePassword.this,"User type must be selected",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

    public void ChangeCust(String username, String password, String confirm){
        HttpUrl url = HttpUrl.parse("https://lamp.ms.wits.ac.za/home/s2305631/CustUpdatePass.php").newBuilder()
                .addQueryParameter("username", username)
                .addQueryParameter("password",password)
                .addQueryParameter("confirm_password",confirm)
                .build();
        Request request = new Request.Builder() //request
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String info = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (info.equals("Update successful")){
                                Toast.makeText(ChangePassword.this,"Password change successful",Toast.LENGTH_SHORT).show();
                                openLogin();
                            }
                            else {
                                Toast.makeText(ChangePassword.this,info,Toast.LENGTH_SHORT).show();
                                u.setText("");
                                p.setText("");
                                c.setChecked(false);
                            }
                        }
                    });
                }

            }
        });

    }

    public void ChangeStaff(String username, String password, String confirm){
        HttpUrl url = HttpUrl.parse("https://lamp.ms.wits.ac.za/home/s2305631/StaffUpdatePass.php").newBuilder()
                .addQueryParameter("username", username)
                .addQueryParameter("password",password)
                .addQueryParameter("confirm_password",confirm)
                .build();
        Request request = new Request.Builder() //request
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                e.printStackTrace();

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()){
                    final String info = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (info.equals("Update successful")){
                                Toast.makeText(ChangePassword.this,"Password change successful",Toast.LENGTH_SHORT).show();
                                openLogin();
                            }
                            else {
                                Toast.makeText(ChangePassword.this,info,Toast.LENGTH_SHORT).show();
                                u.setText("");
                                p.setText("");
                                s.setChecked(false);
                            }
                        }
                    });
                }

            }
        });

    }
}
