package com.example.reeadigital.map;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;

import com.example.reeadigital.R;
import com.example.reeadigital.Utils;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapView;
import com.google.android.gms.maps.MapsInitializer;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;

public class MapScreenFragment extends Fragment implements OnSuccessListener<Location>,OnMapReadyCallback{
    private MapView googleMapView;
    Location currentLocation;
    FusedLocationProviderClient fusedLocationProviderClient;

    @NonNull
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = (View) inflater.inflate(R.layout.fragment_map_screen_layout, container, false);
        googleMapView = view.findViewById(R.id.mv_google_map);
        googleMapView.onCreate(savedInstanceState);
        fusedLocationProviderClient = LocationServices
                .getFusedLocationProviderClient(getContext());
        fetchLastLocation();
        return view;
    }

    private void fetchLastLocation() {
        Log.e("Sabid", "FetchLast1");
        if (ActivityCompat.checkSelfPermission(getContext(),
                Manifest.permission.ACCESS_FINE_LOCATION)
                != PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(getContext()
                        , Manifest.permission.ACCESS_COARSE_LOCATION)
                        != PackageManager.PERMISSION_GRANTED) {

            requestPermissions(new String[]{
                    Manifest.permission.ACCESS_FINE_LOCATION}, Utils.REQUEST_CODE);
        } else {
            Log.e("Sabid", "FetchLast1");
            Task<Location> task = fusedLocationProviderClient.getLastLocation();
            task.addOnSuccessListener(this);
        }
    }

    @Override
    public void onMapReady(@NonNull GoogleMap googleMap) {
        LatLng latLng = new LatLng(currentLocation.getLatitude(), currentLocation.getLongitude());
        MarkerOptions markerOptions = new MarkerOptions().position(latLng);
        googleMap.animateCamera(CameraUpdateFactory.newLatLng(latLng));
        googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 20));
        googleMap.addMarker(markerOptions);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    fetchLastLocation();
        } else {
            googleMapView.onResume();
        }
    }

    @Override
    public void onSuccess(Location location) {
        if (location != null) {
            currentLocation = location;
            googleMapView.getMapAsync(this);
        }
        googleMapView.onResume();
    }
}
