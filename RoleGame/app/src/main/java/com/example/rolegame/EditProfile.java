package com.example.rolegame;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.rolegame.DataBase.ProfileDataBaseHelper;
import com.example.rolegame.DataBase.RoleDataBaseHelper;
import com.example.rolegame.Objects.Profile;

import de.hdodenhof.circleimageview.CircleImageView;

public class EditProfile extends AppCompatActivity {

    //Variables
    Button button;
    CircleImageView circleImageView;
    EditText editText;

    String uriPath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        //getting the profile that was clicked on in the activity before.
        Intent getIntent = getIntent();
        Profile profile = (Profile) getIntent.getSerializableExtra("profile"); //current profile

        button = findViewById(R.id.save_button);
        circleImageView = findViewById(R.id.image_button);
        editText = findViewById(R.id.enterPersonName);

        uriPath = profile.getProfileUri(); //getting the profile image.

        //checking if the profile has an image.
        if (profile.getProfileUri() == null)
        {
            circleImageView.setImageResource(R.drawable.nophoto);
        }
        else {
            circleImageView.setImageURI(Uri.parse(String.valueOf(profile.getProfileUri())));
        }

        editText.setText(profile.getName());

        circleImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent();
                i.setType("image/*");
                i.setAction(Intent.ACTION_OPEN_DOCUMENT);
                startActivityForResult(Intent.createChooser(i, "Select Image"), 100);
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProfileDataBaseHelper myDB = new ProfileDataBaseHelper(EditProfile.this);
                myDB.updateData(String.valueOf(profile.getId()), editText.getText().toString(),uriPath);

                Intent intent = new Intent(EditProfile.this, Preparation.class);
                startActivity(intent);
            }
        });
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
                    circleImageView.setImageURI(uri);
                }
            }
        }
    }

    //adds the menu icons to the action bar.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.edit_profile,menu);
        return true;
    }

    //when the user clicks on any of the menu items this function runs.
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        Intent getIntent = getIntent();
        Profile profile = (Profile) getIntent.getSerializableExtra("profile");

        //Deletes the selected role
        if (item.getItemId() == R.id.delete) {
            AlertDialog.Builder alert = new AlertDialog.Builder(EditProfile.this);
            alert.setTitle("Delete " + profile.getName() + "?");
            alert.setMessage("Are you sure you want to delete the profile: " + profile.getName());
            alert.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    ProfileDataBaseHelper myDB = new ProfileDataBaseHelper(EditProfile.this);
                    myDB.deleteOneRow(String.valueOf(profile.getId()));
                    Intent intent = new Intent(EditProfile.this, Preparation.class);
                    startActivity(intent);

                    finish();
                    myDB.close();
                }
            });
            alert.setNegativeButton("No", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alert.create().show();
        }
        return false;
    }
}