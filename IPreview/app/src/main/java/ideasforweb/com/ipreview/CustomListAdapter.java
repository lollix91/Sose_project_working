package ideasforweb.com.ipreview;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.List;

public class CustomListAdapter extends ArrayAdapter<String> {

    private final Activity context;
    private final List<Conference> conferenceList;

    public CustomListAdapter(Activity context, List<Conference> conferenceList, String[] itemname) {
        super(context, R.layout.mylist, itemname);
        // TODO Auto-generated constructor stub

        this.context=context;
        this.conferenceList = conferenceList;
    }

    public View getView(final int position,View view,ViewGroup parent) {
        LayoutInflater inflater=context.getLayoutInflater();
        View rowView=inflater.inflate(R.layout.mylist, null,true);

        TextView txtTitle = (TextView) rowView.findViewById(R.id.item);
        final ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        TextView extratxt = (TextView) rowView.findViewById(R.id.textView1);

        txtTitle.setText(conferenceList.get(position).getNameEvent());

        //imageView.setImageResource(R.drawable.logo);
        Thread thread = new Thread(new Runnable(){
            @Override
            public void run() {
                try {
                    Bitmap bitmap = getBitmapFromURL(conferenceList.get(position).getUrlImage());
                    imageView.setImageBitmap(bitmap);
                } catch (Exception e) {

                }
            }
        });
        thread.start();

        SimpleDateFormat df = new SimpleDateFormat("EEE, d MMM yyyy");

        extratxt.setText(conferenceList.get(position).getLocationEvent()+" - "+ df.format(conferenceList.get(position).getDateTimeEvent()));

        return rowView;

    };


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