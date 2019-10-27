package com.example.tourismof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class Post_desctiption extends AppCompatActivity {

    private Button Next;
    private EditText description;
    private String Rooms, Bathtype,Type,Sharing,Bedrooms, Price;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_desctiption);

        Next = findViewById(R.id.description_button);
        description = findViewById(R.id.post_description_1);

        Intent intent = getIntent();
        Type = intent.getStringExtra("type");
        Bathtype = intent.getStringExtra("bathtype");
        Rooms = intent.getStringExtra("rooms");
        Sharing = intent.getStringExtra("sharing");
        Price = intent.getStringExtra("price");


        Next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(description.getText().toString().isEmpty()){
                    Toast.makeText(getApplicationContext(), "Please Enter description", Toast.LENGTH_SHORT).show();
                }
                else {
                    sendUserToLocationActivity();
                }
            }
        });
    }

    private void sendUserToLocationActivity() {
        Intent newintent = new Intent(Post_desctiption.this, post_location.class);
        newintent.putExtra("type", Type);
        newintent.putExtra("rooms", Rooms);
        newintent.putExtra("bathtype", Bathtype);
        newintent.putExtra("sharing", Sharing);
        newintent.putExtra("price", Price);
        newintent.putExtra("description", description.getText().toString());
        startActivity(newintent);
    }
}
