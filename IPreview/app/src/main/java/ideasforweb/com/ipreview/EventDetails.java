package ideasforweb.com.ipreview;

import android.content.Context;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.media.Image;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Text;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class EventDetails extends AppCompatActivity {

    private Conference conference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_event_details);

        conference = new Conference();

        Intent intent = getIntent();
        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            if (extras == null) {
                conference = null;
            } else {
                conference.setIdConference(extras.getString("itemID"));
            }
        }
        else {
            conference.setIdConference((String)savedInstanceState.getSerializable("itemID"));
        }

        //now i have the conferenct id, populate the page calling the api
        conferenceRequest(conference.getIdConference(), this);

    }






    public void conferenceRequest(final String conferenceID, final Context appContext) {


        RequestParams rp = new RequestParams();
        //rp.add("what", "eventbyid");
        rp.add("id", conferenceID);

        HttpUtils.getByUrl(HttpUtils.getBaseUrl()+"getInfo", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONObject innerRecord = new JSONObject(response.toString());

                    innerRecord = innerRecord.getJSONObject("ns2.managerResponse");
                    conference.setIdConference(innerRecord.getString("conferenceID"));
                    conference.setNameEvent(innerRecord.getString("name"));
                    conference.setLocationEvent(innerRecord.getString("city"));

                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                    conference.setDateTimeEvent(sdf.parse(innerRecord.getString("date")));

                    //JSONObject dateRecord = innerRecord.getJSONObject("date");
                    //Calendar c = Calendar.getInstance();
                    //c.set(dateRecord.getInt("year"), dateRecord.getInt("month")-1, dateRecord.getInt("day"));
                    //conference.setDateTimeEvent(c.getTime());

                    conference.setUrlImage(new URL(innerRecord.getString("urlImage")));

                    List<UrlPDFs> urlPDFs = new ArrayList<UrlPDFs>();
                    UrlPDFs urlPdf = new UrlPDFs();
                    urlPdf.setNamePDF("Attached PDF Document");
                    urlPdf.setUrlPDF(new URL(innerRecord.getString("urlPDFs")));
                    urlPDFs.add(urlPdf);
                    /*
                    JSONArray innerUrlPDFs = innerRecord.getJSONArray("urlpdfs");
                    for(int j=0; j<innerUrlPDFs.length(); j++) {
                        JSONObject innerPDFs = innerUrlPDFs.getJSONObject(j);
                        UrlPDFs urlPdf = new UrlPDFs();

                        urlPdf.setNamePDF(innerPDFs.getString("name"));
                        urlPdf.setUrlPDF(new URL(innerPDFs.getString("url")));

                        urlPDFs.add(urlPdf);
                    }
                    */

                    conference.setUrlPDFs(urlPDFs);



                    //here i build the page
                    TextView eventName = (TextView) findViewById(R.id.eventName);
                    TextView eventDate = (TextView) findViewById(R.id.eventDate);
                    TextView eventLocation = (TextView) findViewById(R.id.eventLocation);
                    Button checkPois = (Button) findViewById(R.id.viewPois);

                    final ImageView eventImage = (ImageView) findViewById(R.id.eventImage);
                    LinearLayout eventPDFs = (LinearLayout) findViewById(R.id.eventPDFs);

                    SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");

                    eventName.setText(conference.getNameEvent());
                    eventDate.setText(df.format(conference.getDateTimeEvent()));
                    eventLocation.setText(conference.getLocationEvent());

                    checkPois.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(EventDetails.this, PoisActivity.class);
                            intent.putExtra("itemID", conferenceID);
                            startActivity(intent);
                        }
                    });

                    Thread thread = new Thread(new Runnable(){
                        @Override
                        public void run() {
                            try {
                                Bitmap bitmap = getBitmapFromURL(conference.getUrlImage());
                                eventImage.setImageBitmap(bitmap);
                            } catch (Exception e) {

                            }
                        }
                    });
                    thread.start();


                    for(int i=0; i<conference.getUrlPDFs().size(); i++) {
                        final TextView textView = new TextView(appContext);
                        textView.setText("Download PDF: "+conference.getUrlPDFs().get(i).getNamePDF());
                        textView.setHint(conference.getUrlPDFs().get(i).getUrlPDF().toString());
                        textView.setTextSize(18.0f);
                        textView.setTextColor(Color.BLUE);

                        textView.setOnClickListener(new TextView.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                TextView clicked = (TextView)v;

                                Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(clicked.getHint().toString()));
                                startActivity(browserIntent);
                            }
                        });
                        eventPDFs.addView(textView);



                        //Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("http://www.google.com"));
                        //startActivity(browserIntent);

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
    }




    public static Bitmap getBitmapFromURL(URL url) {
        try {
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoInput(true);
            connection.connect();
            InputStream input = connection.getInputStream();
            Bitmap myBitmap = BitmapFactory.decodeStream(input);
            return myBitmap;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}
