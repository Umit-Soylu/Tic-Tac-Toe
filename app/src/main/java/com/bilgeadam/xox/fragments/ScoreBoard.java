package com.bilgeadam.xox.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;
import com.bilgeadam.xox.R;
import com.bilgeadam.xox.recyclers.scoreboard.ScoreBoardAdapter;
import org.jetbrains.annotations.NotNull;

public class ScoreBoard extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull @NotNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View scoreboardView = inflater.inflate(R.layout.fragment_scoreboard, container, false);
        RecyclerView scores = container.findViewById(R.id.scoreboard_recycler);
        scores.setAdapter(new ScoreBoardAdapter());
        return scoreboardView;
    }
}
