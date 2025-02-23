package com.example.firebasephoneauthentication.activities;

import static android.content.ContentValues.TAG;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.util.Log;
import android.view.View;
import android.widget.Toast;


import com.example.firebasephoneauthentication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthOptions;
import com.google.firebase.auth.PhoneAuthProvider;
import java.util.concurrent.TimeUnit;

public class VerifyCodeActivity extends AppCompatActivity {

    //It is the verification id that will be sent to the user
    private String mVerificationId;

    // [START declare_auth]
    private FirebaseAuth mAuth;
    // [END declare_auth]

    TextInputLayout InputVerifyCode;
    String phone;
  //  private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verify_code);


        Intent intent = getIntent();
        InputVerifyCode = (TextInputLayout) findViewById(R.id.TextInputCodeLayout);
        mAuth = FirebaseAuth.getInstance();

         phone = intent.getStringExtra("mobile");
        startPhoneNumberVerification(phone);

    }//End onCreate method

    private void startPhoneNumberVerification(String phoneNumber) {
        // [START start_phone_auth]
        PhoneAuthOptions options =
                PhoneAuthOptions.newBuilder(mAuth)
                        .setPhoneNumber(phoneNumber)       // Phone number to verify
                        .setTimeout(60L, TimeUnit.SECONDS) // Timeout and unit
                        .setActivity(this)                 // Activity (for callback binding)
                        .setCallbacks(mCallbacks)          // OnVerificationStateChangedCallbacks
                        .build();
        PhoneAuthProvider.verifyPhoneNumber(options);
        // [END start_phone_auth]
    }//End of startPhoneNumberVerification


    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks  = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(@NonNull PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();
            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            //Toast.makeText(VerifyCodeActivity.this,"Verification Code ---> "+code, Toast.LENGTH_LONG).show();


            if (code != null) {
                InputVerifyCode.getEditText().setText(code);


            }else{

                InputVerifyCode.getEditText().setFocusable(true);
                InputVerifyCode.setError("Enter the verification code that you received:");

                if(!InputVerifyCode.getEditText().getText().toString().trim().isEmpty()){
                    InputVerifyCode.getEditText().setFocusable(false);
                    code = InputVerifyCode.getEditText().getText().toString().trim();
                }
            }

            //verifying the code
            verifyVerificationCode(code);

        }

        @Override
        public void onVerificationFailed(@NonNull FirebaseException e) {

            Toast.makeText(VerifyCodeActivity.this, e.getMessage(), Toast.LENGTH_LONG).show();
            Log.d("Failed Verification--> ",e.getMessage());

        }

        @Override
        public void onCodeSent(@NonNull String s, @NonNull PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);

            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };
    private void verifyVerificationCode(String code){

        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId,code);

        //signing the user
        signInWithPhoneAuthCredential(credential);


    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential){

        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(VerifyCodeActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {

                        Log.d(TAG, "onVerificationCompleted:" + credential);
                        if(task.isSuccessful()){

                            //verification successful we will start the profile activity
                            Intent intent = new Intent(VerifyCodeActivity.this, setProfileInfo.class);
                            intent.putExtra("phone",phone);
                            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                            startActivity(intent);

                        }else{

                            Log.d(TAG, "onVerificationFailed" + credential);
                            //verification unsuccessful.. display an error message

                            String message = "Something is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }

                            Snackbar snackbar = Snackbar.make(findViewById(R.id.progressbar), message, Snackbar.LENGTH_LONG);
                            snackbar.setAction("Dismiss", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

                                }
                            });
                            snackbar.show();
                        }

                    }
                });
    }
}