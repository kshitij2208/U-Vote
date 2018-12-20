package com.ksapps.uvote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class InfoActivity extends AppCompatActivity {
    TextView nameCand, descCand;
    private DatabaseReference mDataRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        nameCand = (TextView) findViewById(R.id.nameCand);
        descCand = (TextView) findViewById(R.id.descCand);

        mAuth = FirebaseAuth.getInstance();

        Intent i = getIntent();
        Bundle extras = getIntent().getExtras();
        final String name = extras.getString("Name of Candidate");
        String ward = extras.getString("Ward of Candidate");

        if (ward != null) {
            mDataRef = FirebaseDatabase.getInstance().getReference().child("candidates").child(ward);
            mDataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e("Errorr", "hello");
                    //HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.child("B2").getValue();
                    //Log.e("Errorr",map.toString()+" hello1");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String s = child.child("name").getValue().toString();
                        if (s.equals(name)) {
                            String s1 = child.child("desc").getValue().toString();
                            descCand.setText(s1);
                            nameCand.setText(name);
                        }
                    }
//                        Log.e("Errorr", s1);
                    //Toast.makeText(CandInfoActivity.this,s,Toast.LENGTH_LONG).show();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }
    }
}
