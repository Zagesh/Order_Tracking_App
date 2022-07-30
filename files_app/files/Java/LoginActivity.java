package com.example.projectactivity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class LoginActivity extends AppCompatActivity {

    public static final String EXTRA_TEXT = "com.example.projectactivity.EXTRA_TEXT";


    private EditText Username;
    private EditText Password;
    Button Login;
    RadioButton rCust;
    RadioButton rStaff;
    Button Register;
    Button Ch;
    OkHttpClient client = new OkHttpClient();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        Username = findViewById(R.id.editTextUsername);
        Password = findViewById(R.id.editTextPassword);
        rCust = (RadioButton) findViewById(R.id.radioButtonCustomer);
        rStaff = (RadioButton) findViewById(R.id.radioButtonStaff);
        Login = (Button) findViewById(R.id.buttonLogin);
        Register = (Button) findViewById(R.id.buttonRegister);
        Ch = findViewById(R.id.buttonForgotPassword);

        Login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String strUname = Username.getText().toString();
                final String strPass = Password.getText().toString();


                if (rCust.isChecked()){
                    ValidateCust(strUname,strPass);
                }

                else if (rStaff.isChecked()) {
                    ValidateStaff(strUname,strPass);
                }
                else {
                    Toast.makeText(LoginActivity.this,"User type must be selected",Toast.LENGTH_SHORT).show();
                }
            }
        });

        Register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openSignUp();
            }
        });

        Ch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openChange();
            }
        });
    }

    public void openCust() {
        EditText u = findViewById(R.id.editTextUsername);
        String U = u.getText().toString();

        Intent intent = new Intent(this, CustMainActivity.class);
        intent.putExtra(EXTRA_TEXT, U);
        startActivity(intent);
    }
    public void openStaff() {
        EditText u = findViewById(R.id.editTextUsername);
        String U = u.getText().toString();

        Intent intent = new Intent(this, StaffMainActivity.class);
        intent.putExtra(EXTRA_TEXT, U);
        startActivity(intent);
    }
    public void openSignUp() {
        Intent intent = new Intent(this, SignUpActivity.class);
        startActivity(intent);
    }

    public void openChange() {
        Intent intent = new Intent(this, ChangePassword.class);
        startActivity(intent);
    }

    public void ValidateCust(String name,String password){
        HttpUrl url = HttpUrl.parse("https://lamp.ms.wits.ac.za/home/s2305631/test.php").newBuilder()
                .addQueryParameter("uName",name)
                .addQueryParameter("passw",password)
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
                            if (info.equals("Login successful")){
                                Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                                openCust();
                            }
                            else {
                                Toast.makeText(LoginActivity.this,info,Toast.LENGTH_SHORT).show();
                                Username.setText("");
                                Password.setText("");
                                rCust.setChecked(false);
                            }
                        }
                    });
                }

            }
        });

    }

    public void ValidateStaff(String name,String password){
        HttpUrl url = HttpUrl.parse("https://lamp.ms.wits.ac.za/home/s2305631/test2.php").newBuilder()
                .addQueryParameter("uName",name)
                .addQueryParameter("passw",password)
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
                            if (info.equals("Login successful")){
                                Toast.makeText(LoginActivity.this,"Login successful",Toast.LENGTH_SHORT).show();
                                openStaff();
                            }
                            else {
                                Toast.makeText(LoginActivity.this,info,Toast.LENGTH_SHORT).show();
                                Username.setText("");
                                Password.setText("");
                                rStaff.setChecked(false);
                            }
                        }
                    });
                }

            }
        });

    }
}
