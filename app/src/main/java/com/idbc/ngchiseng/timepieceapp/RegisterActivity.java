package com.idbc.ngchiseng.timepieceapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class RegisterActivity extends AppCompatActivity {

    private EditText nameField, passwordField, emailField, phoneField;
    private Button signUp, signUpFb;

    /*  Method that will onCreate the tout activity, link its component, and implements the
    onClickListener for receive the click request.
        @date[15/06/2017]
        @author[ChiSeng Ng]
        @param [Bundle] savedInstanceState InstanceState of the tour activity.
        @return [void]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }
}
