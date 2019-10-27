package com.example.tourismof;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

public class Post_title extends AppCompatActivity {

    private Button Next;
    private EditText title;
    private Spinner rooms, bathtype,type,sharing;
    private String Rooms, Bathtype,Type,Sharing,Bedrooms;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_title);
        Next = findViewById(R.id.post_title_button);
        rooms = findViewById(R.id.sp_rooms);
        type = findViewById(R.id.sp_type);
        bathtype = findViewById(R.id.sp_bathType);
        sharing = findViewById(R.id.sp_sharing);

        ArrayAdapter<CharSequence> Typeadapter = ArrayAdapter.createFromResource(this,
                R.array.Type,
                R.layout.color_spinner_layout);
        Typeadapter.setDropDownViewResource(R.layout.color_spinner_layout);
        ArrayAdapter<CharSequence> Roomsadapter = ArrayAdapter.createFromResource(this,
                R.array.Rooms,
                R.layout.color_spinner_layout);
        Roomsadapter.setDropDownViewResource(R.layout.color_spinner_layout);
        ArrayAdapter<CharSequence> Bathtypedapter = ArrayAdapter.createFromResource(this,
                R.array.Bathtype,
                R.layout.color_spinner_layout);
        Bathtypedapter.setDropDownViewResource(R.layout.color_spinner_layout);
        ArrayAdapter<CharSequence> Sharingdapter = ArrayAdapter.createFromResource(this,
                R.array.sharing,
                R.layout.color_spinner_layout);
        Sharingdapter.setDropDownViewResource(R.layout.color_spinner_layout);
        rooms.setAdapter(Roomsadapter);
        type.setAdapter(Typeadapter);
        bathtype.setAdapter(Bathtypedapter);
        sharing.setAdapter(Sharingdapter);


        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                Next.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(Post_title.this, Price.class);
                        intent.putExtra("type", type.getSelectedItem().toString());
                        intent.putExtra("rooms", Rooms);
                        intent.putExtra("sharing", Sharing);
                        intent.putExtra("bathtype", Bathtype);
                        startActivity(intent);
                    }
                });

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        rooms.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Rooms = rooms.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        bathtype.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Bathtype = bathtype.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        sharing.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                Sharing = sharing.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }
}
