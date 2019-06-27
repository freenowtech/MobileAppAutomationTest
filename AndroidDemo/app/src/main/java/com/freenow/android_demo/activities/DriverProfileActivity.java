package com.freenow.android_demo.activities;

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

import com.bumptech.glide.request.RequestOptions;
import com.freenow.android_demo.R;
import com.freenow.android_demo.models.Driver;
import com.freenow.android_demo.utils.GlideApp;

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
        setProfile(driver);
    }

    private void dialPhoneNumber(String phoneNumber) {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:" + phoneNumber));
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivity(intent);
        }
    }

    private void setProfile(final Driver driver) {
        TextView textViewName = findViewById(R.id.textViewDriverName);
        ImageView imageViewAvatar = findViewById(R.id.imageViewDriverAvatar);
        TextView textViewLocation = findViewById(R.id.textViewDriverLocation);
        TextView textViewDate = findViewById(R.id.textViewDriverDate);
        FloatingActionButton fab = findViewById(R.id.fab);

        textViewName.setText(driver.getName());
        GlideApp.with(this).load(driver.getAvatar()).placeholder(R.drawable.ic_driver).apply(RequestOptions.circleCropTransform()).into(imageViewAvatar);
        textViewLocation.setText(driver.getLocation());
        textViewDate.setText(new SimpleDateFormat("yyyy-MM-dd").format(driver.getRegisteredDate()));
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialPhoneNumber(driver.getPhone());
            }
        });
    }

}
