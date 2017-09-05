package com.idbc.ngchiseng.timepieceapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import uk.co.chrisjenx.calligraphy.CalligraphyConfig;
import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class ProfileEditActivity extends AppCompatActivity implements View.OnClickListener {

    /* This will define the variables that will use in the creation of the product */
    private ImageView profileImage;
    private EditText profileName, profilePassword, profileEmail, profilePhone, profileAddress;
    private Button saveBtn;
    private ProgressBar profileProgressBar;
    SharedPreferences pref_session;
    int id;
    String name, email, phone, address, password, image_base64;

    Bitmap thumbnail;
    private static final int REQUEST_CAMERA = 0;
    private static final int SELECT_FILE = 1;

    /*  Method that will onCreate of the ProfileEdit activity, link its component, and implements the
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

        setContentView(R.layout.activity_profile_edit);

        /* This will manage the toolbar configuration and addressing. */
        Toolbar toolbar = (Toolbar) findViewById(R.id.my_toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(this);

        /* This will handler the components corresponding of the layout with the variables defined. */
        profileImage = (ImageView) findViewById(R.id.edit_profile_image);
        profileName = (EditText) findViewById(R.id.edit_profile_name);
        profilePassword = (EditText) findViewById(R.id.edit_profile_password);
        profileEmail = (EditText) findViewById(R.id.edit_profile_email);
        profilePhone = (EditText) findViewById(R.id.edit_profile_phone);
        profileAddress = (EditText) findViewById(R.id.edit_profile_address);
        saveBtn = (Button) findViewById(R.id.edit_profile_save);
        profileProgressBar = (ProgressBar) findViewById(R.id.edit_profile_progressBar);

        /* This will obtain the session SharedPreferences values */
        pref_session = this.getSharedPreferences("session", 0);
        id = pref_session.getInt("id", 0);
        Log.d("Este es el id: ", String.valueOf(id));
        name = pref_session.getString("first_name", null);
        email = pref_session.getString("email", null);
        phone = pref_session.getString("phone", null);
        address = pref_session.getString("address", null);
        final String image = pref_session.getString("image", null);

        /* This will handler each value of the session SharedPreference with the screen component
        corresponding.
        */
        profileName.setText(name);
        profileEmail.setText(email);
        profilePhone.setText(phone);
        profileAddress.setText(address);
        if (image != null) {
            Uri photo = Uri.parse(image);
            Picasso.with(getBaseContext()).load(photo).into(profileImage);
        }

        profileImage.setOnClickListener(this);
        saveBtn.setOnClickListener(this);
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
        if (v.getId() == R.id.edit_profile_save) {

            /* This block will handler each value obtained in the fields with the variable corresponding
            in a string format
            */
            name = profileName.getText().toString();
            password = profilePassword.getText().toString();
            email = profileEmail.getText().toString();
            phone = profilePhone.getText().toString();
            address = profileAddress.getText().toString();

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
            } else if (!(isValidAddress(address))) {
                Toast.makeText(v.getContext(), R.string.address_invalid, Toast.LENGTH_SHORT).show();
            } else {

                PutUserProfile putUserProfile = new PutUserProfile();

                /* This will evaluate if the thumbnail content a Bitmap of the image that was uploaded
                or taken by the user, cast it to a BaseCode64 format and call the PutUserProfiles
                class with its. In case, the user never uploaded or taken a photo then call the
                PutUserProfiles class with a null value.
                */
                if (thumbnail != null) {
                    ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                    thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
                    byte[] byte_array = bytes.toByteArray();
                    image_base64 = Base64.encodeToString(byte_array, 0);
                    putUserProfile.execute(name, password, email, phone, address, image_base64);
                } else {
                    putUserProfile.execute(name, password, email, phone, address, null);
                }
            }
            //Toast.makeText(this, "Click Save Data", Toast.LENGTH_LONG).show();

        } else if (v.getId() == R.id.edit_profile_image) {
            // Declaration of the alert dialog
            AlertDialog.Builder Selector = new AlertDialog.Builder(this, R.style.ListAlertDialogStyle);
            Selector.setTitle(R.string.upload);
            String [] optionsArray = { getString(R.string.camera), getString(R.string.gallery) };
            // This will handler the behavior corresponding to each option.
            Selector.setItems(optionsArray, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    switch (which) {
                        case 0:
                            cameraIntent();
                            break;
                        case 1:
                            galleryIntent();
                            break;
                    }
                }
            });
            // This will define and handler the cancel option.
            Selector.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.cancel();
                }
            });
            Selector.create().show();

        } else {
            super.onBackPressed();
        }
    }

    /*  Method that use for call the intent of the device camera activity and execute its.
           @date[03/08/2017]
           @reference [https://developer.android.com/training/camera/photobasics.html]
           @author[ChiSeng Ng]
           @param [None]
           @return [void]
   */
    private void cameraIntent() {

        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        // Handle the returned image in the Activity "onActivityResult" method.
        startActivityForResult(takePictureIntent, REQUEST_CAMERA);
    }

    /*  Method that use for call the intent of the device gallery activity and execute its.
          @date[03/08/2017]
          @author[ChiSeng Ng]
          @param [None]
          @return [void]
  */
    private void galleryIntent() {

        // Take the user to their chosen image selection app (gallery or file manager)
        Intent pickIntent = new Intent();
        // Select  Android image gallery or a file manager application(the first in this case).
        pickIntent.setType("image/*");
        // By specifying "ACTION_GET_CONTENT", we are instructing the app to return whatever the user selects.
        pickIntent.setAction(Intent.ACTION_GET_CONTENT);
        // handle the returned image in the Activity "onActivityResult" method.
        startActivityForResult(Intent.createChooser(pickIntent, "Select File"), SELECT_FILE);
    }

    /*  Method that receive the photo encoded into the data intent as a small Bitmap in extras,
    under the key data, and finally call the methods corresponding.
          @date[03/08/2017]
          @author[ChiSeng Ng]
          @param [int] requestCode Integer that identify if the operation was a capture or pick
          photo.
          @param [int] resultCode Integer that identify the state of the capture or pick photo
          operation.
          @return [void]
    */
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {
            if (requestCode == SELECT_FILE)
                onSelectFromGalleryResult(data);
            else if (requestCode == REQUEST_CAMERA)
                onCaptureImageResult(data);
        }
    }

    /*  Method that receive the photo encoded into the data intent as a small Bitmap in extras,
    under the key data, and processes it to show them in the screen, this case was capture by the
    camera.
          @date[03/08/2017]
          @author[ChiSeng Ng]
          @param [Intent] data Intent with the photo encoded in a small Bitmap in extras, under the
          key data.
          @return [void]
    */
    private void onCaptureImageResult(Intent data) {
        thumbnail = (Bitmap) data.getExtras().get("data");
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        assert thumbnail != null;
        thumbnail.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        profileImage.setImageBitmap(thumbnail);
    }

    /*  Method that receive the photo encoded into the data intent as a small Bitmap in extras,
    under the key data, and processes it to show them in the screen, this case was obtain in the
    gallery.
          @date[03/08/2017]
          @author[ChiSeng Ng]
          @param [Intent] data Intent with the photo encoded in a small Bitmap in extras, under the
          key data.
          @return [void]
    */
    private void onSelectFromGalleryResult(Intent data) {

        if (data != null) {
            try {
                thumbnail = MediaStore.Images.Media.getBitmap(getApplicationContext().getContentResolver(), data.getData());
                profileImage.setImageBitmap(thumbnail);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**********************************************************************************************/
    /* Class that will be used for send the profile update data to the TimePiece API. It is made in a
     AsyncTask because this type of process need run in secondary threads for not to saturate the
     main thread, its params mean <Params, Progress Units, Results>
    */
    private class PutUserProfile extends AsyncTask<String, Integer, Integer> {

        /* Method that is initialized in the main thread before entering the execution of the
        secondary thread
            @date[04/09/2017]
            @author[ChiSeng Ng]
            @param [Void]
            @return [void]
        */
        @Override
        protected void onPreExecute() {
            profileProgressBar.setVisibility(View.VISIBLE);
        }

        /* Method that is initialized in the secondary thread, and will communicate with the
        TimePiece API and manage this connection.
            @date[04/09/2017]
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

                url = new URL("http://192.168.1.110:8000/profiles/"+id);
                Log.d("La URL es", String.valueOf(url));
                urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);
                urlConnection.setRequestMethod("PUT");

                JSONObject user = new JSONObject();
                JSONObject profile = new JSONObject();

                user.put("username", email);
                user.put("password", strings[1]);
                user.put("email", strings[2]);

                profile.put("user_fk", user);
                profile.put("first_name", strings[0]);
                profile.put("phone", strings[3]);
                profile.put("address", strings[4]);
                if (strings[5]!= null)
                    profile.put("image_profile",strings[5]);

                OutputStreamWriter writer = new OutputStreamWriter(urlConnection.getOutputStream());
                writer.write(profile.toString());
                writer.flush();

                APIResponse response = JSONResponseController.getJsonResponse(urlConnection, true);

                if (response != null) {
                    if (response.getStatus() == HttpURLConnection.HTTP_OK || response.getStatus() == HttpURLConnection.HTTP_CREATED) {
                        Log.d("OK", response.getBody().toString());
                        result = 0;
                    } else if (response.getStatus() == HttpURLConnection.HTTP_BAD_REQUEST) {
                        Log.d("BAD", "BAD");
                        if (response.getBody().getString("code").equals("email"))
                            result = 1;
                        else
                            result = 2;
                    } else if (response.getStatus() == HttpURLConnection.HTTP_NOT_FOUND) {
                        Log.d("NOT", "FOUND");
                        Log.d("OK", response.getBody().toString());
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
            @date[04/09/2017]
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
                    message = R.string.user_updated;
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                    /* This finish the activity and return to the previous screen */
                    finish();
                    break;
                case (1):
                    message = R.string.email_invalid;
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                    break;
                case (2):
                    message = R.string.password_invalid;
                    Toast.makeText(getBaseContext(), message, Toast.LENGTH_LONG).show();
                    break;
                default:
                    break;
            }
            profileProgressBar.setVisibility(View.GONE);
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

    /*  Method that take a string, and evaluate it, to determine if it is an valid Address.
           @date[04/09/2017]
           @reference [https://stackoverflow.com/questions/24969894/android-email-validation-on-edittext]
           @author[ChiSeng Ng]
           @param [View] view View or widget that represent the button corresponding in this context.
           @return [boolean] Return true if is a valid phone, and false if not.
   */
    public static boolean isValidAddress(String target) {
        return !TextUtils.isEmpty(target) && (target.length() >= 8) && (target.length() <= 128);
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
