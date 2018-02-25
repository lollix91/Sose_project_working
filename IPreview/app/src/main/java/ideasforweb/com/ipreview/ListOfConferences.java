package ideasforweb.com.ipreview;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class ListOfConferences extends AppCompatActivity {

    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_of_conferences);

        //Toolbar toolBar = getActionBar();
        //setSupportActionBar(toolBar);

        myCalendar = Calendar.getInstance();

        final ActionBar actionBar = getSupportActionBar();

        if (actionBar != null) {
            actionBar.setHomeAsUpIndicator(R.drawable.ic_search_white_24dp);
            actionBar.setTitle("Your Conferences");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }



        //to populate the first conferences list, i'll call the manager service
        //with the function managerConferenceListMessageRequestByDate, with the date of today

        listOfConferencesRequest(this);





    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == android.R.id.home) {

            showDatePicker();

            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void showDatePicker() {
        DatePickerDialog dPD = new DatePickerDialog(ListOfConferences.this, date, myCalendar
                .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                myCalendar.get(Calendar.DAY_OF_MONTH));


        dPD.setTitle("Select a Date to Preview Conference.");
        dPD.show();

    }

    DatePickerDialog.OnDateSetListener date = new DatePickerDialog.OnDateSetListener() {

        @Override
        public void onDateSet(DatePicker view, int year, int monthOfYear,
                              int dayOfMonth) {

            myCalendar.set(Calendar.YEAR, year);
            myCalendar.set(Calendar.MONTH, monthOfYear);
            monthOfYear+=1;//january is 0
            myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            conferenceRequestByDate(sdf.format(myCalendar.getTime()), getApplicationContext());
        }

    };

    public void listOfConferencesRequest(final ListOfConferences listOfConferences) {

        final List<Conference> conferences = new ArrayList<Conference>();

        Date d = Calendar.getInstance().getTime();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String curDate =  sdf.format(d);

        RequestParams rp = new RequestParams();
        rp.add("date", curDate);


        HttpUtils.getByUrl(HttpUtils.getBaseUrl()+"getAllConferencesByActualDate", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {

                    JSONObject thisRecord = new JSONObject(response.toString());
                    //innerRecord = innerRecord.getJSONObject("ns1.conferenceType");

                    JSONArray respArray = thisRecord.getJSONArray("ns1.conferenceType");

                    //here i get all the records
                    for(int i=0; i<respArray.length(); i++) {
                        JSONObject innerRecord = respArray.getJSONObject(i);

                        //here i have all the parameters of the record, iterate
                        Conference conference = new Conference();
                        conference.setIdConference(innerRecord.getString("idConference"));
                        conference.setNameEvent(innerRecord.getString("name"));
                        conference.setLocationEvent(innerRecord.getString("city"));

                        //JSONObject dateRecord = innerRecord.getJSONObject("date");
                        //Calendar c = Calendar.getInstance();
                        //c.set(dateRecord.getInt("year"), dateRecord.getInt("month")-1, dateRecord.getInt("day"));
                        //conference.setDateTimeEvent(c.getTime());

                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        conference.setDateTimeEvent(sdf.parse(innerRecord.getString("date")));

                        conference.setUrlImage(new URL(innerRecord.getString("urlImage")));

                        /*
                        List<UrlPDFs> urlPDFs = new ArrayList<UrlPDFs>();
                        JSONArray innerUrlPDFs = innerRecord.getJSONArray("urlpdfs");
                        for(int j=0; j<innerUrlPDFs.length(); j++) {
                            JSONObject innerPDFs = innerUrlPDFs.getJSONObject(j);
                            UrlPDFs urlPdf = new UrlPDFs();

                            urlPdf.setNamePDF(innerPDFs.getString("name"));
                            urlPdf.setUrlPDF(new URL(innerPDFs.getString("url")));

                            urlPDFs.add(urlPdf);
                        }*/

                        List<UrlPDFs> urlPDFs = new ArrayList<UrlPDFs>();
                        UrlPDFs urlPdf = new UrlPDFs();
                        urlPdf.setNamePDF("Attached PDF Document");
                        urlPdf.setUrlPDF(new URL(innerRecord.getString("urlPDFs")));
                        urlPDFs.add(urlPdf);

                        conference.setUrlPDFs(urlPDFs);

                        //now the obj is complete
                        conferences.add(conference);

                    }


                    //here i build the listview
                    String[] stupidArray = new String[conferences.size()];
                    for(int g=0; g<conferences.size(); g++)
                        stupidArray[g] = conferences.get(0).getNameEvent();

                    CustomListAdapter listAdapter = new CustomListAdapter(listOfConferences, conferences, stupidArray);
                    ListView listView = (ListView) findViewById(R.id.listView);
                    listView.setAdapter(listAdapter);

                    listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                        @Override
                        public void onItemClick(AdapterView<?> parent, View view,
                                                int position, long id) {
                            // TODO Auto-generated method stub
                            String itemID = conferences.get(position).getIdConference();

                            Intent intent = new Intent(ListOfConferences.this, EventDetails.class);
                            intent.putExtra("itemID", itemID);
                            startActivity(intent);

                        }
                    });


                } catch ( Exception e) {
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



    public void conferenceRequestByDate(final String conferenceDate, final Context appContext) {


        RequestParams rp = new RequestParams();
        rp.add("date", conferenceDate);

        HttpUtils.getByUrl(HttpUtils.getBaseUrl()+"getConferenceByDate", rp, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                // If the response is JSONObject instead of expected JSONArray
                try {
                    JSONObject innerRecord = new JSONObject(response.toString());

                    String itemID = innerRecord.getString("idConference");

                    Intent intent = new Intent(ListOfConferences.this, EventDetails.class);
                    intent.putExtra("itemID", itemID);
                    startActivity(intent);


                } catch (JSONException e) {
                    // TODO Auto-generated catch block
                    Toast.makeText(getApplicationContext(), "No conference found.", Toast.LENGTH_LONG).show();
                    e.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String error, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "No conference found.", Toast.LENGTH_LONG).show();
            }

            /*
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray timeline) {
                // Pull out the first event on the public timeline

            }
            */
        });
    }





}
