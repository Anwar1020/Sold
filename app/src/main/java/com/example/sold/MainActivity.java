package com.example.sold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class MainActivity extends AppCompatActivity {
    ///--- FIREBASE AREA-----
    private FirebaseAuth mAuth;
    private FirebaseUser currentUser;
    private String userid;
    private DatabaseReference myRef;
    private FirebaseDatabase fd;
    //Button Area
    private LinearLayout joinauction,createauction;
    private String current_auction;
    //--- TOOLBAR
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        fd=FirebaseDatabase.getInstance();
        if(currentUser!=null)
        {
           setTittle();
        }

        joinauction=findViewById(R.id.joinauctionid);
        createauction=findViewById(R.id.createauctionid);
        joinauction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoOngoingAuction();
            }
        });
        createauction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                gotoMyauctionvsVscreateAuction();
            }
        });

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
             //  Toast.makeText(MainActivity.this,"Flooooooatingggg",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this,profilepage.class);
                startActivity(intent);

            }
        });



    }

    private void gotoMyauctionvsVscreateAuction() {
        try{
        if(current_auction.equals("NULL"))
        {
            Intent intent = new Intent(MainActivity.this,CreateNewAuction.class);
            startActivity(intent);
        }
        else {
            Intent intent = new Intent(MainActivity.this,MyAuction.class);
            startActivity(intent);
        }
        } catch (Exception e) {
            Toast.makeText(MainActivity.this,"Please Wait a little ...",Toast.LENGTH_SHORT).show();
            Log.d("Exception",e.toString());
        }


    }

    private void gotoOngoingAuction() {
        Intent intent = new Intent(MainActivity.this,OnGoingAuction.class);
        startActivity(intent);
    }

    private void setTittle() {
        ///---TOOLBAR area
        toolbar = findViewById(R.id.maintoolbar);
        setSupportActionBar(toolbar);
        userid=currentUser.getUid().toString();
        myRef=fd.getReference().child("Users").child(userid);
        final String[] s = new String[1];
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {

                s[0] ="Default";
                s[0] =snapshot.child("nickname").getValue(String.class);
                current_auction=snapshot.child("current_auction").getValue().toString();
                Log.d("Up",s[0]+"abc "+userid);
                getSupportActionBar().setTitle(s[0]);
                getSupportActionBar().setDisplayShowHomeEnabled(true);


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }


    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        if(currentUser==null)
        {
            Intent intent = new Intent(MainActivity.this,loginpage.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
            finish();
        }

    }
}
