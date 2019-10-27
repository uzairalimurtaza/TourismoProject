package com.example.tourismof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Price extends AppCompatActivity {

    private Button btn;
    private EditText Price;
    private String Rooms, Bathtype,Type,Sharing,Bedrooms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_price);

        btn = findViewById(R.id.price_button);
        Price = findViewById(R.id.post_price);

        Intent intent = getIntent();
        Type = intent.getStringExtra("type");
        Bathtype = intent.getStringExtra("bathtype");
        Rooms = intent.getStringExtra("rooms");
        Sharing = intent.getStringExtra("sharing");


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToPostActivity();
            }
        });

    }

    private void sendUserToPostActivity() {
        Intent newintent = new Intent(com.example.tourismof.Price.this, Post_desctiption.class);
        newintent.putExtra("type", Type);
        newintent.putExtra("rooms", Rooms);
        newintent.putExtra("bathtype", Bathtype);
        newintent.putExtra("sharing", Sharing);
        newintent.putExtra("price", Price.getText().toString());
        startActivity(newintent);
    }
}
