package com.example.tourismof;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.icu.util.Calendar;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;

public class Multiple_images extends AppCompatActivity {

    private ImageView imageView;
    private Button button;
    private int Gallery_Pick = 1, uploadCount = 0;
    private Uri ImageUri, individualImage;
    private ArrayList<Uri> ImageList = new ArrayList<Uri>();
    private String postID, posttime,saveCurrentDate, saveCurrentTime,postRandomName;
    private StorageReference PostsImagesRefrence;
    private DatabaseReference UsersRef, PostsRef;
    private ProgressDialog loadingBar;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multiple_images);

        button = findViewById(R.id.Add_post_button);
        imageView = findViewById(R.id.post_image);
        PostsRef = FirebaseDatabase.getInstance().getReference().child("Posts");
        PostsImagesRefrence = FirebaseStorage.getInstance().getReference();

        Calendar calFordDate = Calendar.getInstance();
        SimpleDateFormat currentDate = new SimpleDateFormat("dd-MMMM-yyyy");
        saveCurrentDate = currentDate.format(calFordDate.getTime());
        loadingBar = new ProgressDialog(this);

        Calendar calFordTime = Calendar.getInstance();
        SimpleDateFormat currentTime = new SimpleDateFormat("HH:mm");
        saveCurrentTime = currentTime.format(calFordDate.getTime());

        postRandomName = saveCurrentDate + saveCurrentTime;



        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            postID = extras.getString("postID");
            posttime = extras.getString("posttime");
        }
        Toast.makeText(Multiple_images.this, postID, Toast.LENGTH_SHORT).show();
        Toast.makeText(Multiple_images.this, posttime, Toast.LENGTH_SHORT).show();

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loadingBar.setTitle("Adding Pictures");
                loadingBar.setMessage("Please wait, while we are adding your pictures...");
                loadingBar.show();
                loadingBar.setCanceledOnTouchOutside(true);
                for (uploadCount = 0; uploadCount < ImageList.size(); uploadCount++) {


                    individualImage = ImageList.get(uploadCount);

                    final StorageReference filePath = PostsImagesRefrence.child("Post Images").child(individualImage.getLastPathSegment() + "image "  + postRandomName + ".jpg");
                    filePath.putFile(individualImage).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            filePath.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {

                                    PostsRef.child(postID).child("postimages").push().setValue(String.valueOf(uri)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Toast.makeText(Multiple_images.this, "Post Added Successfully", Toast.LENGTH_SHORT).show();
                                            SendUserToMainActivity();
                                        }
                                    });

                                }
                            });
                        }
                    });
                }

            }
        });

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                galleryIntent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                startActivityForResult(galleryIntent, Gallery_Pick);
            }


        });
    }

    private void SendUserToMainActivity() {

        Intent mainIntent = new Intent(Multiple_images.this, FirstActivity.class);
        loadingBar.dismiss();
        startActivity(mainIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Gallery_Pick && resultCode == RESULT_OK && data != null) {
            if (data.getClipData() != null) {

                int countClipData = data.getClipData().getItemCount();

                int CurrentImageSelect = 0;

                while (CurrentImageSelect < countClipData) {

                    ImageUri = data.getClipData().getItemAt(CurrentImageSelect).getUri();
                    ImageList.add(ImageUri);
                    CurrentImageSelect = CurrentImageSelect + 1;
                    imageView.setImageURI(ImageUri);
                }
            } else {
                Toast.makeText(Multiple_images.this, "Please Selct any 3 images", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
