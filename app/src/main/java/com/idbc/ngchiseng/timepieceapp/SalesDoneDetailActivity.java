package com.idbc.ngchiseng.timepieceapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class SalesDoneDetailActivity extends AppCompatActivity {

    /*  Method that will onCreate the tout activity, link its component, and implements the
    onClickListener for receive the click request.
        @date[21/07/2017]
        @author[ChiSeng Ng]
        @param [Bundle] savedInstanceState InstanceState of the activity.
        @return [void]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sales_done_detail);

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
        String paymentDate = (String) intent.getExtras().get("PaymentDate");
        String finishDate = (String) intent.getExtras().get("FinishDate");
        String description = (String) intent.getExtras().get("Description");

        /* This will handler each view component of the layout */
        ImageView announceImage = (ImageView) findViewById(R.id.done_detail_image);
        TextView announcePrice = (TextView) findViewById(R.id.done_detail_price);
        TextView announceName = (TextView) findViewById(R.id.done_detail_name);
        TextView announceAddress = (TextView) findViewById(R.id.done_detail_address);
        TextView announceTitle = (TextView) findViewById(R.id.done_detail_title);
        TextView announceNumItems = (TextView) findViewById(R.id.done_detail_num_items);
        TextView announceTotal = (TextView) findViewById(R.id.done_detail_total);
        TextView announcePaymentDate = (TextView) findViewById(R.id.done_detail_first_date);
        TextView announceFinishDate = (TextView) findViewById(R.id.done_detail_second_date);
        TextView announceDescription = (TextView) findViewById(R.id.done_detail_description);

        /* This will set each value to the view component corresponding in the layout */
        announceImage.setImageResource(imageId);
        announcePrice.setText(price);
        announceName.setText(String.format(res.getString(R.string.buyer_name),name));
        announceAddress.setText(address);
        announceTitle.setText(title);
        announceNumItems.setText(String.format(res.getString(R.string.sales) , quantity));
        announceTotal.setText(String.format(res.getString(R.string.total_paid) , total));
        announcePaymentDate.setText(String.format(res.getString(R.string.payment_date), paymentDate));
        announceFinishDate.setText(String.format(res.getString(R.string.finish_date), finishDate));
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
