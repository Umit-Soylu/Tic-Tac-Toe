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
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.RecyclerView;
import com.bilgeadam.xox.R;
import com.bilgeadam.xox.animations.Animations;
import com.bilgeadam.xox.fragments.Contacts;
import com.bilgeadam.xox.fragments.ScoreResult;
import com.bilgeadam.xox.recyclers.contacts.ContactAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static android.content.pm.PackageManager.PERMISSION_GRANTED;

public class WelcomeActivity extends AppCompatActivity {
    public static final String X_PLAYER_KEY = "PlayerXName", O_PLAYER_KEY = "PlayerOName";

    private Animations animations;
    private String XPlayerName, OPlayerName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        XPlayerName = null;
        OPlayerName = null;

        super.onCreate(savedInstanceState);

        animations = new Animations();

        setContentView(R.layout.activity_welcome);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull @NotNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == PermissionRequestConfig.READ_CONTACT.ordinal()){
            if (grantResults[0] == PERMISSION_GRANTED)
                generateContactFragment();
            else
                ; //TODO, read input from user
        } else
            Log.d(this.getClass().getSimpleName(), String.format("Unknown onRequestPermissionsResult for %d", requestCode));
    }

    public void clickForContacts(View view){
        // Check for read contact permission
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PERMISSION_GRANTED)
            requestPermissions(new String[]{Manifest.permission.READ_CONTACTS}, PermissionRequestConfig.READ_CONTACT.ordinal());
        else {
            generateContactFragment();
        }
    }

    public void clickContactName(View view){
        String clickedName = (String) ((TextView) view).getText();

        // Remove selected item from adapter
        RecyclerView recyclerView = (RecyclerView) view.getParent().getParent();
        int position = recyclerView.getChildAdapterPosition((View) view.getParent());

        ContactAdapter adapter = (ContactAdapter) recyclerView.getAdapter();
        adapter.removeItem(position);
        adapter.notifyItemRemoved(position);

        if (XPlayerName == null) {
            XPlayerName = clickedName;
            Log.v(this.getClass().getSimpleName(), String.format("Player X name is %s", clickedName));
        } else if (OPlayerName == null) {
            OPlayerName = clickedName;
            Log.v(this.getClass().getSimpleName(), String.format("Player O name is %s", clickedName));

            proceedToScoreActivity();
        }
    }

    public void proceedToScoreActivity(){
        Intent intent = new Intent(this, GameActivity.class);
        intent.putExtra(X_PLAYER_KEY, XPlayerName);
        intent.putExtra(O_PLAYER_KEY, OPlayerName);
        startActivity(intent);
    }

    private void generateContactFragment(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.contact_info, new Contacts(readContacts()));
        transaction.commit();

        // Change visibility status for the new view
        long animationLength = 1500;

        animations.rotateText(findViewById(R.id.welcome_text), Optional.of(animationLength));
        animations.moveButton(findViewById(R.id.start_game), Optional.of(animationLength), getWindowManager());

        Handler handler = new Handler(Looper.getMainLooper());
        handler.postDelayed(() -> findViewById(R.id.contact_info).setVisibility(View.VISIBLE), animationLength);
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