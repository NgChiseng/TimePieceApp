package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileRatingActivity extends AppCompatActivity implements View.OnClickListener {

    /* This will define the variables that will use in the creation of the product */
    private ListView listAnnounces;
    private TextView profileName, profileEmail, profilePhone, profileAddress;
    private ImageView profileImage, profileEdit;
    private String text;
    SharedPreferences pref_session;
    private ProgressBar progressBar;
    ArrayList<Announce> data;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        This will define the fonts calling CalligraphyConfig() method to change the letter font
        defined in the assets/fonts/ directory.
         */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/MyriadPro-SemiExt.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setContentView(R.layout.activity_profile_rating);

        Resources res = getResources();
        final String operation = res.getString(R.string.profile_rating_operation);

        /* This will manage the toolbar configuration and addressing. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        /* This will handler the components corresponding of the layout with the variables defined. */
        profileName = (TextView) findViewById(R.id.profile_name);
        profileEmail = (TextView) findViewById(R.id.profile_email);
        profilePhone = (TextView) findViewById(R.id.profile_phone);
        profileAddress = (TextView) findViewById(R.id.profile_address);
        profileImage = (ImageView) findViewById(R.id.profile_image);
        profileEdit = (ImageView) findViewById(R.id.profile_edit);
        progressBar = (ProgressBar) findViewById(R.id.profile_rating_progressBar);

        pref_session = getSharedPreferences("session", 0);
        profileName.setText(pref_session.getString("username", null));
        profileEmail.setText(pref_session.getString("email", null));
        profilePhone.setText(pref_session.getString("phone", null));
        profileAddress.setText(pref_session.getString("address", null));
        final String image = pref_session.getString("image", null);
        if (image == "1") {
            //Uri photo = Uri.parse(image);
            //Picasso.with(getContext()).load(photo).into(profileImage);
            profileImage.setImageResource(R.drawable.francisco4);
        } else if (pref_session.getInt("id", 0) == 2){
            profileImage.setImageResource(R.drawable.chi);
        }

        // Initialized ArrayList of Announces
        data = new ArrayList<Announce>();

        /*  This block will obtain and add the date corresponding to each Announce object on the
        ArrayList of Announces.

        data.add(new Announce(R.drawable.circulo_1, "Clases de Cocina", "creó", "1", "12", "MAR", "2017"));
        data.add(new Announce(R.drawable.circulo_1, "Manejo de Redes Sociales", "creó", "1", "12", "MAR", "2017"));
        data.add(new Announce(R.drawable.circulo_3, "Clases de Guitarra", "compró", "1", "12", "MAR", "2017"));
        data.add(new Announce(R.drawable.circulo_3, "Zapatos Artesanales", "compró", "1", "12", "MAR", "2017"));
        data.add(new Announce(R.drawable.circulo_2, "Medicinas para niños", "compró", "2", "12", "MAR", "2017"));

        /*  This will handler the ArrayList of Announces and the data inside its with the screen list
        and components o widgets, using the AnnouncesAdapter.
         */
        data.add(new Announce(R.drawable.circulo_3, "Relojes Viejos", "compró", "2", "28", "NOV", "2017"));
        data.add(new Announce(R.drawable.circulo_3, "Limpieza domestica", "compró", "3", "28", "NOV", "2017"));
        data.add(new Announce(R.drawable.circulo_3, "Chequeo y Reparaciones Electricas", "compró", "1", "28", "NOV", "2017"));

        listAnnounces = (ListView) findViewById(R.id.profile_rating_list);
        listAnnounces.setAdapter(new AnnouncesAdapter(this, R.layout.profile_rating_item, data) {

            /* This will implement the abstract method onEntry(Implemented in AnnounceAdapter), with
            the respective elements and handlers.
             */
            @Override
            public void onEntry(Object entry, View view) {

                if (entry != null){

                    ImageView announceImage = (ImageView) view.findViewById(R.id.profile_rating_item_image);
                    announceImage.setImageResource(((Announce) entry).getImage());

                    TextView announceOperation = (TextView) view.findViewById(R.id.profile_rating_item_operation);
                    text = String.format(operation, ((Announce) entry).getName(), ((Announce) entry).getTitle());
                    announceOperation.setText(text);

                    TextView announcePoint = (TextView) view.findViewById(R.id.profile_rating_item_point);
                    announcePoint.setText(((Announce) entry).getPoint());

                    TextView announceDay = (TextView) view.findViewById(R.id.profile_rating_item_day);
                    announceDay.setText(((Announce) entry).getCurrency());

                    TextView announceMonth = (TextView) view.findViewById(R.id.profile_rating_item_month);
                    announceMonth.setText(((Announce) entry).getPrice());

                    TextView announceYear = (TextView) view.findViewById(R.id.profile_rating_item_year);
                    announceYear.setText(((Announce) entry).getUnit());
                }
            }
        });

        profileEdit.setOnClickListener(this);
        //AttemptProfileRating attemptProfileRating = new AttemptProfileRating();
        //attemptProfileRating.execute("","");
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
      case is used for to start the activity corresponding.
          @date[06/08/2017]
          @author[ChiSeng Ng]
          @param [View] view View or widget that represent the button corresponding in this context.
          @return [void]
  */
    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.profile_edit:
                Intent intent = new Intent(this, ProfileEditActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                //finish();
                break;
            default:
                super.onBackPressed();
                break;
        }
    }

    /*  Method that use the Calligraphy dependence to attach the base context and to can change the
    letter's font corresponding.
           @date[06/08/2017]
           @reference [https://github.com/chrisjenx/Calligraphy/]
           @author[ChiSeng Ng]
           @param [Context] newBase Base Context of the Parent Activity.
           @return [void]
   */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Class that will be used for send Log In's data to the server's API and process the response.
    It is made in a AsyncTask because this type of process need run in secondary threads for not to
    saturate the main thread, its params mean <Params, Progress Units, Results>
     */
    public class AttemptProfileRating extends AsyncTask<String, Integer, Integer> {

        /* Method that is initialized in the main thread before entering the execution of the
        secondary thread
            @date[31/08/2017]
            @author[ChiSeng Ng]
            @param [Void]
            @return [void]
        */
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        /* Method that is initialized in the secondary thread, and will communicate with the
        TimePiece API and manage this connection. In this function is to send validated Log In's
        data to the server's API and process the response. Returns an integer value ([-1..1):
        * -1, if an error occurred during the communication.
        * 0, if everything went OK (redirecting to MainActivity and updating SharedPreferences afterwards).
        * 1, if the credentials provided aren't valid.
            @date[31/08/2017]
            @author[ChiSeng Ng]
            @param [String...] strings All the string that entered like params.
            @return [Integer] Represent the result of the connection operation.
        */
        @Override
        protected Integer doInBackground(String... strings) {

            Integer result = 0;
            try {
                // Defining and initializing server's communication's variables
                String credentials = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8");
                credentials += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");

                URL url = new URL("http://192.168.1.110:8000/api-login/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(2000);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(credentials);
                writer.flush();
                /* This get the JSON object from the response urlConnection and save it the
                "response" variable. In this case the true value is because the value received is a
                JSON object, if received a JSON array object then is false.
                */
                APIResponse response = JSONResponseController.getJsonResponse(connection,true);

                if (response != null){

                    if (response.getStatus()==HttpURLConnection.HTTP_OK) {
                        JSONObject response_body = response.getBody();
                        Log.d("OK", "OK");
                        return 0;

                    } else if (response.getStatus() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        Log.d("BAD", "BAD");
                        result = 1;
                    } else {
                        Log.d("NOT", "FOUND");
                        result = -1;
                    }
                }

            } catch (MalformedURLException e) {
                return result;
            } catch (IOException e) {
                return result;
            } catch (JSONException e) {
                return result;
            }
            return result;
        }

        /* Method that is initialized after the secondary thread finish it execution, and will
        process the doInBackground() method results.
            @date[01/09/2017]
            @author[ChiSeng Ng]
            @param [Integer] anInt Results passed from doInBackground() method.
            @return [Void]
        */
        @Override
        protected void onPostExecute(Integer anInt) {
            switch (anInt) {
                case (-1):
                    Toast.makeText(getBaseContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    break;
                case (0):
                    /*  This block will obtain and add the date corresponding to each Announce object on the
                    ArrayList of Announces.*/
                    data.add(new Announce(R.drawable.circulo_3, "Relojes Viejos", "compró", "2", "28", "NOV", "2017"));
                    data.add(new Announce(R.drawable.circulo_3, "Limpieza domestica", "compró", "3", "28", "NOV", "2017"));
                    data.add(new Announce(R.drawable.circulo_3, "Chequeo y Reparaciones Electricas", "compró", "1", "28", "NOV", "2017"));
                    listAnnounces.deferNotifyDataSetChanged();
                    progressBar.setVisibility(View.GONE);
                    break;
                case (1):
                    Toast.makeText(getBaseContext(), R.string.email_password_invalid, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }
}
