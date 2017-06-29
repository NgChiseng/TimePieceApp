package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.AccessToken;
import com.facebook.AccessTokenTracker;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    This will declare the all widgets or elements of the Login screen.
     */
    private AutoCompleteTextView emailField;
    private TextInputEditText passwordField;
    private TextView forgotPassword,signUp;
    private Button logIn,logInFb;
    private ProgressBar logProgressBar;

    private LoginButton loginButtonFb;
    private CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

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
        /*
        This will define the fonts calling CalligraphyConfig() method to change the letter font
        defined in the assets/fonts/ directory.
         */
        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
                .setDefaultFontPath("fonts/MyriadPro-SemiExt.otf")
                .setFontAttrId(R.attr.fontPath)
                .build()
        );
        setContentView(R.layout.activity_login);

        emailField = (AutoCompleteTextView) findViewById(R.id.login_email);
        passwordField = (TextInputEditText) findViewById(R.id.login_password);
        forgotPassword = (TextView) findViewById(R.id.login_forgot);
        signUp = (TextView) findViewById(R.id.login_signup);
        logIn = (Button) findViewById(R.id.login_button);
        logInFb = (Button) findViewById(R.id.login_fb);
        logProgressBar = (ProgressBar) findViewById(R.id.login_progressBar);

        loginButtonFb = (LoginButton) findViewById(R.id.login_buttonfb);

        forgotPassword.setOnClickListener(this);
        signUp.setOnClickListener(this);
        logIn.setOnClickListener(this);

        logInFb.setOnClickListener(this);
        //  This will manage the facebook log in answers(if was success,cancel or occur an error)
        callbackManager = CallbackManager.Factory.create();
        loginButtonFb.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d("TAG", "facebook:onSuccess:" + loginResult);
                //handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Log.d("TAG", "facebook:onCancel");
                // ...
            }
            @Override
            public void onError(FacebookException error) {
                Log.d("TAG", "facebook:onError", error);
                // ...
            }
        });
    }

    /* Facebook Handler
    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);
        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        mAuth.signInWithCredential(credential).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG, "signInWithCredential:onComplete:" + task.isSuccessful());
                        // If sign in fails, display a message to the user. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.w(TAG, "signInWithCredential", task.getException());
                            Toast.makeText(LoginActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                        //...
                    }
        });
    }
    Nota: este handler es un ejemplo de usar la authenticacion con fireBase*/


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);
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

        switch (view.getId()){

            case (R.id.login_button):

                if (isValidEmail(emailField.getText().toString())) {
                    Intent intentLogin = new Intent(this, MainActivity.class);
                    intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentLogin);
                    //finish();
                } else {
                    Toast.makeText(view.getContext(), R.string.email_invalid , Toast.LENGTH_LONG).show();
                }
                break;

            case (R.id.login_forgot):
                Intent intentForgot = new Intent(this, PasswordResetActivity.class);
                intentForgot.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentForgot);
                //finish();
                break;

            case (R.id.login_signup):
                Intent intentSingUp = new Intent(this, RegisterActivity.class);
                intentSingUp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentSingUp);
                //finish();
                break;

            case (R.id.login_fb):
                /* Intent intentFb = new Intent(this, MainActivity.class);
                intentFb.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentFb);
                //finish(); */
                loginButtonFb.performClick();
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
