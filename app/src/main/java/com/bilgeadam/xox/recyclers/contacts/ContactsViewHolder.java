package com.bilgeadam.xox.recyclers.contacts;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bilgeadam.xox.R;
import org.jetbrains.annotations.NotNull;

class ContactsViewHolder extends RecyclerView.ViewHolder {
    private final TextView displayName;

    ContactsViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        displayName = itemView.findViewById(R.id.contact_name);
    }

    TextView getDisplayName() {
        return displayName;
    }
}
