package com.example.rolegame;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.annotation.RequiresPermission;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.rolegame.DataBase.ProfileDataBaseHelper;

import java.security.Permission;

import de.hdodenhof.circleimageview.CircleImageView;

public class NewProfile extends AppCompatActivity {

    //variables
    private static final int PERMISSION_CODE = 1000;

    CircleImageView profileImage;
    Button save_button;
    EditText name;

    String uriPath; //The path of the image uri.

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.add_new_profile);

        uriPath = null; //Resets the uri

        //getting the references
        profileImage = findViewById(R.id.image_button);
        save_button = findViewById(R.id.save_button);
        name = findViewById(R.id.enterPersonName);

        //on clicking the circle image button
        profileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    //if user doesn't have permission ask him for one.
                    if (checkSelfPermission(Manifest.permission.CAMERA) == PackageManager.PERMISSION_DENIED ||
                            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_DENIED) {
                        String[] permission = {Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE};
                        requestPermissions(permission, PERMISSION_CODE);
                    }
                    else {
                        //if permission granted
                        openCamera();
                    }
                }
                else {
                    openCamera();
                }
            }
        });

        //on clicking the save button
        save_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Checks if the user entered a name or not.
                String n = name.getText().toString();
                if (n.matches(""))
                {
                    Toast.makeText(NewProfile.this, "name must be entered to continue", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    //Adds the profile to the database.
                    ProfileDataBaseHelper myDB = new ProfileDataBaseHelper(NewProfile.this);
                    myDB.addProfile(name.getText().toString().trim(), uriPath, true);

                    Intent intent = new Intent(NewProfile.this, Preparation.class);
                    setResult(Activity.RESULT_OK,intent);

                    //Finishes the activity and plays the closing animation.
                    finish();
                }
            }
        });

    }

    private void openCamera() {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE, "New Picture");
        values.put(MediaStore.Images.Media.DESCRIPTION, "From the Camera");
        Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
        uriPath = uri.toString();
        Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT,uri);
        startActivityForResult(cameraIntent, 101);
    }

    //runs when user presses.
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case PERMISSION_CODE:{
                openCamera();
            }
        }
    }

    //Makes it possible to use the image URI in different activities any time.
    @SuppressLint("WrongConstant")
    @RequiresApi(api = Build.VERSION_CODES.KITKAT)

    //The function that lets the user choose an image from the gallery.
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == 100) {
                Uri uri = data.getData();

                if (null != uri) {
                    getContentResolver().takePersistableUriPermission(uri, 1);
                    uriPath = uri.toString();
                    profileImage.setImageURI(uri);
                }
            }
            else if (requestCode == 101) {

                profileImage.setImageURI(Uri.parse(String.valueOf(uriPath)));
            }
        }
    }

    //adds the menu icons to the action bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_profile,menu);
        return true;
    }

    //sets an onClick on the appbar icons that transfers the user the wanted activity.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        //sends the user to the profile list activity.
        if (item.getItemId() == R.id.gallery) {
            Intent i = new Intent();
            i.setType("image/*");
            i.setAction(Intent.ACTION_OPEN_DOCUMENT);
            startActivityForResult(Intent.createChooser(i, "Select Image"), 100);
            return true;
        }
        return false;
    }
}