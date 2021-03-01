package com.example.sold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class CreateNewAuction extends AppCompatActivity {
    private Toolbar toolbar;
    private Button submitauction;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef;
    private String newId,auctionid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_auction);

        toolbar = findViewById(R.id.createauctiontoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create New Auction:");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        mAuth = FirebaseAuth.getInstance();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
         myRef = database.getReference("GIVE_AUCTION_ID");
        submitauction=findViewById(R.id.createauctionsubmitbutton);
        submitauction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                newId=createNewId();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Log.d("Check id","Id= "+newId+auctionid);

            }
        });
    }

    private String createNewId() {

        String id="abc",chgn;
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {

                auctionid=snapshot.getValue().toString();
                Log.d("ABC","   e d   + "+auctionid);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        Log.d("ABCD","   e d   + "+auctionid);
        return id;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}