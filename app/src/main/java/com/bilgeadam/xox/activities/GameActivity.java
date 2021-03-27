package com.bilgeadam.xox.activities;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bilgeadam.xox.R;
import com.bilgeadam.xox.fragments.GameBoard;
import com.bilgeadam.xox.fragments.GameInfo;
import com.bilgeadam.xox.game.Logic;

public class GameActivity extends FragmentActivity {
    public static final String GAME_LOGIC_BUNDLE = "logic";

    private Logic gameLogic;
    private FragmentManager fragmentManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameLogic = new Logic();

        // Create bundle to deliver object to fragments
        Bundle bundle = new Bundle();
        bundle.putSerializable(GAME_LOGIC_BUNDLE, gameLogic);

        // Declare Fragments
        Fragment gameInfo = new GameInfo();
        gameInfo.setArguments(bundle);

        Fragment gameBoard = new GameBoard();

        // Initialize fragments via manager
        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(R.id.game_info_frame, gameInfo);
        transaction.add(R.id.game_board_frame, gameBoard);
        transaction.commit();

        setContentView(R.layout.activity_game);
    }
}
