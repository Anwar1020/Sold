package com.example.sold;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class MyAuction extends AppCompatActivity {

    private Dialog dialog;
    private String code;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_auction);

        toolbar = findViewById(R.id.myauctiontoolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("hello");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        dialog = new Dialog(this);
        code="abracadabra";
        Bundle bundle = getIntent().getExtras();
        if (bundle != null)
        {
            code=bundle.getString("id").toString();
            showcode();
        }
        Log.d("bid",code);


    }
    private void showcode() {
        dialog.setContentView(R.layout.auctioncodeview);
        TextView codetext = dialog.findViewById(R.id.codeviewid);
        codetext.setText(code);
        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.myauctionmenu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId())
        {
            case R.id.invitejoinidpop :
                showcode();
                return true;

            case R.id.menusettingid :
                Toast.makeText(MyAuction.this,"Valokotha",Toast.LENGTH_SHORT).show();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }
}