package com.ksapps.uvote;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class ImortantDatesActivity extends AppCompatActivity {

    TextView t0,t2,t3,t4,t5;
    Button b1,b2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_important_dates);

        t0=(TextView)findViewById(R.id.textView0);

        t2=(TextView)findViewById(R.id.textView2);

        t3=(TextView)findViewById(R.id.textView3);

        t4=(TextView)findViewById(R.id.textView4);

        t5=(TextView)findViewById(R.id.textView5);

        b1=(Button)findViewById(R.id.button4);
        b2=(Button)findViewById(R.id.button5);


    }
}
