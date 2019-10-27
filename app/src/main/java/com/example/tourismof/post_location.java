package com.example.tourismof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class post_location extends AppCompatActivity {

    private Button btn;
    private EditText location;
    private String Rooms, Bathtype,Type,Sharing,Bedrooms,description, Price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_location);

        btn = findViewById(R.id.Location_button);
        location = findViewById(R.id.Location_of_post);

        Intent intent = getIntent();
        Type = intent.getStringExtra("type");
        Bathtype = intent.getStringExtra("bathtype");
        Rooms = intent.getStringExtra("rooms");
        Sharing = intent.getStringExtra("sharing");
        Price = intent.getStringExtra("price");
        description = intent.getStringExtra("description");



        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent newintent = new Intent(post_location.this, PostActivity.class);
                newintent.putExtra("type", Type);
                newintent.putExtra("rooms", Rooms);
                newintent.putExtra("bathtype", Bathtype);
                newintent.putExtra("sharing", Sharing);
                newintent.putExtra("price", Price);
                newintent.putExtra("description", description);
                newintent.putExtra("location", location.getText().toString());
                if(location.getText().toString().isEmpty()){

                    Toast.makeText(getApplicationContext(), "Please Enter Location", Toast.LENGTH_SHORT).show();
                }
                else {
                    startActivity(newintent);
                }
            }
        });



    }
}
