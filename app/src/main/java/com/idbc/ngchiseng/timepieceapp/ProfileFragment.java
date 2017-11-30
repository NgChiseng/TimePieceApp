package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import org.json.JSONException;
import org.json.JSONObject;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView profileName, profileEmail, profilePhone, profileAddress, profileAnnounces, profileDonations, profilePurchases, profileTotal;
    ImageView profileImage, profileEdit;
    int numberAnnounces, numberDonations, numberPurchases, numberTotal;
    RelativeLayout profileRating;
    ProgressBar profileProgressBar;
    SharedPreferences pref_session;
    int id;
    String name, email, phone, address;
    View profileView;
    FitChart fitChart;
    List<FitChartValue> values;
    /*  Method that will onCreate the fragment, inflate its View, link its component, and will return
    the render to the main Activity.
        @date[29/06/2017]
        @author[ChiSeng Ng]
        @param [LayoutInflater] inflater The layout inflater corresponding of the caller Activity.
        @param [ViewGroup] container The viewGroup that will content the fragment on the layout.
        @param [Bundle] savedInstanceState InstanceState of the activity.
        @return [View] The inflated an rendered view that will show its on the screen.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        profileName = (TextView) profileView.findViewById(R.id.profile_name);
        profileEmail = (TextView) profileView.findViewById(R.id.profile_email);
        profilePhone = (TextView) profileView.findViewById(R.id.profile_phone);
        profileAddress = (TextView) profileView.findViewById(R.id.profile_address);
        profileAnnounces = (TextView) profileView.findViewById(R.id.profile_announces);
        profileDonations = (TextView) profileView.findViewById(R.id.profile_donations);
        profilePurchases = (TextView) profileView.findViewById(R.id.profile_purchases);
        profileTotal = (TextView) profileView.findViewById(R.id.profile_total);
        profileImage = (ImageView) profileView.findViewById(R.id.profile_image);
        profileEdit = (ImageView) profileView.findViewById(R.id.profile_edit);
        profileRating = (RelativeLayout) profileView.findViewById(R.id.profile_rating_relative_layout);
        profileProgressBar = (ProgressBar) profileView.findViewById(R.id.profile_progressBar);

        /* This will obtain the session SharedPreferences id used in the GetUserInfo class to search
         * the corresponding user
         */
        pref_session = getActivity().getSharedPreferences("session", 0);
        id = pref_session.getInt("id", 0);

        /* This will call the GetUserInfo class to obtain the response of the API with the profile
        info corresponding.
        */
        GetUserInfo profileInfo = new GetUserInfo();
        profileInfo.execute();

        values = new ArrayList<>();
        fitChart = (FitChart) profileView.findViewById(R.id.profile_fitChart);

        profileEdit.setOnClickListener(this);
        profileRating.setOnClickListener(this);

        return profileView;
    }

    /*  Method that handler the event handler with the event listener defined corresponding.
           @date[01/09/2017]
           @author[ChiSeng Ng]
           @param [View] v View or widget that represent the button corresponding in this context.
           @return [void]
   */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.profile_edit) {
            Intent intent = new Intent(getContext(), ProfileEditActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //finish();
        } else if (v.getId() == R.id.profile_rating_relative_layout) {
            Intent intent = new Intent(getContext(), ProfileRatingActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intent);
            //finish();
        }
    }

    /* Class that will be used for get the response of the TimePiece API, with the profile
    information updated. It is made in a AsyncTask because this type of process need run in
    secondary threads for not to saturate the main thread, its params mean
    <Params, Progress Units, Results>
    */
    private class GetUserInfo extends AsyncTask<String, Integer, JSONObject> {

        /* Method that is initialized in the main thread before entering the execution of the
        secondary thread
            @date[05/09/2017]
            @author[ChiSeng Ng]
            @param [Void]
            @return [void]
        */
        @Override
        protected void onPreExecute(){
            profileProgressBar.setVisibility(View.VISIBLE);
        }

        /* Method that is initialized in the secondary thread, and will communicate with the
        TimePiece API and manage this connection.
        @date[05/09/2017]
        @author[ChiSeng Ng]
        @param [String...] strings All the string that entered like params.
                @return [Integer] Represent the result of the connection operation.
        */
        @Override
        protected JSONObject doInBackground(String... params) {
            JSONObject response_body = new JSONObject();
            URL url;
            HttpURLConnection urlConnection;
            try {
                url = new URL("http://192.168.1.110:8000/profiles/"+id);
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setConnectTimeout(3000);

                APIResponse response = JSONResponseController.getJsonResponse(urlConnection,true);

                if (response != null) {
                    Log.w("RESPONSE", String.valueOf(response.getBody()));
                    if (response.getStatus() == HttpURLConnection.HTTP_OK) {
                        Log.w("El response status fue", String.valueOf(response.getStatus()));
                        response_body = response.getBody();
                        Log.d("La url de imagen es", response_body.getString("image_profile"));
                        response_body.put("status",0);
                    } else {
                        response_body.put("status",1);
                    }
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
            return response_body;
        }

        /* Method that is initialized after the secondary thread finish it execution, and will
        process the doInBackground() method results.
            @date[04/09/2017]
            @author[ChiSeng Ng]
            @param [Integer] anInt Results passed from doInBackground() method.
            @return [Void]
        */
        @Override
        protected void onPostExecute(JSONObject response) {
            //Log.d("RESPONSE POST",String.valueOf(response.getInt("status")));
            Log.d("Estoy en el:", "onPostExecute");
            Log.w("RESPONSE", response.toString());
            try {
                if (response.getInt("status") == 0) {

                    JSONObject userFkObject = response.getJSONObject("user_fk");
                    String name_obtained = userFkObject.getString("first_name");
                    String email_obtained = userFkObject.getString("email");
                    String phone_obtained = response.getString("phone");
                    String address_obtained = response.getString("address");
                    String image_obtained = response.getString("image_profile");
                    System.out.println("AQUI");
                    System.out.println(image_obtained);
                    Log.w("La imagen obtenida fue", image_obtained);
                    Picasso.with(getContext()).load(image_obtained).into(profileImage);
                    /* This will put the data sent into the session SharedPreference */
                    SharedPreferences.Editor editor = getActivity().getSharedPreferences("session", 0).edit();
                    editor.putString("first_name", name_obtained);
                    editor.putString("email", email_obtained);
                    editor.putString("phone", phone_obtained);
                    editor.putString("address", address_obtained);
                    editor.putString("image", image_obtained);
                    editor.apply();
                    Toast.makeText(getContext(), R.string.data_updated, Toast.LENGTH_SHORT).show();
                }else {
                    //Toast.makeText(getContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                    /* This will obtain the rest of the session SharedPreference value after called the
                    GetUserInfo class.
                    */
                    name = pref_session.getString("username", null);
                    email = pref_session.getString("email", null);
                    phone = pref_session.getString("phone", null);
                    address = pref_session.getString("address", null);
                    final String image = pref_session.getString("image", null);

                    /* This will handler each value of the session SharedPreference with the screen component
                    corresponding.
                    */
                    profileName.setText(name);
                    profileEmail.setText(email);
                    profilePhone.setText(phone);
                    profileAddress.setText(address);
                    if (image == "1" & pref_session.getInt("id", 0) == 1) {
                        //Uri photo = Uri.parse(image);
                        //Picasso.with(getContext()).load(photo).into(profileImage);
                        profileImage.setImageResource(R.drawable.francisco4);
                    } else if (pref_session.getInt("id", 0) == 2){
                        profileImage.setImageResource(R.drawable.chi);
                    }

                    /*  This will obtain and set the values are going to show in the profile */
                    numberAnnounces = pref_session.getInt("announces", 0);
                    numberDonations = pref_session.getInt("donations", 0);
                    numberPurchases = pref_session.getInt("purchases", 0);
                    numberTotal = numberAnnounces + numberDonations + numberPurchases;

                    /* This will set the corresponding values to show on the profile screen */
                    profileAnnounces.setText(Integer.toString(numberAnnounces));
                    profileDonations.setText(Integer.toString(numberDonations));
                    profilePurchases.setText(Integer.toString(numberPurchases));
                    profileTotal.setText(Integer.toString(numberTotal));

                    /* This will create the list array that is going to save each profile data that wil show on
                    the screen. This function is implemented using a fitChart library:
                    @reference https://github.com/javiersantos/FitChart
                    */

                    /* This will order each value from highest to lowest in the array list */
                    if (numberAnnounces >= numberDonations){
                        if (numberAnnounces >= numberPurchases){
                            values.add(new FitChartValue(numberAnnounces, ContextCompat.getColor(getContext(), R.color.circle1)));
                            if (numberDonations >= numberPurchases){
                                values.add(new FitChartValue(numberDonations, ContextCompat.getColor(getContext(), R.color.circle2)));
                                values.add(new FitChartValue(numberPurchases, ContextCompat.getColor(getContext(), R.color.circle3)));
                            } else {
                                values.add(new FitChartValue(numberPurchases, ContextCompat.getColor(getContext(), R.color.circle3)));
                                values.add(new FitChartValue(numberDonations, ContextCompat.getColor(getContext(), R.color.circle2)));
                            }
                        } else {
                            values.add(new FitChartValue(numberPurchases, ContextCompat.getColor(getContext(), R.color.circle3)));
                            values.add(new FitChartValue(numberAnnounces, ContextCompat.getColor(getContext(), R.color.circle1)));
                            values.add(new FitChartValue(numberDonations, ContextCompat.getColor(getContext(), R.color.circle2)));
                        }
                    } else {
                        if (numberDonations >= numberPurchases){
                            values.add(new FitChartValue(numberDonations, ContextCompat.getColor(getContext(), R.color.circle2)));
                            if (numberAnnounces >= numberPurchases){
                                values.add(new FitChartValue(numberAnnounces, ContextCompat.getColor(getContext(), R.color.circle1)));
                                values.add(new FitChartValue(numberPurchases, ContextCompat.getColor(getContext(), R.color.circle3)));
                            } else {
                                values.add(new FitChartValue(numberPurchases, ContextCompat.getColor(getContext(), R.color.circle3)));
                                values.add(new FitChartValue(numberAnnounces, ContextCompat.getColor(getContext(), R.color.circle1)));
                            }
                        } else {
                            values.add(new FitChartValue(numberPurchases, ContextCompat.getColor(getContext(), R.color.circle3)));
                            values.add(new FitChartValue(numberDonations, ContextCompat.getColor(getContext(), R.color.circle2)));
                            values.add(new FitChartValue(numberAnnounces, ContextCompat.getColor(getContext(), R.color.circle1)));
                        }
                    }

                    /* This will handler the fitChart and will declare the min value like 0 percent, will set
                    the max or total value and will set the corresponding values in the fitChart.*/
                    fitChart.setMinValue(0);
                    fitChart.setMaxValue(numberTotal);
                    fitChart.setValues(values);
                    //Toast.makeText(getContext(), R.string.data_updated, Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            profileProgressBar.setVisibility(View.GONE);
        }
    }
}
