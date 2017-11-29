package com.idbc.ngchiseng.timepieceapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
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

public class PurchasesInProcessDetailActivity extends AppCompatActivity implements View.OnClickListener {

    SharedPreferences pref_others;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchases_in_process_detail);

        Resources res = getResources();

        /* Data that received corresponding to the PurchasesFragment of each methods´s through
        interfaces.
        */
        Intent intent = getIntent();
        int imageId = (int) intent.getExtras().get("ImageId");
        String price = (String) intent.getExtras().get("Price");
        String name = (String) intent.getExtras().get("Name");
        String address = (String) intent.getExtras().get("Address");
        String title = (String) intent.getExtras().get("Title");
        String quantity = (String) intent.getExtras().get("Quantity");
        String total = (String) intent.getExtras().get("Total");
        String description = (String) intent.getExtras().get("Description");

        /* This will handler each view component of the layout */
        ImageView announceImage = (ImageView) findViewById(R.id.in_process_detail_image);
        TextView announcePrice = (TextView) findViewById(R.id.in_process_detail_price);
        TextView announceName = (TextView) findViewById(R.id.in_process_detail_name);
        TextView announceAddress = (TextView) findViewById(R.id.in_process_detail_address);
        TextView announceTitle = (TextView) findViewById(R.id.in_process_detail_title);
        TextView announceNumItems = (TextView) findViewById(R.id.in_process_detail_num_items);
        TextView announceTotal = (TextView) findViewById(R.id.in_process_detail_total);
        TextView announceDescription = (TextView) findViewById(R.id.in_process_detail_description);
        Button cancelBtn = (Button) findViewById(R.id.in_process_detail_cancel);
        Button finalizeBtn = (Button) findViewById(R.id.in_process_detail_finalize);
        progressBar = (ProgressBar) findViewById(R.id.purchases_in_procress_progressBar);
        pref_others = getSharedPreferences("others", 0);

        /* This will set each value to the view component corresponding in the layout */
        announceImage.setImageResource(imageId);
        announcePrice.setText(price);
        announceName.setText(String.format(res.getString(R.string.seller_name),name));
        announceAddress.setText(address);
        announceTitle.setText(title);
        announceNumItems.setText(String.format(res.getString(R.string.acquisitions) , quantity));
        announceTotal.setText(String.format(res.getString(R.string.total_paid) , total));
        announceDescription.setText(description);

        cancelBtn.setOnClickListener(this);
        finalizeBtn.setOnClickListener(this);

         /* This will manage the toolbar configuration and addressing. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
       case is used for to start the activity corresponding.
           @date[17/07/2017]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [void]
   */
    @Override
    public void onClick(View v) {

        switch(v.getId()) {

            case R.id.in_process_detail_cancel:
                Toast.makeText(v.getContext(), "Click Cancel" , Toast.LENGTH_LONG).show();
                break;
            case R.id.in_process_detail_finalize:
                //Toast.makeText(v.getContext(), "Click Finalize" , Toast.LENGTH_LONG).show();
                PurchasesInProcessDetailActivity.AttemptPurchasesInProcess attemptPurchasesInProcess = new PurchasesInProcessDetailActivity.AttemptPurchasesInProcess();
                attemptPurchasesInProcess.execute("","");
                break;
            default:
                super.onBackPressed();
                break;
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Class that will be used for send Log In's data to the server's API and process the response.
    It is made in a AsyncTask because this type of process need run in secondary threads for not to
    saturate the main thread, its params mean <Params, Progress Units, Results>
     */
    public class AttemptPurchasesInProcess extends AsyncTask<String, Integer, Integer> {

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
                    progressBar.setVisibility(View.GONE);
                    //Toast.makeText(getBaseContext(), R.string.payment_successful, Toast.LENGTH_SHORT).show();
                    // Declaration of the alert dialog
                    SharedPreferences.Editor editor = getSharedPreferences("others", 0).edit();
                    editor.putBoolean("product_finish", true);
                    editor.apply();
                    AlertDialog.Builder publicationSelector = new AlertDialog.Builder(PurchasesInProcessDetailActivity.this,R.style.ListAlertDialogStyle);
                    publicationSelector.setMessage(R.string.successful_confirmation);
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
