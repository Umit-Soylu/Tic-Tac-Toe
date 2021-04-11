package com.bilgeadam.xox.recyclers.scoreboard;

import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bilgeadam.xox.R;
import org.jetbrains.annotations.NotNull;

class ScoreBoardViewHolder extends RecyclerView.ViewHolder {
    private final TextView id, name, score;

    public ScoreBoardViewHolder(@NonNull @NotNull View itemView) {
        super(itemView);
        id = itemView.findViewById(R.id.id);
        name = itemView.findViewById(R.id.player_name);
        score = itemView.findViewById(R.id.score);
    }

    protected TextView getId() {
        return id;
    }

    protected TextView getName() {
        return name;
    }

    protected TextView getScore() {
        return score;
    }
}
