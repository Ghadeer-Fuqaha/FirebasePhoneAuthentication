package com.example.firebasephoneauthentication.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.firebasephoneauthentication.R;
import com.google.android.material.textfield.TextInputLayout;

public class MainActivity extends AppCompatActivity {

    TextInputLayout mobileNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mobileNumber = (TextInputLayout) findViewById(R.id.TextInputPhoneLayout);

        findViewById(R.id.sendVer).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String phone = mobileNumber.getEditText().getText().toString().trim();

                //Validate phone number before send it to VerifyCodeActivity

                if(phone.isEmpty() || phone.length()<10){

                    mobileNumber.setError("Enter valid phone number:");
                    mobileNumber.requestFocus();
                    return;
                }

                Intent intent = new Intent(MainActivity.this,VerifyCodeActivity.class);
                intent.putExtra("mobile",phone);
                startActivity(intent);
            }
        });
    }
}