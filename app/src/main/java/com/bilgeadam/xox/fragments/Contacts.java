package com.bilgeadam.xox.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.bilgeadam.xox.R;
import com.bilgeadam.xox.recyclers.contacts.ContactAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class Contacts extends Fragment {
    private final List<String> contactsList;

    public Contacts(List<String> contactsList) {
        this.contactsList = contactsList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View contactsView = inflater.inflate(R.layout.fragment_contacts, container, false);

        RecyclerView contacts = contactsView.findViewById(R.id.contacts_recycler);
        contacts.setLayoutManager(new LinearLayoutManager(contactsView.getContext()));
        contacts.setAdapter(new ContactAdapter(contactsList));

        return contactsView;
    }
}
