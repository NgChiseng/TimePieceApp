package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    /* This will define the variables that will use in the creation of the product */
    private EditText cardName, cardNumber, cardExpiration, cardCVV, cardholderAddress, cardholderCity, cardholderState, cardholderPostalCode, cardholderCountry;
    private Button paymentBtn;

    /*  Method that will onCreate of the Payment activity, link its component, and implements the
    onClickListener for receive the click request.
        @date[03/08/2017]
        @author[ChiSeng Ng]
        @param [Bundle] savedInstanceState InstanceState of the activity.
        @return [void]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        /*
        This will define the fonts calling CalligraphyConfig() method to change the letter font
        defined in the assets/fonts/ directory.
         */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/MyriadPro-SemiExt.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );

        setContentView(R.layout.activity_payment);

        /* This will manage the toolbar configuration and addressing. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        paymentBtn = (Button) findViewById(R.id.payment_card_btn);
        /* This will handler the components corresponding of the layout with the variables defined.
        productImage = (ImageView) findViewById(R.id.edit_product_image);
        productPrice = (EditText) findViewById(R.id.edit_product_price);
        productUnit = (EditText) findViewById(R.id.edit_product_unit);
        productAddress = (EditText) findViewById(R.id.edit_product_address);
        productTitle = (EditText) findViewById(R.id.edit_product_title);
        productDescription = (EditText) findViewById(R.id.edit_product_description);
        productBtn = (Button) findViewById(R.id.edit_product_button);

        /* This will set the actual values in the moment, without the modification.
        productImage.setImageResource(imageId);
        productPrice.setText(price);
        productUnit.setText(unit);
        productAddress.setText(address);
        productTitle.setText(title);
        productDescription.setText(description);

        productImage.setOnClickListener(this);*/
        paymentBtn.setOnClickListener(this);
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
       case is used for to start the activity corresponding.
           @date[03/08/2017]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [void]
   */
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.payment_card_btn) {
            Toast.makeText(this, "Click payment", Toast.LENGTH_LONG).show();
        } else {
            super.onBackPressed();
        }
    }

    /*  Method that use the Calligraphy dependence to attach the base context and to can change the
    letter's font corresponding.
           @date[03/08/2017]
           @reference [https://github.com/chrisjenx/Calligraphy/]
           @author[ChiSeng Ng]
           @param [Context] newBase Base Context of the Parent Activity.
           @return [void]
   */
    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
