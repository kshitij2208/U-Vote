package com.ksapps.uvote;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

public class HelpActivity extends AppCompatActivity {

    TextView t1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);


        t1=(TextView)findViewById(R.id.textView);

        final ArrayList<String> list = new ArrayList<String>();
        list.add("FAQ");
        list.add("ASK A QUESTION");

        final ArrayAdapter adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,list);

        ListView lView = (ListView) findViewById(R.id.mylist);
        lView.setAdapter(adapter);

        lView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==0)
                {
                    Intent i1=new Intent(HelpActivity.this,FaqActivity.class);
                    startActivity(i1);
                }

                if(position==1)
                {

                    Intent i1=new Intent(HelpActivity.this,ChatBotActivity.class);
                    startActivity(i1);


                }

            }
        });

    }
}
