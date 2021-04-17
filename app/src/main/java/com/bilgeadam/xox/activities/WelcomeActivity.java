package com.bilgeadam.xox.activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import com.bilgeadam.xox.R;
import com.bilgeadam.xox.animations.Animations;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class WelcomeActivity extends AppCompatActivity {
    private Animations animations;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        animations = new Animations();

        setContentView(R.layout.activity_welcome);

        // Check for read contact permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PERMISSION_GRANTED){
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PermissionRequestConfig.READ_CONTACT.ordinal());
        } else
           readContacts();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionRequestConfig.READ_CONTACT.ordinal() && grantResults[0] == PERMISSION_GRANTED)
            readContacts();
        else
            Log.d(this.getClass().getSimpleName(), String.format("Unknown onRequestPermissionsResult for %d", requestCode));
    }

    public void clickStart(View view){
        long animationLength = 1500;

        animations.rotateText(findViewById(R.id.welcome_text), Optional.of(animationLength));
        animations.moveButton(findViewById(R.id.start_game), Optional.of(animationLength), getWindowManager());

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> startActivity(new Intent(this, GameActivity.class)), animationLength);
    }

    private List<String> readContacts(){
        List<String> contacts = new ArrayList<>();

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PERMISSION_GRANTED) {
            try (Cursor cursor = getContentResolver().query(ContactsContract.Contacts.CONTENT_URI,
                    new String[]{ContactsContract.Contacts.DISPLAY_NAME},
                    null,
                    null,
                    String.format("%s ASC", ContactsContract.Contacts.DISPLAY_NAME))) {

                while (cursor.moveToNext()) {
                    String newContact = cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    contacts.add(newContact);
                    Log.v(this.getClass().getSimpleName(), String.format("New contact is %s", newContact));
                }
            }
        }
        return contacts;
    }
}