package com.mytaxi.android_demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.models.Driver;

public class DriverProfileActivity extends AppCompatActivity {

    private static final String EXTRA_DRIVER = "driver";

    public static Intent createIntent(Activity activity, Driver driver) {
        Intent intent = new Intent(activity, DriverProfileActivity.class);
        intent.putExtra(EXTRA_DRIVER, driver);
        return intent;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_profile);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final Driver driver = intent.getParcelableExtra(EXTRA_DRIVER);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, driver.getName() + ": " + driver.getPhone(), Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        TextView textViewName = (TextView) findViewById(R.id.textViewDriverName);
        textViewName.setText(driver.getName());
        ImageView imageViewAvatar = (ImageView) findViewById(R.id.imageViewDriverAvatar);
        Glide.with(this).load(driver.getAvatar()).into(imageViewAvatar);
    }

}
