package com.mytaxi.android_demo.activities;

import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.mytaxi.android_demo.App;
import com.mytaxi.android_demo.R;
import com.mytaxi.android_demo.adapters.DriverAdapter;
import com.mytaxi.android_demo.dependencies.component.AppComponent;
import com.mytaxi.android_demo.models.Driver;
import com.mytaxi.android_demo.utils.PermissionHelper;
import com.mytaxi.android_demo.utils.network.HttpClient;
import com.mytaxi.android_demo.utils.storage.SharedPrefStorage;

import javax.inject.Inject;

import static com.mytaxi.android_demo.misc.Constants.DEFAULT_LOCATION;
import static com.mytaxi.android_demo.misc.Constants.DEFAULT_ZOOM;
import static com.mytaxi.android_demo.misc.Constants.LOG_TAG;
import static com.mytaxi.android_demo.utils.PermissionHelper.PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION;

public class MainActivity extends AuthenticatedActivity
        implements NavigationView.OnNavigationItemSelectedListener, OnMapReadyCallback {

    private static final String KEY_LOCATION = "location";

    @Inject
    HttpClient mHttpClient;

    @Inject
    PermissionHelper mPermissionHelper;

    @Inject
    SharedPrefStorage mSharedPrefStorage;

    private GoogleMap mMap;
    private FusedLocationProviderClient mFusedLocationProviderClient;
    private BitmapDescriptor mIconMarker;
    private Location mLastKnownLocation;
    private AutoCompleteTextView mSearchView;
    private DriverAdapter mAdapter;

    @Override
    protected void onResume() {
        super.onResume();
        if (!isAuthenticated()) {
            startActivity(AuthenticationActivity.createIntent(MainActivity.this));
        } else {
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            NavigationView nav = drawer.findViewById(R.id.nav_view);
            ((TextView) nav.getHeaderView(0).findViewById(R.id.nav_username)).setText(mSharedPrefStorage.loadUser().getUsername());
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadInstanceState(savedInstanceState);
        setContentView(R.layout.activity_main);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        AppComponent appComponent = App.getApplicationContext(this).getAppComponent();
        appComponent.inject(this);

        mFusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(this);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getDeviceLocation();
            }
        });

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        mSearchView = findViewById(R.id.textSearch);
        mSearchView.setDropDownAnchor(R.id.searchContainer);
        mHttpClient.fetchDrivers(new HttpClient.DriverCallback() {
            @Override
            public void run() {
                mAdapter = new DriverAdapter(MainActivity.this, mDrivers, new DriverAdapter.OnDriverClickCallback() {
                    @Override
                    public void execute(Driver driver) {
                        startActivity(DriverProfileActivity.createIntent(MainActivity.this, driver));
                    }
                });
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        mSearchView.setAdapter(mAdapter);
                    }
                });
            }
        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        mIconMarker = BitmapDescriptorFactory.fromResource(R.drawable.ic_marker);
        updateLocationUI();
        getDeviceLocation();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        mPermissionHelper.setLocationPermissionGranted(false);
        switch (requestCode) {
            case PERMISSIONS_REQUEST_ACCESS_FINE_LOCATION: {
                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    mPermissionHelper.setLocationPermissionGranted(true);
                }
            }
        }
        updateLocationUI();
    }

    private void updateLocationUI() {
        if (mMap == null) {
            return;
        }
        try {
            if (mPermissionHelper.isLocationPermissionGranted()) {
            } else {
                mLastKnownLocation = null;
                mPermissionHelper.getLocationPermission(this);
            }
        } catch (SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    private void getDeviceLocation() {
    /*
     * Get the best and most recent location of the device, which may be null in rare
     * cases when a location is not available.
     */
        try {
            if (mPermissionHelper.isLocationPermissionGranted()) {
                Task locationResult = mFusedLocationProviderClient.getLastLocation();
                locationResult.addOnCompleteListener(this, new OnCompleteListener() {
                    @Override
                    public void onComplete(@NonNull Task task) {
                        mMap.clear();
                        if (task.isSuccessful() && ((mLastKnownLocation = (Location) task.getResult()) != null)) {
                            LatLng lastKnownLatLng = new LatLng(mLastKnownLocation.getLatitude(), mLastKnownLocation.getLongitude());
                            mMap.addMarker(new MarkerOptions().position(lastKnownLatLng).draggable(true).icon(mIconMarker));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(lastKnownLatLng, DEFAULT_ZOOM));
                        } else {
                            mMap.addMarker(new MarkerOptions().position(DEFAULT_LOCATION).draggable(true).icon(mIconMarker));
                            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(DEFAULT_LOCATION, DEFAULT_ZOOM));
                        }
                    }
                });
            }
        } catch(SecurityException e)  {
            Log.e("Exception: %s", e.getMessage());
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_logout) {
            mSharedPrefStorage.resetUser();
            Log.i(LOG_TAG, "User is logged out");
            startActivity(AuthenticationActivity.createIntent(MainActivity.this));
        }

        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        if (mMap != null) {
            outState.putParcelable(KEY_LOCATION, mLastKnownLocation);
            super.onSaveInstanceState(outState);
        }
    }

    private void loadInstanceState(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            mLastKnownLocation = savedInstanceState.getParcelable(KEY_LOCATION);
        }
    }

}
