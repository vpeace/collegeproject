package com.example.root.college;

import com.firebase.client.Firebase;

/**
 * Created by root on 17/9/16.
 */
public class StudentsC extends android.app.Application {
    @Override
    public void onCreate(){
        super.onCreate();
        Firebase.setAndroidContext(this);
    }
}
