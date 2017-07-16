package com.example.root.college;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.firebase.client.Firebase;

public class MainActivity extends AppCompatActivity {
    private String stdstring,branchstring,preferencestring;
    Firebase mRef1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRef1 = new Firebase("https://colllege-143511.firebaseio.com/");
        stdstring = "T.E.";
        branchstring = "I.T.";
        preferencestring = "ACS";
        final  EditText name=(EditText)findViewById(R.id.editText);
        Button add = (Button)findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String namestring = name.getText().toString();
                if(!namestring.equals("")) {
                    Student student = new Student(namestring,stdstring, branchstring, preferencestring,0+"",0+"",0+"",0+"",0+"",0+"",0+"");
                    mRef1.push().setValue(student);
                }
                    else
                    Toast.makeText(getApplicationContext(),"Enter Name Please",Toast.LENGTH_SHORT).show();
                    name.setText("");


            }

        });
        Button seeallentries = (Button)findViewById(R.id.buttonseeAllEntries);
        seeallentries.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            startActivity(new Intent(getApplication(),LawyerFragment.class));
            }
        });
        Spinner stdspinner = (Spinner) findViewById(R.id.spinner1);
        String[] std = new String[]{"T.E.", "B.E."};
        ArrayAdapter<String> stdadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, std);
        stdspinner.setAdapter(stdadapter);
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
        Spinner branchspinner = (Spinner) findViewById(R.id.spinner2);
        String[] branch = new String[]{"I.T.","CMPS","EXTC"};
        ArrayAdapter<String> branchadapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, branch);
        branchspinner.setAdapter(branchadapter);
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

