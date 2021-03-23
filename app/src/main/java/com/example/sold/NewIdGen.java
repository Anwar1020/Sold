package com.example.sold;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;

public class NewIdGen {
    private FirebaseDatabase database;
    private DatabaseReference myRef;
    public String ID="NOT";
    public int previd;
    final private int XOR_VALUE=648527;
    HashMap<Character,Character>hashMap=new HashMap<Character,Character>();
    NewIdGen()
    {
        setHashMap();
        getAuctionId();
    }
    public void setHashMap() {
        hashMap.put('1','j');
        hashMap.put('2','y');
        hashMap.put('3','z');
        hashMap.put('4','x');
        hashMap.put('5','v');
        hashMap.put('6','s');
        hashMap.put('7','h');
        hashMap.put('8','a');
        hashMap.put('9','n');
        hashMap.put('0','p');
    }

    public void getAuctionId()
    {
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("GIVE_AUCTION_ID");
        final String[] auctionid = new String[1];
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull com.google.firebase.database.DataSnapshot snapshot) {

                auctionid[0] =snapshot.getValue().toString();
                Log.d("newid", auctionid[0]);
                processTemp(auctionid[0]);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    private void processTemp(String t) {
        int x=Integer.valueOf(t);
        int a=x^XOR_VALUE;
        t=String.valueOf(a);
        String te="";
        for(int i=0;i<3;i++)
        {
            te+=hashMap.get(t.charAt(i));
        }
        for(int i=3;i<6;i++)
        {
            te+=t.charAt(i);
        }
        ID=te;
        Log.d("newid",ID);
        previd=x+1;
    }


    public void changeid() {
        myRef.setValue(String.valueOf(previd));
    }
}
