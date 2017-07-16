package com.reversetechnologies.collegeteachers;

import android.animation.FloatArrayEvaluator;
import android.animation.ObjectAnimator;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.media.Rating;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class RatingActivity extends AppCompatActivity {
    RatingBar rbcs, rbaap, rbta, rbos, rboverallsuitability;
    TextView name, std, branch, preference, cs, aap, ta, os, overallsuitability;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        final DatabaseReference reference = firebaseDatabase.getReference();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.lawyerpage_onclick_content_scrolling);
        final EditText remarks = (EditText)findViewById(R.id.person_description_et);
        rbcs = (RatingBar) findViewById(R.id.ratingStarscs);
        rbaap = (RatingBar) findViewById(R.id.ratingStarsaap);
        rbta = (RatingBar) findViewById(R.id.ratingStarsta);
        rbos = (RatingBar) findViewById(R.id.ratingStarsos);
        rboverallsuitability = (RatingBar) findViewById(R.id.ratingStarsoverallsuitability);
        name = (TextView) findViewById(R.id.name_tv);
        std = (TextView) findViewById(R.id.std_tv);
        branch = (TextView) findViewById(R.id.branch_tv);
        preference = (TextView) findViewById(R.id.preference_tv);
        cs = (TextView) findViewById(R.id.textViewCommunicationSkills);
        aap = (TextView) findViewById(R.id.textViewaap);
        ta = (TextView) findViewById(R.id.textViewta);
        os = (TextView) findViewById(R.id.textViewos);
        overallsuitability = (TextView) findViewById(R.id.textViewoverallsuitability);
        name.setText(name.getText() + getIntent().getStringExtra("Name"));
        std.setText(std.getText() + getIntent().getStringExtra("Std"));
        branch.setText(branch.getText() + getIntent().getStringExtra("Branch"));
        preference.setText(preference.getText() + getIntent().getStringExtra("Preference"));
        GradientDrawable gradientDrawable = new GradientDrawable(
                GradientDrawable.Orientation.TOP_BOTTOM, //set a gradient direction
                new int[]{Color.parseColor("#FF4081"), Color.parseColor("#FF4081")});
        Button done = (Button)findViewById(R.id.buttondone);
        done.setBackground(gradientDrawable);
        rbcs.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                cs.setText("1) Communication Skills"+": "+ratingBar.getRating()+"/10");
            }
        });
        rbaap.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                aap.setText("2) Appearance and Personality"+": "+ratingBar.getRating()+"/10");
            }
        });
        rbta.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                ta.setText("3) Technical Achievements"+": "+ratingBar.getRating()+"/10");
            }
        });
        rbos.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                os.setText("4) Organisational Skills"+": "+ratingBar.getRating()+"/10");
            }
        });
        rboverallsuitability.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                overallsuitability.setText("5) Overall Suitability"+": "+ratingBar.getRating()+"/10");
            }
        });
        /*Query query = reference.orderByChild("namestring").equalTo(getIntent().getStringExtra("Name"));
        query.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                    Student post = postSnapshot.getValue(Student.class);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {


            }
        });*/
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Query query = reference.orderByChild("namestring").equalTo(getIntent().getStringExtra("Name"));
                query.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        DataSnapshot nodeDataSnapshot = dataSnapshot.getChildren().iterator().next();
                        Student post = nodeDataSnapshot.getValue(Student.class);
                     Map<String, Object> chatfieldsMap = new HashMap<>();
                        chatfieldsMap.put("communicationskills", (rbcs.getRating()+Float.parseFloat(post.getCommunicationskills()))+"");
                        chatfieldsMap.put("appearanceandpersonality",(rbaap.getRating()+Float.parseFloat(post.getAppearanceandpersonality()))+"");
                        chatfieldsMap.put("technicalachievements",(rbta.getRating()+ Float.parseFloat(post.getTechnicalachievements()))+"");
                        chatfieldsMap.put("organisationalskills",(rbos.getRating()+ Float.parseFloat(post.getOrganisationalskills()))+"");
                        chatfieldsMap.put("overallsuitability",(rboverallsuitability.getRating()+ Float.parseFloat(post.getOverallsuitability()))+"");
                        chatfieldsMap.put("remarks",remarks.getText().toString()+ "\n" + post.getRemarks());
                        chatfieldsMap.put("total",rboverallsuitability.getRating()+rbcs.getRating()+rbaap.getRating()+rbos.getRating()+rbta.getRating()+Float.parseFloat(post.getCommunicationskills())+Float.parseFloat(post.getAppearanceandpersonality())+ Float.parseFloat(post.getTechnicalachievements())+Float.parseFloat(post.getOrganisationalskills())+Float.parseFloat(post.getOverallsuitability())+"");
                        reference.child(nodeDataSnapshot.getKey()+"/").updateChildren(chatfieldsMap);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {


                    }
                });
                finish();
            }
        });


    }
}
