package com.example.sold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;

public class loginpage extends AppCompatActivity {
    private TextView signup,forgotpassword;
    private EditText signinemail,signinpassword;
    private Button login;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;
    AlertDialog.Builder alertDialogbuilder;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginpage);
        // Initialize Firebase Auth
        FirebaseApp.initializeApp(this);
        mAuth = FirebaseAuth.getInstance();
        signup = (TextView) findViewById(R.id.signup);
        forgotpassword = (TextView) findViewById(R.id.forgetpassword);
        signinemail = (EditText) findViewById(R.id.signinemail);
        signinpassword = (EditText) findViewById(R.id.signinpassword);
        login = (Button) findViewById(R.id.loginbutton);
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        signup.getPaint().setUnderlineText(true);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(loginpage.this,signuppage.class);
                startActivity(intent);
            }
        });
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userLogin();
            }
        });
        forgotpassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                processForgotpassword();
            }
        });
    }

    private void processForgotpassword() {
        Toast.makeText(loginpage.this,"This feature will added soon...",Toast.LENGTH_SHORT).show();
    }

    private void userLogin() {
        String email = signinemail.getText().toString().trim();
        String password = signinpassword.getText().toString();
        if (email.isEmpty()){
            signinemail.setError("Enter an email address");
            signinemail.requestFocus();
            return;
        }

        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            signinemail.setError("Enter a valid email address");
            signinemail.requestFocus();
            return;
        }

        //checking the validity of the password
        if (password.isEmpty()) {
            signinpassword.setError("Enter a password");
            signinpassword.requestFocus();
            return;
        }
        if (password.length() < 6) {
            signinpassword.setError("Password atleast have 6 length");
            signinpassword.requestFocus();
            return;
        }
        progressBar.setVisibility(View.VISIBLE);
        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.INVISIBLE);
                        if (task.isSuccessful()) {

                            // Sign in success, update UI with the signed-in user's information
                            finish();
                            Intent intent = new Intent(loginpage.this,MainActivity.class);
                            startActivity(intent);


                        } else {

                            Log.e("Shikari", "Error signing in with email link", task.getException());
                            if(task.getException() instanceof FirebaseAuthInvalidUserException)
                                Toast.makeText(loginpage.this,"You are not registered",Toast.LENGTH_SHORT).show();
                            if(task.getException() instanceof FirebaseAuthInvalidCredentialsException)
                            {
                                signinpassword.setError("Your Password is Incorrect");
                                signinpassword.requestFocus();
                            }
                            else
                                Toast.makeText(loginpage.this,"Your Login is Failed",Toast.LENGTH_SHORT).show();

                        }

                        //
                    }
                });

    }


}

