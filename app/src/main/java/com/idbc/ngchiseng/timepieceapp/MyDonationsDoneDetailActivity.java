package com.idbc.ngchiseng.timepieceapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDonationsDoneDetailActivity extends AppCompatActivity {

    /*  Method that will onCreate of the MyDonationsDone activity, link its component, and implements the
    onClickListener for receive the click request.
        @date[01/08/2017]
        @author[ChiSeng Ng]
        @param [Bundle] savedInstanceState InstanceState of the activity.
        @return [void]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations_done_detail);

        Resources res = getResources();

        /* Data that received corresponding to the MyDonationsFragment of each methods´s through
        interfaces.
        */
        Intent intent = getIntent();
        int imageId = (int) intent.getExtras().get("ImageId");
        String done = (String) intent.getExtras().get("Done");
        String name = (String) intent.getExtras().get("Name");
        String title = (String) intent.getExtras().get("Title");
        String required = (String) intent.getExtras().get("Required");
        String donationDate = (String) intent.getExtras().get("DonationDate");
        String description = (String) intent.getExtras().get("Description");

        /* This will handler each view component of the layout */
        ImageView announceImage = (ImageView) findViewById(R.id.done_detail_image);
        TextView announceDone = (TextView) findViewById(R.id.done_detail_first_amount);
        TextView announceName = (TextView) findViewById(R.id.done_detail_name);
        TextView announceTitle = (TextView) findViewById(R.id.done_detail_title);
        TextView announceRequired = (TextView) findViewById(R.id.done_detail_required);
        TextView announceDonationDate = (TextView) findViewById(R.id.done_detail_first_date);
        TextView announceDescription = (TextView) findViewById(R.id.done_detail_description);

        /* This will set each value to the view component corresponding in the layout */
        announceImage.setImageResource(imageId);
        announceDone.setText(String.format(res.getString(R.string.donation_item_donated), done));
        announceName.setText(String.format(res.getString(R.string.beneficiary_name),name));
        announceTitle.setText(title);
        announceRequired.setText(String.format(res.getString(R.string.donation_item_required) , required));
        announceDonationDate.setText(String.format(res.getString(R.string.donation_date) , donationDate));
        announceDescription.setText(description);

         /* This will manage the toolbar configuration and addressing. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }
}
