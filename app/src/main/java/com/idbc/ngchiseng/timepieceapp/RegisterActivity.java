package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import org.json.JSONObject;

import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

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

        String name, password, email, phone;

        /* This block will handler each value obtained in the fields with the variable corresponding
        in a string format
         */
        name = nameField.getText().toString();
        password = passwordField.getText().toString();
        email = emailField.getText().toString();
        phone = phoneField.getText().toString();

        /* This block will be used to validate each value introduced by the user and make the
        functionality corresponding.
         */
        if (TextUtils.isEmpty(name)) {
            Toast.makeText(v.getContext(), R.string.name_invalid, Toast.LENGTH_SHORT).show();
        } else if (!(isValidPassword(password))) {
            Toast.makeText(v.getContext(), R.string.password_invalid, Toast.LENGTH_SHORT).show();
        } else if (!(isValidEmail(email))) {
            Toast.makeText(v.getContext(), R.string.email_invalid, Toast.LENGTH_LONG).show();
        } else if (!(isValidPhone(phone))) {
            Toast.makeText(v.getContext(), R.string.phone_invalid, Toast.LENGTH_SHORT).show();
        } else {
            /* Intent intentSignUp = new Intent(this, LoginActivity.class);
            intentSignUp.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(intentSignUp);
            //finish();*/
            PostUserProfiles postUserProfiles = new PostUserProfiles();
            postUserProfiles.execute(name, password, email, phone);
        }
    }

    /* Class that will be used for send the register data to the TimePiece API. It is made in a
     AsyncTask because this type of process need run in secondary threads for not to saturate the
     main thread, its params mean <Params, Progress Units, Results>
      */
    private class PostUserProfiles extends AsyncTask<String, Integer, Integer> {

        /* Method that is initialized in the main thread before entering the execution of the
        secondary thread
            @date[30/08/2017]
            @author[ChiSeng Ng]
            @param [Void]
            @return [void]
        */
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        /* Method that is initialized in the secondary thread, and will communicate with the
        TimePiece API and manage this connection.
            @date[30/08/2017]
            @author[ChiSeng Ng]
            @param [String...] strings All the string that entered like params.
            @return [Integer] Represent the result of the connection operation.
        */
        @Override
        protected Integer doInBackground(String... strings) {
            URL url;
            HttpURLConnection urlConnection;
            Integer result = -1;
            try {
                /* Note: Change the url for the deployment */
                url = new URL("http://192.168.1.110:8000/profiles/");
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setRequestMethod("POST");
                urlConnection.setConnectTimeout(10000);

                JSONObject user = new JSONObject();
                JSONObject profile = new JSONObject();
                user.put("first_name", strings[0]);
                user.put("password",strings[1]);
                user.put("username",strings[2]);
                user.put("email",strings[2]);
                profile.put("user_fk",user);
                profile.put("phone",strings[3]);

                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(profile.toString());
                writer.flush();
                APIResponse response = JSONResponseController.getJsonResponse(urlConnection, true);

                if (response != null) {
                    Log.w("RESPONSE BODY REQUEST",response.getBody().toString());
                    if (response.getStatus() == HttpURLConnection.HTTP_OK || response.getStatus() == HttpURLConnection.HTTP_CREATED) {
                        Log.d("OK", response.getBody().toString());
                        result = 0;
                    } else if (response.getStatus() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        Log.d("BAD", "BAD");
                        result = 1;
                    } else if (response.getStatus() == HttpURLConnection.HTTP_NOT_FOUND) {
                        Log.d("NOT", "FOUND");
                        result = -1;
                    }
                }
            } catch (Exception e) {
                return result;
            }
            return result;
        }

        /* Method that is initialized after the secondary thread finish it execution, and will
        process the doInBackground() method results.
            @date[30/08/2017]
            @author[ChiSeng Ng]
            @param [Integer] anInt Results passed from doInBackground() method.
            @return [Void]
        */
        @Override
        protected void onPostExecute(Integer anInt) {
            int message;
            switch (anInt) {
                case (-1):
                    message = R.string.connection_error;
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                    break;
                case (0):
                    message = R.string.user_created;
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                    finish();
                    break;
                case (1):
                    message = R.string.invalid_credentials;
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
            progressBar.setVisibility(View.GONE);
            //finish();
        }
    }

    /*  Method that take a string, and evaluate it, to determine if it is an email.
           @date[12/06/2017]
           @reference [https://stackoverflow.com/questions/24969894/android-email-validation-on-edittext]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [boolean] Return true if is a valid email, and false if not.
   */
    public static boolean isValidEmail(String target) {
        return !TextUtils.isEmpty(target) && Patterns.EMAIL_ADDRESS.matcher(target).matches();
    }

    /*  Method that take a string, and evaluate it, to determine if it is an password.
           @date[30/08/2017]
           @reference [https://stackoverflow.com/questions/24969894/android-email-validation-on-edittext]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [boolean] Return true if is a valid password, and false if not.
   */
    public static boolean isValidPassword(String target) {
        return !TextUtils.isEmpty(target) && (target.length() >= 8) && (target.length() <= 15);
    }

    /*  Method that take a string, and evaluate it, to determine if it is an phone.
           @date[30/08/2017]
           @reference [https://stackoverflow.com/questions/24969894/android-email-validation-on-edittext]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [boolean] Return true if is a valid phone, and false if not.
   */
    public static boolean isValidPhone(String target) {
        return !TextUtils.isEmpty(target) && Patterns.PHONE.matcher(target).matches();
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
