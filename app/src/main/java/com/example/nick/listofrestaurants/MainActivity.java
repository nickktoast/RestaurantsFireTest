package com.example.nick.listofrestaurants;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.provider.Settings;
import android.support.annotation.RequiresApi;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import static com.example.nick.listofrestaurants.MainActivity.memes;
import static java.sql.Types.NULL;


public class MainActivity extends AppCompatActivity implements ActivityCompat.OnRequestPermissionsResultCallback {

    private LocationManager locationManager;
    private LocationListener locationListener;
    private Button button;
    private Button button2;
    private Button button3;
    private ImageView imageView;
    private double longitude = NULL;
    private double latitude = NULL;
    private int counter = 0;
    private StorageReference storageReference = FirebaseStorage.getInstance().getReference();
    private StorageReference pathReference = storageReference.child("image");

    public static String[] memes = {
            "deway1.jpg", "deway2.jpg", "deway3.jpg", "deway4.jpg", "deway5.jpg", "deway6.jpg", "deway7.jpg",
            "deway8.jpg"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //mLayout = findViewById(R.id.layout);
        button = findViewById(R.id.button);
        button2 = findViewById(R.id.button2);
        button3 = findViewById(R.id.button3);
        imageView = findViewById(R.id.imageView2);

        StorageReference image = pathReference.child(memes[0]);
        System.out.println(image);
        GlideApp.with(this)
                .load(image)
                .override(600,600)
                .into(imageView);

        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);
        locationListener = new LocationListener() {
            @Override
            public void onLocationChanged(Location location) {
                longitude = location.getLongitude();
                System.out.println(longitude);
                latitude = location.getLatitude();
                System.out.println(latitude);
            }

            @Override
            public void onStatusChanged(String s, int i, Bundle bundle) {

            }

            @Override
            public void onProviderEnabled(String s) {

            }

            @Override
            public void onProviderDisabled(String s) {
                Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
                startActivity(intent);
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                configureButton();
            }
        });
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pictureList();
            }
        });
        button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                view.getId();
                if (counter >= 7){
                    counter = 0;
                }
                else{
                    counter++;
                }
                StorageReference image = pathReference.child(memes[counter]);
                System.out.println(image);
                GlideApp.with(view.getContext())
                        .load(image)
                        .override(600,600)
                        .into(imageView);

            }
        });

        /*
        Button button = (Button) findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showRestaurants();
            }
        });
        */
    }

    private void pictureList(){
        Intent intent = new Intent(this, PictureList.class);
        startActivity(intent);
    }

    private void configureButton(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                requestPermissions(new String[]{
                        Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION,
                        Manifest.permission.INTERNET
                }, 10);
                return;
            }
        }

        button.setOnClickListener(new View.OnClickListener(){
        @SuppressLint("MissingPermission")
            public void onClick (View view){
                locationManager.requestLocationUpdates("gps", 5000, 0, locationListener);
                if (longitude != NULL && latitude != NULL){
                    whenChanged(longitude, latitude);
                }
            }
        });
    }

    private void whenChanged(double longitude, double latitude){
        Intent intent = new Intent(this, DisplayRestaurants.class);
        Bundle b = new Bundle();
        b.putDouble("longitude", longitude);
        b.putDouble("latitude", latitude);
        intent.putExtras(b);
        startActivity(intent);
    }

    public void onRequestPermissionResult(int requestCode, String[] permissions, int[] grantResults){
        switch (requestCode){
            case 10:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    configureButton();
                return;
        }
    }


    /*
    @SuppressLint("MissingPermission")
    public void startRestaurants() {
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {
            Intent intent = new Intent(this, DisplayRestaurants.class);
            LocationManager lm = (LocationManager)getSystemService(Context.LOCATION_SERVICE);
            Location location = lm.getLastKnownLocation(LocationManager.GPS_PROVIDER);
            Double longitude = location.getLongitude();
            Double latitude = location.getLatitude();
            String longit = Double.toString(longitude);
            String lat = Double.toString(latitude);
            intent.putExtra("long", longit);
            intent.putExtra("lat", lat);
            startActivity(intent);
        }
    }

    public void showRestaurants(){
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {
            // Permission is already available, show restaurants
            Snackbar.make(mLayout,
                    "Location permission available. Show restaurants.",
                    Snackbar.LENGTH_SHORT).show();
            startRestaurants();
        } else {
            // Permission is missing and must be requested.
            requestLocationPermission();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions,
                                           int[] grantResults) {
        if (requestCode == PERMISSION_REQUEST_LOCATION) {
            // Request for location permission.
            if (grantResults.length == 1 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Permission has been granted. Start preview Activity.
                Snackbar.make(mLayout, "Location permission granted. Showing restaurants.",
                        Snackbar.LENGTH_SHORT)
                        .show();
                startRestaurants();
            } else {
                // Permission request was denied.
                Snackbar.make(mLayout, "Location permission request was denied.",
                        Snackbar.LENGTH_SHORT)
                        .show();
            }
        }
    }

    private void requestLocationPermission() {
        // Permission has not been granted and must be requested.
        if (ActivityCompat.shouldShowRequestPermissionRationale(this,
                Manifest.permission.ACCESS_FINE_LOCATION)) {
            // Provide an additional rationale to the user if the permission was not granted
            // and the user would benefit from additional context for the use of the permission.
            // Display a SnackBar with a button to request the missing permission.
            Snackbar.make(mLayout, "Location access is required to display restaurants near you.",
                    Snackbar.LENGTH_INDEFINITE).setAction("OK", new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // Request the permission
                    ActivityCompat.requestPermissions(MainActivity.this,
                            new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                            PERMISSION_REQUEST_LOCATION);
                }
            }).show();

        } else {
            Snackbar.make(mLayout,
                    "Permission is not available. Requesting location permission.",
                    Snackbar.LENGTH_SHORT).show();
            // Request the permission. The result will be received in onRequestPermissionResult().
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    PERMISSION_REQUEST_LOCATION);
        }
    }
    */
}

class MyRunnable implements Runnable {

    private Context context;
    private ImageView imageView;

    public MyRunnable(Context context) {
        this.context = context;
    }

    public void run() {

        StorageReference storageReference = FirebaseStorage.getInstance().getReference();
        StorageReference pathReference = storageReference.child("image");
        imageView = (ImageView) ((Activity) context).findViewById(R.id.imageView2);
        for(int i = 0; i < 8; i++){
            StorageReference image = pathReference.child(memes[i]);
            System.out.println(image);
            System.out.println("Got to here\n");
            GlideApp.with(context)
                    .load(image)
                    .override(600,600)
                    .into(imageView);
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if(i == 7){
                i = 0;
            }
            else{
                i++;
            }
        }
    }
}