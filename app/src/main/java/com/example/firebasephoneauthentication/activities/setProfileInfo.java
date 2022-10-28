package com.example.firebasephoneauthentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.firebasephoneauthentication.R;
import com.example.firebasephoneauthentication.model.User;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Locale;

public class setProfileInfo extends AppCompatActivity {

    TextInputLayout username,status;
    Button nextHome;
    String phone,UserName,Status;
    FirebaseDatabase rootNode;
    DatabaseReference reference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_profile_info);

        username = (TextInputLayout) findViewById(R.id.TextInputUsernameLayout);
        status = (TextInputLayout) findViewById(R.id.TextInputStatusLayout);

        nextHome = (Button) findViewById(R.id.NextHomePage);

        Intent intent = getIntent();


        //save data into Firebase
        nextHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                rootNode = FirebaseDatabase.getInstance();
                reference = rootNode.getReference("users");

                //Get all values
                phone = intent.getStringExtra("phone");
                UserName = username.getEditText().getText().toString().trim();
                Status = status.getEditText().getText().toString().trim();

                User user = new User(phone,UserName,Status);

                reference.child(phone).setValue(user);

                Intent goHome = new Intent(setProfileInfo.this,HomeActivity.class);
                startActivity(goHome);
            }
        });



    }
}