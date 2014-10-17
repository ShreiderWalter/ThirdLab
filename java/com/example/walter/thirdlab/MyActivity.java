package com.example.walter.thirdlab;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.Notification;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.walter.thirdlab.core.PlanetItem;
import com.example.walter.thirdlab.database.DatabaseHandler;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.zip.Inflater;


public class MyActivity extends Activity{

    private SolarGLSurfaceView solarGLSurfaceView;
    private RelativeLayout planetsContainer, mainFrame, votingFrame;
    private LinearLayout horizontalScrollView;
    private LinkedList<HorizontalListItem> items = new LinkedList<HorizontalListItem>();
    private RatingBar ratingBar;

    private Integer currentPlanet;
    private DatabaseHandler databaseHandler;

    private final Planet[] planets = {new Planet("Earth", R.drawable.earth_for_view, R.drawable.earth, 2), new Planet("Moon", R.drawable.moon_for_view, R.drawable.moon, 1),
            new Planet("Mercury", R.drawable.mercury_for_view, R.drawable.mercury_map, 1), new Planet("Venus", R.drawable.venus_for_view, R.drawable.venus_map, 1), new Planet("Mars", R.drawable.mars_for_view, R.drawable.mars_map, 2),
            new Planet("Jupiter", R.drawable.jupiter_for_view, R.drawable.jupiter_map, 4), new Planet("Saturn", R.drawable.saturn_for_view, R.drawable.saturn_map, 3), new Planet("Uranus", R.drawable.uranus_for_view, R.drawable.uranus_map, 3),
            new Planet("Neptune", R.drawable.neptune_for_view, R.drawable.neptune_map, 3), new Planet("Sun", R.drawable.sun_for_view, R.drawable.sun, 5)};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        databaseHandler = new DatabaseHandler(this);
        solarGLSurfaceView = new SolarGLSurfaceView(this);
        planetsContainer = (RelativeLayout) findViewById(R.id.planets_container);
        //ratingBar = (RatingBar) findViewById(R.id.rating_bar);
        votingFrame = (RelativeLayout) findViewById(R.id.vote_frame);
        mainFrame = (RelativeLayout) findViewById(R.id.main_frame);
        planetsContainer.addView(solarGLSurfaceView);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);

        ViewGroup.LayoutParams params = planetsContainer.getLayoutParams();
        params.height = size.y * 2 / 3;
        planetsContainer.setLayoutParams(params);

        params = votingFrame.getLayoutParams();
        params.height = size.y / 3;
        votingFrame.setLayoutParams(params);

        horizontalScrollView = (LinearLayout) findViewById(R.id.horizontal_view);
        final LayoutInflater inflater = LayoutInflater.from(this);
        for(int i = 0; i < planets.length; ++i){
            HorizontalListItem currentLayout = (HorizontalListItem) inflater.inflate(R.layout.list_item, null, false);
            items.add(currentLayout);
            TextView currentText = (TextView) currentLayout.findViewById(R.id.item_label);
            currentText.setText(planets[i].getPlanetName());
            ImageView currentImage = (ImageView) currentLayout.findViewById(R.id.item_picture);
            currentImage.setImageDrawable(getResources().getDrawable(planets[i].getDrawableId()));
            CheckBox currentBox = (CheckBox) currentLayout.findViewById(R.id.item_checkbox);
            currentLayout.setCheckBox(currentBox);
            horizontalScrollView.addView(currentLayout);


        }

        LayoutInflater layoutInflater = LayoutInflater.from(MyActivity.this);
        final LinearLayout currentLayout = (LinearLayout) layoutInflater.inflate(R.layout.rating_bar, null, false);

        for(int i = 0; i < items.size(); ++i){
            items.get(i).getCheckBox().setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    for(int j = 0; j < items.size();  ++j){
                        if(items.get(j).getCheckBox() != (CheckBox)buttonView && isChecked){
                            items.get(j).getCheckBox().setChecked(false);
                        }
                    }
                    for(int j = 0; j < items.size(); ++j){
                        if(items.get(j).getCheckBox().isChecked()) {
                            items.get(j).setBackground(getResources().getDrawable(R.drawable.item_checked));
                            currentPlanet = j;
                            votingFrame.removeAllViews();
                            votingFrame.addView(currentLayout);

                            ratingBar = (RatingBar) currentLayout.findViewById(R.id.rating_bar);
                            ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
                                @Override
                                public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                                    if(currentPlanet != null) {
                                        localHandler.postDelayed(localRunnable, 2000);
                                        votingFrame.removeAllViews();
                                        RelativeLayout progressLayout = (RelativeLayout) inflater.inflate(R.layout.total_rating, null, false);
                                        ProgressBar progressBar = (ProgressBar) progressLayout.findViewById(R.id.progressBar);
                                        ratingBar.setRating(0);
                                        int totalRating = (int)(totalRating(planets[currentPlanet].getPlanetName()) * 20);
                                        progressBar.setProgress(totalRating);
                                        votingFrame.addView(progressLayout);
                                        databaseHandler.addPlanetItem(new PlanetItem(planets[currentPlanet].getPlanetName(), (int) rating));
                                        Toast.makeText(MyActivity.this, "Your vote is accepted. ", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });

                            solarGLSurfaceView.getmRenderer().setmMoon(new Sphere(6, planets[j].getRadius(), planets[j].getMapId()));
                            //solarGLSurfaceView.getmRenderer().loadTexture();
                            //localHandler.postDelayed(localRunnable, 10000);
                            //solarGLSurfaceView.invalidate();
                        } else {
                            items.get(j).setBackground(getResources().getDrawable(R.color.black));
                        }
                    }
                }
            });
        }

    }

    private float totalRating(String name){
        float total = 0.0f;
        int counter = 0;
        ArrayList<PlanetItem> planets = (ArrayList) databaseHandler.getAllPlanetItems();
        for(PlanetItem planetItem: planets){
            if(planetItem.getName().equals(name)){
                total += planetItem.getCurrentRate();
                counter++;
            }
        }
        return total / counter;
    }

    private Handler localHandler = new Handler();
    private Runnable localRunnable = new Runnable() {
        @Override
        public void run() {
            solarGLSurfaceView.invalidate();
        }
    };
}
