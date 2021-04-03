package com.bilgeadam.xox.activities;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.bilgeadam.xox.R;
import com.bilgeadam.xox.animations.Animations;
import com.bilgeadam.xox.fragments.GameBoard;
import com.bilgeadam.xox.fragments.GameInfo;
import com.bilgeadam.xox.game.Logic;

import java.util.Optional;

public class GameActivity extends FragmentActivity {
    public static final String GAME_KEY = "logic";

    private Logic gameLogic;
    private FragmentManager fragmentManager;
    private Animations animations;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        gameLogic = new Logic();
        animations = new Animations();

        // Create bundle to deliver object to fragments
        Bundle bundle = new Bundle();
        bundle.putSerializable(GAME_KEY, gameLogic);

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

    public void clickImage(View view){
        view.setOnClickListener(null); // Cancels onClick method

        int index = Integer.parseInt((String) view.getTag());

        Log.i(this.getClass().getSimpleName(), String.format("Image tag: %d", index));

        animations.dropDownImage((ImageView) view, gameLogic.getCurrentPlayer().getDrawable(), Optional.of(500L));

        if(gameLogic.processTurn(index / 10 - 1, index % 10 - 1)){
            // Game continues
            refreshGameInfoFragment();
        } else{
            Toast.makeText(this, getString(R.string.game_draw), Toast.LENGTH_LONG).show();
            // Game draw or someone won
            // Go to score activity
        }
    }

    private void refreshGameInfoFragment(){
        Fragment gameInfo = fragmentManager.findFragmentById(R.id.game_info_frame);

        FragmentTransaction transaction =  fragmentManager.beginTransaction();
        transaction.detach(gameInfo);
        transaction.attach(gameInfo);
        transaction.commit();
    }
}
