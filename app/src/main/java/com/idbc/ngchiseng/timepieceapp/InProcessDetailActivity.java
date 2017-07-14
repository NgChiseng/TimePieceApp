package com.idbc.ngchiseng.timepieceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class InProcessDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_in_process_detail);

        /* Data that received corresponding to the PurchasesFragment of each methods´s through
        interfaces.
        */
        Intent intent = getIntent();
        //int imageId = intent.getIntExtra();
        String title = (String) intent.getExtras().get("Title");

        TextView announceTitle = (TextView) findViewById(R.id.in_process_detail_title);
        announceTitle.setText(title);
    }
}
