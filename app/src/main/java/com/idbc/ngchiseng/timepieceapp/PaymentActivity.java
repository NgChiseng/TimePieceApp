package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class PaymentActivity extends AppCompatActivity implements View.OnClickListener {

    /* This will define the variables that will use in the creation of the product */
    private EditText cardName, cardNumber, cardExpiration, cardCVV, cardholderAddress, cardholderCity, cardholderState, cardholderPostalCode;
    private AutoCompleteTextView cardholderCountry;
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

        /* This will handler the components corresponding of the layout with the variables defined. */
        cardName = (EditText) findViewById(R.id.payment_card_name);
        cardNumber = (EditText) findViewById(R.id.payment_card_number);
        cardExpiration = (EditText) findViewById(R.id.payment_card_expiration);
        cardCVV = (EditText) findViewById(R.id.payment_card_cvv);
        cardholderAddress = (EditText) findViewById(R.id.payment_card_address);
        cardholderCity = (EditText) findViewById(R.id.payment_card_city);
        cardholderState = (EditText) findViewById(R.id.payment_card_state);
        cardholderPostalCode = (EditText) findViewById(R.id.payment_card_postal_code);
        cardholderCountry = (AutoCompleteTextView) findViewById(R.id.payment_card_country);
        paymentBtn = (Button) findViewById(R.id.payment_card_btn);

        /* This will create the arrayAdapter, and set its with the values corresponding in the
         variable handled.
        */
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this, R.array.countries_array, android.R.layout.simple_list_item_1);
        cardholderCountry.setAdapter(adapter);
        cardholderCountry.setThreshold(1);

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
