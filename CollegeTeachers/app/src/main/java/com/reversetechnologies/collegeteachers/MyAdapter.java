package com.reversetechnologies.collegeteachers;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

/**
 * Created by simba on 6/27/16.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    private List<Student> mLawyer = null;
    private List<Student> worldpopulationlist ;

    // Store the context for easy access
    private Context mContext;

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        // each data item is just a string in this case
        public TextView mTextView_position, mTextView_name, mTextView_study, mTextView_location,mtotal;


        public ViewHolder(View itemView) {
            super(itemView);
            mTextView_position = (TextView) itemView.findViewById(R.id.lawyer_position_tv);
            mTextView_name = (TextView) itemView.findViewById(R.id.lawyer_name_tv);
            mTextView_study = (TextView) itemView.findViewById(R.id.lawyer_study_tv);
            mTextView_location = (TextView) itemView.findViewById(R.id.lawyer_location_tv);
            mtotal = (TextView)itemView.findViewById(R.id.total_tv);

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int position = getLayoutPosition();
            Student lawyer = mLawyer.get(position);
            Intent intent = new Intent(mContext, RatingActivity.class);
            intent.putExtra("Std" , lawyer.getStdstring());
            intent.putExtra("Name" , lawyer.getNamestring());
            intent.putExtra("Branch" , lawyer.getBranchstring());
            intent.putExtra("Preference" , lawyer.getPreferencestring());
            mContext.startActivity(intent);

        }
    }



        // Pass in the contact array into the constructor
        public MyAdapter(Context context, List<Student> lawyers) {
            mLawyer=lawyers;

            this.worldpopulationlist = new ArrayList<Student>();
            this.worldpopulationlist.addAll(mLawyer);
            mContext = context;
        }

        // Easy access to the context object in the recyclerview
        private Context getContext() {
            return mContext;


    }

    // Usually involves inflating a layout from XML and returning the holder
    @Override
    public MyAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        // Inflate the custom layout
        View contactView = inflater.inflate(R.layout.find_a_lawyer_item_recycler_view, parent, false);
        // Return a new holder instance
        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    // Involves populating data into the item through holder
    @Override
    public void onBindViewHolder(MyAdapter.ViewHolder viewHolder, int position) {
        // Get the data model based on position
        Student lawyer = mLawyer.get(position);

        // Set item views based on your views and data model
        TextView textView_position = viewHolder.mTextView_position;
        textView_position.setText("Std: "+lawyer.getStdstring());
        TextView textView_name = viewHolder.mTextView_name;
        textView_name.setText("Name: "+lawyer.getNamestring());
        TextView textView_study = viewHolder.mTextView_study;
        textView_study.setText("Branch: "+lawyer.getBranchstring());
        TextView textView_location = viewHolder.mTextView_location;
        textView_location.setText("Preference: "+lawyer.getPreferencestring());
        TextView textView_total = viewHolder.mtotal;
        textView_total.setText("Total: "+lawyer.getTotal());

    }

    @Override
    public int getItemCount() {
        return mLawyer.size();
    }

    public void filter(String charText) {
        charText = charText.toLowerCase(Locale.getDefault());
        mLawyer.clear();
        final Firebase firebaseDatabaseRef =  new Firebase("https://colllege-143511.firebaseio.com/");

            final String finalCharText = charText;
            firebaseDatabaseRef.addValueEventListener(new com.firebase.client.ValueEventListener() {
                @Override
                public void onDataChange(com.firebase.client.DataSnapshot dataSnapshot) {
                    Log.e("Count " ,""+dataSnapshot.getChildrenCount());
                    for (com.firebase.client.DataSnapshot postSnapshot: dataSnapshot.getChildren()) {
                        Student post = postSnapshot.getValue(Student.class);
                        if(finalCharText.length()==0)
                        {
                            mLawyer.add(post);
                            notifyDataSetChanged();
                            continue;
                        }
                        if(post.getNamestring().toLowerCase(Locale.getDefault()).contains(finalCharText)|| post.getPreferencestring().toLowerCase(Locale.getDefault()).equals(finalCharText)) {
                            mLawyer.add(post);
                            notifyDataSetChanged();
                        }
                    }
                    Log.d("Lawyer details","");
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {

                }
            });
            notifyDataSetChanged();


    }
}