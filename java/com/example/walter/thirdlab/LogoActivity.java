package com.example.walter.thirdlab;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.RelativeLayout;
import android.widget.TextView;


/**
 * Created by walter on 28.09.14.
 */
public class LogoActivity extends Activity {

    private Handler animationHandler = new Handler();
    private Runnable animationRunnable = new Runnable() {
        @Override
        public void run() {
            animation = new AlphaAnimation(0.0f, 1.0f);
            animation.setDuration(1700);
            meinName.setAnimation(animation);
            present.setAnimation(animation);
        }
    };

    private Handler startNextActivity = new Handler();
    private Runnable nexActivityRunnable = new Runnable() {
        @Override
        public void run() {
            next = new Intent(LogoActivity.this, MyActivity.class);
            startActivity(next);
            finish();
        }
    };

    private Intent next;
    private AlphaAnimation animation;
    private RelativeLayout darkBoard;
    private TextView meinName, present;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logo);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        darkBoard = (RelativeLayout) findViewById(R.id.dark_board);
        meinName = (TextView) findViewById(R.id.mein_name_ist);
        present = (TextView) findViewById(R.id.presents);

        Display currentDisplay = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        currentDisplay.getSize(size);
        Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/default.ttf");
        meinName.setTypeface(typeface);
        present.setTypeface(typeface);
        meinName.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 30);
        present.setTextSize(TypedValue.COMPLEX_UNIT_SP, size.y / 44);

        animation = new AlphaAnimation(0.0f, 1.0f);
        animation.setDuration(3000);
        darkBoard.setAnimation(animation);

        //animationHandler.postDelayed(animationRunnable, 2000);
        startNextActivity.postDelayed(nexActivityRunnable, 3000);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        animationHandler.removeCallbacks(animationRunnable);
        animationHandler.removeCallbacks(nexActivityRunnable);
    }
}
