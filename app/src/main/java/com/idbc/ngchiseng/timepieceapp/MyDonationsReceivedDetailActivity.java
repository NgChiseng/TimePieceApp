package com.idbc.ngchiseng.timepieceapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class MyDonationsReceivedDetailActivity extends AppCompatActivity {

    /*  Method that will onCreate of the MyDonationsReceived activity, link its component, and implements the
    onClickListener for receive the click request.
        @date[01/08/2017]
        @author[ChiSeng Ng]
        @param [Bundle] savedInstanceState InstanceState of the activity.
        @return [void]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_donations_received_detail);

        Resources res = getResources();

        /* Data that received corresponding to the MyDonationsFragment of each methods´s through
        interfaces.
        */
        Intent intent = getIntent();
        int imageId = (int) intent.getExtras().get("ImageId");
        String received = (String) intent.getExtras().get("Received");
        String name = (String) intent.getExtras().get("Name");
        String title = (String) intent.getExtras().get("Title");
        String required = (String) intent.getExtras().get("Required");
        String receptionDate = (String) intent.getExtras().get("ReceptionDate");
        String description = (String) intent.getExtras().get("Description");

        /* This will handler each view component of the layout */
        ImageView announceImage = (ImageView) findViewById(R.id.received_detail_image);
        TextView announceReceived = (TextView) findViewById(R.id.received_detail_first_amount);
        TextView announceName = (TextView) findViewById(R.id.received_detail_name);
        TextView announceTitle = (TextView) findViewById(R.id.received_detail_title);
        TextView announceRequired = (TextView) findViewById(R.id.received_detail_required);
        TextView announceReceptionDate = (TextView) findViewById(R.id.received_detail_first_date);
        TextView announceDescription = (TextView) findViewById(R.id.received_detail_description);

        /* This will set each value to the view component corresponding in the layout */
        announceImage.setImageResource(imageId);
        announceReceived.setText(String.format(res.getString(R.string.donation_item_received), received));
        announceName.setText(String.format(res.getString(R.string.donor_name),name));
        announceTitle.setText(title);
        announceRequired.setText(String.format(res.getString(R.string.donation_item_required) , required));
        announceReceptionDate.setText(String.format(res.getString(R.string.reception_date) , receptionDate));
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
