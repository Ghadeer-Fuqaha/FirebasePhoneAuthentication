package com.example.firebasephoneauthentication.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.firebasephoneauthentication.R;
import com.example.firebasephoneauthentication.model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;

public class ViewProfile extends AppCompatActivity {

    Toolbar toolbar;
    TextView username,status,phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_profile);

        toolbar = (Toolbar) findViewById(R.id.viewProfileToolbar);
        username = (TextView) findViewById(R.id.username);
        status = (TextView) findViewById(R.id.status);
        phone = (TextView) findViewById(R.id.phone);

        setSupportActionBar(toolbar);

        // add back arrow to toolbar
        if (getSupportActionBar() != null){
            getSupportActionBar().setTitle("Profile");
            toolbar.setTitleTextColor(Color.WHITE);
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setDisplayShowHomeEnabled(true);
        }

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ViewProfile.this,HomeActivity.class));
                finish();
            }
        });


        //get the phone number of current user
        String phoneNumber = FirebaseAuth.getInstance().getCurrentUser().getPhoneNumber();
        Log.d("phone --> ",phoneNumber);
        //Retrieve Data from Firebase
        DatabaseReference reference = FirebaseDatabase.getInstance().getReference("users");

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                
                if(snapshot.getValue() !=null) {


                    User user = snapshot.child(phoneNumber).getValue(User.class);





                username.setText(user.getUserName());
                status.setText(user.getStatus());
                phone.setText(user.getPhone());
                }else{

                    Toast.makeText(ViewProfile.this, "no data Retrieved!", Toast.LENGTH_SHORT).show();
                }
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }//End onCreate
}