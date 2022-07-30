package com.example.projectactivity;

import android.app.Activity;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class PHPRequest {
    public void doRequest(final Activity a, String net, String name, final RequestHandler rh){
        OkHttpClient client = new OkHttpClient();
        HttpUrl url = HttpUrl.parse(net).newBuilder()
                .addQueryParameter("uName",name)
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
                    a.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                rh.processResponse(info);
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                }

            }
        });
    }
}
