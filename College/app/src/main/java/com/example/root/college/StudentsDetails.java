package com.example.root.college;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.ui.FirebaseRecyclerAdapter;

public class StudentsDetails extends AppCompatActivity {
    Firebase mRef;
    RecyclerView mRecyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_students_details);
        mRecyclerView = (RecyclerView)findViewById(R.id.rv);
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));




    }
    @Override
    protected void onStart() {
        super.onStart();
        mRef = new Firebase("https://colllege-143511.firebaseio.com/users1");
        FirebaseRecyclerAdapter<String, MessageViewHolder> adapter = new FirebaseRecyclerAdapter<String, MessageViewHolder>(String.class,
                android.R.layout.two_line_list_item,
                MessageViewHolder.class,
                mRef) {
            @Override
            protected void populateViewHolder(MessageViewHolder messageViewHolder, String s, int i) {

                messageViewHolder.mText.setText(s);
            }
        };
        mRecyclerView.setAdapter(adapter);
    }
    public static class MessageViewHolder extends RecyclerView.ViewHolder
    {
        TextView mText;
        public  MessageViewHolder(View v)
        {
            super(v);
            mText = (TextView) v.findViewById(android.R.id.text1);

        }
    }
}
