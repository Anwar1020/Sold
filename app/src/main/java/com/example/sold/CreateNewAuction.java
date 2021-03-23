package com.example.sold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class CreateNewAuction extends AppCompatActivity {
    private Toolbar toolbar;
    private Button submitauction;
    private FirebaseAuth mAuth;
    private DatabaseReference myRef,myRef2;
    private NewIdGen newIdGen;
    private EditText auctionName,total_money,total_team;
    private String userid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_new_auction);
        toolbar = findViewById(R.id.createauctiontoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Create New Auction:");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        auctionName=findViewById(R.id.auctionnameid);
        total_team=findViewById(R.id.totalteamid);
        total_money=findViewById(R.id.totalauctionamountid);

        //idmaking
        newIdGen = new NewIdGen();

        mAuth = FirebaseAuth.getInstance();
        myRef = FirebaseDatabase.getInstance().getReference("Auctions");
        myRef2 = FirebaseDatabase.getInstance().getReference("Users");

        submitauction = findViewById(R.id.createauctionsubmitbutton);
        submitauction.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               if(newIdGen.ID.equals("NOT"))
               {
                   Toast.makeText(CreateNewAuction.this,"Please Wait a little...",Toast.LENGTH_SHORT).show();

               }
               else {
                   newIdGen.changeid();

                   gotomyauction();
               }

            }
        });
    }
    @Override
    public void onStart() {
        super.onStart();
        FirebaseUser currentUser = mAuth.getCurrentUser();
        userid=currentUser.getUid().toString();

    }

    private void gotomyauction() {
        String name = auctionName.getText().toString().trim();
        String totalMoney = total_money.getText().toString();
        String totalTeam = total_team.getText().toString();
        String auctionId=myRef.push().getKey();
        myRef2.child(userid).child("current_auction").setValue(auctionId);
        HashMap<String,String> info = new HashMap<>();
        info.put("auction_name",name);
        info.put("total_money",totalMoney);
        info.put("total_teams",totalTeam);
        info.put("invite_id",newIdGen.ID);
        myRef.child(auctionId).setValue(info);
        Intent intent = new Intent(CreateNewAuction.this,MyAuction.class);
        intent.putExtra("id",newIdGen.ID);
        startActivity(intent);
        finish();
    }




    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}