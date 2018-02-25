package ideasforweb.com.ipreview;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class PoisActivity extends FragmentActivity implements OnMapReadyCallback {

    private String conferenceID;
    private GoogleMap mMap;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pois);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);


        Intent intent = getIntent();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                conferenceID = null;
            } else {
                conferenceID = extras.getString("itemID");
            }
        }
        else {
            conferenceID = (String)savedInstanceState.getSerializable("itemID");
        }

    }


    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        CameraUpdate cu = CameraUpdateFactory.zoomBy(12);
        mMap.moveCamera(cu);

        poisRequest(conferenceID, getApplicationContext());

    }



    public List<LatLng> poisRequest(final String conferenceID, final Context appContext) {


        RequestParams rp = new RequestParams();
        rp.add("id", conferenceID);

        final List<LatLng> latLngs = new ArrayList<>();

        HttpUtils.getByUrl(HttpUtils.getBaseUrl()+"getJsonPois", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    System.err.println();
                    JSONObject innerRecord = new JSONObject(response.toString());

                    JSONArray innerLatLng = innerRecord.getJSONArray("results");
                    for(int j=0; j<innerLatLng.length(); j++) {
                        JSONObject innerInnerLatLng = innerLatLng.getJSONObject(j);

                        String poiName = innerInnerLatLng.getString("name");
                        JSONObject geometryObj = innerInnerLatLng.getJSONObject("geometry");
                        JSONObject latLonObj = geometryObj.getJSONObject("location");

                        Double lat = latLonObj.getDouble("lat");
                        Double lon = latLonObj.getDouble("lng");
                        LatLng latLng = new LatLng(lat, lon);
                        latLngs.add(latLng);

                        mMap.addMarker(new MarkerOptions().position(latLng).title(poiName));

                        if(j==0)
                            mMap.moveCamera(CameraUpdateFactory.newLatLng(latLng));


                    }



                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }

            /*
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
            */
        });

        return latLngs;
    }




}
