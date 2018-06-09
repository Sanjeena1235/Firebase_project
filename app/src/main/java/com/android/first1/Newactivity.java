package com.android.first1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

public class Newactivity extends AppCompatActivity {
  ImageView userimg;
  TextView usernames,emails;
  Button btn2;
  FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newactivity);

        userimg=(ImageView)findViewById(R.id.image);
        usernames=(TextView)findViewById(R.id.username);
        emails=(TextView)findViewById(R.id.email);
        btn2=(Button)findViewById(R.id.logout);


        usernames.setText(FirebaseAuth.getInstance().getCurrentUser().getDisplayName());
        emails.setText(FirebaseAuth.getInstance().getCurrentUser().getEmail());

        userimg.setImageURI(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl());

        Picasso.with(getBaseContext())
                .load(FirebaseAuth.getInstance().getCurrentUser().getPhotoUrl())
                .into(userimg);
         btn2.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View view) {
                 FirebaseAuth.getInstance().signOut();
                 finish();
               //  FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
               //  auth.signOut();
             }
         });



    }
}
