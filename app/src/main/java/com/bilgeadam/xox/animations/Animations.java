package com.bilgeadam.xox.animations;

import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class Animations {
    private final static long DURATION = 500;
    private final static float ALPHA = 0.15F;

    /**
     * Rotates given text 360 degree for each 100 ms.
     * @param myText Text to be animated
     * @param duration Duration of the animation
     */
    public void rotateText(@NotNull View myText, Optional<Long> duration){
        long durationValue = duration.orElse(DURATION);
        myText.animate().rotationXBy(360F * durationValue / 100).alpha(ALPHA).setDuration(durationValue);
    }

    /**
     * Moves the button out of the screen
     * @param myButton Button to be moved
     * @param duration Duration of the animation
     * @param currentWindow Current window manager
     */
    public void moveButton(@NotNull View myButton, Optional<Long> duration, WindowManager currentWindow){
        long durationValue = duration.orElse(DURATION);

        // Get current windows boundaries
        Rect bounds = currentWindow.getCurrentWindowMetrics().getBounds();

        // Get current button location
        int[] buttonLocation = new int[2];
        myButton.getLocationInWindow(buttonLocation);

        switch (Direction.generateRandomDirection()){
            case Right:
                myButton.animate().translationXBy(bounds.right - buttonLocation[0]).alpha(ALPHA).setDuration(durationValue);
                break;
            case Left:
                myButton.animate().translationXBy(bounds.left - buttonLocation[0] - myButton.getWidth()).alpha(ALPHA).setDuration(durationValue);
                break;
            case Up:
                myButton.animate().translationYBy(bounds.top - buttonLocation[1] - myButton.getHeight()).alpha(ALPHA).setDuration(durationValue);
                break;
            case Bottom:
                myButton.animate().translationYBy(bounds.bottom - buttonLocation[1]).alpha(ALPHA).setDuration(durationValue);
                break;
        }
    }
}
