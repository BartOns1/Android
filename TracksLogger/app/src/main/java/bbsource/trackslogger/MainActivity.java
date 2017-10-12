package bbsource.trackslogger;

import android.Manifest;
import android.Manifest.permission;
import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.fitness.data.Goal;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.List;



public class MainActivity extends AppCompatActivity implements OnMapReadyCallback, GoogleApiClient.OnConnectionFailedListener {


    private static final int GPSREQUEST_FINE_LOCATION = 777;
    TextView tvCapturedGeoLoc;
    private GoogleApiClient googleApiClient;
    ImageView ivRadar;

    private static final String google_map_key = "AIzaSyBvqVBvWAak2PLh0YEEwWHx780LEXX72kk";
    private double longitude=50.85077;
    private double latitude=4.724099;
    private SupportMapFragment mapFragment;
    private Intent receiverServiceIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivRadar = (ImageView) findViewById(R.id.ivRadar);
        tvCapturedGeoLoc = (TextView) findViewById(R.id.tvCapturedGeoLocation);
        mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        String[] participants= new String[]{"person1", "person2", "person3", "person4",
                "person5", "person6", "person7", "person8", "person9", "person10", "person11"};
        String[] colors= new String[]{"blue", "green", "purple", "black",
                "pink", "yellow", "black", "brown", "white", "red", "darkred"};

        ArrayAdapter<String> aa_names =new ArrayAdapter<String>(this, R.layout.list_os, participants);
        ListView listviewName= (ListView) findViewById(R.id.listViewOS);
        listviewName.setAdapter(aa_names);

        ArrayAdapter<String> aa_colors = new ArrayAdapter<String>(this, R.layout.list_col, colors);
       // ListView listViewCol = (ListView) findViewById(R.id.list_color);
        //listViewCol.setAdapter(aa_colors);


        googleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();
        System.out.println("**************************************************");


        receiverServiceIntent = new Intent(MainActivity.this, BackgroundReceiverService.class);
        startService(receiverServiceIntent);
        Log.d("ReceiverServiceIntent", receiverServiceIntent.toString());






    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopService(receiverServiceIntent);
        Log.d("RrecieverServiceIntent", receiverServiceIntent.toString());

    }





    @Override
    public void onMapReady(GoogleMap googleMap) {

        CameraUpdate center = CameraUpdateFactory.newLatLng(new LatLng(longitude, latitude));
        CameraUpdate zoom = CameraUpdateFactory.zoomTo(12);
        googleMap.moveCamera(center);
        googleMap.animateCamera(zoom);


       // getNearByRestaurent(longitude,latitude , googleMap);
    }

    private void getNearByRestaurent(double lat, double longu, GoogleMap googleMap) {
        googleMap.clear();
        String url = getUrl(lat, longu);
        Object[] DataTransfer = new Object[2];
        DataTransfer[0] = googleMap;
        DataTransfer[1] = url;
        NearByPlacesProvider nearByPlacesProvider = new NearByPlacesProvider();
        nearByPlacesProvider.execute(DataTransfer);
        Toast.makeText(this, "Nearby Restaurants", Toast.LENGTH_LONG).show();

    }

    private String getUrl(double latitude, double longitude) {
        StringBuilder googlePlacesUrl = new StringBuilder("https://maps.googleapis.com/maps/api/place/nearbysearch/json?");
        googlePlacesUrl.append("location=" + latitude + "," + longitude);
        googlePlacesUrl.append("&radius=" + 15000);
        googlePlacesUrl.append("&type=restaurant");
        googlePlacesUrl.append("&sensor=true");
        googlePlacesUrl.append("&key=" + google_map_key);
        Log.d("url", googlePlacesUrl.toString());
        return (googlePlacesUrl.toString());
    }

    private boolean checkPermision() {
        int result = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);
        return result == PackageManager.PERMISSION_GRANTED;
    }

    private void requestPermission() {
        if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
            Toast.makeText(this, "GPS permission needed in order to capture your location", Toast.LENGTH_LONG).show();
        } else {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, GPSREQUEST_FINE_LOCATION);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String permissions[], int[] grantResults) {
        switch (requestCode) {
            case GPSREQUEST_FINE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Snackbar.make(tvCapturedGeoLoc, "Permission Granted, Now you can access location data.", Snackbar.LENGTH_LONG).show();
                } else {
                    Snackbar.make(tvCapturedGeoLoc, "Permission denied, You cannot access locatino data.", Snackbar.LENGTH_LONG).show();
                }
                break;
        }


    }

    LocationListener locationListener = new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            String msg = "Location captured successfully: longitude:%s  latitude:%s";
            msg = String.format(msg, location.getLongitude(), location.getLatitude());
            longitude = location.getLongitude();
            latitude = location.getLatitude();
            mapFragment.getMapAsync(MainActivity.this);

            try {
                msg += "\nStreet: " + getAddressFromLocation(location.getLatitude(), location.getLongitude());
            } catch (IOException e) {
                e.printStackTrace();
            }
            tvCapturedGeoLoc.setText(msg);
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

            Toast.makeText(MainActivity.this, "Provider Enabled", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onProviderDisabled(String provider) {
            Toast.makeText(MainActivity.this, "Provider Disabled", Toast.LENGTH_SHORT).show();
        }
    };

    private String getAddressFromLocation(double latitude, double longitude) throws IOException {
        Geocoder geoCoder = new Geocoder(this);
        List<Address> matches = geoCoder.getFromLocation(latitude, longitude, 1);
        if (matches.size() < 1) return "";
        return (matches.isEmpty() ? null : matches.get(0)).getAddressLine(0).toString();
    }

    public void btnCapturedLocationOnClick(View view) {
        if (checkPermision()) {

        } else {
            requestPermission();
        }
        LocationManager locationManager = (LocationManager) this.getSystemService(Context.LOCATION_SERVICE);

        if (ActivityCompat.checkSelfPermission(this, permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
    }


    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        Toast.makeText(this, "Unable to find nearby restaurants, check your internet connection", Toast.LENGTH_LONG).show();
    }


}
