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
import com.bilgeadam.xox.data.Score;
import com.bilgeadam.xox.recyclers.scoreboard.ScoreBoardAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ScoreBoard extends Fragment {
    private final List<Score> scoreList;

    public ScoreBoard(List<Score> scoreList){
        super();
        this.scoreList = scoreList;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View scoreboardView = inflater.inflate(R.layout.fragment_scoreboard, container, false);

        RecyclerView scores = scoreboardView.findViewById(R.id.scoreboard_recycler);
        scores.setLayoutManager(new LinearLayoutManager(scoreboardView.getContext()));
        scores.setAdapter(new ScoreBoardAdapter(scoreList));

        return scoreboardView;
    }
}
