package com.bilgeadam.xox.recyclers.contacts;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bilgeadam.xox.R;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ContactAdapter extends RecyclerView.Adapter<ContactsViewHolder> {
    private final List<String> contacts;

    public ContactAdapter(List<String> contacts) {
        this.contacts = contacts;
    }

    @NonNull
    @NotNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.contact_item, parent, false);
        return new ContactsViewHolder(viewHolder);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull ContactsViewHolder holder, int position) {
        holder.getDisplayName().setText(contacts.get(position));
    }

    @Override
    public int getItemCount() {
        return contacts.size();
    }

    public void removeItem(int index){
        contacts.remove(index);
    }
}
