package com.idbc.ngchiseng.timepieceapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class SalesInProcessDetailActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_in_process_detail);

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

        /* This will set each value to the view component corresponding in the layout */
        announceImage.setImageResource(imageId);
        announcePrice.setText(price);
        announceName.setText(String.format(res.getString(R.string.buyer_name),name));
        announceAddress.setText(address);
        announceTitle.setText(title);
        announceNumItems.setText(String.format(res.getString(R.string.sales) , quantity));
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
           @date[18/07/2017]
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
                Toast.makeText(v.getContext(), "Click Finalize" , Toast.LENGTH_LONG).show();
                break;
            default:
                super.onBackPressed();
                break;
        }
    }
}
