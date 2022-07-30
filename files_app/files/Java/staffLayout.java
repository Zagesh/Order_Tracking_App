package com.example.projectactivity;

import android.content.Context;
import android.graphics.Color;
import android.widget.LinearLayout;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

public class staffLayout extends LinearLayout {

    TextView number;
    TextView time;
    TextView status;
    TextView rating;

    public staffLayout(Context context) {
        super(context);

        setOrientation(LinearLayout.HORIZONTAL);

        LinearLayout labelleft = new LinearLayout(context);
        labelleft.setOrientation(LinearLayout.VERTICAL);

        LinearLayout left = new LinearLayout(context);
        left.setOrientation(LinearLayout.VERTICAL);

        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
        lp.weight = 0;
        lp.bottomMargin = 20;

        TextView n = new TextView(context);
        TextView t = new TextView(context);
        TextView s = new TextView(context);
        TextView ra = new TextView(context);
        n.setText("Order number: ");
        n.setTextSize(20);
        n.setTextColor(Color.parseColor("#000000"));
        t.setText("Time of order: ");
        s.setText("Status order: ");
        ra.setText("Order Rating: ");

        n.setPadding(20,20,0,20);
        t.setPadding(20,20,0,20);
        s.setPadding(20,20,0,20);
        ra.setPadding(20,20,0,20);
        labelleft.addView(n);
        labelleft.addView(t);
        labelleft.addView(s);
        labelleft.addView(ra);


        number = new TextView(context);
        number.setPadding(0,20,20,20);
        number.setTextSize(20);
        number.setTextColor(Color.parseColor("#000000"));

        time = new TextView(context);
        time.setPadding(0,20,20,20);

        status = new TextView(context);
        status.setPadding(0,20,20,20);

        rating = new TextView(context);
        rating.setPadding(0,20,20,20);

        left.addView(number);
        left.addView(time);
        left.addView(status);
        left.addView(rating);

        addView(labelleft, lp);
        addView(left, lp);
    }

    public void populate(JSONObject jo) throws JSONException {
        number.setText(jo.getString("Order_Num"));
        time.setText(jo.getString("Order_Time"));
        status.setText(jo.getString("Order_Status"));
        rating.setText(jo.getString("Order_Rating"));

        String st = jo.getString("Order_Status");
        String in = jo.getString("Order_Rating");
        if (st.equals("Collected") && !(in.equals("null"))){
            number.setTextColor(Color.parseColor("#ffffff"));
            time.setTextColor(Color.parseColor("#ffffff"));
            status.setTextColor(Color.parseColor("#ffffff"));
            rating.setTextColor(Color.parseColor("#ffffff"));
        }
    }
}
