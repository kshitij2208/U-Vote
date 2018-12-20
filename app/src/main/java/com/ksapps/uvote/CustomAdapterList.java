package com.ksapps.uvote;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ksapps.uvote.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

    public class CustomAdapterList extends BaseAdapter implements ListAdapter {
    private DatabaseReference mDataRef;
    private FirebaseAuth mAuth;
    private FirebaseUser mUser;
    ListView lvCandList;
    String mUser1;
    ArrayList<String> arrayList = new ArrayList<>();
    private Context context;

    public CustomAdapterList(ArrayList<String> list, Context context) {
        this.arrayList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayList.size();
    }

    @Override
    public Object getItem(int pos) {
        return arrayList.get(pos);
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = convertView;
        if (view == null) {
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.candidate_item, null);
        }

        //Handle TextView and display string from your list
        TextView candidateList = (TextView) view.findViewById(R.id.CandName);
        candidateList.setText(arrayList.get(position));


        return view;
    }

}


