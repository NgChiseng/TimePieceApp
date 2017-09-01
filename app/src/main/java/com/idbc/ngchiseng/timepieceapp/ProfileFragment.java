package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProfileFragment extends Fragment implements View.OnClickListener {

    TextView profileName, profileEmail, profilePhone, profileAddress, profileAnnounces, profileDonations, profilePurchases, profileTotal;
    ImageView profileImage, profileEdit;
    int numberAnnounces, numberDonations, numberPurchases, numberTotal;
    RelativeLayout profileRating;
    SharedPreferences pref_session;
    String name, email, phone, address;

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
        final View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

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

        /* This will obtain the session SharedPreferences values */
        pref_session = getActivity().getSharedPreferences("session", 0);
        name = pref_session.getString("first_name", null);
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
        if (image != null) {
            Uri photo = Uri.parse(image);
            Picasso.with(getContext()).load(photo).into(profileImage);
        }

        /*  This will obtain and set the values are going to show in the profile */
        numberAnnounces = 40;
        numberDonations = 6;
        numberPurchases = 2;
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
        List<FitChartValue> values = new ArrayList<>();

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
        the max or total value and will set the corresponding values in the fitChart.
         */
        final FitChart fitChart = (FitChart) profileView.findViewById(R.id.profile_fitChart);
        fitChart.setMinValue(0);
        fitChart.setMaxValue(numberTotal);
        fitChart.setValues(values);

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
}
