package com.idbc.ngchiseng.timepieceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;

public class PasswordResetActivity extends AppCompatActivity implements View.OnClickListener {

    private AutoCompleteTextView emailField;
    private Button submitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_reset);

        emailField = (AutoCompleteTextView) findViewById(R.id.password_reset_email);
        submitButton = (Button) findViewById(R.id.password_reset_button);

        submitButton.setOnClickListener(this);
    }


    /*  Method that handler the event handler with the event listener defined corresponding, in this
    case is used for to start the activity corresponding. In this case only show the AlertDialog
    and start the login Activity
        @date[12/06/2017]
        @author[ChiSeng Ng]
        @param [View] view View or widget that represent the button corresponding in this context.
        @return [void]
    */
    @Override
    public void onClick(View view) {

    }
}
