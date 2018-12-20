package com.ksapps.uvote;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CandInfoActivity extends AppCompatActivity {
    private DatabaseReference mDataRef, mDataRef1;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    ListView lvCandList;
    String mUser1,ward;
    ArrayList<String> arrayList = new ArrayList<>();
    ArrayList<String>arrayList2=new ArrayList<>();
    CustomAdapterList adapterList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cand_info);
        lvCandList=(ListView)findViewById(R.id.lvCandList);
        mAuth = FirebaseAuth.getInstance();
        mUser = mAuth.getCurrentUser();
        mUser1 = mUser.getDisplayName();
        Log.e("Errorr", mUser1);

        mDataRef = FirebaseDatabase.getInstance().getReference().child("voters").child(mUser1);
        mDataRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                ward = dataSnapshot.child("ward").getValue().toString();
                Log.e("Errorr", ward + " Test");
                getCandidate();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        lvCandList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String s1 = arrayList2.get(position);
                Intent i= new Intent(CandInfoActivity.this,InfoActivity.class);
                i.putExtra("Name of Candidate",s1);
                i.putExtra("Ward of Candidate",ward);
                startActivity(i);
            }
        });

    }

    public void getCandidate(){
        if(ward!= null) {
            mDataRef = FirebaseDatabase.getInstance().getReference().child("candidates").child(ward);
            mDataRef.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Log.e("Errorr", "hello");
                    //HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.child("B2").getValue();
                    //Log.e("Errorr",map.toString()+" hello1");
                    for (DataSnapshot child : dataSnapshot.getChildren()) {
                        String s = child.child("name").getValue().toString();
                        arrayList2.add(s);
                        Log.e("Errorr", s);
                        //Toast.makeText(CandInfoActivity.this,s,Toast.LENGTH_LONG).show();
                    }
                    displayCandidate();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }
    public void displayCandidate(){
        adapterList = new CustomAdapterList(arrayList2,this);

        lvCandList.setAdapter(adapterList);
        Log.e("Errorr",arrayList2.toString()+"List");
//        ArrayAdapter arrayAdapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, arrayList2);
//        lvCandList=(ListView)findViewById(R.id.lvCandList);
//       lvCandList.setAdapter(arrayAdapter);


    }
}
