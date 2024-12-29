package com.example.abreak;

import android.Manifest;
import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionDeniedResponse;
import com.karumi.dexter.listener.PermissionGrantedResponse;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.single.PermissionListener;

import org.w3c.dom.Text;

import java.io.IOException;
import java.util.List;

public class usermapact extends AppCompatActivity implements OnMapReadyCallback, GoogleMap.OnMapLongClickListener,
        GoogleMap.OnMarkerDragListener{

    SupportMapFragment supportMapFragment;
    FusedLocationProviderClient fusedLocationProviderClient;
    private GoogleMap mMap;
    private Geocoder geocoder;
    private static final String TAG = "MapsActivity";
    private LatLng destinationLocation,userLocation;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_usermapact);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        supportMapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.google_map1);

        supportMapFragment.getMapAsync(this);
        geocoder = new Geocoder(this);


        fusedLocationProviderClient = (FusedLocationProviderClient) LocationServices.getFusedLocationProviderClient(this);

        Dexter.withContext(getApplicationContext()).withPermission(Manifest.permission.ACCESS_FINE_LOCATION)
                .withListener(new PermissionListener() {
                    @Override
                    public void onPermissionGranted(PermissionGrantedResponse permissionGrantedResponse) {
                        getCurrentLocation();
                    }

                    @Override
                    public void onPermissionDenied(PermissionDeniedResponse permissionDeniedResponse) {

                    }

                    @Override
                    public void onPermissionRationaleShouldBeShown(PermissionRequest permissionRequest, PermissionToken permissionToken) {
                        permissionToken.continuePermissionRequest();
                    }
                }).check();


    }

    public void getCurrentLocation() {

        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        Task<Location> task = fusedLocationProviderClient.getLastLocation();
        task.addOnSuccessListener(new OnSuccessListener<Location>() {
            @Override
            public void onSuccess(Location location) {
                supportMapFragment.getMapAsync(new OnMapReadyCallback() {
                    @Override
                    public void onMapReady(@NonNull GoogleMap googleMap) {
                        if(location != null) {
                            LatLng latLng = new LatLng(location.getLatitude(),location.getLongitude());
                            userLocation = latLng;
                            MarkerOptions markerOptions = new MarkerOptions().position(latLng)
                                    .title("Current Location!")
                                    .icon(setIcon(usermapact.this,R.drawable.mappincurrent));
                            googleMap.addMarker(markerOptions);
                            googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng,15));
                        }
                        else{
                            Toast.makeText(usermapact.this, "Please On your Location App permisiions", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public BitmapDescriptor setIcon(Activity context, int drawableID) {

        Drawable drawable = ActivityCompat.getDrawable(context,drawableID);

        drawable.setBounds(0,0,drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight());

        Bitmap bitmap = Bitmap.createBitmap(drawable.getIntrinsicWidth(),drawable.getIntrinsicHeight(),Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);

        drawable.draw(canvas);

        return BitmapDescriptorFactory.fromBitmap(bitmap);

    }

    @Override
    public void onMapLongClick(@NonNull LatLng latLng) {

    }

    @Override
    public void onMarkerDrag(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragEnd(@NonNull Marker marker) {

    }

    @Override
    public void onMarkerDragStart(@NonNull Marker marker) {

    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        mMap = googleMap;

        mMap.setOnMapLongClickListener(this);
        mMap.setOnMarkerDragListener(this);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Locations");
        ValueEventListener valueEventListener = databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot itemSnapshot : snapshot.getChildren()) {
                    LocationHelper locationHelper = itemSnapshot.getValue(LocationHelper.class);
                    Double latitude = locationHelper.getLatitude();
                    Double longitude = locationHelper.getLongitude();
                    String na = locationHelper.getName();
                    try {
                        List<Address> addresses = geocoder.getFromLocation(locationHelper.getLatitude(),locationHelper.getLongitude(),1);
                        Address address = addresses.get(0);
                        String streetAddress = address.getAddressLine(0);
                        //Log.d(TAG, "onDataChange: "+latitude+" "+longitude);
                        LatLng location = new LatLng(latitude,longitude);
                        MarkerOptions mo = new MarkerOptions()
                                .position(location)
                                .title(na);
                        mMap.addMarker(mo);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                    //mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(location,12));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        mMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(@NonNull Marker marker) {
                String add = marker.getTitle();

                DatabaseReference db = FirebaseDatabase.getInstance().getReference().child("Locations").child(add);


                db.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        String phone = String.valueOf(snapshot.child("phno").getValue());
                        Log.d(TAG, "onMarkerClick: "+add+" "+phone);
                        Double lat = (Double) snapshot.child("latitude").getValue();
                        Double lon = (Double) snapshot.child("longitude").getValue();
                        LatLng latLng = new LatLng(lat,lon);
                        destinationLocation = latLng;
                        showBottomDialog(add,phone,lat,lon);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(usermapact.this, "Error", Toast.LENGTH_SHORT).show();
                    }
                });

                return false;
            }
        });

    }

    private void showBottomDialog(String phone,String name,Double lat,Double lon){
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.bottomsheet_layout);

        Button navigateinpopup = dialog.findViewById(R.id.navigateinpopup);
        Button callmechinpopup = dialog.findViewById(R.id.callmechinpopup);
        TextView phoneinpopup = dialog.findViewById(R.id.phoneinpopup);
        TextView nameinpopup = dialog.findViewById(R.id.nameinpopup);


        nameinpopup.setText(name);
        phoneinpopup.setText(phone);

        callmechinpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_DIAL);
                intent.setData(Uri.parse("tel:"+phone));
                startActivity(intent);
            }
        });

        navigateinpopup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation:q="+lat+","+lon+"&mode=d"));
                intent1.setPackage("com.google.android.apps.maps");

                if(intent1.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent1);
                }
            }
        });





        dialog.show();
        dialog.getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getWindow().getAttributes().windowAnimations = R.style.DialogAnimation;
        dialog.getWindow().setGravity(Gravity.BOTTOM);

    }

}