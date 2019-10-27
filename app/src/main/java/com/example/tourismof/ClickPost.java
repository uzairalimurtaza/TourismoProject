package com.example.tourismof;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Iterator;

import de.hdodenhof.circleimageview.CircleImageView;

public class ClickPost extends AppCompatActivity {

    private TextView text1, text2, text3,text4,text5,text6, Owner_Name_inClickPost, Owner_PHONE;
    private TextView sharig_field, baths_fiels, bedrooms;
    private ImageView imageView;
    private CircleImageView ownerImage_inClickPost;

    private String image, price, description, location,postKey,type, ownerID;
    private DatabaseReference postRef;
    private String newString = "New String", Phone_number,owner_name,owner_image, BathType, Sharing_Status, BedRooms;
    private LinearLayout linearLayout,BedRooms_linearLayout, location_linear, bathrooms;
    private ImageButton chat_with_owner;
    private ViewFlipper viewFlipper;
    private DatabaseReference UserRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_click_post);
        Owner_Name_inClickPost = findViewById(R.id.owner_name_in_clickpost);
        Owner_PHONE = findViewById(R.id.owner_ph_in_clickPost);
        chat_with_owner = findViewById(R.id.chat_with_owner_icon);
        ownerImage_inClickPost = findViewById(R.id.owner_image_in_clickpost);

        text1 = findViewById(R.id.location_post);
        text2 = findViewById(R.id.price_post);
        text3 = findViewById(R.id.description_post);
        text4 = findViewById(R.id.title_post);
        text6 = findViewById(R.id.sharing);
        sharig_field = findViewById(R.id.sharing_post);
        baths_fiels = findViewById(R.id.bathtype);
        bedrooms = findViewById(R.id.No_of_bed_rooms);
        linearLayout = findViewById(R.id.product_page_return_view);
        // BedRooms_linearLayout = findViewById(R.id.bedRooms_linear_layout);
        location_linear = findViewById(R.id.location_linera_layout);
        // bathrooms = findViewById(R.id.bathrooms_layout_click);
        viewFlipper = findViewById(R.id.viewFlipper_slide_show);



        postKey = getIntent().getExtras().get("postKey").toString();
        ownerID = getIntent().getExtras().get("ownerID").toString();


        UserRef = FirebaseDatabase.getInstance().getReference().child("Users");

        UserRef.child(ownerID).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.exists()){

                    Phone_number = dataSnapshot.child("ph_no").getValue().toString();
                    owner_name = dataSnapshot.child("fullname").getValue().toString();
                    owner_image = dataSnapshot.child("Profile").getValue().toString();

                    Owner_PHONE.setText(Phone_number);
                    Owner_Name_inClickPost.setText(owner_name);
                    Picasso.get().load(owner_image).into(ownerImage_inClickPost);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
















        postRef = FirebaseDatabase.getInstance().getReference().child("Posts").child(postKey);

        postRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()){
                    description = dataSnapshot.child("description").getValue().toString();
                    price = dataSnapshot.child("price").getValue().toString();
                    location = dataSnapshot.child("location").getValue().toString();
                    type = dataSnapshot.child("type").getValue().toString();
                    BathType = dataSnapshot.child("bathtype").getValue().toString();
                    Sharing_Status = dataSnapshot.child("sharing").getValue().toString();
                    BedRooms = dataSnapshot.child("rooms").getValue().toString();

                    text1.setText(location);
                    text2.setText(price);
                    text3.setText(description);
                    text4.setText(type);
                    baths_fiels.setText(BathType);
                    sharig_field.setText(Sharing_Status);
                    bedrooms.setText(BedRooms);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        postRef.child("postimages").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                Iterator<DataSnapshot> items = dataSnapshot.getChildren().iterator();
                while (items.hasNext()){
                    DataSnapshot item = items.next();
                    String link = item.getValue().toString();

                    ImageView imageView = new ImageView(ClickPost.this);
                    Picasso.get().load(link).into(imageView);

                    viewFlipper.addView(imageView);

                    viewFlipper.setFlipInterval(2500);
                    viewFlipper.setAutoStart(true);

                    viewFlipper.startFlipping();
                    viewFlipper.setInAnimation(ClickPost.this, android.R.anim.slide_in_left);
                    viewFlipper.setOutAnimation(ClickPost.this, android.R.anim.slide_out_right);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserTodelete();
            }
        });
        chat_with_owner.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                sendUserToContactsActivity();
            }
        });
    }

    private void sendUserToContactsActivity() {
        Intent intent = new Intent(ClickPost.this, Chat.class);
        intent.putExtra("ownerID",ownerID);
        startActivity(intent);
    }

    private void sendownerTochat() {

        Intent intent = new Intent(ClickPost.this, Contacts.class);
        startActivity(intent);


    }

    private void sendUserTodelete() {
        postRef.removeValue();
        sendUserToMainActivity();
        Toast.makeText(this, "post is deleted", Toast.LENGTH_SHORT).show();
    }

    private void sendUserToMainActivity() {
        Intent mainIntent = new Intent(ClickPost.this,FirstActivity.class);
        mainIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(mainIntent);
        finish();
    }

}