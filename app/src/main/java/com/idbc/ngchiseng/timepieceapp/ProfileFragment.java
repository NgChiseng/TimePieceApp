package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.txusballesteros.widgets.FitChart;
import com.txusballesteros.widgets.FitChartValue;

import java.util.ArrayList;
import java.util.Collection;

public class ProfileFragment extends Fragment {

    /*  Method that will onCreate the fragment, inflate its View, link its component, and will return
    the render to the main Activity.
        @date[29/06/2017]
        @author[ChiSeng Ng]
        @param [LayoutInflater] inflater The layout inflater corresponding of the caller Activity.
        @param [ViewGroup] container The viewGroup that will content the fragment on the layout.
        @param [Bundle] savedInstanceState InstanceState of the tour activity.
        @return [View] The inflated an rendered view that will show its on the screen.
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View profileView = inflater.inflate(R.layout.fragment_profile, container, false);

        TextView profileName, profileEmail, profilePhone, profileAddress, profileAnnounces, profileDonations, profilePurchases, profileTotal;
        ImageView profileImage, profileEdit;
        int numberAnnounces, numberDonations, numberPurchases, numberTotal;

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

        /*  This will obtain and set the values are going to show in the profile */
        numberAnnounces = 40;
        numberDonations = 6;
        numberPurchases = 2;
        numberTotal = numberAnnounces + numberDonations + numberPurchases;

        Collection<FitChartValue> values = new ArrayList<>();
        values.add(new FitChartValue(numberAnnounces, 0x2d4302));
        values.add(new FitChartValue(numberDonations, 0x75a80d));
        values.add(new FitChartValue(numberPurchases, 0x8fc026));
        // This will handler the fitChart and will declare the min value like 0 percent;
        final FitChart fitChart = (FitChart) profileView.findViewById(R.id.profile_fitChart);
        fitChart.setMinValue(0);
        fitChart.setMaxValue(numberTotal);

        fitChart.setValue(48);

        return profileView;
    }
}
