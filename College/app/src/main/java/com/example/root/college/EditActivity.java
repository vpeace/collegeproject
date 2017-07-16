package com.example.root.college;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditActivity extends AppCompatActivity {
    private String stdstring,branchstring,preferencestring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        Button update = (Button)findViewById(R.id.update);
        stdstring = getIntent().getStringExtra("Std");
        branchstring = getIntent().getStringExtra("Branch");
        preferencestring = getIntent().getStringExtra("Branch");
        final EditText name=(EditText)findViewById(R.id.editText);
        final Spinner stdspinner = (Spinner) findViewById(R.id.spinner1);
        Spinner branchspinner = (Spinner) findViewById(R.id.spinner2);
        Spinner preferencespinner = (Spinner)findViewById(R.id.spinner3);
        String[] std = new String[]{"T.E.", "B.E."};
        ArrayAdapter<String> stdadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, std);
        stdspinner.setAdapter(stdadapter);
        String[] branch = new String[]{"I.T.","CMPS","EXTC"};
        ArrayAdapter<String> branchadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, branch);
        branchspinner.setAdapter(branchadapter);
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        name.setText("Name: "+getIntent().getStringExtra("Name"));
        name.setEnabled(false);
        for(int i=0;i<std.length;i++)
        {
            if(std[i].equals(getIntent().getStringExtra("Std")))
            {
                stdspinner.setSelection(i);
                break;
            }
        }
        for(int i=0;i<branch.length;i++)
        {
            if(branch[i].equals(getIntent().getStringExtra("Branch")))
            {
                branchspinner.setSelection(i);
                break;
            }
        }
        String[] preference;
        if(getIntent().getStringExtra("Std").equals("T.E.")) {
            preference = new String[]{"ACS","ATS" , "ASS"};
        }
        else if(getIntent().getStringExtra("Std").equals("B.E."))
        {
            preference = new String[]{"CS","TS","SS"};
        }
        else
        {
            preference = new String[]{""};
        }
        ArrayAdapter<String> preferenceadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, preference);
        preferencespinner.setAdapter(preferenceadapter);
        for(int i=0;i<preference.length;i++)
        {
            if(preference[i].equals(getIntent().getStringExtra("Branch")))
            {
                preferencespinner.setSelection(i);
                break;
            }
        }
        branchspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                branchstring = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("nothing", "selected");
            }
        });
        stdspinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

                stdstring = parent.getItemAtPosition(position).toString();
                setPreference();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("nothing", "selected");
            }
        });
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Query query = reference.orderByChild("namestring").equalTo(getIntent().getStringExtra("Name"));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                            DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                            Map<String, Object> chatfieldsMap = new HashMap<>();
                            chatfieldsMap.put("branchstring", branchstring);
                            chatfieldsMap.put("namestring",getIntent().getStringExtra("Name"));
                            chatfieldsMap.put("preferencestring",preferencestring);
                            chatfieldsMap.put("stdstring",stdstring);
                            reference.child(nodeDataSnapshot.getKey()+"/").updateChildren(chatfieldsMap);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });
            }
        });


    }
    public void setPreference()
    {
        Spinner preferencespinner = (Spinner)findViewById(R.id.spinner3);
        String[] preference;
        if(stdstring.equals("T.E.")) {
            preference = new String[]{"ACS","ATS" , "ASS"};
        }
        else if(stdstring.equals("B.E."))
        {
            preferencestring = "CS";
            preference = new String[]{"CS","TS","SS"};
        }
        else
        {
            preference = new String[]{""};
        }
        ArrayAdapter<String> preferenceadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, preference);
        preferencespinner.setAdapter(preferenceadapter);
        preferencespinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                preferencestring = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                Log.d("nothing" , "selected");
            }
        });
    }
}
