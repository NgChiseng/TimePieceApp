package com.idbc.ngchiseng.timepieceapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    This will declare the all widgets or elements of the Login screen.
     */
    private AutoCompleteTextView emailField;
    private EditText passwordField;
    private TextView forgotPassword;
    private Button logIn,logUp;
    private ProgressBar logProgressBar;

    /*  Method that will onCreate the login activity, link its component, and implements the
    onClickListener for receive the click request.
        @date[12/06/2017]
        @author[ChiSeng Ng]
        @param [Bundle] savedInstanceState InstanceState of the tour activity.
        @return [void]
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailField = (AutoCompleteTextView) findViewById(R.id.login_email);
        passwordField = (EditText) findViewById(R.id.login_password);
        forgotPassword = (TextView) findViewById(R.id.login_forgot);
        logIn = (Button) findViewById(R.id.login_button);
        logUp = (Button) findViewById(R.id.login_signup);
        logProgressBar = (ProgressBar) findViewById(R.id.login_progressBar);

        forgotPassword.setOnClickListener(this);
        logIn.setOnClickListener(this);
        logUp.setOnClickListener(this);
    }

    /*  Method that handler the event handler with the event listener defined corresponding, in this
        case is used for to start the activity corresponding.
            @date[12/06/2017]
            @author[ChiSeng Ng]
            @param [View] view View or widget that represent the button corresponding in this context.
            @return [void]
    */
    @Override
    public void onClick(View view) {

        Intent intent;

        switch (view.getId()){

            case (R.id.login_button):

                if (isValidEmail(emailField.getText().toString())) {
                    intent = new Intent(this, MainActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intent);
                    finish();
                } else {
                    Toast.makeText(view.getContext(), R.string.email_invalid , Toast.LENGTH_LONG).show();
                }
                break;

            case (R.id.login_signup):
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            case (R.id.login_forgot):
                intent = new Intent(this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
                break;

            default:
                break;
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
}
