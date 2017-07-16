package com.reversetechnologies.collegeteachers;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Spinner;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "LawyerFragment";
    ArrayList<Student> lawyer;
    static int i = 0;
    String[] name = new String[20];
    AutoCompleteTextView name_tv;

    public MainActivity() {
        // Required empty public constructor
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);      // Inflate the layout for this fragment
        setContentView(R.layout.activity_students_details);
        final RecyclerView rvLawyer = (RecyclerView) findViewById(R.id.rv);
        lawyer = new ArrayList<>();
        name_tv = (AutoCompleteTextView) findViewById(R.id.add_lawyerBtn);
        // Create adapter passing in the sample user data
        final MyAdapter adapter = new MyAdapter(this, lawyer);
        final Firebase firebaseDatabaseRef = new Firebase("https://colllege-143511.firebaseio.com/");
        Spinner stdspinner = (Spinner) findViewById(R.id.spinner1);
        String[] std = new String[]{"CS", "TS", "GS", "ACS", "ATS"};
        ArrayAdapter<String> stdadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, std);
        stdspinner.setAdapter(stdadapter);
        stdspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                adapter.filter(parent.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("nothing", "selected");
            }
        });

        name_tv.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                adapter.filter(s.toString());
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // adapter.filter(s.toString());
            }
        });
        firebaseDatabaseRef.addValueEventListener(new com.firebase.client.ValueEventListener() {

            @Override
            public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                lawyer.clear();
                Log.e("Count ", "" + dataSnapshot.getChildrenCount());
                name[0] = "";
                for (com.firebase.client.DataSnapshot postSnapshot : dataSnapshot.getChildren()) {
                    Student post = postSnapshot.getValue(Student.class);
                    lawyer.add(post);
                    name[0] = name[0] + post.getNamestring() + ",";
                }
                adapter.notifyDataSetChanged();
                if (name[0].contains("null"))
                    setAdapter(name[0].substring(4));
                else
                    setAdapter(name[0]);
                View progressView = findViewById(R.id.progressBar);
                if (progressView != null) {
                    progressView.setVisibility(View.GONE);
                }
                Log.d("Lawyer details", "");
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {
                Log.d(TAG, firebaseError.getMessage());
            }
        });
        //rvLawyer.setItemAnimator(new SlideInUpAnimator());
        // Attach the adapter to the recyclerview to populate items
        rvLawyer.setAdapter(adapter);
        // Set layout manager to position the items
        rvLawyer.setLayoutManager(new LinearLayoutManager(this.getApplicationContext()));
        // That's all!
    }

    public void setAdapter(String name) {
        String names[] = new String[10];
        names = name.split(",");
/*
        for(int i=0;i<names.length;i++)
        {
            Toast.makeText(this,names[i],Toast.LENGTH_SHORT).show();
        }*/
        ArrayAdapter adapter1 = new ArrayAdapter(this, android.R.layout.simple_list_item_1, names);
        name_tv.setAdapter(adapter1);
        name_tv.setThreshold(1);
    }
}

