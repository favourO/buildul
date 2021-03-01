package com.example.life.buildul;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.mikhaellopez.circularimageview.CircularImageView;

public class UploadProfile_Picture extends AppCompatActivity {
    private static final int GALLERY_REQUEST = 1;
    private Uri imageUri = null;
    private StorageReference storageReference;
    private CircularImageView imageView;
    private Button upload;
    private DatabaseReference ref;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_profile__picture);
        mAuth = FirebaseAuth.getInstance();

        /*storageReference = FirebaseStorage.getInstance().getReference().child("Profile_images");
        ref = FirebaseDatabase.getInstance().getReference().child("Users");
        imageView = (CircularImageView) findViewById(R.id.circular_image);
        upload = (Button) findViewById(R.id.upload_image);*/

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImage();
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadProfile();
            }
        });
    }

    private void getImage(){
        storageReference = FirebaseStorage.getInstance().getReference().child("Profile_image");
        Intent galleryIntent = new Intent();
        galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
        galleryIntent.setType("image/*");
        startActivityForResult(galleryIntent, GALLERY_REQUEST);
    }


    /*@Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_REQUEST && resultCode == RESULT_OK){
            Uri imageUri = data.getData();

            CropImage.activity(imageUri)
                    .setGuidelines(CropImageView.Guidelines.ON)
                    .setAspectRatio(1, 1)
                    .start(this);
        }

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
            CropImage.ActivityResult result = CropImage.getActivityResult(data);

            if (resultCode == RESULT_OK){
                imageUri = result.getUri();

                imageView.setImageURI(imageUri);

            }else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE){
                Exception error = result.getError();
            }
        }
    }*/


    private void uploadProfile(){
        final String UID = mAuth.getCurrentUser().getUid();

        if (imageUri != null){
            final StorageReference filepath = storageReference.child(imageUri.getLastPathSegment());

            /*filepath.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    //String downloadUri = taskSnapshot.getD

                    ref.child(UID).child("image").setValue(filepath);
                }
            });*/

        }
    }
}
