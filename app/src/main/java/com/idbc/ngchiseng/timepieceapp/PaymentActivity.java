package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
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

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    /* This will define the variables that will use in the creation of the product */
    private EditText cardName, cardNumber, cardExpiration, cardCVV, cardholderAddress, cardholderCity, cardholderState, cardholderPostalCode;
    private AutoCompleteTextView cardholderCountry;
    private Button paymentBtn;
    private ProgressBar progressBar;
    private TextView paymentTotal;
    private SharedPreferences pref_others;

    /*  Method that will onCreate of the Payment activity, link its component, and implements the
    onClickListener for receive the click request.
        @date[03/08/2017]
        @author[ChiSeng Ng]
        @param [Bundle] savedInstanceState InstanceState of the activity.
        @return [void]
     */
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

        setContentView(R.layout.activity_payment);

        /* This will manage the toolbar configuration and addressing. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        /* This will handler the components corresponding of the layout with the variables defined. */
        cardName = (EditText) findViewById(R.id.payment_card_name);
        cardNumber = (EditText) findViewById(R.id.payment_card_number);
        cardExpiration = (EditText) findViewById(R.id.payment_card_expiration);
        cardCVV = (EditText) findViewById(R.id.payment_card_cvv);
        cardholderAddress = (EditText) findViewById(R.id.payment_card_address);
        cardholderCity = (EditText) findViewById(R.id.payment_card_city);
        cardholderState = (EditText) findViewById(R.id.payment_card_state);
        cardholderPostalCode = (EditText) findViewById(R.id.payment_card_postal_code);
        cardholderCountry = (AutoCompleteTextView) findViewById(R.id.payment_card_country);
        paymentBtn = (Button) findViewById(R.id.payment_card_btn);
        progressBar = (ProgressBar) findViewById(R.id.payment_progressBar);
        paymentTotal = (TextView) findViewById(R.id.payment_card_total);

        pref_others = getSharedPreferences("others", 0);
        paymentTotal.setText(pref_others.getString("shopping_total", null));
        /* This will create the arrayAdapter, and set its with the values corresponding in the
         variable handled.
        */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries_array, android.R.layout.simple_list_item_1);
        cardholderCountry.setAdapter(adapter);
        cardholderCountry.setThreshold(1);

        paymentBtn.setOnClickListener(this);
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
       case is used for to start the activity corresponding.
           @date[03/08/2017]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [void]
   */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.payment_card_btn) {
            //Toast.makeText(this, "Click payment", Toast.LENGTH_LONG).show();
            AttemptPayment attemptPayment = new AttemptPayment();
            attemptPayment.execute("","");
        } else {
            super.onBackPressed();
        }
    }

    /*  Method that use the Calligraphy dependence to attach the base context and to can change the
    letter's font corresponding.
           @date[03/08/2017]
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
    public class AttemptPayment extends AsyncTask<String, Integer, Integer> {

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
                connection.setConnectTimeout(2500);

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
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getBaseContext(), R.string.payment_successful, Toast.LENGTH_SHORT).show();
                    // Declaration of the alert dialog
                    AlertDialog.Builder publicationSelector = new AlertDialog.Builder(PaymentActivity.this,R.style.ListAlertDialogStyle);
                    publicationSelector.setMessage(R.string.payment_successful);
                    publicationSelector.setPositiveButton(R.string.finalize, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(getBaseContext(), MainActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    });
                    publicationSelector.show();
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
