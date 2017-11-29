package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.DialogPreference;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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


public class DetailFragment extends Fragment implements View.OnClickListener {

    /*  This will declare the global variables that are going to use on the fragment. */
    private TextView numberItems;
    private int numberItemsActual = 1;
    private ProgressBar progressBar;

    /*  Method that will onCreate the fragment, inflate its View, link its component, and will return
    the render to the main Activity.
        @date[05/07/2017]
        @author[ChiSeng Ng]
        @param [LayoutInflater] inflater The layout inflater corresponding of the caller Activity.
        @param [ViewGroup] container The viewGroup that will content the fragment on the layout.
        @param [Bundle] savedInstanceState InstanceState of the MainActivity(contain the data).
        @return [View] The inflated an rendered view that will show its on the screen.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        /* Data that received through the bundle corresponding in the MainActivity of each methods´s
        interfaces.
         */
        int imageId = getArguments().getInt("ImageId");
        String price = getArguments().getString("Price");
        String name = getArguments().getString("Name");
        String address = getArguments().getString("Address");
        String title = getArguments().getString("Title");
        String description = getArguments().getString("Description");

        // Inflate the layout for this fragment
        final View detailView = inflater.inflate(R.layout.fragment_detail, container, false);

        /* This will handler the variables with the corresponding layout views, elements or widgets */
        ImageView announceImage =(ImageView) detailView.findViewById(R.id.detail_image);
        TextView announcePrice = (TextView) detailView.findViewById(R.id.detail_price);
        TextView announceName = (TextView) detailView.findViewById(R.id.detail_name);
        TextView announceAddress = (TextView) detailView.findViewById(R.id.detail_address);
        TextView announceTitle = (TextView) detailView.findViewById(R.id.detail_title);
        Button announceButton = (Button) detailView.findViewById(R.id.detail_button);
        TextView announceDescription = (TextView) detailView.findViewById(R.id.detail_description);
        numberItems = (TextView) detailView.findViewById(R.id.detail_num_items);
        ImageView sumBtn = (ImageView) detailView.findViewById(R.id.detail_sum);
        ImageView subtractionBtn = (ImageView) detailView.findViewById(R.id.detail_subtraction);
        progressBar = (ProgressBar) detailView.findViewById(R.id.fragment_detail_progressBar);

        /* This will set the date corresponding to show it on the screen */
        announceImage.setImageResource(imageId);
        announcePrice.setText(price);
        announceName.setText(name);
        announceAddress.setText(address);
        announceTitle.setText(title);
        announceDescription.setText(description);
        numberItems.setText(Integer.toString(numberItemsActual));

        announceButton.setOnClickListener(this);
        sumBtn.setOnClickListener(this);
        subtractionBtn.setOnClickListener(this);

        return detailView;
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
       case is used for to start the activity corresponding.
           @date[06/07/2017]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [void]
   */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case (R.id.detail_sum):
                numberItemsActual = numberItemsActual + 1;
                numberItems.setText(Integer.toString(numberItemsActual));
                break;
            case (R.id.detail_subtraction):
                if (numberItemsActual > 1){
                    numberItemsActual = numberItemsActual - 1;
                    numberItems.setText(Integer.toString(numberItemsActual));
                }
                break;
            default:
                //Toast.makeText(view.getContext(), "Click add to Shopping Bag" , Toast.LENGTH_LONG).show();
                AttemptDetailFragment attemptDetailFragment = new AttemptDetailFragment();
                attemptDetailFragment.execute("","");
                break;
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Class that will be used for send Log In's data to the server's API and process the response.
    It is made in a AsyncTask because this type of process need run in secondary threads for not to
    saturate the main thread, its params mean <Params, Progress Units, Results>
     */
    public class AttemptDetailFragment extends AsyncTask<String, Integer, Integer> {

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
                connection.setConnectTimeout(1500);

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
                    Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    break;
                case (0):
                    //Toast.makeText(getContext(), R.string.payment_successful, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    // Declaration of the alert dialog
                    AlertDialog.Builder publicationSelector = new AlertDialog.Builder(getActivity(),R.style.ListAlertDialogStyle);
                    publicationSelector.setMessage(R.string.collected_in_shoppingbag);
                    publicationSelector.setPositiveButton(R.string.finalize, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            getActivity().onBackPressed();
                        }
                    });
                    publicationSelector.create().show();
                    break;
                case (1):
                    Toast.makeText(getContext(), R.string.email_password_invalid, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
        }
    }
}
