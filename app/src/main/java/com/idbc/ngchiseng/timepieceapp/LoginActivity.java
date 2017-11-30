package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

import static java.security.AccessController.getContext;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    /*
    This will declare the all widgets or elements of the Login screen.
     */
    private AutoCompleteTextView emailField;
    private TextInputEditText passwordField;
    private TextView forgotPassword,signUp;
    private Button logIn,logInFb;
    private ProgressBar progressBar;

    private LoginButton loginButtonFb;
    private CallbackManager callbackManager;
    AccessTokenTracker accessTokenTracker;
    AccessToken accessToken;

    /*  Method that will onCreate the login activity, link its component, and implements the
    onClickListener for receive the click request.
        @date[12/06/2017]
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
        setContentView(R.layout.activity_login);

        emailField = (AutoCompleteTextView) findViewById(R.id.login_email);
        passwordField = (TextInputEditText) findViewById(R.id.login_password);
        forgotPassword = (TextView) findViewById(R.id.login_forgot);
        signUp = (TextView) findViewById(R.id.login_signup);
        logIn = (Button) findViewById(R.id.login_button);
        logInFb = (Button) findViewById(R.id.login_fb);
        progressBar = (ProgressBar) findViewById(R.id.login_progressBar);

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
                Log.d("FB", "facebook:onSuccess:" + loginResult);
                Intent intentFb = new Intent(getBaseContext(), MainActivity.class);
                intentFb.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intentFb);
                //finish();
                //handleFacebookAccessToken(loginResult.getAccessToken());
            }
            @Override
            public void onCancel() {
                Log.d("FB", "facebook:onCancel");
                // ...
            }
            @Override
            public void onError(FacebookException error) {
                Log.d("FB", "facebook:onError", error);
                // ...
            }
        });
    }



    /*  Method that will redirect the login's result to the callbackManager created in the
    onCreate() method.
        @date[29/06/2017]
        @author[ChiSeng Ng]
        @param [int] requestCode Code of the request in the login event.
        @param [int] resultCode Code resulting in the login event's request.
        @param [Intent] data Intent with the data obtained in the succes login event's request.
        @return [void]
    */
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

                String email, password;

                /* This block will handler each value obtained in the fields with the variable corresponding
                in a string format
                */
                email = emailField.getText().toString();
                password = passwordField.getText().toString();

                if (!(isValidEmail(email))) {
                    Toast.makeText(view.getContext(), R.string.email_invalid , Toast.LENGTH_LONG).show();
                } else if (!(isValidPassword(password))) {
                    Toast.makeText(view.getContext(), R.string.password_invalid, Toast.LENGTH_SHORT).show();
                } else {
                    /* Intent intentLogin = new Intent(this, MainActivity.class);
                    intentLogin.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(intentLogin);
                    //finish();*/
                    AttemptLogin attemptLogin = new AttemptLogin();
                    attemptLogin.execute(email, password);
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
                SharedPreferences.Editor editor = getSharedPreferences("session", 0).edit();
                editor.putBoolean("logged",true);
                editor.putInt("id", 2);
                editor.putInt("announces", 4);
                editor.putInt("donations", 0);
                editor.putInt("purchases", 2);
                editor.putString("token","123456789");
                editor.putString("username","Chiseng Ng Yu");
                editor.putString("email","ngyu1101@hotmail.com");
                editor.putString("first_name","Chiseng");
                editor.putString("phone","04125634975");
                editor.putString("address","Caracas, Venezuela");
                editor.apply();
                progressBar.setVisibility(View.VISIBLE);
                loginButtonFb.performClick();
                break;

            default:
                break;
        }
    }

    /*--------------------------------------------------------------------------------------------*/
    /* Class that will be used for send Log In's data to the server's API and process the response.
    It is made in a AsyncTask because this type of process need run in secondary threads for not to
    saturate the main thread, its params mean <Params, Progress Units, Results>
     */
    public class AttemptLogin extends AsyncTask<String, Integer, Integer> {

        /* Method that is initialized in the main thread before entering the execution of the
        secondary thread
            @date[31/08/2017]
            @author[ChiSeng Ng]
            @param [Void]
            @return [void]
        */
        @Override
        protected void onPreExecute() {
            progressBar.setVisibility(View.VISIBLE);
        }

        /* Method that is initialized in the secondary thread, and will communicate with the
        TimePiece API and manage this connection. In this function is to send validated Log In's
        data to the server's API and process the response. Returns an integer value ([-1..1):
        * -1, if an error occurred during the communication.
        * 0, if everything went OK (redirecting to MainActivity and updating SharedPreferences afterwards).
        * 1, if the credentials provided aren't valid.
            @date[31/08/2017]
            @author[ChiSeng Ng]
            @param [String...] strings All the string that entered like params.
            @return [Integer] Represent the result of the connection operation.
        */
        @Override
        protected Integer doInBackground(String... strings) {

            Integer result = 0;
            try {
                // Defining and initializing server's communication's variables
                String credentials = URLEncoder.encode("username", "UTF-8") + "=" + URLEncoder.encode(strings[0], "UTF-8");
                credentials += "&" + URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(strings[1], "UTF-8");

                URL url = new URL("http://192.168.1.110:8000/api-login/");
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoOutput(true);
                connection.setRequestMethod("POST");
                connection.setConnectTimeout(1700);

                OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream());
                writer.write(credentials);
                writer.flush();
                /* This get the JSON object from the response urlConnection and save it the
                "response" variable. In this case the true value is because the value received is a
                JSON object, if received a JSON array object then is false.
                */
                APIResponse response = JSONResponseController.getJsonResponse(connection,true);

                if (response != null){

                    if (response.getStatus()==HttpURLConnection.HTTP_OK) {
                        JSONObject response_body = response.getBody();

                        /* This will create the session SharedPreferences with the data corresponding
                        Note: Lack to analyze if is better to include its on TimePieceSharePreferences
                        class.
                        */
                        SharedPreferences.Editor editor = getSharedPreferences("session", 0).edit();
                        editor.putBoolean("logged",true);
                        editor.putInt("id",response_body.getInt("id"));
                        editor.putString("token",response_body.getString("token"));
                        editor.putString("username",response_body.getString("username"));
                        editor.putString("email",response_body.getString("email"));
                        editor.putString("first_name",response_body.getString("first_name"));
                        editor.putString("phone",response_body.getString("phone"));
                        editor.putString("address",response_body.getString("address"));
                        editor.apply();

                        Log.d("OK", "OK");
                        return 0;

                    } else if (response.getStatus() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        Log.d("BAD", "BAD");
                        result = 1;
                    } else {
                        Log.d("NOT", "FOUND");
                        result = -1;
                    }
                }

            } catch (MalformedURLException e) {
                return result;
            } catch (IOException e) {
                return result;
            } catch (JSONException e) {
                return result;
            }
            return result;
        }

        /* Method that is initialized after the secondary thread finish it execution, and will
        process the doInBackground() method results.
            @date[01/09/2017]
            @author[ChiSeng Ng]
            @param [Integer] anInt Results passed from doInBackground() method.
            @return [Void]
        */
        @Override
        protected void onPostExecute(Integer anInt) {
            switch (anInt) {
                case (-1):
                    Toast.makeText(getBaseContext(), R.string.connection_error, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    break;
                case (0):
                    Intent intent = new Intent(getBaseContext(), MainActivity.class);
                    Toast.makeText(getBaseContext(), R.string.welcome, Toast.LENGTH_SHORT).show();
                    /* This will create the session SharedPreferences with the data corresponding
                        Note: Lack to analyze if is better to include its on TimePieceSharePreferences
                        class.
                        */
                    SharedPreferences.Editor editor = getSharedPreferences("session", 0).edit();
                    editor.putBoolean("logged",true);
                    editor.putInt("id",1);
                    editor.putInt("announces", 0);
                    editor.putInt("donations", 0);
                    editor.putInt("purchases", 3);
                    editor.putString("token","123456789");
                    editor.apply();
                    startActivity(intent);
                    finish();
                    progressBar.setVisibility(View.GONE);
                    break;
                case (1):
                    Toast.makeText(getBaseContext(), R.string.email_password_invalid, Toast.LENGTH_SHORT).show();
                    progressBar.setVisibility(View.GONE);
                    break;
                default:
                    break;
            }
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

    /*  Method that take a string, and evaluate it, to determine if it is an password.
           @date[31/08/2017]
           @reference [https://stackoverflow.com/questions/24969894/android-email-validation-on-edittext]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [boolean] Return true if is a valid password, and false if not.
   */
    public static boolean isValidPassword(String target) {
        return !TextUtils.isEmpty(target) && (target.length() >= 8) && (target.length() <= 15);
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
