package com.mytaxi.android_demo.activities;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.models.Driver;

import java.text.SimpleDateFormat;

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
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        Intent intent = getIntent();
        final Driver driver = intent.getParcelableExtra(EXTRA_DRIVER);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhoneNumber(driver.getPhone());
            }
        });

        TextView textViewName = findViewById(R.id.textViewDriverName);
        textViewName.setText(driver.getName());
        ImageView imageViewAvatar = findViewById(R.id.imageViewDriverAvatar);
        Glide.with(this).load(driver.getAvatar()).apply(RequestOptions.circleCropTransform()).into(imageViewAvatar);
        TextView textViewLocation = findViewById(R.id.textViewDriverLocation);
        textViewLocation.setText(driver.getLocation());
        TextView textViewDate = findViewById(R.id.textViewDriverDate);
        textViewDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(driver.getRegisteredDate()));
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

}
