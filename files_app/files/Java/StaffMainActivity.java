package com.example.projectactivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.nfc.Tag;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.Objects;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class StaffMainActivity extends AppCompatActivity implements Custom_Dialog1.Custom_DialogInterface,Custom_Dialog2.Custom_DialogInterface {

    TextView username;
    String U;

    LinearLayout l;

    Button press1;
    Button press2;
    Button press3;
    final OkHttpClient client = new OkHttpClient();
    PHPRequest req = new PHPRequest();

    private SwipeRefreshLayout swipeRefreshLayout;

    public void opendialog1(View view)
    {
        Custom_Dialog1 custom_dialog = new Custom_Dialog1();
        custom_dialog.show(getSupportFragmentManager(),"I have no idea");
    }

    public void opendialog2(View view1)
    {
        Custom_Dialog2 custom_dialog = new Custom_Dialog2();
        custom_dialog.show(getSupportFragmentManager(),"I have no idea");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_staff_main);

        Intent intent = getIntent();
        U = intent.getStringExtra(LoginActivity.EXTRA_TEXT);

        l = findViewById(R.id.lStaff);


        username = findViewById(R.id.textViewUname);
        username.setText(U);

        press1 = findViewById(R.id.buttonAdd);
        press2 = findViewById(R.id.buttonUpdate);

        press3=findViewById(R.id.button2);

        press3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                applyRate(U);
            }
        });

        Order(U);

        swipeRefreshLayout = findViewById(R.id.RefreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        Order(U);
                    }
                }, 2500);
            }
        });
        swipeRefreshLayout.setColorSchemeColors(
                Color.parseColor("#33ccff"),
                Color.parseColor("#e37fff"),
                Color.parseColor("#33ccff")
        );

    }


    @Override
    public void applyOrder(String text1, String text2,String text3) {
        HttpUrl url = HttpUrl.parse("https://lamp.ms.wits.ac.za/home/s2305631/OrderAdd.php").newBuilder()
                .addQueryParameter("Restuarant",text3)
                .addQueryParameter("Cust_name",text1)
                .addQueryParameter("Staff_name",text2)
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
                if (response.isSuccessful()) {
                    final String message = response.body().string();
                    //Log.d("myTag", "**"+message+"**");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (message.contains("Order successful"))
                            {
                                Toast.makeText(StaffMainActivity.this, "Your order was successful!", Toast.LENGTH_SHORT).show();
                            }else if (!(message.contains("Order successful"))) {

                                Toast.makeText(StaffMainActivity.this, "Your order was unsuccessful "+message, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });

                }
            }
        });
    }


    @Override
    public void applyupdate(String num, String status) {
        HttpUrl url = HttpUrl.parse("https://lamp.ms.wits.ac.za/home/s2305631/OrderUpdate.php").newBuilder()
                .addQueryParameter("username", U)
                .addQueryParameter("update", status)
                .addQueryParameter("number", num)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String message = response.body().string();
                    //Log.d("myTag", "**"+message+"**");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            if (message.equals("Update successful"))
                            {
                                Toast.makeText(StaffMainActivity.this, "Your update was successful!", Toast.LENGTH_SHORT).show();
                            }else if (!(message.contains("Update successful"))) {

                                Toast.makeText(StaffMainActivity.this, "Your update was unsuccessful "+message, Toast.LENGTH_SHORT).show();

                            }
                        }
                    });
                }
            }
        });
    }


    public void applyRate(String name) {
        HttpUrl url = HttpUrl.parse("https://lamp.ms.wits.ac.za/home/s2305631/AvgRating.php").newBuilder()
                .addQueryParameter("name", name)
                .build();

        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {

            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                if (response.isSuccessful()) {
                    final String message = response.body().string();
                    //Log.d("myTag", "**"+message+"**");
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                outputs(message);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });

                }
            }
        });
    }


    public void Order(String name){
        req.doRequest(StaffMainActivity.this, "https://lamp.ms.wits.ac.za/home/s2305631/staffMain.php", name,
                new RequestHandler() {
                    @Override
                    public void processResponse(String response) throws JSONException {
                        display(response);
                    }
                });
    }


    public void outputs(String json) throws JSONException {
        JSONArray ja = new JSONArray(json);
        JSONObject jo = ja.getJSONObject(0);

        String str = jo.getString("Your average rating");

        Toast.makeText(StaffMainActivity.this, "Your average rating is: " + str + "%", Toast.LENGTH_LONG).show();
    }


    public void display(String json) throws JSONException {
        l.removeAllViews();
        JSONArray ja = new JSONArray(json);
        for (int i = 0; i < ja.length(); ++i){
            JSONObject jo = ja.getJSONObject(i);
            staffLayout sl = new staffLayout(this);
            sl.populate(jo);
            l.addView(sl);
            if (i%2==0) {
                sl.setBackgroundColor(Color.parseColor("#33ccff"));
            }
            else {
                sl.setBackgroundColor(Color.parseColor("#e37fff"));
            }
        }
    }
}

