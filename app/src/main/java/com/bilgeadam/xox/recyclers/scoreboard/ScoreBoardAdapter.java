package com.bilgeadam.xox.recyclers.scoreboard;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bilgeadam.xox.R;
import com.bilgeadam.xox.data.Score;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ScoreBoardAdapter extends RecyclerView.Adapter<ScoreBoardViewHolder> {
    private final List<Score> scoreList;

    public ScoreBoardAdapter(List<Score> scores) {
        this.scoreList = scores;
    }

    @NonNull
    @NotNull
    @Override
    public ScoreBoardViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        View viewHolder = LayoutInflater.from(parent.getContext()).inflate(R.layout.scoreboard_item, parent, false);
        return new ScoreBoardViewHolder(viewHolder);
    }


    @Override
    public void onBindViewHolder(@NonNull @NotNull ScoreBoardViewHolder holder, int position) {
        Score currentScore = scoreList.get(position);

        holder.getId().setText(String.valueOf(position + 1));
        holder.getName().setText(currentScore.getName());
        holder.getScore().setText(String.valueOf(currentScore.getScore()));
    }

    @Override
    public int getItemCount() {
        return scoreList.size();
    }
}
