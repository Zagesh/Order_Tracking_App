package com.example.projectactivity;

import androidx.annotation.ColorInt;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class CustMainActivity extends AppCompatActivity implements Custom_Dialog.Custom_DialogInterface {

    LinearLayout l;
    TextView username;
    String U;
    OkHttpClient client = new OkHttpClient();
    PHPRequest req = new PHPRequest();

    String ord;
    String rat;

    private SwipeRefreshLayout swipeRefreshLayout;

    public void openDialog(View view){
        Custom_Dialog custom_dialog = new Custom_Dialog();
        custom_dialog.show(getSupportFragmentManager(), "Test");
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cust_main);

        Intent intent = getIntent();
        U = intent.getStringExtra(LoginActivity.EXTRA_TEXT);


        username = findViewById(R.id.textViewUname);
        username.setText(U);


        l = findViewById(R.id.lCustomer);

        Orders(U);

        swipeRefreshLayout = findViewById(R.id.refreshLayout);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        swipeRefreshLayout.setRefreshing(false);
                        Orders(U);
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


    public void Orders(String name){
        req.doRequest(CustMainActivity.this, "https://lamp.ms.wits.ac.za/home/s2305631/custMain.php", name,
                new RequestHandler() {
                    @Override
                    public void processResponse(String response) throws JSONException {
                        outputs(response);
                    }
                });
    }


    public void outputs(String json) throws JSONException {
        l.removeAllViews();
        JSONArray ja = new JSONArray(json);
        for (int i = 0; i < ja.length(); ++i){
            JSONObject jo = ja.getJSONObject(i);
            custLayout cl = new custLayout(this);
            cl.populate(jo);
            l.addView(cl);
            if (i%2==0) {
                cl.setBackgroundColor(Color.parseColor("#33ccff"));
            }
            else {
                cl.setBackgroundColor(Color.parseColor("#e37fff"));
            }
        }
    }


    @Override
    public void applyTexts(String t1, String rate) {
        ord = t1;
        rat = rate;

        Rating(U, ord, rat);
    }


    public void Rating(String usern, String ordNum,String ordRate){
        HttpUrl url = HttpUrl.parse("https://lamp.ms.wits.ac.za/home/s2305631/rateOrd.php").newBuilder()
                .addQueryParameter("username", usern)
                .addQueryParameter("ordNum", ordNum)
                .addQueryParameter("orderRate", ordRate)
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
                            if (message.equals("Update successful")){
                                Toast.makeText(CustMainActivity.this, "Rating Successful", Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(CustMainActivity.this, message, Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }

            }
        });
    }
}
