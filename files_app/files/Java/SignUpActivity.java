package com.example.projectactivity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.tabs.TabLayout;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static okhttp3.HttpUrl.*;

public class SignUpActivity extends AppCompatActivity{

    private EditText Username;
    private EditText FName;
    private EditText Password;
    private EditText Password2;
    private EditText PNum;
    Button SignUpCust;
    Button SignUpStaff;
    OkHttpClient client = new OkHttpClient();

    public void CustSignUp(String uName, String fName, String number, String passW, String passW2){
        HttpUrl url = parse("https://lamp.ms.wits.ac.za/home/s2305631/reg.php").newBuilder()
                .addQueryParameter("username",uName)
                .addQueryParameter("fullname",fName)
                .addQueryParameter("phonenumber",number)
                .addQueryParameter("password",passW)
                .addQueryParameter("confirm_password",passW2)
                .build();

        Request request = new Request.Builder()
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
                    final String message = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (message.equals("Registration successful")){
                                Toast.makeText(SignUpActivity.this, "Your registration was successful!", Toast.LENGTH_SHORT).show();
                                openLogin();
                            }else {
                                Toast.makeText(SignUpActivity.this,message, Toast.LENGTH_SHORT).show();
                                Username.setText("");
                                FName.setText("");
                                Password.setText("");
                                Password2.setText("");
                                PNum.setText("");
                            }
                        }
                    });
                }

            }
        });

    }

    public void StaffSignUp(String uname,String fname,String passw,String passw2, String number){
        HttpUrl url = HttpUrl.parse("https://lamp.ms.wits.ac.za/home/s2305631/staffreg.php").newBuilder()
                .addQueryParameter("username",uname)
                .addQueryParameter("fullname",fname)
                .addQueryParameter("password",passw)
                .addQueryParameter("confirm_password",passw2)
                .addQueryParameter("phonenumber",number)
                .build();

        Request request = new Request.Builder()
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
                    final String message = response.body().string();
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (message.equals("Registration successful")){
                                Toast.makeText(SignUpActivity.this, "Your registration was successful!", Toast.LENGTH_SHORT).show();
                                openLogin();
                            }else {
                                Toast.makeText(SignUpActivity.this,message, Toast.LENGTH_SHORT).show();
                                Username.setText("");
                                FName.setText("");
                                Password.setText("");
                                Password2.setText("");
                                PNum.setText("");
                            }
                        }
                    });
                }

            }
        });

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        Username = findViewById(R.id.editTextUsername);
        FName = findViewById(R.id.editTextFirstName);
        Password = findViewById(R.id.editTextPassword);
        Password2 = findViewById(R.id.editTextConfirm);
        PNum = findViewById(R.id.editTextPhone);

        SignUpCust = findViewById(R.id.buttonCustSignUp);

        SignUpCust.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uName = Username.getText().toString();
                final String fName = FName.getText().toString();
                final String Num = PNum.getText().toString();
                final String Pass = Password.getText().toString();
                final String Pass2 = Password2.getText().toString();

                CustSignUp(uName, fName, Num, Pass, Pass2);
            }
        });

        SignUpStaff = findViewById(R.id.buttonStaffSignUp);

        SignUpStaff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String uName = Username.getText().toString();
                final String fName = FName.getText().toString();
                final String Num = PNum.getText().toString();
                final String Pass = Password.getText().toString();
                final String Pass2 = Password2.getText().toString();

                StaffSignUp(uName, fName, Pass, Pass2, Num);
            }
        });

    }

    public void openLogin() {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);
    }

}
