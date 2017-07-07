package com.idbc.ngchiseng.timepieceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class ShoppingBagActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_bag);

        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        Button shoppingBagPayBtn = (Button) findViewById(R.id.shopping_bag_btn);
        shoppingBagPayBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.shopping_bag_btn){
            Toast.makeText(v.getContext(), "Click Shopping Bag Pay" , Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }
    }
}
