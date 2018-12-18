package com.ksapps.uvote;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.firebase.client.Firebase;
import com.firebase.ui.auth.AuthUI;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;

public class BaseActivity extends AppCompatActivity {
    public ProgressDialog mProgressDialog;
    private Button button3;
    private FirebaseDatabase database;
    private DatabaseReference myRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference().child("room");
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                if (dataSnapshot.hasChild("6513")) {
                    Date currentTime = Calendar.getInstance().getTime();
                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                    String currentDateandTime = sdf.format(currentTime);
                    String compareTime = dataSnapshot.child("6513").child("sTime").getValue().toString()+":00";
                    Log.e("Errorr",compareTime+" Hello");
                    Date d1, d2;
                    try {
                        d1 = sdf.parse(currentDateandTime);
                        d2 = sdf.parse(compareTime);
                        int compareResult = d1.compareTo(d2);
                        Log.e("Errorr", "Hello "+compareResult);
                        if (compareResult > 0) {
                            Log.e("Errorr", "1 is younger than 2");
                        } else if (compareResult < 0) {
                            Log.e("Errorr", "2 is younger than 1");
                        } else {
                            Log.e("Errorr", "1 is equals than 2");
                        }
                    } catch (ParseException e) {
                        Log.e("Errorr",e.toString());
                        e.printStackTrace();
                    }
                    Log.e("Errorr", currentDateandTime);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
