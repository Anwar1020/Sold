package com.example.sold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class profilepage extends AppCompatActivity {
    private TextView username;
    private Button signout;
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String userid;
    private DatabaseReference myRef;
    private FirebaseDatabase fd;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profilepage);
        mAuth = FirebaseAuth.getInstance();
        toolbar = findViewById(R.id.profiletoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("My Profile");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        username=findViewById(R.id.profusernameid);
        signout=findViewById(R.id.signoutbuttonid);
        currentUser = mAuth.getCurrentUser();
        fd=FirebaseDatabase.getInstance();
        userid=currentUser.getUid().toString();
        Log.d("TS", "Value is: " + userid);
        show_username();
        signout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signoutf();
            }
        });

    }
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
    private void signoutf() {
        Toast.makeText(profilepage.this,"Signing out",Toast.LENGTH_SHORT).show();
        mAuth.signOut();
        Intent intent = new Intent(profilepage.this,loginpage.class);
        startActivity(intent);
        finish();
    }
    private void show_username() {
        myRef=fd.getReference().child("Users").child(userid);
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {
                String s;
                s="Default";
                s=snapshot.child("nickname").getValue(String.class);
                username.setText(s);
               // Toast.makeText(profilepage.this,s+"55555",Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
