package com.example.sold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class signuppage extends AppCompatActivity {

    private EditText nickname,signemail,signpassword,confirmsignpassword;
    private Button submit;
    private ProgressBar progressBar ;
    private FirebaseAuth mAuth;
    private DatabaseReference  mdataRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signuppage);
        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("");



        // Text and Button stuff
        nickname = (EditText) findViewById(R.id.addnickname);
        signemail = (EditText) findViewById(R.id.signupemail);
        signpassword = (EditText) findViewById(R.id.signuppassword);
        confirmsignpassword = (EditText) findViewById(R.id.confirmsignuppassword);
        submit = (Button) findViewById(R.id.signupsubmit);
        progressBar = (ProgressBar) findViewById(R.id.progressBar2);

        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userSignup();
            }


        });
    }
    private void userSignup() {
        String email = signemail.getText().toString().trim();
        String password = signpassword.getText().toString();
        String confirmpassword = confirmsignpassword.getText().toString();
        final String snickname = nickname.getText().toString().trim();
        //checking the validity of the email
        if (snickname.isEmpty()) {
            nickname.setError("Enter a kickass nickname");
            nickname.requestFocus();
            return;
        }
        if (email.isEmpty()) {
            signemail.setError("Enter an email address");
            signemail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signemail.setError("Enter a valid email address");
            signemail.requestFocus();
            return;
        }

        //checking the validity of the password
        if (password.isEmpty()) {
            signpassword.setError("Enter a password");
            signpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            signpassword.setError("Password atleast have 6 length");
            signpassword.requestFocus();
            return;
        }
        if (confirmpassword.isEmpty()) {
            confirmsignpassword.setError("Please confirm password");
            confirmsignpassword.requestFocus();
            return;
        }
        if (!(password.equals(confirmpassword))) {
            confirmsignpassword.setError("Your confirm password is not matched");
            confirmsignpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);


        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {
                            ///-- Storing value nickmane uid in databse
                            FirebaseUser currentUser = mAuth.getCurrentUser();
                            String uid = currentUser.getUid();
                            mdataRef = FirebaseDatabase.getInstance().getReference().child("Users").child(uid);
                            HashMap<String,String> info = new HashMap<>();
                            info.put("nickname",snickname);
                            info.put("image","default");
                            info.put("thumbpic","default");
                            info.put("current_auction","NULL");
                            mdataRef.setValue(info).addOnCompleteListener(new OnCompleteListener<Void>() {
                                @Override
                                public void onComplete(@NonNull Task<Void> task) {
                                    if(task.isSuccessful())
                                    {
                                        /// Done--
                                        Toast.makeText(signuppage.this,"Your Signup is Done",Toast.LENGTH_SHORT).show();
                                        Intent intent = new Intent(signuppage.this,MainActivity.class);
                                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else
                                    {
                                        Toast.makeText(signuppage.this,"Your Signup is failed",Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });


                        } else {
                            // If sign in fails, display a message to the user.
                            if(task.getException() instanceof FirebaseAuthUserCollisionException)
                                Toast.makeText(signuppage.this,"You are already registered",Toast.LENGTH_SHORT).show();
                            else
                                Toast.makeText(signuppage.this,"Your Signup is failed",Toast.LENGTH_SHORT).show();
                        }


                    }
                });
    }

}
