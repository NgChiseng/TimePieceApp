package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    This will declare the all widgets or elements of the Register screen.
     */
    private EditText nameField, emailField, phoneField;
    private TextInputEditText passwordField;
    private Button signUp;
    private ProgressBar progressBar;

    /*  Method that will onCreate the tout activity, link its component, and implements the
    onClickListener for receive the click request.
        @date[15/06/2017]
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
        setContentView(R.layout.activity_register);

        nameField = (EditText) findViewById(R.id.signup_name_complete);
        passwordField = (TextInputEditText) findViewById(R.id.signup_password);
        emailField = (EditText) findViewById(R.id.signup_email);
        phoneField = (EditText) findViewById(R.id.signup_phone);
        signUp = (Button) findViewById(R.id.signup_btn);
        progressBar = (ProgressBar) findViewById(R.id.signup_progressBar);

        signUp.setOnClickListener(this);
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
            case is used for to start the activity corresponding.
        @date[15/06/2017]
        @author[ChiSeng Ng]
        @param [View] view View or widget that represent the button corresponding in this context.
        @return [void]
    */
    @Override
    public void onClick(View v) {
            if (isValidEmail(emailField.getText().toString())) {
                Intent intentSignUp = new Intent(this, LoginActivity.class);
                intentSignUp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSignUp);
                //finish();
            } else {
                Toast.makeText(v.getContext(), R.string.email_invalid , Toast.LENGTH_LONG).show();
            }
    }

    /*  Method that take a string, and evaluate it, to determine if it is an email.
           @date[12/06/2017]
           @reference [https://stackoverflow.com/questions/24969894/android-email-validation-on-edittext]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [void]
   */
    public static boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /*  Method that use the Calligraphy dependence to attach the base context and to can change the
    letter's font corresponding.
           @date[13/06/2017]
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
