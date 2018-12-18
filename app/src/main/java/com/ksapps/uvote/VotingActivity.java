package com.ksapps.uvote;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

public class VotingActivity extends AppCompatActivity {

    private FirebaseAuth auth;
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    private FirebaseUser user;
    LinearLayout container;
    private EditText etRoomId;
    private Button btnSubmit;
    String roomId, number;
    ArrayList<String> arrayList;
    TextView tvTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (isOnline()) {
            setContentView(R.layout.activity_voting);

            etRoomId = (EditText) findViewById(R.id.etRoomId);
            btnSubmit = (Button) findViewById(R.id.btnSubmit);
            container = (LinearLayout) findViewById(R.id.container);
            tvTitle = (TextView) findViewById(R.id.title);
            auth = FirebaseAuth.getInstance();
            database = FirebaseDatabase.getInstance();
            myRef = database.getReference().child("room");

            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

            arrayList = new ArrayList<>();

            btnSubmit.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    if (TextUtils.isEmpty(etRoomId.getText().toString())) {
                        Toast.makeText(getApplicationContext(), "Enter email address!", Toast.LENGTH_SHORT).show();
                        return;
                    }

                    roomId = etRoomId.getText().toString();

                    myRef.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull final DataSnapshot dataSnapshot) {
                            if (dataSnapshot.hasChild(roomId)) {
                                if (!dataSnapshot.child(roomId).hasChild("hasVoted")) {
                                    myRef.child(roomId).child("hasVoted").child(user.getDisplayName()).setValue("0");
                                    return;
                                }

                                Date currentTime = Calendar.getInstance().getTime();
                                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                String currentDateandTime = sdf.format(currentTime);
                                Log.e("Errrorr",currentDateandTime);
                                String startTime = dataSnapshot.child(roomId).child("sTime").getValue().toString()+":00";
                                String endTime = dataSnapshot.child(roomId).child("eTime").getValue().toString()+":00";
                                Date d1, d2;
                                Date d3;
                                try {
                                    d1 = sdf.parse(currentDateandTime);
                                    d2 = sdf.parse(startTime);
                                    d3 = sdf.parse(endTime);
                                    Log.e("Errorr",d1.toString()+" ");
                                    int compareResult1 = d1.compareTo(d2);
                                    int compareResult2 = d1.compareTo(d3);
                                    Log.e("Errorr",compareResult1+" "+compareResult2);
                                    if (compareResult1 < 0 || compareResult2 > 0) {
                                        Toast.makeText(VotingActivity.this, "Cannot enter room", Toast.LENGTH_SHORT).show();
                                        return;
                                    } else if (compareResult1 > 0 && compareResult2 < 0) {
                                        Toast.makeText(VotingActivity.this, "Success", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Log.e("Errorr", "1 is equals than 2");
                                    }
                                } catch (ParseException e) {
                                    Log.e("Errorr",e.toString());
                                    e.printStackTrace();
                                }
                                Log.e("Errorr", currentDateandTime);


                                //if(dataSnapshot.child(roomId).child("sTime").getValue())
                                if (!dataSnapshot.child(roomId).child("hasVoted").child(user.getDisplayName()).getValue().toString().equals("1")) {
                                    tvTitle.setText(dataSnapshot.child(roomId).child("title").getValue().toString());
                                    HashMap<String, String> map = (HashMap<String, String>) dataSnapshot.child(roomId).child("candidates").getValue();
                                    Iterator<String> itr = map.keySet().iterator();
                                    while (itr.hasNext()) {
                                        LayoutInflater layoutInflater =
                                                (LayoutInflater) getBaseContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                                        final View addView = layoutInflater.inflate(R.layout.button, null);
                                        final Button buttonRemove = (Button) addView.findViewById(R.id.vote);
                                        buttonRemove.setText(itr.next());
                                        final View.OnClickListener thisListener = new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                Log.e("Errorr", buttonRemove.getText().toString());
                                                number = dataSnapshot.child(roomId).child("candidates").child(buttonRemove.getText()
                                                        .toString()).getValue().toString();
                                                int num = Integer.parseInt(number);
                                                myRef.child(roomId).child("candidates").child(buttonRemove.getText().toString()).setValue(num + 1);
                                                myRef.child(roomId).child("hasVoted").child(user.getDisplayName()).setValue("1");
                                                Toast.makeText(VotingActivity.this, "Your vote has been recorded", Toast.LENGTH_LONG).show();
                                                Intent intent = new Intent(VotingActivity.this, ResultsActivity.class);
                                                intent.putExtra("roomId", roomId);
                                                startActivity(intent);
                                                finish();
                                            }
                                        };
                                        buttonRemove.setOnClickListener(thisListener);
                                        container.addView(addView);
                                        btnSubmit.setEnabled(false);
                                    }

                                } else {
                                    Toast.makeText(VotingActivity.this, "You have already voted in this room", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(VotingActivity.this, ResultsActivity.class);
                                    intent.putExtra("roomId", roomId);
                                    startActivity(intent);
                                    finish();
                                }
                            } else {
                                Toast.makeText(VotingActivity.this, "No Such Room Exists", Toast.LENGTH_SHORT).show();
                                return;
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
        } else
            setContentView(R.layout.no_internet);

    }

    public Boolean isOnline() {
        try {
            Process p1 = java.lang.Runtime.getRuntime().exec("ping -c 1 www.google.com");
            int returnVal = p1.waitFor();
            boolean reachable = (returnVal == 0);
            return reachable;
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return false;
    }
}
